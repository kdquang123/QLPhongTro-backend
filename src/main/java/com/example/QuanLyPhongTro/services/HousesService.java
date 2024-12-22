package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Houses;
import com.example.QuanLyPhongTro.repositories.HousesRepository;

import java.util.List;

@Service
public class HousesService {
    @Autowired
    private HousesRepository _housesRepository;

    public List<Houses> getAllHouses() {
        return _housesRepository.findAll();
    }

    public Houses getHouseById(int id) {
        return _housesRepository.findById(id).orElse(null);
    }

    public Page<Houses> getHouses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _housesRepository.findAll(pageable);
    }

    public Houses addHouse(Houses house) {
        return _housesRepository.save(house);
    }

    public Houses updateHouse(int id, Houses houseDetails) {
        Houses house = getHouseById(id);
        if (house != null) {
            house.setName(houseDetails.getName());
            house.setAddress(houseDetails.getAddress());
            house.setTotalRooms(houseDetails.getTotalRooms());
            house.setDescription(houseDetails.getDescription());
            house.setStatus(houseDetails.getStatus());
            house.setCreatedAt(houseDetails.getCreatedAt());
            house.setUser(houseDetails.getUser());
            return _housesRepository.save(house);
        }
        return null;
    }

    public boolean deleteHouse(int id) {
        if (_housesRepository.existsById(id)) {
            _housesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Houses> getHousesByUserId(int userId) {
        return _housesRepository.findByUserId(userId); // Gọi phương thức trong Repository
    }
}