package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Tenants;
import com.example.QuanLyPhongTro.repositories.TenantsRepository;

import java.util.List;

@Service
public class TenantsService {
    @Autowired
    private TenantsRepository _tenantsRepository;

    public List<Tenants> getAllTenants() {
        return _tenantsRepository.findAll();
    }

    public Tenants getTenantById(int id) {
        return _tenantsRepository.findById(id).orElse(null);
    }

    public Page<Tenants> getTenants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _tenantsRepository.findAll(pageable);
    }

    public Tenants addTenant(Tenants tenant) {
        return _tenantsRepository.save(tenant);
    }

    public Tenants updateTenant(int id, Tenants tenantDetails) {
        Tenants tenant = getTenantById(id);
        if (tenant != null) {
            tenant.setFullName(tenantDetails.getFullName());
            tenant.setPhoneNumber(tenantDetails.getPhoneNumber());
            tenant.setEmail(tenantDetails.getEmail());
            tenant.setIsRepresentative(tenantDetails.getIsRepresentative());
            tenant.setRoom(tenantDetails.getRoom());
            return _tenantsRepository.save(tenant);
        }
        return null;
    }

    public boolean deleteTenant(int id) {
        if (_tenantsRepository.existsById(id)) {
            _tenantsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Tenants> getTenantsByRoomId(int roomId) {
        return _tenantsRepository.findByRoomId(roomId); // Gọi đến phương thức trong repository
    }

    public List<Tenants> getTenantsByHouseId(int houseId) {
        return _tenantsRepository.findByRoom_House_Id(houseId); // Gọi đến phương thức trong repository
    }
}