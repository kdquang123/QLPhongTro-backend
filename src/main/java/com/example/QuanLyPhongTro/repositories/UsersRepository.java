package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
