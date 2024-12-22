package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Rooms;
import com.example.QuanLyPhongTro.repositories.RoomsRepository;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private RoomsRepository _roomsRepository;

    public List<Rooms> getAllRooms() {
        return _roomsRepository.findAll();
    }

    public Rooms getRoomById(int id) {
        return _roomsRepository.findById(id).orElse(null);
    }

    public Page<Rooms> getRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _roomsRepository.findAll(pageable);
    }

    public Rooms addRoom(Rooms room) {
        return _roomsRepository.save(room);
    }

    public Rooms updateRoom(int id, Rooms roomDetails) {
        Rooms room = getRoomById(id);
        if (room != null) {
            room.setPrice(roomDetails.getPrice());
            room.setRoomNumber(roomDetails.getRoomNumber());
            room.setDescription(roomDetails.getDescription());
            room.setOccupancyStatus(roomDetails.getOccupancyStatus());
            room.setMaxOccupants(roomDetails.getMaxOccupants());
            room.setCreatedAt(roomDetails.getCreatedAt());
            room.setHouse(roomDetails.getHouse());
            return _roomsRepository.save(room);
        }
        return null;
    }

    public boolean deleteRoom(int id) {
        if (_roomsRepository.existsById(id)) {
            _roomsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Rooms> getRoomsByHouseId(int houseId) {
        return _roomsRepository.findByHouseId(houseId);
    }
}