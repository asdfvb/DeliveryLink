package com.sambuja.deliverylink.convert;

import com.sambuja.deliverylink.dto.DtoInterface;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Data
public class CjDelivery implements ConvertExcel{

    private Workbook workbook;

    private final String[] headerList = {
            "상품주문번호",
            "수취인명",
            "수취인연락처1",
            "통합배송지",
            "배송메세지",
            "상품명",
            "상품종류",
            "수량",
            "배송방법(구매자 요청)",
            "배송방법",
            "택배사",
            "송장번호",
            "발송일",
            "주문번호"
    };

    private final List<HashMap<String, Object>> rowList = new ArrayList<>();

    public CjDelivery(Workbook workbook){
        this.workbook = workbook;
    }

    @Override
    public void readRowData() {

        Sheet sheet = this.workbook.getSheetAt(0);

        for( int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
            Row row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
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
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                HashMap<String, Object> rowItem = new HashMap<>();
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
    public <T extends DtoInterface> List<T> downloadExcelFile() {
        return null;
    }


}
