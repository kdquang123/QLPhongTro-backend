package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.RoomServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomServicesRepository extends JpaRepository<RoomServices, Integer> {
    List<RoomServices> findByRoomId(int roomId);
}
