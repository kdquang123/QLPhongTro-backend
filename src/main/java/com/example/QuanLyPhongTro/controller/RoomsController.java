package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Rooms;
import com.example.QuanLyPhongTro.services.RoomsService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    @Autowired
    private RoomsService _roomsService;

    // Lấy tất cả các phòng
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Rooms> getAllRooms() {
        return _roomsService.getAllRooms();
    }

    // Lấy phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Rooms> getRoomById(@PathVariable int id) {
        Rooms room = _roomsService.getRoomById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy danh sách phòng với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách phòng với phân trang
    public Page<Rooms> getRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _roomsService.getRooms(page, size);
    }

    // Thêm phòng mới
    @PostMapping("")
    public Rooms addRoom(@RequestBody Rooms room) {
        return _roomsService.addRoom(room);
    }

    // Cập nhật phòng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Rooms> updateRoom(@PathVariable int id, @RequestBody Rooms roomDetails) {
        Rooms updatedRoom = _roomsService.updateRoom(id, roomDetails);
        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa phòng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        if (_roomsService.deleteRoom(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<Rooms>> getRoomsByHouseId(@PathVariable int houseId) {
        List<Rooms> rooms = _roomsService.getRoomsByHouseId(houseId);
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có phòng nào
        }
        return ResponseEntity.ok(rooms);
    }
}