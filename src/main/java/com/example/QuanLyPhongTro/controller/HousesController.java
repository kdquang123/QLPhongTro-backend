package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Houses;
import com.example.QuanLyPhongTro.services.HousesService;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HousesController {

    @Autowired
    private HousesService _housesService;

    // Lấy tất cả các ngôi nhà
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Houses> getAllHouses() {
        return _housesService.getAllHouses();
    }

    // Lấy ngôi nhà theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Houses> getHouseById(@PathVariable int id) {
        Houses house = _housesService.getHouseById(id);
        if (house != null) {
            return ResponseEntity.ok(house);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy ngôi nhà với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách ngôi nhà với phân trang
    public Page<Houses> getHouses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _housesService.getHouses(page, size);
    }

    // Thêm ngôi nhà mới
    @PostMapping("")
    public Houses addHouse(@RequestBody Houses house) {
        return _housesService.addHouse(house);
    }

    // Cập nhật ngôi nhà theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Houses> updateHouse(@PathVariable int id, @RequestBody Houses houseDetails) {
        Houses updatedHouse = _housesService.updateHouse(id, houseDetails);
        if (updatedHouse != null) {
            return ResponseEntity.ok(updatedHouse);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa ngôi nhà theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable int id) {
        if (_housesService.deleteHouse(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Houses>> getHousesByUserId(@PathVariable int userId) {
        List<Houses> houses = _housesService.getHousesByUserId(userId);
        if (houses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(houses);
    }
}