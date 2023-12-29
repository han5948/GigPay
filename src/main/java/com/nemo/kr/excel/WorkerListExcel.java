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

import com.nemo.kr.dto.WorkerDTO;


public class WorkerListExcel extends AbstractXlsxStreamingView {
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
	private String[] worker_app_use_status_kr = {"미설치", "승인", "APP탈퇴", "관중", "승인대기", "수수료미납", "도출자"};//0: 대기(미설치)  1:승인(전체사용가능) 2:APP탈퇴  3:관중(app로그인도 안됨) 4:승인대기 5:수수료미납
	private String[] worker_app_status_kr = {"미설치", "LOGIN", "LOGOUT"}; //0:미설치 1:로그인 2:로그아웃 
	private String[] worker_rating = {"미평가", "F", "E", "D", "C", "B", "A"}; //0:미평가  1:F  2:E  3:D  4:C  5:B  6:A	 
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
	  
		companyName 	= (String) model.get("companyName");
		//style 정의
		initSheetFormat(workbook);

		// 상단 칼럼명 세팅 
		createLabelRow(sheet1);

		//스크랩리스트 셋팅
		createDataRow(sheet1, (List<WorkerDTO>) model.get("resultList"));
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
  		numberFormat2.setFont(cellFont);
  		numberFormat2.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		numberFormat2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
  		numberFormat2.setBorderTop(BorderStyle.THIN);
  		numberFormat2.setBorderRight(BorderStyle.THIN);
  		numberFormat2.setBorderBottom(BorderStyle.THIN);
  		numberFormat2.setBorderLeft(BorderStyle.THIN);
  		
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
		
  		titleFormat2 = workbook.createCellStyle();
  		titleFormat2.setFont(titleFont);
  		titleFormat2.setAlignment(HorizontalAlignment.LEFT);
  		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
   
  		titleFormat3 = workbook.createCellStyle();
  		titleFormat3.setFont(titleFont);
  		titleFormat3.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat3.setWrapText(true);
    
  		titleFormat4 = workbook.createCellStyle();
  		titleFormat4.setFont(titleFont);
  		titleFormat4.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat4.setWrapText(true);
  		titleFormat4.setBorderBottom(BorderStyle.DOUBLE);
    
  		cellFormat = workbook.createCellStyle();
  		cellFormat.setFont(cellFont);
  		cellFormat.setAlignment(HorizontalAlignment.LEFT);
  		cellFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat.setWrapText(true); 

  		cellFormat2 = workbook.createCellStyle();
  		cellFormat2.setFont(cellFont);
  		cellFormat2.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat2.setWrapText(true);

  		cellFormat3 = workbook.createCellStyle();
  		cellFormat3.setFont(cellFont);
  		cellFormat3.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat3.setWrapText(true);
  		cellFormat3.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
  		cellFormat3.setBorderBottom(BorderStyle.DOUBLE);

  		cellFormat4 = workbook.createCellStyle();
  		cellFormat4.setFont(cellFont);
  		cellFormat4.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat4.setWrapText(true);
    
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
    
  		numberFormat2 = workbook.createCellStyle();
  		numberFormat2.setFont(cellFont);
  		numberFormat2.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		numberFormat2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
  		numberFormat2.setBorderTop(BorderStyle.THIN);
  		numberFormat2.setBorderRight(BorderStyle.THIN);
  		numberFormat2.setBorderBottom(BorderStyle.THIN);
  		numberFormat2.setBorderLeft(BorderStyle.THIN);
	}

	private void createLabelRow(Sheet sheet) throws Exception {
	    String title = companyName + "구직자 관리";
	    int colNum = 0;
	    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 29));
    	
	    Row row = sheet.createRow(rowNum);
	    row.setHeight((short)500);
	    Cell cell = row.createCell(0);
  		cell.setCellStyle(subjectFormat);
  		cell.setCellValue(title);
//		sheet.addCell(new jxl.write.Label(0, rowNum,   title, subjectFormat));
//		sheet.setRowView(rowNum, 500);
	    
		rowNum ++;
