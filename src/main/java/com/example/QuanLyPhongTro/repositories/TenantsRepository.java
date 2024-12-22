package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantsRepository extends JpaRepository<Tenants, Integer> {
    List<Tenants> findByRoomId(int roomId);
    List<Tenants> findByRoom_House_Id(int houseId);
}
