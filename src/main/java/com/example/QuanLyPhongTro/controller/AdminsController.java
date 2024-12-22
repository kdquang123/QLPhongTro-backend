package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.models.Admins;
import com.example.QuanLyPhongTro.services.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminsController {
    @Autowired
    private AdminsService _adminsService;

    // Phương thức để lấy tất cả admin
    @GetMapping("/all")
    public List<Admins> getAllAdmins() {
        return _adminsService.getAllAdmins();
    }

    // Phương thức để phân trang admin
    @GetMapping("")
    public Page<Admins> getAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _adminsService.getAdmins(page, size);
    }

    // Phương thức để tìm admin theo ID
    @GetMapping("/{id}")
    public Admins findById(@PathVariable int id) {
        return _adminsService.getAdminById(id);
    }

    // Phương thức để thêm admin mới
    @PostMapping("")
    public Admins addAdmin(@Valid @RequestBody Admins admin) {
        return _adminsService.addAdmin(admin);
    }

    // Phương thức để cập nhật thông tin admin
    @PutMapping("/{id}")
    public ResponseEntity<Admins> updateAdmin(@PathVariable int id, @RequestBody Admins adminDetails) {
        Admins updatedAdmin = _adminsService.updateAdmin(id, adminDetails);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    // Phương thức để xóa admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        if (_adminsService.deleteAdmin(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}