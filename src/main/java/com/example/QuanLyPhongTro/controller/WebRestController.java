package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.config.VNPayConfig;
import com.example.QuanLyPhongTro.jwt.JwtTokenProvider;
import com.example.QuanLyPhongTro.models.CustomUserDetails;
import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.payload.LoginRequest;
import com.example.QuanLyPhongTro.payload.LoginResponse;
import com.example.QuanLyPhongTro.payload.RandomStuff;
import com.example.QuanLyPhongTro.payload.RegisterRequest;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class WebRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

//    @PostMapping("/register")
//    public String registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
//        // Kiểm tra xem tên người dùng đã tồn tại hay chưa
//        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
//            return "Tên người dùng đã tồn tại!";
//        }
//
//        // Kiểm tra thông tin thanh toán
//        if (registerRequest.getPaymentInfo() == null || !registerRequest.getPaymentInfo().isSuccessful()) {
//            return "Thanh toán không thành công!";
//        }
//
//        // Xác thực ID giao dịch
//        String transactionId = registerRequest.getPaymentInfo().getTransactionId();
//        if (!verifyVNPayTransaction(transactionId)) {
//            return "Thanh toán không thành công!";
//        }
//
//        // Tạo mới người dùng
//        Users user = new Users();
//        user.setUsername(registerRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        user.setEmail(registerRequest.getEmail());
//        user.setPhoneNumber(registerRequest.getPhoneNumber());
//
//        // Lưu người dùng vào database
//        userRepository.save(user);
//        return "Đăng ký thành công!";
//    }

    public boolean verifyVNPayTransaction(String transactionId) {
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String vnp_HashSecret = VNPayConfig.secretKey; // Khóa bí mật của bạn
        String vnp_PayUrl = VNPayConfig.vnp_PayUrl; // URL của VNPay

        // Tạo tham số yêu cầu
        Map<String, String> params = new HashMap<>();
        params.put("vnp_TmnCode", vnp_TmnCode);
        params.put("vnp_TransactionId", transactionId);
        params.put("vnp_RequestId", UUID.randomUUID().toString()); // ID yêu cầu ngẫu nhiên
        params.put("vnp_Version", "2.0.0");
        params.put("vnp_Command", "querydr");

        // Tạo chữ ký
        StringBuilder hashData = new StringBuilder();
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                hashData.append('&');
            }
        }
        String vnpSecureHash = VNPayConfig.hmacSHA512(vnp_HashSecret, hashData.toString());

        // Gửi yêu cầu đến VNPay
        String requestUrl = vnp_PayUrl + "?vnp_Version=2.0.0&vnp_TmnCode=" + vnp_TmnCode
                + "&vnp_TransactionId=" + transactionId
                + "&vnp_RequestId=" + params.get("vnp_RequestId")
                + "&vnp_SecureHash=" + vnpSecureHash;

        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(requestUrl, String.class);
            // Xử lý phản hồi từ VNPay
            return response.contains("00"); // Kiểm tra mã phản hồi
        } catch (HttpClientErrorException e) {
            // Xử lý lỗi khi gọi API
            e.printStackTrace();
            return false;
        }
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }

}

