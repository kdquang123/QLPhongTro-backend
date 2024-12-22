package com.example.QuanLyPhongTro.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName = "letuananhadvertisementimages"; // Thay đổi thành tên bucket của bạn

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(bucketName, fileName, inputStream, metadata);

        return amazonS3.getUrl(bucketName, fileName).toString(); // Trả về URL của file đã được tải lên
    }
}