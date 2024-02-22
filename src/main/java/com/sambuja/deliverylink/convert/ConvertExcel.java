package com.sambuja.deliverylink.convert;

import com.sambuja.deliverylink.dto.DtoInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface ConvertExcel {

    void readRowData();

    void getRowData();

    <T extends DtoInterface> List<T> downloadExcelFile();
}
