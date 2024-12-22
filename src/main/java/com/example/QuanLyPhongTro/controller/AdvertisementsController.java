package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.dto.AdvertisementDTO;
import com.example.QuanLyPhongTro.dto.AdvertisementDetailDTO;
import com.example.QuanLyPhongTro.dto.PageDTO;
import com.example.QuanLyPhongTro.models.AdvertisementImages;
import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.repositories.AdvertisementImagesRepository;
import com.example.QuanLyPhongTro.repositories.AdvertisementsRepository;
import com.example.QuanLyPhongTro.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Advertisements;
import com.example.QuanLyPhongTro.services.AdvertisementsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementsController {

    @Autowired
    private AdvertisementsService _advertisementsService;

    @Autowired
    private AdvertisementsRepository _advertisementsRepository;

    @Autowired
    private S3Service _s3Service;

    @Autowired
    private AdvertisementImagesRepository _advertisementImagesRepository;

    // Lấy tất cả quảng cáo
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Advertisements> getAllAdvertisements() {
        return _advertisementsService.getAllAdvertisements();
    }

    // Lấy quảng cáo theo ID
    @GetMapping("/get/{id}")
    public ResponseEntity<AdvertisementDetailDTO> getAdvertisementById(@PathVariable int id) {
        Advertisements advertisement = _advertisementsService.getAdvertisementById(id);
        if (advertisement != null) {
            AdvertisementDetailDTO advertisementDetailDTO = new AdvertisementDetailDTO(advertisement);
            return ResponseEntity.ok(advertisementDetailDTO);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/user/get/{id}")
    public ResponseEntity<PageDTO<AdvertisementDTO>> getAdvertisementsByUserId(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        // Gọi service với các tham số lọc
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AdvertisementDTO> advertisements = _advertisementsService.getAdvertisementsByUser(id,status,pageRequest);

        return ResponseEntity.ok(new PageDTO<AdvertisementDTO>(advertisements));
    }

    @GetMapping("/admin/get")
    public ResponseEntity<PageDTO<AdvertisementDTO>> getAdvertisementsByAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        // Gọi service với các tham số lọc
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AdvertisementDTO> advertisements = _advertisementsService.getAdvertisementsByAmin(pageRequest);

        return ResponseEntity.ok(new PageDTO<AdvertisementDTO>(advertisements));
    }

    // Lấy quảng cáo với phân trang
    @GetMapping ("/get") // Giữ nguyên để trả về danh sách quảng cáo với phân trang
    public ResponseEntity<PageDTO<AdvertisementDTO>> getAdvertisements(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) Integer areaMin,
            @RequestParam(required = false) Integer areaMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Gọi service với các tham số lọc
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AdvertisementDTO> advertisements = _advertisementsService.getAdvertisements(address, priceMin, priceMax, areaMin, areaMax, pageRequest);

        return ResponseEntity.ok(new PageDTO<AdvertisementDTO>(advertisements));
    }

    // Thêm quảng cáo mới
    @PostMapping("/create")
    public ResponseEntity<String> createAdvertisement(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("cost") Double cost,
            @RequestParam("area") Integer area,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("city") String city,
            @RequestParam("district") String district,
            @RequestParam("ward") String ward,
            @RequestParam("cityName") String cityName,
            @RequestParam("districtName") String districtName,
            @RequestParam("wardName") String wardName,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("userId") Integer userId,
            @RequestParam("type") Integer type
    ) {
        try {
            Advertisements newAd=new Advertisements();
            newAd.setTitle(title);
            newAd.setDescription(description);
            newAd.setCost(cost);
            newAd.setArea(area);
            newAd.setStatus(0);
            newAd.setLatitude(latitude);
            newAd.setLongitude(longitude);
            String address=wardName+", "+districtName+", "+cityName;
            newAd.setAddress(address);
            newAd.setType(type);
            Users user=new Users();
            user.setId(userId);
            newAd.setUser(user);
            newAd.setCreatedAt(new Date());
            Advertisements savedAd=_advertisementsRepository.save(newAd);
            // Lưu ảnh vào AWS S3 và lưu đường dẫn vào bảng advertisement_image
            for (MultipartFile image : images) {
                String imageUrl = _s3Service.uploadFile(image);
                AdvertisementImages newadvertisementImage = new AdvertisementImages();
                newadvertisementImage.setAdvertisement(savedAd);
                newadvertisementImage.setImagePath(imageUrl);
                _advertisementImagesRepository.save(newadvertisementImage);
            }
            return ResponseEntity.ok("Đăng thành công,chờ duyệt!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng thất bại!");
        }
    }

    // Cập nhật quảng cáo theo ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Advertisements> updateAdvertisement(@PathVariable int id, @RequestBody Advertisements advertisementDetails) {
        Advertisements updatedAdvertisement = _advertisementsService.updateAdvertisement(id, advertisementDetails);
        if (updatedAdvertisement != null) {
            return ResponseEntity.ok(updatedAdvertisement);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa quảng cáo theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable int id) {
        if (_advertisementsService.deleteAdvertisement(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Advertisements> approveAdvertisement(@PathVariable int id, @RequestBody Advertisements advertisementDetails) {
        Advertisements updatedAdvertisement = _advertisementsRepository.findById(id).orElse(null);
        if (updatedAdvertisement != null) {
            updatedAdvertisement.setStatus(advertisementDetails.getStatus());
            _advertisementsRepository.save(updatedAdvertisement);
            return ResponseEntity.ok(updatedAdvertisement);
        }
        return ResponseEntity.notFound().build();
    }

}