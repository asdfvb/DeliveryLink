package com.sambuja.deliverylink.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileDto {
    private List<MultipartFile> files;
    private String pwd;
    private String etcCnt;
}
