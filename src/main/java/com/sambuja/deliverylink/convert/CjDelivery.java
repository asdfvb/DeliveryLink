package com.sambuja.deliverylink.convert;

import com.sambuja.deliverylink.dto.CjDto;
import com.sambuja.deliverylink.dto.DtoInterface;
import com.sambuja.deliverylink.dto.CjDto;
import com.sambuja.deliverylink.dto.NaverDto;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class CjDelivery implements ConvertExcel{

    private Workbook workbook;

    private final String name = "CJ";

    private final List<CjDto> rowList = new ArrayList<>();

    private List<DtoInterface> subRowList = new ArrayList<>();

    public CjDelivery(Workbook workbook){
        this.workbook = workbook;
    }

    @Override
    public List<CjDto> getRowList(){
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

        for( int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
            Row row = sheet.getRow(i);
            CjDto item = new CjDto();

            for(int index = 0; index < row.getLastCellNum(); index++){
                Cell cell = row.getCell(index);

                if(cell == null || cell.getCellType() == CellType.BLANK){
                    System.out.println(">>> BLANK : " + i + "행 : " + index + "열");
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
    public List<CjDto> makeDistinctData() {
        List<CjDto> distinctList = new ArrayList<>();

        for(CjDto dto : this.rowList){

            List<CjDto> validationList = distinctList
                                            .stream()
                                            .filter(
                                                    item -> dto.getProductNumber().equals(item.getProductNumber())
                                            )
                                            .map(
                                                item -> {
                                                    item.setDeliveryNumber(dto.getDeliveryNumber());
                                                    return item;
                                                }
                                            ).collect(Collectors.toList());

            if(validationList.size() > 0){

                for(int i = 0; i < distinctList.size(); i ++){
                    CjDto cj = distinctList.get(i);
                    for(int j = 0; j < validationList.size(); j ++){
                        CjDto cj2 = validationList.get(j);
                        if(cj.getProductNumber().equals(cj2.getProductNumber())){
                            distinctList.get(i).cloneDto(cj2);
                        }
                    }
                }

                continue;
            }

            //rowList 행별로 subList에 고객주문번호가 일치하는 데이터 있는지 필터
            List<DtoInterface> collect = this.subRowList
                                            .stream()
                                            .filter(
                                                    item -> item.getValueByCellIndex(item.getHeaderList(0)).equals(dto.getProductNumber())
                                            )
                                            .collect(Collectors.toList());

            //없으면 진행
            if(collect.size() == 0 ) continue;

            DtoInterface dtoInterface = collect.get(0);

            //주문번호가 같은 내역을 조회
            List<DtoInterface> collect1 = this.subRowList
                                            .stream()
                                            .filter(
                                                    item -> item.getValueByCellIndex(item.getHeaderList(13)).equals(dtoInterface.getValueByCellIndex(dtoInterface.getHeaderList(13)))
                                            )
                                            .collect(Collectors.toList());

            for(DtoInterface subDto : collect1){
                CjDto tmpDto = new CjDto();
                tmpDto.cloneDto(dto);
                tmpDto.setProductNumber(subDto.getValueByCellIndex(subDto.getHeaderList(0)));
                distinctList.add(tmpDto);
            }

        }

        return distinctList;
    }


}
