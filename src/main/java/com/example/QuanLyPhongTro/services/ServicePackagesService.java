package com.example.QuanLyPhongTro.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.ServicePackages;
import com.example.QuanLyPhongTro.repositories.ServicePackagesRepository;

import java.util.List;

@Service
public class ServicePackagesService {
    @Autowired
    private ServicePackagesRepository _servicePackagesRepository;

    public List<ServicePackages> getAllServicePackages() {
        return _servicePackagesRepository.findAll();
    }

    public ServicePackages getServicePackageById(int id) {
        return _servicePackagesRepository.findById(id).orElse(null);
    }

    public Page<ServicePackages> getServicePackages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _servicePackagesRepository.findAll(pageable);
    }

    public ServicePackages addServicePackage(ServicePackages servicePackage) {
        return _servicePackagesRepository.save(servicePackage);
    }

    public ServicePackages updateServicePackage(int id, ServicePackages servicePackageDetails) {
        ServicePackages servicePackage = getServicePackageById(id);
        if (servicePackage != null) {
            servicePackage.setName(servicePackageDetails.getName());
            servicePackage.setDescription(servicePackageDetails.getDescription());
            servicePackage.setDuration(servicePackageDetails.getDuration());
            servicePackage.setCreatedAt(servicePackageDetails.getCreatedAt());
            servicePackage.setPrice(servicePackageDetails.getPrice());
            return _servicePackagesRepository.save(servicePackage);
        }
        return null;
    }

    public boolean deleteServicePackage(int id) {
        if (_servicePackagesRepository.existsById(id)) {
            _servicePackagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}