package com.example.QuanLyPhongTro.services;

import com.example.QuanLyPhongTro.models.Admins;
import com.example.QuanLyPhongTro.models.CustomUserDetails;
import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.repositories.AdminsRepository;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AdminsRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        Users user = userRepository.findByUsername(username);
        if (user != null && user.getStatus()!=0) {
            return new CustomUserDetails(user);
        }

        Admins admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return new CustomUserDetails(admin);
        }

        throw new UsernameNotFoundException(username);
    }

    public CustomUserDetails loadUserById(int userId) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new CustomUserDetails(user);
        }

        Admins admin = adminRepository.findById(userId).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(admin);
        }

        throw new UsernameNotFoundException("User not found with id: " + userId);
    }

    public CustomUserDetails loadUserById(int userId, String role) {
        if ("ROLE_USER".equalsIgnoreCase(role)) {
            Users user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                return new CustomUserDetails(user);
            }
        } else if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
            Admins admin = adminRepository.findById(userId).orElse(null);
            if (admin != null) {
                return new CustomUserDetails(admin);
            }
        }

        throw new UsernameNotFoundException("User not found with id: " + userId + " and role: " + role);
    }
}