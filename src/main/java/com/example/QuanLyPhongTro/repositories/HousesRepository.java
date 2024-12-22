package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.Houses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HousesRepository extends JpaRepository<Houses, Integer> {
    List<Houses> findByUserId(int userId);
}
