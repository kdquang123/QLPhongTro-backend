package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.AdvertisementImages;
import com.example.QuanLyPhongTro.repositories.AdvertisementImagesRepository;

import java.util.List;

@Service
public class AdvertisementImagesService {
    @Autowired
    private AdvertisementImagesRepository _advertisementImagesRepository;

    public List<AdvertisementImages> getAllImages() {
        return _advertisementImagesRepository.findAll();
    }

    public AdvertisementImages getImageById(int id) {
        return _advertisementImagesRepository.findById(id).orElse(null);
    }

    public Page<AdvertisementImages> getAdvertisementImages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Gọi phương thức để lấy hình ảnh theo thứ tự giảm dần
        return _advertisementImagesRepository.findAll(pageable);
    }

    public AdvertisementImages addImage(AdvertisementImages image) {
        return _advertisementImagesRepository.save(image);
    }

    public AdvertisementImages updateImage(int id, AdvertisementImages imageDetails) {
        AdvertisementImages image = getImageById(id);
        if (image != null) {
            image.setImagePath(imageDetails.getImagePath());
            image.setAdvertisement(imageDetails.getAdvertisement());
            return _advertisementImagesRepository.save(image);
        }
        return null;
    }

    public boolean deleteImage(int id) {
        if (_advertisementImagesRepository.existsById(id)) {
            _advertisementImagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}