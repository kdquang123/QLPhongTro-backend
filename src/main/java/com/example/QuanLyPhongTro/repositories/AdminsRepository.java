package com.example.QuanLyPhongTro.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongTro.models.Admins;

public interface AdminsRepository extends JpaRepository<Admins, Integer> {
    // Có thể thêm các phương thức tùy chỉnh nếu cần
    Admins findByUsername(String username);
}

