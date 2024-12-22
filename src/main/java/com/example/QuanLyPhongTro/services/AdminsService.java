package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Admins;
import com.example.QuanLyPhongTro.repositories.AdminsRepository;

import java.util.List;

@Service
public class AdminsService {
	@Autowired
	private AdminsRepository _adminsRepository;
	public Admins getAdminById(int id) {
		return _adminsRepository.findById(id).orElse(null);
	}

	public List<Admins> getAllAdmins() {
		return _adminsRepository.findAll();
	}

	public Page<Admins> getAdmins(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return _adminsRepository.findAll(pageable);
	}

	public Admins addAdmin(Admins admin) {
		return _adminsRepository.save(admin);
	}

	public Admins updateAdmin(int id, Admins adminDetails) {
		Admins admin = getAdminById(id);
		if (admin != null) {
			admin.setUsername(adminDetails.getUsername());
			admin.setPassword(adminDetails.getPassword());
			admin.setCreatedAt(adminDetails.getCreatedAt());
			return _adminsRepository.save(admin);
		}
		return null;
	}

	public boolean deleteAdmin(int id) {
		if (_adminsRepository.existsById(id)) {
			_adminsRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
