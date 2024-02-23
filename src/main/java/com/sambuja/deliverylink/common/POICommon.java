package com.sambuja.deliverylink.common;

import com.sambuja.deliverylink.convert.CjDelivery;
import com.sambuja.deliverylink.convert.ConvertExcel;
import com.sambuja.deliverylink.convert.NaverSmartStore;
import com.sambuja.deliverylink.dto.DtoInterface;
import com.sambuja.deliverylink.except.ErrorCode;
import com.sambuja.deliverylink.except.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class POICommon {

    @Value("${file.uploadPath}")
    private String filePath;

    public File convertMulPartToFile(MultipartFile multipartFile) throws IOException {

        File uploadPathFolder = new File(filePath, "tmp");

        String fullPath = filePath + "tmp" + File.separator + "temp.xlsx";

        if(!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
            log.info("-------------------makeFolder------------------");
            log.info("uploadPathFolder.exists(): "+uploadPathFolder.exists());
            log.info("mkdirs: "+mkdirs);
        }

        Path absolutePath = Paths.get(fullPath).toAbsolutePath();

        multipartFile.transferTo(absolutePath);
        return new File(fullPath);
    }

    //엑셀로 들어온 파일이 스마트스토어, CJ대한통운 파일인지 구분하는 메소드
    public ConvertExcel validateExcelFile(MultipartFile file, String pwd) throws IOException {
        Workbook workbook = null;
        Sheet sheet = null;

        File tempFile = this.convertMulPartToFile(file);

        FileInputStream fis = new FileInputStream(tempFile);

        try{
            POIFSFileSystem poifs = new POIFSFileSystem(fis);

            workbook = WorkbookFactory.create(poifs.getRoot(), pwd);
        }catch (IOException e){
            throw new ErrorException(e);
        }catch (Exception e){
            workbook = new XSSFWorkbook(new FileInputStream(this.convertMulPartToFile(file)));
        } finally{
            if(tempFile.exists()){
                tempFile.delete();
            }
        }

        sheet = workbook.getSheetAt(0);

        //네이버 - 발주발송관리
        //CJ - Sheet1
        return ("발주발송관리".equals(sheet.getSheetName())) ? new NaverSmartStore(workbook) : new CjDelivery(workbook);
    };

    public <T extends DtoInterface> Workbook createNewExcel(List<T> rowList) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("발송발주관리");

        try{
            T t = rowList.get(0);

            Row headerRow = sheet.createRow(1);

            //헤더 먼저 입력 (고정)
            for(int i = 0; i < t.getHeaderSize(); i++){
                headerRow.createCell(i).setCellValue(t.getHeaderList(i));
            }

            //실제 고객 내용 입력
            for(int i = 0; i < rowList.size(); i++){
                //2번째 행부터 입력
                Row row = sheet.createRow(i + 2);
                T dto = rowList.get(i);

                for(int j = 0; j < dto.getHeaderSize(); j++){
                    row.createCell(j).setCellValue(dto.getValueByCellIndex(dto.getHeaderList(j)));
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            throw new ErrorException(e, ErrorCode.ARRAY_INDEX_OUT);
        }



        return workbook;
    }
}
