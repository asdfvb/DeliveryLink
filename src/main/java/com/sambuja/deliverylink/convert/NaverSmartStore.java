package com.sambuja.deliverylink.convert;

import com.sambuja.deliverylink.dto.CjDto;
import com.sambuja.deliverylink.dto.DtoInterface;
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

    private final String name = "Naver";

    private final List<NaverDto> rowList = new ArrayList<>();

    private List<DtoInterface> subRowList = new ArrayList<>();

    public NaverSmartStore(Workbook workbook){
        this.workbook = workbook;
    }

    @Override
    public List<NaverDto> getRowList(){
        return rowList;
    }

    @Override
    public void setRowList(List<DtoInterface> list){
        this.subRowList = list;
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
                    item.setValueByCellIndex(item.getHeaderList(index), "");
                    continue;
                }

                switch (cell.getCellType()) {
                    case BOOLEAN:
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case NUMERIC:
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getNumericCellValue()));
                        break;
                    case STRING:
                        item.setValueByCellIndex(item.getHeaderList(index), String.valueOf(cell.getRichStringCellValue()));
                        break;
                    default:
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

            //distinctList에 1개라도 들어가 있는 데이터일경우는 continue
            if(distinctList.size() > 0 && naverDto.getOrderNumber() != null) continue;

            //동일 수취인의 총 주문 리스트 구하기
            List<NaverDto> collect = this.rowList.stream()
                    .filter(item -> item.getOrderNumber().equals(dto.getOrderNumber()))
                    .collect(Collectors.toList());

            BigDecimal decimal = new BigDecimal(dto.calculateCjBoxCnt(collect) / dto.getHEIGHT());

            //총 주문 수량의 높이를 구해서 몇개의 박스가 필요한지 구하기
            int remainValue = decimal.setScale(0, RoundingMode.HALF_UP).intValue();

            //박스 수만큼 포문돌려서 데이터 리스트 만듬
            for( int i = 0; i < remainValue; i++){
                dto.setCnt("1");
                distinctList.add(dto);
            }
        }

        return distinctList;
    }
}
