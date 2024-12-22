package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.dto.PaymentRestDTO;
import com.example.QuanLyPhongTro.dto.TransactionStatusDTO;
import com.example.QuanLyPhongTro.config.VNPayConfig;
import com.example.QuanLyPhongTro.models.ServicePackages;
import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.payload.RegisterRequest;
import com.example.QuanLyPhongTro.repositories.ServicePackagesRepository;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class VNPayController {
    @Autowired
    private UsersRepository _userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ServicePackagesRepository _servicePackagesRepository;

    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(HttpServletRequest req,@RequestBody RegisterRequest request) throws UnsupportedEncodingException {
        System.out.println(request.getPrice()+"aaa");
        //nhập cái giá gói dịch vụ vào request
        long amount =(long)(request.getPrice()*100);
        //nhập ngân hàng muốn chuyển vào request
        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", Base64.getEncoder().encodeToString((request.getUsername()+"|"+request.getPassword()+"|"+request.getFullName()+"|"+request.getEmail()+"|"+request.getPhoneNumber()+"|"+request.getIdService()).getBytes()));
        vnp_Params.put("vnp_OrderType", VNPayConfig.orderType);
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

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

        PaymentRestDTO paymentRestDTO = new PaymentRestDTO();
        paymentRestDTO.setStatus("Ok");
        paymentRestDTO.setMessage("Successfully");
        paymentRestDTO.setURL(paymentUrl);
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
        return ResponseEntity.status(HttpStatus.OK).body(paymentRestDTO);
    }

    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
            HttpServletRequest request,
            HttpServletResponse response,
//            @RequestParam(value = "vnp_Amount") String amount,
//            @RequestParam(value = "vnp_BankCode") String bankcode,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode
    ) throws IOException {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
//        // Tạo đối tượng JSON trả về
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", "Ok");
//        response.put("message", "Successfully");
//        response.put("data", params);
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        String orderInfo = new String(Base64.getDecoder().decode(order));
        String[] userInfo = orderInfo.split("\\|");
        String userName = userInfo[0];
        String password = userInfo[1];
        String fullName = userInfo[2];
        String email = userInfo[3];
        String phoneNumber = userInfo[4];
        int idService = Integer.parseInt(userInfo[5]);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(userName);
        registerRequest.setPassword(password);
        registerRequest.setFullName(fullName);
        registerRequest.setEmail(email);
        registerRequest.setPhoneNumber(phoneNumber);
        registerRequest.setIdService(idService);
        if (responseCode.equals("00")){
            Users user = new Users();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setFullName(registerRequest.getFullName());
            user.setEmail(registerRequest.getEmail());
            user.setPhoneNumber(registerRequest.getPhoneNumber());
            ServicePackages sv = _servicePackagesRepository.findById(idService).orElse(null);
            user.setServicePackage(sv);
            user.setStatus(1);
//            String dateString = LocalDate.now().toString();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = format.parse(dateString);
//            user.setCreatedAt(date);
            _userRepository.save(user);
            response.sendRedirect("http://localhost:3000/successful");
        }else{
            response.sendRedirect("http://localhost:3000/failure");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
