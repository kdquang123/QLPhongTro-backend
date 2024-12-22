package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.dto.AdvertisementDTO;
import com.example.QuanLyPhongTro.dto.PageDTO;
import com.example.QuanLyPhongTro.dto.SupportRequestDTO;
import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.repositories.SupportRequestsRepository;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.SupportRequests;
import com.example.QuanLyPhongTro.services.SupportRequestsService;

import java.util.List;

@RestController
@RequestMapping("/support-requests")
public class SupportRequestsController {

    @Autowired
    private SupportRequestsService _supportRequestsService;
    @Autowired
    private SupportRequestsRepository _supportRequestRepository;

    // Lấy tất cả các yêu cầu hỗ trợ
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<SupportRequests> getAllSupportRequests() {
        return _supportRequestsService.getAllSupportRequests();
    }

    // Lấy yêu cầu hỗ trợ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SupportRequests> getSupportRequestById(@PathVariable int id) {
        SupportRequests supportRequest = _supportRequestsService.getSupportRequestById(id);
        if (supportRequest != null) {
            return ResponseEntity.ok(supportRequest);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy danh sách yêu cầu hỗ trợ với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách yêu cầu hỗ trợ với phân trang
    public Page<SupportRequests> getSupportRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _supportRequestsService.getSupportRequests(page, size);
    }

    // Thêm yêu cầu hỗ trợ mới
    @PostMapping("")
    public SupportRequests addSupportRequest(@RequestBody SupportRequests supportRequest) {
        return _supportRequestsService.addSupportRequest(supportRequest);
    }

    // Cập nhật yêu cầu hỗ trợ theo ID
    @PutMapping("/{id}")
    public ResponseEntity<SupportRequests> updateSupportRequest(@PathVariable int id, @RequestBody SupportRequests supportRequestDetails) {
        SupportRequests updatedSupportRequest = _supportRequestsService.updateSupportRequest(id, supportRequestDetails);
        if (updatedSupportRequest != null) {
            return ResponseEntity.ok(updatedSupportRequest);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa yêu cầu hỗ trợ theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportRequest(@PathVariable int id) {
        if (_supportRequestsService.deleteSupportRequest(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //Phuc test
    // Lấy danh sách request chưa được trả lời (cho admin)
    @GetMapping("/pending")
    public List<SupportRequests> getPendingRequests() {
        return _supportRequestsService.getPendingRequests();
    }

    // Admin trả lời request
    @PostMapping("/{id}/reply")
    public SupportRequests replyToRequest(@PathVariable int id, @RequestBody String replyContent) {
        return _supportRequestsService.replyToRequest(id, replyContent);
    }

    // User lấy tất cả các request của họ
    @GetMapping("/user/{userId}")
    public List<SupportRequests> getUserRequests(@PathVariable int userId) {
        return _supportRequestsService.getAllSupportRequests() // Hoặc thêm filter ở đây
                .stream()
                .filter(request -> request.getUser().getId() == userId)
                .toList();
    }

    // Lấy thông tin user của request
    @GetMapping("/{supportRequestId}/user")
    public ResponseEntity<Users> getUserBySupportRequestId(@PathVariable Integer supportRequestId) {
        Users user = _supportRequestsService.getUserBySupportRequestId(supportRequestId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy
    }
    @PutMapping("/change-status/{id}")
    public ResponseEntity<SupportRequests> changeUserStatus(@PathVariable int id, @RequestBody SupportRequests supportRequests) {
        SupportRequests updateSupportRequests = _supportRequestRepository.findById(id).orElse(null);
        if (updateSupportRequests != null) {
            updateSupportRequests.setStatus(supportRequests.getStatus());
            _supportRequestRepository.save(updateSupportRequests);
            return ResponseEntity.ok(supportRequests);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/change-reply/{id}")
    public ResponseEntity<SupportRequests> changeAdminReply(@PathVariable int id, @RequestBody SupportRequests supportRequests) {
        SupportRequests updateSupportRequests = _supportRequestRepository.findById(id).orElse(null);
        if (updateSupportRequests != null) {
            updateSupportRequests.setAdminReply(supportRequests.getAdminReply());
            _supportRequestRepository.save(updateSupportRequests);
            return ResponseEntity.ok(supportRequests);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/admin/get")
    public ResponseEntity<PageDTO<SupportRequestDTO>> getAdvertisementsByAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        // Gọi service với các tham số lọc
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SupportRequestDTO> supportrequest = _supportRequestsService.getSupportRequestByAmin(pageRequest);

        return ResponseEntity.ok(new PageDTO<SupportRequestDTO>(supportrequest));
    }
}