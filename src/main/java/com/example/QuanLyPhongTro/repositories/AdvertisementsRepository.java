package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.Advertisements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvertisementsRepository extends JpaRepository<Advertisements, Integer> , JpaSpecificationExecutor<Advertisements> {
    Page<Advertisements> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
