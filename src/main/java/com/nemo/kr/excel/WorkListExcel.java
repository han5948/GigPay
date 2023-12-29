package com.nemo.kr.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.nemo.kr.dto.WorkDTO;


public class WorkListExcel extends AbstractXlsxStreamingView {
	private static final double CELL_DEFAULT_HEIGHT = 50;
	private static final double CELL_DEFAULT_WIDTH = 90;
	private Font subjectFont;
	private Font titleFont;
	private Font cellFont;
	private CellStyle subjectFormat;
	private CellStyle titleFormat1;
	private CellStyle titleFormat2;
	private CellStyle titleFormat3;
	private CellStyle titleFormat4;
	private CellStyle titleFormat5;
	private CellStyle cellFormat;
	private CellStyle cellFormat2;
	private CellStyle cellFormat3;
	private CellStyle cellFormat4;
	private CellStyle numberFormat;
	private CellStyle numberFormat2;
	
	private Sheet sheet1;
	private int rowNum = 0;
	private int totalColumn = 5;
	private String companySeq;
	private String companyName;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = createFileName((String) model.get("fileName"));
	  
		setFileNameToResponse(request, response, fileName);

		this.sheet1 = workbook.createSheet("scrapList");
//		sheet1.setColumnView(0, 20);	 // column width 지정
//		sheet1.setColumnView(1, 20);
//		sheet1.setColumnView(2, 20);
//		sheet1.setColumnView(3, 20);
//		sheet1.setColumnView(4, 20);
//		sheet1.setColumnView(5, 20);
		sheet1.setColumnWidth(0, 20);
		sheet1.setColumnWidth(1, 20);
		sheet1.setColumnWidth(2, 20);
		sheet1.setColumnWidth(3, 20);
		sheet1.setColumnWidth(4, 20);
		sheet1.setColumnWidth(5, 20);
		/*
		 * sheet1.setColumnView(6, 40); sheet1.setColumnView(7, 20);
		 * sheet1.setColumnView(8, 20);
		 */
	  
		companySeq 	= (String) model.get("companySeq");
		companyName 	= (String) model.get("companyName");
	  
		//style 정의
		initSheetFormat(workbook);

		// 상단 칼럼명 세팅 
		createLabelRow(sheet1, (WorkDTO) model.get("workDTO"));

