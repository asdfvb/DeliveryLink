package com.sambuja.deliverylink.convert;

import com.sambuja.deliverylink.dto.NaverDto;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class NaverSmartStore implements ConvertExcel {
    private Workbook workbook;



    private final List<NaverDto> rowList = new ArrayList<>();

    public NaverSmartStore(Workbook workbook){
        this.workbook = workbook;
    }

    @Override
    public void readRowData() {

        Sheet sheet = this.workbook.getSheetAt(0);

        for( int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
            Row row = sheet.getRow(i);
            for(int index = 1; index < row.getLastCellNum(); index++){
                Cell cell = row.getCell(index);

                if(cell == null || cell.getCellType() == CellType.BLANK){
                    System.out.println(">>> BLANK : " + i + "행 : " + index + "열");
                    continue;
                }

                switch (cell.getCellType()) {
                    case BOOLEAN:
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue());
                        break;
                    case STRING:
                        System.out.println(cell.getRichStringCellValue());
                        break;
                    default:
                        System.out.println("default: " + cell.getCellFormula());
                        break;
                }

            }
        }

    }

    @Override
    public void getRowData() {

        Sheet sheet = this.workbook.getSheetAt(0);

        for( int i = 2; i < sheet.getPhysicalNumberOfRows(); i ++) {
            Row row = sheet.getRow(i);
            NaverDto item = new NaverDto();

            for(int index = 0; index < row.getLastCellNum(); index++){
                Cell cell = row.getCell(index);

                if(cell == null || cell.getCellType() == CellType.BLANK){
                    System.out.println(">>> BLANK : " + i + "행 : " + index + "열");
                    item.setValueByCellIndex(item.getHeaderList(index), "");
                    continue;
                }

                switch (cell.getCellType()) {
                    case BOOLEAN:
                        System.out.println(cell.getBooleanCellValue());
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue());
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getNumericCellValue()));
                        break;
                    case STRING:
                        System.out.println(cell.getRichStringCellValue());
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getRichStringCellValue()));
                        break;
                    default:
                        System.out.println("default: " + cell.getCellFormula());
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getCellFormula()));
                        break;
                }

            }
            rowList.add(item);
        }
    }

    @Override
    public List<NaverDto> makeDistinctData() {
        List<NaverDto> distinctList = new ArrayList<>();

        for(NaverDto dto : this.rowList){

            NaverDto naverDto = distinctList.stream().filter(distinct -> dto.getOrderNumber().equals(distinct.getOrderNumber())).findAny().orElseGet(NaverDto::new);

            if(distinctList.size() > 0 && naverDto.getOrderNumber() != null) continue;

            //동일 수취인의 총 주문 수량 구하기
            int cntSum = this.rowList.stream()
                    .filter(item -> item.getOrderNumber().equals(dto.getOrderNumber()))
                    .mapToInt(item -> Math.round(Float.parseFloat(item.getCnt()))).sum();

            BigDecimal bd = new BigDecimal(cntSum);

            int remainValue = bd.divide(BigDecimal.valueOf(5)).setScale(0, RoundingMode.UP).intValue();

            for( int i = 0; i < remainValue; i++){
                dto.setCnt("1");
                distinctList.add(dto);
            }
        }

        return distinctList;
    }
}
