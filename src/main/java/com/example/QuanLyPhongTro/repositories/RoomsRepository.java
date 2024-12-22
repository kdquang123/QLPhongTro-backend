package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    List<Rooms> findByHouseId(int houseId);
}
