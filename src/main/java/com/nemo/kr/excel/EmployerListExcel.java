package com.nemo.kr.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
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

import com.nemo.kr.dto.EmployerDTO;


public class EmployerListExcel extends AbstractXlsxStreamingView {
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
//  		sheet1.setColumnView(0, 20);	 // column width 지정
//  		sheet1.setColumnView(1, 20);
//  		sheet1.setColumnView(2, 20);
//  		sheet1.setColumnView(3, 20);
//  		sheet1.setColumnView(4, 20);
//  		sheet1.setColumnView(5, 20);
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
  		createLabelRow(sheet1, (EmployerDTO) model.get("employerDTO"));

  		//스크랩리스트 셋팅
  		createDataRow(sheet1,  (EmployerDTO) model.get("employerDTO"), (List<EmployerDTO>) model.get("resultList"));
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
  		
//  		subjectFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
//  		titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
//  		cellFont = new WritableFont(WritableFont.ARIAL, 10);
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
  		
//  		subjectFormat = new WritableCellFormat(subjectFont);
//  		subjectFormat.setAlignment(Alignment.CENTRE);                 	// 셀 정렬
//  		subjectFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 정렬
//  		subjectFormat.setBorder(Border.ALL, BorderLineStyle.THIN);   // 보더와 보더라인스타일 설정
//  		subjectFormat.setBackground(Colour.LIGHT_BLUE);              // 배경색 설정

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
  		
//  		titleFormat1 = new WritableCellFormat(titleFont);
//  		titleFormat1.setAlignment(Alignment.CENTRE);                   	// 셀 정렬
//  		titleFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//  		titleFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);     	// 보더와 보더라인스타일 설정
//  		titleFormat1.setBackground(Colour.GRAY_25);                    	// 배경색 설정
  		
  		titleFormat2 = workbook.createCellStyle();
  		titleFormat2.setFont(titleFont);
  		titleFormat2.setAlignment(HorizontalAlignment.LEFT);
  		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		
//  		titleFormat2 = new WritableCellFormat(titleFont);
//  		titleFormat2.setAlignment(Alignment.LEFT);                      // 셀 정렬
//  		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//  		titleFormat2.setBorder(Border.NONE, BorderLineStyle.NONE);      // 보더와 보더라인스타일 설정
  		
  		titleFormat3 = workbook.createCellStyle();
  		titleFormat3.setFont(titleFont);
  		titleFormat3.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat3.setWrapText(true);
  		
//  		titleFormat3 = new WritableCellFormat(titleFont);
//  		titleFormat3.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//  		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//  		titleFormat3.setWrap(true); 
  		
  		titleFormat4 = workbook.createCellStyle();
  		titleFormat4.setFont(titleFont);
  		titleFormat4.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat4.setWrapText(true);
  		titleFormat4.setBorderBottom(BorderStyle.DOUBLE);
  		
//  		titleFormat4 = new WritableCellFormat(titleFont);
//  		titleFormat4.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//  		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//  		titleFormat4.setWrap(true); 
//  		titleFormat4.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
  		
  		cellFormat = workbook.createCellStyle();
  		cellFormat.setFont(cellFont);
  		cellFormat.setAlignment(HorizontalAlignment.LEFT);
  		cellFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat.setWrapText(true);
  		
//  		cellFormat = new WritableCellFormat(cellFont);
//  		cellFormat.setAlignment(Alignment.LEFT);                      		// 셀 정렬
//  		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//  		cellFormat.setWrap(true); 
  		
  		cellFormat2 = workbook.createCellStyle();
  		cellFormat2.setFont(cellFont);
  		cellFormat2.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat2.setWrapText(true);
  		
//  		cellFormat2 = new WritableCellFormat(cellFont);
//  		cellFormat2.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//  		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//  		cellFormat2.setWrap(true); 
  		
  		cellFormat3 = workbook.createCellStyle();
  		cellFormat3.setFont(cellFont);
  		cellFormat3.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat3.setWrapText(true);
  		cellFormat3.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
  		cellFormat3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		cellFormat3.setBorderBottom(BorderStyle.DOUBLE);
  		
//  		cellFormat3 = new WritableCellFormat(cellFont);
//  		cellFormat3.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//  		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//  		cellFormat3.setWrap(true); 
//  		cellFormat3.setBackground(Colour.PALE_BLUE);                    	// 배경색 설정
//  		cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
  		
  		cellFormat4 = workbook.createCellStyle();
  		cellFormat4.setFont(cellFont);
  		cellFormat4.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat4.setWrapText(true);
  		cellFormat4.setBorderBottom(BorderStyle.DOUBLE);
  		
//  		cellFormat4 = new WritableCellFormat(cellFont);
//  		cellFormat4.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//  		cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//  		cellFormat4.setWrap(true); 
//  		cellFormat4.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
  		
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		
//  		numberFormat = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//  		numberFormat.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
  		
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
  		
//  		numberFormat2 = new WritableCellFormat(titleFont, NumberFormats.THOUSANDS_INTEGER);
//  		numberFormat2.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//  		numberFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);     	// 보더와 보더라인스타일 설정
//  		numberFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
//  		numberFormat2.setBackground(Colour.GRAY_25);                    	// 배경색 설정
  	}

  	private void createLabelRow(Sheet sheet,  EmployerDTO employerDTO) throws Exception {
  		String title = companyName + "구인처관리";
	    int colNum = 0;
	    if( employerDTO.getAdminLevel().equals("0") ) {
	    	sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 23));
	    	//sheet.mergeCells(0, rowNum, 22, rowNum); // 시작열, 시작행, 종료열, 종료행
	    }else {
	    	sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 22));
	    	//sheet.mergeCells(0, rowNum, 21, rowNum); 
	    }
	    Row row = sheet.createRow(rowNum);
	    row.setHeight((short)500);
	    row.createCell(colNum).setCellValue(title);
	    row.getCell(colNum).setCellStyle(subjectFormat);