//		sheet.setRowView(rowNum, 500);
  		Row row1 = sheet.createRow(rowNum);
  		row1.setHeight((short)500);
		
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("번호");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
	    
  		sheet.setColumnWidth(colNum, (short)(40*256));
  		row1.createCell(colNum).setCellValue("지점");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
	    
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("근로자명");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
	    
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("소장평가");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
	    
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("출역");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("os");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("핸드폰");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("주민번호");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(40*256));
  		row1.createCell(colNum).setCellValue("주민주소");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(40*256));
  		row1.createCell(colNum).setCellValue("일주소(모바일)");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(40*256));
  		row1.createCell(colNum).setCellValue("전문분야");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(15*256));
  		row1.createCell(colNum).setCellValue("안전교육");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("혈압");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("특징");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("메모");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("은행명");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("계좌번호");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("예금주");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("연락처1");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("연락처2");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("연락처3");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("또가요");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("완료");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(30*256));
  		row1.createCell(colNum).setCellValue("마지막 출역 일자");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("APP상태");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("Login상태");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("앱버젼");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("상태");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
  		
	    sheet.setColumnWidth(colNum, (short)(20*256));
  		row1.createCell(colNum).setCellValue("등록일시");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);

  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row1.createCell(colNum).setCellValue("등록자");
  		row1.getCell(colNum++).setCellStyle(titleFormat1);
	    //sheet.setRowView(rowNum, 500);
	    
	    ++rowNum;
	}

	private void createDataRow(Sheet sheet, List<WorkerDTO> resultList) throws Exception {
		try {
			int colNum = 0;
			StringBuffer ilboCount = new StringBuffer();
			for (int i = 0; i < resultList.size(); i++) {
				WorkerDTO item = resultList.get(i);
				
				Row row = sheet.createRow(rowNum);
				row.createCell(colNum).setCellValue((i+1));
				row.getCell(colNum++).setCellStyle(numberFormat);
				row.createCell(colNum).setCellValue(item.getCompany_name());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_name());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(worker_rating[Integer.parseInt(item.getWorker_rating())]);
				row.getCell(colNum++).setCellStyle(cellFormat2);
				
				ilboCount.setLength(0);
				if( Integer.parseInt(item.getIlbo_total_count()) > 0 ) {
					ilboCount.append(item.getIlbo_output_count());
					ilboCount.append("/");
					ilboCount.append(item.getIlbo_total_count());
				}
				row.createCell(colNum).setCellValue(ilboCount.toString());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getOs_type());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_phone());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_jumin());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_addr());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_ilgaja_addr());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_job_name());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_OSH_yn().equals("0") ? "미이수" : "이수");
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_blood_pressure());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_feature());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_memo());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_bank_name());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_bank_account());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_bank_owner());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_tel1());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_tel2());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getWorker_tel3());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getRe_count());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getComplete_count());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getIlbo_last_date());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(worker_app_use_status_kr[Integer.parseInt(item.getWorker_app_use_status())]);
				row.getCell(colNum++).setCellStyle(cellFormat2);
				if( Integer.parseInt(item.getWorker_app_status()) < 3 ) {
					row.createCell(colNum).setCellValue(worker_app_status_kr[Integer.parseInt(item.getWorker_app_status())]);
					row.getCell(colNum++).setCellStyle(cellFormat2);
				}else {
					row.createCell(colNum).setCellValue(item.getWorker_app_status());
					row.getCell(colNum++).setCellStyle(cellFormat2);
				}
				row.createCell(colNum).setCellValue(item.getApp_version());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getUse_yn().equals("0") ? "미사용" : "사용");
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getReg_date());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				row.createCell(colNum).setCellValue(item.getReg_admin());
				row.getCell(colNum++).setCellStyle(cellFormat2);
				
				row.setHeight((short)400);
			  
				colNum = 0;
				rowNum++;	  
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
			sheet.createRow(rowNum).setHeight((short)400);
		}catch(Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
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
