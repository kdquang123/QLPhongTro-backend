package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Tenants;
import com.example.QuanLyPhongTro.services.TenantsService;

import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantsController {

    @Autowired
    private TenantsService _tenantsService;

    // Lấy tất cả người thuê
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Tenants> getAllTenants() {
        return _tenantsService.getAllTenants();
    }

    // Lấy người thuê theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Tenants> getTenantById(@PathVariable int id) {
        Tenants tenant = _tenantsService.getTenantById(id);
        if (tenant != null) {
            return ResponseEntity.ok(tenant);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy danh sách người thuê với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách người thuê với phân trang
    public Page<Tenants> getTenants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _tenantsService.getTenants(page, size);
    }

    // Thêm người thuê mới
    @PostMapping("")
    public Tenants addTenant(@RequestBody Tenants tenant) {
        return _tenantsService.addTenant(tenant);
    }

    // Cập nhật người thuê theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Tenants> updateTenant(@PathVariable int id, @RequestBody Tenants tenantDetails) {
        Tenants updatedTenant = _tenantsService.updateTenant(id, tenantDetails);
        if (updatedTenant != null) {
            return ResponseEntity.ok(updatedTenant);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa người thuê theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable int id) {
        if (_tenantsService.deleteTenant(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Tenants>> getTenantsByRoomId(@PathVariable int roomId) {
        List<Tenants> tenants = _tenantsService.getTenantsByRoomId(roomId);
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/count/{roomId}")
    public ResponseEntity<Integer> getTenantCountByRoomId(@PathVariable int roomId) {
        int count = _tenantsService.getTenantsByRoomId(roomId).size();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<Tenants>> getTenantsByHouseId(@PathVariable int houseId) {
        List<Tenants> tenants = _tenantsService.getTenantsByHouseId(houseId);
        if (tenants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tenants);
    }
}