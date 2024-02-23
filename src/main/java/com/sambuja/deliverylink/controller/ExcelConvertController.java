package com.sambuja.deliverylink.controller;

import com.sambuja.deliverylink.common.POICommon;
import com.sambuja.deliverylink.convert.ConvertExcel;
import com.sambuja.deliverylink.dto.DtoInterface;
import com.sambuja.deliverylink.dto.FileDto;
import com.sambuja.deliverylink.except.ErrorCode;
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

        List<DtoInterface> dtoInterfaces = excel.makeDistinctData();

        Workbook newExcel = this.poiCommon.createNewExcel(dtoInterfaces);

        String uploadPath = this.makeFolder();

        File file= new File(uploadPath + "\\NaverToCJ.xlsx");

        newExcel.write(new FileOutputStream(file));

        newExcel.close();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/copyToNaverDelivery")
    public ResponseEntity copyToNaverDelivery(@ModelAttribute FileDto dto, HttpServletResponse response) throws IOException {

        ConvertExcel excel1 = null;
        ConvertExcel excel2 = null;
        ConvertExcel targetExcelFile = null;
        ConvertExcel subExcelFile = null;
        Workbook newExcel = null;

        try{
            for(MultipartFile file : dto.getFiles()) {
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                if (!extension.equals("xlsx") && !extension.equals("xls")) {
                    log.warn("Please. Check File. You Can Upload Only Excel File");
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }

                if(excel1 == null) {
                    excel1 = this.poiCommon.validateExcelFile(file, dto.getPwd());
                    excel1.getRowData();
                }else {
                    excel2 = this.poiCommon.validateExcelFile(file, dto.getPwd());
                    excel2.getRowData();
                }
            }

            targetExcelFile = ("CJ".equals(excel1.getName())) ? excel1 : excel2;
            subExcelFile = ("CJ".equals(excel1.getName())) ? excel2 : excel1;

            if(targetExcelFile == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            targetExcelFile.setRowList(subExcelFile.getRowList());

            List<DtoInterface> dtoInterfaces = targetExcelFile.makeDistinctData();

            newExcel = this.poiCommon.createNewExcel(dtoInterfaces);

            String uploadPath = this.makeFolder();

            File file= new File(uploadPath + "\\CjToNaver.xlsx");

            newExcel.write(new FileOutputStream(file));

            newExcel.close();
        }catch (ArrayIndexOutOfBoundsException e){
            throw new ErrorException(e, ErrorCode.ARRAY_INDEX_OUT);
        }

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
