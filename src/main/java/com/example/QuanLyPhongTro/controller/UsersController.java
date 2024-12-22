package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.models.Advertisements;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.services.UsersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService _usersService;
    @Autowired
    private UsersRepository _usersRepository;

    // Lấy tất cả người dùng
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Users> getAllUsers() {
        return _usersService.getAllUsers();
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        Users user = _usersService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }



    // Lấy người dùng theo ID
    @GetMapping("/get-by-name")
    public ResponseEntity<String> getUserById(@RequestParam("username") String username) {
        Users user = _usersRepository.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok("Đã tồn tại");
        }
        return ResponseEntity.notFound().build();
    }



    // Lấy danh sách người dùng với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách người dùng với phân trang
    public Page<Users> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _usersService.getUsers(page, size);
    }

    // Thêm người dùng mới
    @PostMapping("")
    public Users addUser(@Valid @RequestBody Users user) {
        return _usersService.addUser(user);
    }

    // Cập nhật người dùng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users userDetails) {
        Users updatedUser = _usersService.updateUser(id, userDetails);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa người dùng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (_usersService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<Users> changeUserStatus(@PathVariable int id, @RequestBody Users user) {
        Users updateUser = _usersRepository.findById(id).orElse(null);
        if (updateUser != null) {
            updateUser.setStatus(user.getStatus());
            _usersRepository.save(updateUser);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}