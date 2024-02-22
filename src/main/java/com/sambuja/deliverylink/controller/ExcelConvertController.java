package com.sambuja.deliverylink.controller;

import com.sambuja.deliverylink.common.POICommon;
import com.sambuja.deliverylink.convert.ConvertExcel;
import com.sambuja.deliverylink.dto.DtoInterface;
import com.sambuja.deliverylink.dto.FileDto;
import com.sambuja.deliverylink.except.ErrorException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class ExcelConvertController {

    @Value("${file.uploadPath}")
    private String filePath;

    private POICommon poiCommon;

    @Autowired
    public ExcelConvertController(POICommon poiCommon) {
        this.poiCommon = poiCommon;
    }

    @PostMapping("/copyToCJDelivery")
    public ResponseEntity copyToCJDelivery(@ModelAttribute FileDto dto, HttpServletResponse response) throws IOException {

        MultipartFile uploadFile = dto.getFiles().get(0);

        String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            log.warn("Please. Check File. You Can Upload Only Excel File");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        ConvertExcel excel = this.poiCommon.validateExcelFile(uploadFile, dto.getPwd());

        excel.getRowData();

        List<DtoInterface> dtoInterfaces = excel.downloadExcelFile();

        Workbook newExcel = this.poiCommon.createNewExcel(dtoInterfaces);

        String uploadPath = this.makeFolder();

        File file= new File(uploadPath + "\\testWrite.xlsx");

        newExcel.write(new FileOutputStream(file));

        newExcel.close();
        // 이미지 파일만 업로드
        /*if (!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {
            log.warn("this file is not image type");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }*/

        /*try {
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=student.xlsx");

            newExcel.write(response.getOutputStream());
        } catch (IOException e) {
            log.debug("다운로드중 에러가 발생하였습니다.");

        }finally {
            try {
                if(newExcel != null) {
                    newExcel.close();
                }
            } catch (IOException e) {
                // checked 예외를 사용하면 추후 의존이나 예외 누수 문제가 생길 수 있으므로
                // RuntimeException으로 한번 감싸서, cause가 나올 수 있게 발생한 예외를 넣어준다.
                throw new RuntimeException(e);
            }
        }*/

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder --------
        File uploadPathFolder = new File(filePath, folderPath);

        if(!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
            log.info("-------------------makeFolder------------------");
            log.info("uploadPathFolder.exists(): "+uploadPathFolder.exists());
            log.info("mkdirs: "+mkdirs);
        }

        return filePath + folderPath;

    }

}