		//스크랩리스트 셋팅
		createDataRow(sheet1,  (WorkDTO) model.get("workDTO"), (List<WorkDTO>) model.get("resultList"));
	}

	private void initSheetFormat(Workbook workbook) throws Exception {
		
		subjectFont = workbook.createFont();
  		subjectFont.setFontName("Arial");
  		subjectFont.setFontHeight((short)(16*20));
  		subjectFont.setBold(true);
  		subjectFont.setColor(IndexedColors.WHITE.getIndex());
  		
  		titleFont = workbook.createFont();
  		titleFont.setFontName("Arial");
  		titleFont.setBold(true);
  		titleFont.setFontHeight((short)(10*20));
  		
  		cellFont = workbook.createFont();
  		cellFont.setFontName("Arial");
  		cellFont.setFontHeight((short)(10*20));
		
//		subjectFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
//		titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
//		cellFont = new WritableFont(WritableFont.ARIAL, 10);
  		
  		subjectFormat = workbook.createCellStyle();
  		subjectFormat.setFont(subjectFont);
  		subjectFormat.setAlignment(HorizontalAlignment.CENTER);
  		subjectFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		subjectFormat.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
  		subjectFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		subjectFormat.setBorderBottom(BorderStyle.THIN);
  		subjectFormat.setBorderTop(BorderStyle.THIN);
  		subjectFormat.setBorderLeft(BorderStyle.THIN);
  		subjectFormat.setBorderRight(BorderStyle.THIN);
  		
//		subjectFormat = new WritableCellFormat(subjectFont);
//		subjectFormat.setAlignment(Alignment.CENTRE);                 	// 셀 정렬
//		subjectFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 정렬
//		subjectFormat.setBorder(Border.ALL, BorderLineStyle.THIN);   // 보더와 보더라인스타일 설정
//		subjectFormat.setBackground(Colour.LIGHT_BLUE);              // 배경색 설정

  		titleFormat1 = workbook.createCellStyle();
  		titleFormat1.setFont(titleFont);
  		titleFormat1.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat1.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
  		titleFormat1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		titleFormat1.setBorderBottom(BorderStyle.THIN);
  		titleFormat1.setBorderTop(BorderStyle.THIN);
  		titleFormat1.setBorderLeft(BorderStyle.THIN);
  		titleFormat1.setBorderRight(BorderStyle.THIN);
  		
//		titleFormat1 = new WritableCellFormat(titleFont);
//		titleFormat1.setAlignment(Alignment.CENTRE);                   	// 셀 정렬
//		titleFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);     	// 보더와 보더라인스타일 설정
//		titleFormat1.setBackground(Colour.GRAY_25);                    	// 배경색 설정

  		titleFormat2 = workbook.createCellStyle();
  		titleFormat2.setFont(titleFont);
  		titleFormat2.setAlignment(HorizontalAlignment.LEFT);
  		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
//  		titleFormat2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		titleFormat2 = new WritableCellFormat(titleFont);
//		titleFormat2.setAlignment(Alignment.LEFT);                      // 셀 정렬
//		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat2.setBorder(Border.NONE, BorderLineStyle.NONE);      // 보더와 보더라인스타일 설정
   
  		titleFormat3 = workbook.createCellStyle();
  		titleFormat3.setFont(titleFont);
  		titleFormat3.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat3.setWrapText(true);
  		
//		titleFormat3 = new WritableCellFormat(titleFont);
//		titleFormat3.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		titleFormat3.setWrap(true); 
    
  		titleFormat4 = workbook.createCellStyle();
  		titleFormat4.setFont(titleFont);
  		titleFormat4.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat4.setWrapText(true);
  		titleFormat4.setBorderBottom(BorderStyle.DOUBLE);
  		
//		titleFormat4 = new WritableCellFormat(titleFont);
//		titleFormat4.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		titleFormat4.setWrap(true); 
//		titleFormat4.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
    	
  		cellFormat = workbook.createCellStyle();
  		cellFormat.setFont(cellFont);
  		cellFormat.setAlignment(HorizontalAlignment.LEFT);
  		cellFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat.setWrapText(true);
  		
//		cellFormat = new WritableCellFormat(cellFont);
//		cellFormat.setAlignment(Alignment.LEFT);                      		// 셀 정렬
//    	cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//    	cellFormat.setWrap(true); 

  		cellFormat2 = workbook.createCellStyle();
  		cellFormat2.setFont(cellFont);
  		cellFormat2.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat2.setWrapText(true);
  		
//    	cellFormat2 = new WritableCellFormat(cellFont);
//    	cellFormat2.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//    	cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//    	cellFormat2.setWrap(true); 

  		cellFormat3 = workbook.createCellStyle();
  		cellFormat3.setFont(cellFont);
  		cellFormat3.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat3.setWrapText(true);
  		cellFormat3.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
  		cellFormat3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		cellFormat3.setBorderBottom(BorderStyle.DOUBLE);
  		
//    	cellFormat3 = new WritableCellFormat(cellFont);
//    	cellFormat3.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//    	cellFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//    	cellFormat3.setWrap(true); 
//    	cellFormat3.setBackground(Colour.PALE_BLUE);                    	// 배경색 설정
//    	cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정

  		cellFormat4 = workbook.createCellStyle();
  		cellFormat4.setFont(cellFont);
  		cellFormat4.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat4.setWrapText(true);
  		cellFormat4.setBorderBottom(BorderStyle.DOUBLE);
  		
//    	cellFormat4 = new WritableCellFormat(cellFont);
//    	cellFormat4.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//    	cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//    	cellFormat4.setWrap(true); 
//    	cellFormat4.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
    
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		
//    	numberFormat = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//    	numberFormat.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//    	numberFormat.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
    
  		numberFormat2 = workbook.createCellStyle();
  		numberFormat2.setFont(titleFont);
  		numberFormat2.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		numberFormat2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
  		numberFormat2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		numberFormat2.setBorderTop(BorderStyle.THIN);
  		numberFormat2.setBorderRight(BorderStyle.THIN);
  		numberFormat2.setBorderBottom(BorderStyle.THIN);
  		numberFormat2.setBorderLeft(BorderStyle.THIN);
  		
//    	numberFormat2 = new WritableCellFormat(titleFont, NumberFormats.THOUSANDS_INTEGER);
//    	numberFormat2.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//    	numberFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);     	// 보더와 보더라인스타일 설정
//    	numberFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
//    	numberFormat2.setBackground(Colour.GRAY_25);                    	// 배경색 설정
	}

	private void createLabelRow(Sheet sheet,  WorkDTO workDTO) throws Exception {
	    String title = companyName + "현장관리";
	    int colNum = 0;
	    if( workDTO.getAdminLevel().equals("0") ) {
	    	sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 26));
	    	//sheet.mergeCells(0, rowNum, 25, rowNum); // 시작열, 시작행, 종료열, 종료행
	    }else {
	    	sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 25));
	    	//sheet.mergeCells(0, rowNum, 24, rowNum); 
	    }
	    
	    Row row = sheet.createRow(rowNum++);
	    row.setHeight((short)500);
	    Cell cell = row.createCell(0);
	    cell.setCellValue(title);
  		cell.setCellStyle(subjectFormat);
