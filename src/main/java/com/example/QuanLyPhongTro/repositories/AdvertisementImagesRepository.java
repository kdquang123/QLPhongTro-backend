package com.example.QuanLyPhongTro.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongTro.models.AdvertisementImages;

public interface AdvertisementImagesRepository extends JpaRepository<AdvertisementImages, Integer>{
}
