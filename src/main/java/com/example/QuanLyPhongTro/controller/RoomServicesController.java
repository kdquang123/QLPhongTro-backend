package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.RoomServices;
import com.example.QuanLyPhongTro.services.RoomServicesService;

import java.util.List;

@RestController
@RequestMapping("/room-services")
public class RoomServicesController {

    @Autowired
    private RoomServicesService _roomServicesService;

    // Lấy tất cả dịch vụ phòng
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<RoomServices> getAllRoomServices() {
        return _roomServicesService.getAllRoomServices();
    }

    // Lấy dịch vụ phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomServices> getRoomServiceById(@PathVariable int id) {
        RoomServices roomService = _roomServicesService.getRoomServiceById(id);
        if (roomService != null) {
            return ResponseEntity.ok(roomService);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy dịch vụ phòng với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách dịch vụ phòng với phân trang
    public Page<RoomServices> getRoomServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _roomServicesService.getRoomServices(page, size);
    }

    // Thêm dịch vụ phòng mới
    @PostMapping("")
    public RoomServices addRoomService(@RequestBody RoomServices roomService) {
        return _roomServicesService.addRoomService(roomService);
    }

    // Cập nhật dịch vụ phòng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<RoomServices> updateRoomService(@PathVariable int id, @RequestBody RoomServices roomServiceDetails) {
        RoomServices updatedRoomService = _roomServicesService.updateRoomService(id, roomServiceDetails);
        if (updatedRoomService != null) {
            return ResponseEntity.ok(updatedRoomService);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa dịch vụ phòng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomService(@PathVariable int id) {
        if (_roomServicesService.deleteRoomService(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<RoomServices>> getRoomServicesByRoomId(@PathVariable int roomId) {
        List<RoomServices> roomServices = _roomServicesService.getRoomServicesByRoomId(roomId);
        if (roomServices.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có dịch vụ nào
        }
        return ResponseEntity.ok(roomServices);
    }
}