//		sheet.addCell(new jxl.write.Label(0, rowNum,   title, subjectFormat));
//		sheet.setRowView(rowNum, 500);
	    
  		Row row1 = sheet.createRow(rowNum);
  		row1.setHeight((short)500);
 	    Cell cell1 = row1.createCell(colNum);
   		cell1.setCellStyle(titleFormat1);
   		cell1.setCellValue("번호");
   		sheet.setColumnWidth(colNum++, (short)(10*256));
		
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "번호", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
   		Row row2 = row1;
	    if( workDTO.getAdminLevel().equals("0") ) {
	    	sheet.setColumnWidth(colNum, (short)(30*256));
	  		row2.createCell(colNum).setCellValue("지점");
	  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    	sheet.addCell(new jxl.write.Label(colNum, rowNum,   "지점", titleFormat1));
//		    sheet.setColumnView(colNum++, 30);
	    }
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
  		row2.createCell(colNum).setCellValue("구인처 명");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "구인처 명", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
  		sheet.setColumnWidth(colNum, (short)(40*256));
  		row2.createCell(colNum).setCellValue("현장 명");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장명", titleFormat1));
//	    sheet.setColumnView(colNum++, 40);
	    
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("출역");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "출역", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("총출역");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "총출역", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("현장도착시간");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장도착시간", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("현장마감시간");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장마감시간", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(40*256));
  		row2.createCell(colNum).setCellValue("현장주소");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장주소", titleFormat1));
//	    sheet.setColumnView(colNum++, 40);
  		sheet.setColumnWidth(colNum, (short)(40*256));
  		row2.createCell(colNum).setCellValue("작업설명");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "작업설명", titleFormat1));
