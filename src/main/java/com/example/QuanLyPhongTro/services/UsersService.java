package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.repositories.UsersRepository;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository _usersRepository;

    public List<Users> getAllUsers() {
        return _usersRepository.findAll();
    }

    public Users getUserById(int id) {
        return _usersRepository.findById(id).orElse(null);
    }

    public Page<Users> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _usersRepository.findAll(pageable);
    }

    public Users addUser(Users user) {
        return _usersRepository.save(user);
    }

    public Users updateUser(int id, Users userDetails) {
        Users user = getUserById(id);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setFullName(userDetails.getFullName());
            user.setStatus(userDetails.getStatus());
            user.setCreatedAt(userDetails.getCreatedAt());
            user.setServicePackage(userDetails.getServicePackage());
            return _usersRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(int id) {
        if (_usersRepository.existsById(id)) {
            _usersRepository.deleteById(id);
            return true;
        }
        return false;
    }
}