//		sheet.addCell(new jxl.write.Label(0, rowNum,   title, subjectFormat));
//		sheet.setRowView(rowNum, 500);
	    
		rowNum ++;
		Row row1 = sheet.createRow(rowNum);
		row1.setHeight((short)500);
		//sheet.setRowView(rowNum, 500);
		
		sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("번호");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "번호", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    if( employerDTO.getAdminLevel().equals("0") ) {
	    	sheet.setColumnWidth(colNum, (short)(30*256));
			row1.createCell(colNum).setCellValue("지점");
			row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    	sheet.addCell(new jxl.write.Label(colNum, rowNum,   "지점", titleFormat1));
//		    sheet.setColumnView(colNum++, 30);
	    }
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("출역");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "출역", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
		sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("총출역");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "총출역", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("현장수");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현장수", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("회사명");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "회사명", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("사업자등록번호");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "사업자등록번호", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("대표자");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "대표자", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("업태");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "업태", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("업종");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "업종", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("면세요청");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "면세요청", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    sheet.setColumnWidth(colNum, (short)(15*256));
		row1.createCell(colNum).setCellValue("담당자");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "담당자", titleFormat1));
//	    sheet.setColumnView(colNum++, 15);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("전화");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "전화", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("전화번호");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "전화번호", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("팩스");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "팩스", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(30*256));
		row1.createCell(colNum).setCellValue("이메일");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "이메일", titleFormat1));
//	    sheet.setColumnView(colNum++, 30);
	    
	    sheet.setColumnWidth(colNum, (short)(40*256));
		row1.createCell(colNum).setCellValue("주소");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "주소", titleFormat1));
//	    sheet.setColumnView(colNum++, 40);
	    
	    sheet.setColumnWidth(colNum, (short)(20*256));
		row1.createCell(colNum).setCellValue("특징");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "특징", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
	    
	    sheet.setColumnWidth(colNum, (short)(20*256));
		row1.createCell(colNum).setCellValue("메모");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "메모", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
	    
	    sheet.setColumnWidth(colNum, (short)(20*256));
		row1.createCell(colNum).setCellValue("마지막출역일자");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "마지막출역일자", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("상태");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "상태", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    sheet.setColumnWidth(colNum, (short)(20*256));
		row1.createCell(colNum).setCellValue("등록일시");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "등록일시", titleFormat1));
//	    sheet.setColumnView(colNum++, 20);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("등록자");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "등록자", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
		row1.createCell(colNum).setCellValue("소유자");
		row1.getCell(colNum++).setCellStyle(titleFormat1);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "소유자", titleFormat1));
//	    sheet.setColumnView(colNum++, 10);
	    
	    //sheet.setRowView(rowNum, 500);
	    
	    ++rowNum;
  	}

  	private void createDataRow(Sheet sheet, EmployerDTO employerDTO, List<EmployerDTO> resultList) throws Exception {
  		int colNum = 0;
	  
  		for (int i = 0; i < resultList.size(); i++) {
  			EmployerDTO item = resultList.get(i);
  			Row row = sheet.createRow(rowNum);
  			
  			row.createCell(colNum).setCellValue((i+1));
			row.getCell(colNum++).setCellStyle(numberFormat);
  			//sheet.addCell(new jxl.write.Number(colNum, rowNum,   (i+1), numberFormat));
  			if( employerDTO.getAdminLevel().equals("0") ) {
  				row.createCell(colNum).setCellValue(item.getCompany_name());
  				row.getCell(colNum++).setCellStyle(cellFormat2);
  				//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getCompany_name(), cellFormat2));
  			}
  			
  			row.createCell(colNum).setCellValue(Integer.parseInt(item.getOut_count()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getOut_count()), cellFormat2));
  			row.createCell(colNum).setCellValue(Integer.parseInt(item.getTotal_out_count()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getTotal_out_count()), cellFormat2));
  			row.createCell(colNum).setCellValue(Integer.parseInt(item.getWork_count()));
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getWork_count()), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_name(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_num());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_num(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_owner());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_owner(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_business());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_business(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_sector());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_sector(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getIs_tax().equals("1") ? "요청" : "");
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getIs_tax().equals("1") ? "요청" : "", cellFormat2));
  			row.createCell(colNum).setCellValue(item.getTax_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getTax_name(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getTax_phone());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getTax_phone(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_tel());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_tel(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_fax());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_fax(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_email());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_email(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_addr());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_addr(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_feature());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_feature(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getEmployer_memo());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getEmployer_memo(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getIlbo_last_date());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getIlbo_last_date(), cellFormat2));
  			row.createCell(colNum).setCellValue(item.getUse_yn());
			row.getCell(colNum++).setCellStyle(cellFormat2);
  			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getUse_yn(), cellFormat2));
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