//	    sheet.setColumnView(colNum++, 40);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("조식유무");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "조식유무", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("최저나이");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "최저나이", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("최고나이");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "최고나이", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("혈압제한");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "혈압제한", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("작업자정보");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "작업자정보", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("현장매니저");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장매니저", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("현장매니저전화");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장매니저전화", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("현장 담당자");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장 담당자", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("현장 전화");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장 전화", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("현장 메모");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장 메모", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("현장 팩스");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장 팩스", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("현장 담당자 이메일");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장 담당자 이메일", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("마지막 출역 일자");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "마지막 출역 일자", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("상태");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "상태", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("등록일시");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "등록일시", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("등록자");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "등록자", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("소유자");
  		row2.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "소유자", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    //sheet.setRowView(rowNum, 500);
	    
	    ++rowNum;
	}

	private void createDataRow(Sheet sheet, WorkDTO workDTO, List<WorkDTO> resultList) throws Exception {
		int colNum = 0;
		for (int i = 0; i < resultList.size(); i++) {
			WorkDTO item = resultList.get(i);
		  
			Row row = sheet.createRow(rowNum);
			row.createCell(colNum).setCellValue((i+1));
			row.getCell(colNum++).setCellStyle(numberFormat);
			//sheet.addCell(new jxl.write.Number(colNum, rowNum,   (i+1), numberFormat));
			if( workDTO.getAdminLevel().equals("0") ) {
				row.createCell(colNum).setCellValue(item.getCompany_name());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getCompany_name(), cellFormat2));
			}
			row.createCell(colNum).setCellValue(item.getEmployer_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_name(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_name(), cellFormat2));
			row.createCell(colNum).setCellValue(Integer.parseInt(item.getOut_count()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getOut_count()), cellFormat2));
			row.createCell(colNum).setCellValue(Integer.parseInt(item.getTotal_out_count()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getTotal_out_count()), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_arrival());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_arrival(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_finish());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_finish(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_addr());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_addr(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_memo());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_memo(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_breakfast_yn().equals("0") ? "무" : "유");
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_breakfast_yn().equals("0") ? "무" : "유", cellFormat2));
			row.createCell(colNum).setCellValue(Integer.parseInt(item.getWork_age_min()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getWork_age_min()), cellFormat2));
			row.createCell(colNum).setCellValue(Integer.parseInt(item.getWork_age()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getWork_age()), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_blood_pressure());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_blood_pressure(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getIs_worker_info() == null ? "" : "허용");
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getIs_worker_info() == null ? "" : "허용", cellFormat2));
			row.createCell(colNum).setCellValue(item.getManager_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getManager_name(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getManager_phone());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getManager_phone(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_manager_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_manager_name(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_manager_phone());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_manager_phone(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_comment());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_comment(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_manager_fax());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_manager_fax(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getWork_manager_email());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getWork_manager_email(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getIlbo_last_date());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getIlbo_last_date(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getUse_yn().equals("0") ? "미사용" : "사용");
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getUse_yn().equals("0") ? "미사용" : "사용", cellFormat2));
			row.createCell(colNum).setCellValue(item.getReg_date());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getReg_date(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getReg_admin());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getReg_admin(), cellFormat2));
			row.createCell(colNum).setCellValue(item.getOwner_id());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getOwner_id(), cellFormat2));
			
			row.setHeight((short)400);
			//sheet.setRowView(rowNum, 400);
		  
			colNum = 0;
			rowNum++;	  
		}
	  
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
		//sheet.mergeCells(0, rowNum, 2, rowNum); // 시작열, 시작행, 종료열, 종료행

		sheet.createRow(rowNum).setHeight((short)400);
		//sheet.setRowView(rowNum, 400);
		
	}

	private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String userAgent = request.getHeader("User-Agent");

		if ( userAgent.indexOf("MSIE 5.5") >= 0 ) {
			response.setContentType("doesn/matter");
			response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		}
  /* 
    response.setContentLength(workbook.getBytes().length);
    response.setContentLength(res)*/
	}

	private String createFileName(String filename) {
		try {
			// 파일제목 한글깨짐현상 수정 .getBytes("KSC5601"), "8859_1"
			filename = new String((filename).getBytes("KSC5601"), "8859_1");
		} catch (Exception e) {
			e.printStackTrace();

			filename = "data.xls";
		}

		return filename;
	}
}

