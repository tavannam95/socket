package com.a2m.gen.services.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.TsstUser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class ExportToExcelService {

    @Value("${path.upload.dir}")
    private String pathUploadDir;
    
    private List < TsstUser > studentList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

   
    
    public ExportToExcelService(List<TsstUser> studentList) {
        super();
        this.studentList = studentList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(XSSFWorkbook workbook) {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        style.setFont(font);

        createCell(row, 0, "No.", style); 
        createCell(row, 1, "Full Name", style);       
        createCell(row, 2, "E-mail", style);    
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "Account", style);
        createCell(row, 5, "Password", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines(XSSFWorkbook workbook, List<TsstUser> tsstUser) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (TsstUser user : tsstUser) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            int idx = 1;
            createCell(row, columnCount++, idx, style);
            createCell(row, columnCount++, user.getEamStudentInfo().getFullName(), style);
            createCell(row, columnCount++, user.getEamStudentInfo().getEmail(), style);
            createCell(row, columnCount++, user.getEamStudentInfo().getCellPhone(), style);
            createCell(row, columnCount++, user.getUserId(), style);
            createCell(row, columnCount++, "123456789", style);
             idx++;
        }
    }
     
    public String exportExcel(List<TsstUser> tsstUser) throws IOException {
        String randomUID = UUID.randomUUID().toString();

        String afterDest = randomUID.replaceAll("[\\-\\+\\.\\!\\@\\#\\$\\%\\&\\*\\(\\)\\`\\~\\;\\/\\|\\?\\^:,]", "");
        // Creating a PdfDocument object
        String dest = pathUploadDir + afterDest + ".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeaderLine(workbook);
        writeDataLines(workbook, tsstUser);
        try (FileOutputStream out = new FileOutputStream(dest)) {
            workbook.write(out);
        }
        workbook.close();
        return afterDest;

    }
    
}
