package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.config.VNPayConfig;
import com.example.QuanLyPhongTro.models.Rooms;
import com.example.QuanLyPhongTro.payload.InvoiceRequest;
import com.example.QuanLyPhongTro.services.EmailService;
import com.example.QuanLyPhongTro.services.RoomsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Invoices;
import com.example.QuanLyPhongTro.services.InvoicesService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {

    @Autowired
    private InvoicesService _invoicesService;

    @Autowired
    private RoomsService _roomsService;

    @Autowired(required = false)
    private EmailService emailService;

    // Lấy tất cả hóa đơn
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Invoices> getAllInvoices() {
        return _invoicesService.getAllInvoices();
    }

    // Lấy hóa đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Invoices> getInvoiceById(@PathVariable int id) {
        Invoices invoice = _invoicesService.getInvoiceById(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy hóa đơn với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách hóa đơn với phân trang
    public Page<Invoices> getInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _invoicesService.getInvoices(page, size);
    }

    // Thêm hóa đơn mới
    @PostMapping("")
    public Invoices addInvoice(@RequestBody Invoices invoice) {
        return _invoicesService.addInvoice(invoice);
    }

    // Cập nhật hóa đơn theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Invoices> updateInvoice(@PathVariable int id, @RequestBody Invoices invoiceDetails) {
        Invoices updatedInvoice = _invoicesService.updateInvoice(id, invoiceDetails);
        if (updatedInvoice != null) {
            return ResponseEntity.ok(updatedInvoice);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa hóa đơn theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int id) {
        if (_invoicesService.deleteInvoice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceRequest request, HttpServletRequest req) {
        // Tạo hóa đơn mới với thông tin từ yêu cầu
        Rooms room = _roomsService.getRoomById(request.getRoomId());

        if (room == null) {
            return ResponseEntity.badRequest().body("Phòng không tồn tại.");
        }

        Invoices invoice = _invoicesService.createInvoice(request.getTotal(), room); // Tạo hóa đơn

        String paymentLink = createPaymentLink(invoice, request, req); // Tạo link thanh toán

        // Tạo thông điệp gửi tới người dùng
        String message = request.getMessage() + "\nVui long thanh toan hoa don cua ban tai: " + paymentLink;
        emailService.sendEmail(request.getEmail(), "Hóa Đơn Thanh Toán", message); // Gửi email

        return ResponseEntity.ok("Hóa đơn đã được tạo và gửi đến email của bạn.");
    }

    private String createPaymentLink(Invoices invoice, InvoiceRequest request, HttpServletRequest req) {
        System.out.println(invoice.getId());
        long amount = (long)(request.getTotal() * 100); // Chuyển đổi sang đơn vị tiền tệ (VND)
        String bankCode = req.getParameter("bankCode"); // Nhập ngân hàng muốn chuyển vào
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8); // Tạo số tham chiếu giao dịch
        String vnp_IpAddr = VNPayConfig.getIpAddress(req); // Địa chỉ IP của người dùng
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode; // Mã đối tác

        // Thiết lập các tham số cần thiết
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount)); // Số tiền
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        } else {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        // Thông tin đơn hàng
        String orderInfo = String.format("%s|%s|%s|%s|%s",
                invoice.getId(),
                request.getTotal(),
                request.getMessage(),
                request.getRoomId(),
                request.getEmail());
        vnp_Params.put("vnp_OrderInfo", Base64.getEncoder().encodeToString(orderInfo.getBytes()));
        vnp_Params.put("vnp_OrderType", VNPayConfig.orderType); // Loại đơn hàng

        // Ngôn ngữ
        String locate = req.getParameter("language");
        vnp_Params.put("vnp_Locale", (locate != null && !locate.isEmpty()) ? locate : "vn");

        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/invoices/payment_infor");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Thời gian tạo
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Thời gian hết hạn (nếu cần)
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo chuỗi hash và URL
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl; // Trả về URL thanh toán đầy đủ
    }

    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
            HttpServletRequest request,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode
    ) {
        try {
            System.out.println("Received Order Info: " + order); // Log giá trị nhận được
            System.out.println("Received Response Code: " + responseCode); // Log mã phản hồi

            // Giải mã thông tin đơn hàng
            String decodedOrderInfo = new String(Base64.getDecoder().decode(order));
            System.out.println("Decoded Order Info: " + decodedOrderInfo); // Kiểm tra giá trị đã giải mã

            // Tách thông tin cần thiết
            String[] orderDetails = decodedOrderInfo.split("\\|");
            System.out.println("Order Details Length: " + orderDetails.length); // Kiểm tra số lượng phần tử

            if (orderDetails.length > 0) {
                try {
                    int invoiceId = Integer.parseInt(orderDetails[0]); // Lấy ID hóa đơn từ thông tin
                    System.out.println("Invoice ID: " + invoiceId); // Kiểm tra ID hóa đơn

                    // Kiểm tra mã phản hồi
                    if ("00".equals(responseCode)) {
                        // Thanh toán thành công, cập nhật trạng thái hóa đơn
                        Invoices invoice = _invoicesService.getInvoiceById(invoiceId);
                        if (invoice != null) {
                            invoice.setStatus(1); // 1: đã thanh toán
                            _invoicesService.updateInvoice(invoice); // Cập nhật hóa đơn
                            return ResponseEntity.status(HttpStatus.OK).body("Thanh toán thành công!");
                        } else {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóa đơn không tồn tại!");
                        }
                    } else {
                        // Thanh toán không thành công
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán không thành công!");
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID hóa đơn không hợp lệ!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin đơn hàng không hợp lệ!");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin đơn hàng không hợp lệ!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }
}