package com.example.QuanLyPhongTro;

import com.example.QuanLyPhongTro.models.Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.services.AdminsService;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private AdminsService _adminsService;

    @GetMapping("/getall")
    public List<Admins> getAllAdmins() {
        return _adminsService.getAllAdmins();
    }

    @GetMapping("/find/{id}")
    public String sayHello() {
        return _adminsService.getAdminById(1).getUsername();
    }

    @PostMapping("/add")
    public Admins addAdmin(@RequestBody Admins admin) {
        return _adminsService.addAdmin(admin);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admins> updateAdmin(@PathVariable int id, @RequestBody Admins adminDetails) {
        Admins updatedAdmin = _adminsService.updateAdmin(id, adminDetails);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        if (_adminsService.deleteAdmin(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
