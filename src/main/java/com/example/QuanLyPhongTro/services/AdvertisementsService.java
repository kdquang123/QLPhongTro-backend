package com.example.QuanLyPhongTro.services;

import com.example.QuanLyPhongTro.dto.AdvertisementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Advertisements;
import com.example.QuanLyPhongTro.repositories.AdvertisementsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta. persistence. criteria. Predicate;

@Service
public class AdvertisementsService {
    @Autowired
    private AdvertisementsRepository _advertisementsRepository;

    public List<Advertisements> getAllAdvertisements() {
        return _advertisementsRepository.findAll();
    }

    public Advertisements getAdvertisementById(int id) {
        return _advertisementsRepository.findById(id).orElse(null);
    }

    public Page<AdvertisementDTO> getAdvertisements(String address, Integer priceMin, Integer priceMax, Integer areaMin, Integer areaMax, PageRequest pageRequest) {

        // Sử dụng Specification để tạo query động
        Specification<Advertisements> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(

            );

            predicates.add(criteriaBuilder.equal(root.get("status"), 1));

            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }
            if (priceMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), priceMin));
            }
            if (priceMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cost"), priceMax));
            }
            if (areaMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("area"), areaMin));
            }
            if (areaMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("area"), areaMax));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Trả về kết quả phân trang
        Page<Advertisements> advertisementsPage = _advertisementsRepository.findAll(spec, pageRequest);

        // Chuyển đối tượng Advertisement thành AdvertisementDTO
        return advertisementsPage.map(advertisement -> new AdvertisementDTO(advertisement));
    }

    public Advertisements addAdvertisement(Advertisements advertisement) {
        return _advertisementsRepository.save(advertisement);
    }

    public Advertisements updateAdvertisement(int id, Advertisements advertisementDetails) {
        Advertisements advertisement = getAdvertisementById(id);
        if (advertisement != null) {
            advertisement.setDescription(advertisementDetails.getDescription());
            advertisement.setStatus(advertisementDetails.getStatus());
            advertisement.setAddress(advertisementDetails.getAddress());
            advertisement.setCost(advertisementDetails.getCost());
            advertisement.setCreatedAt(advertisementDetails.getCreatedAt());
            advertisement.setMaxOccupants(advertisementDetails.getMaxOccupants());
            advertisement.setTitle(advertisementDetails.getTitle());
            advertisement.setArea(advertisementDetails.getArea());
            advertisement.setLatitude(advertisementDetails.getLatitude());
            advertisement.setLongitude(advertisementDetails.getLongitude());
            advertisement.setUser(advertisementDetails.getUser());
            advertisement.setType(advertisementDetails.getType());
            return _advertisementsRepository.save(advertisement);
        }
        return null;
    }

    public boolean deleteAdvertisement(int id) {
        if (_advertisementsRepository.existsById(id)) {
            _advertisementsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<AdvertisementDTO> getAdvertisementsByUser(Integer id,Integer status,PageRequest pageRequest){
        Specification<Advertisements> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(

            );

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), id));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    // Trả về kết quả phân trang
    Page<Advertisements> advertisementsPage = _advertisementsRepository.findAll(spec, pageRequest);

    // Chuyển đối tượng Advertisement thành AdvertisementDTO
        return advertisementsPage.map(advertisement -> new AdvertisementDTO(advertisement));
    };

    public Page<AdvertisementDTO> getAdvertisementsByAmin(PageRequest pageRequest){
        Specification<Advertisements> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(

            );


                predicates.add(criteriaBuilder.equal(root.get("status"), 0));


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        // Trả về kết quả phân trang
        Page<Advertisements> advertisementsPage = _advertisementsRepository.findAll(spec, pageRequest);

        // Chuyển đối tượng Advertisement thành AdvertisementDTO
        return advertisementsPage.map(advertisement -> new AdvertisementDTO(advertisement));
    }



}