package com.nemo.kr.excel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.nemo.kr.dto.AccountDTO;



/**
  * @FileName : AccountExcel.java
  * @Date : 2020. 12. 29. 
  * @작성자 : Park YunSoo
  * @프로그램 설명 : 입출금내역 엑셀 다운
  */
public class AccountExcel extends AbstractXlsxStreamingView {
	private Font subjectFont;
	private Font titleFont;
	private Font cellFont;
	private CellStyle subjectFormat;
	private CellStyle titleFormatTan;
	private CellStyle titleFormat2;
	private CellStyle titleFormatTan2;
	private CellStyle titleFormat4;
	private CellStyle titleFormat5;
	private CellStyle cellFormatNormal;
	private CellStyle cellFormatTan;
	private CellStyle titleFormatLightGreen;
	private CellStyle cellFormat4;
	private CellStyle numberFormat;
	private CellStyle numberFormat2;
	private Sheet sheet1;
	private String[] tax_status = {"발행예정", "발행완료", "발행취소"}; //0:미평가  1:F  2:E  3:D  4:C  5:B  6:A	 
	private int rowNum = 0;
	private int totalColumn = 5;
	private String companySeq;
	private String companyName;
  
	@SuppressWarnings("unchecked")
	@Override
  	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = createFileName((String) model.get("fileName"));
	  
		setFileNameToResponse(request, response, fileName);

		this.sheet1 = workbook.createSheet("계산서");

		companyName = (String) model.get("companyName");
		// style 정의
		initSheetFormat(workbook);
	  
		// 상단 칼럼명 세팅 
		createLabelRow(sheet1);

		// 계산서 셋팅
		//createDataRow(sheet1, (List<AccountDTO>) model.get("list"), model.get("endDate").toString());
		createDataRow(sheet1, (List<AccountDTO>) model.get("resultList"));
	}

	private void initSheetFormat(Workbook workbook) throws Exception {
  		titleFont = workbook.createFont();
  		titleFont.setFontName("돋움");
  		titleFont.setFontHeight((short)(11*20));
  		titleFont.setColor(IndexedColors.AQUA.getIndex());
  		
  		cellFont = workbook.createFont();
  		cellFont.setFontName("돋움");
  		cellFont.setFontHeight((short)(11*20));
  		
//		cellFont = new WritableFont(Font.createFont("돋움"), 11);
//		titleFont = new WritableFont(Font.createFont("돋움"), 11);
//		titleFont.setColour(Colour.AQUA);
		
  		titleFormatTan = workbook.createCellStyle();
  		titleFormatTan.setFont(cellFont);
  		titleFormatTan.setAlignment(HorizontalAlignment.CENTER);
  		titleFormatTan.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormatTan.setFillForegroundColor(IndexedColors.TAN.getIndex());
  		titleFormatTan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		titleFormatTan.setBorderBottom(BorderStyle.THIN);
  		titleFormatTan.setBorderTop(BorderStyle.MEDIUM);
  		titleFormatTan.setBorderLeft(BorderStyle.THIN);
  		titleFormatTan.setBorderRight(BorderStyle.THIN);
  		titleFormatTan.setWrapText(true);
  		
//		titleFormatTan = new WritableCellFormat(cellFont);
//		titleFormatTan.setBorder(Border.ALL, BorderLineStyle.THIN);
//		titleFormatTan.setBorder(Border.TOP, BorderLineStyle.MEDIUM);
//		titleFormatTan.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		titleFormatTan.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		titleFormatTan.setBackground(Colour.TAN);
//		titleFormatTan.setWrap(true);
		
  		titleFormatTan2 = workbook.createCellStyle();
  		titleFormatTan2.setFont(titleFont);
  		titleFormatTan2.setAlignment(HorizontalAlignment.CENTER);
  		titleFormatTan2.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormatTan2.setFillForegroundColor(IndexedColors.TAN.getIndex());
  		titleFormatTan2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		titleFormatTan2.setBorderBottom(BorderStyle.THIN);
  		titleFormatTan2.setBorderTop(BorderStyle.MEDIUM);
  		titleFormatTan2.setBorderLeft(BorderStyle.THIN);
  		titleFormatTan2.setBorderRight(BorderStyle.THIN);
  		titleFormatTan2.setWrapText(true);
  		
//		titleFormatTan2 = new WritableCellFormat(titleFont);
//		titleFormatTan2.setBorder(Border.ALL, BorderLineStyle.THIN);
//		titleFormatTan2.setBorder(Border.TOP, BorderLineStyle.MEDIUM);
//		titleFormatTan2.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		titleFormatTan2.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		titleFormatTan2.setBackground(Colour.TAN);
//		titleFormatTan2.setWrap(true);
		
  		cellFormatNormal = workbook.createCellStyle();
  		cellFormatNormal.setFont(cellFont);
  		cellFormatNormal.setAlignment(HorizontalAlignment.LEFT);
  		cellFormatNormal.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormatNormal.setBorderBottom(BorderStyle.THIN);
  		cellFormatNormal.setBorderRight(BorderStyle.DOTTED);
  		
//		cellFormatNormal = new WritableCellFormat(cellFont);
//		cellFormatNormal.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
//		cellFormatNormal.setBorder(Border.RIGHT, BorderLineStyle.DOTTED);
//		cellFormatNormal.setAlignment(Alignment.LEFT);                      		// 셀 정렬
//		cellFormatNormal.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬

  		cellFormatTan = workbook.createCellStyle();
  		cellFormatTan.setFont(cellFont);
  		cellFormatTan.setAlignment(HorizontalAlignment.CENTER);
  		cellFormatTan.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormatTan.setFillForegroundColor(IndexedColors.TAN.getIndex());
  		cellFormatTan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		cellFormatTan.setBorderBottom(BorderStyle.THIN);
  		cellFormatTan.setBorderLeft(BorderStyle.THIN);
  		cellFormatTan.setBorderLeft(BorderStyle.DOTTED);
  		cellFormatTan.setWrapText(true);
  		
//		cellFormatTan = new WritableCellFormat(cellFont);
//		cellFormatTan.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
//		cellFormatTan.setBorder(Border.RIGHT, BorderLineStyle.THIN);
//		cellFormatTan.setBorder(Border.RIGHT, BorderLineStyle.DOTTED);
//		cellFormatTan.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		cellFormatTan.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormatTan.setBackground(Colour.TAN);
//		cellFormatTan.setWrap(true);
		
  		titleFormatLightGreen = workbook.createCellStyle();
  		titleFormatLightGreen.setFont(cellFont);
  		titleFormatLightGreen.setAlignment(HorizontalAlignment.CENTER);
  		titleFormatLightGreen.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormatLightGreen.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
  		titleFormatLightGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		titleFormatLightGreen.setBorderTop(BorderStyle.MEDIUM);
  		titleFormatLightGreen.setBorderRight(BorderStyle.THIN);
  		titleFormatLightGreen.setBorderBottom(BorderStyle.THIN);
  		titleFormatLightGreen.setBorderLeft(BorderStyle.THIN);
  		titleFormatLightGreen.setWrapText(true);
  		
//		titleFormatLightGreen = new WritableCellFormat(cellFont);
//		titleFormatLightGreen.setBorder(Border.ALL, BorderLineStyle.THIN);
//		titleFormatLightGreen.setBorder(Border.TOP, BorderLineStyle.MEDIUM);
//		titleFormatLightGreen.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		titleFormatLightGreen.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		titleFormatLightGreen.setBackground(Colour.LIGHT_GREEN);
//		titleFormatLightGreen.setWrap(true);
    
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		
//    	numberFormat = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//    	numberFormat.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//    	numberFormat.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
	}
	
	private void createLabelRow(Sheet sheet) throws Exception {
		int colNum = 0;
		Row row = sheet.createRow(rowNum);
		row.setHeight((short)500);
		//sheet.setRowView(rowNum, 500);
		rowNum ++;
		rowNum ++;
		rowNum ++;
		rowNum ++;
		rowNum ++;
		Row row2 = sheet.createRow(rowNum);
		row2.setHeight((short)930);
//		sheet.setRowView(rowNum, 930);
		
		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("전자(세금)계산서 종류\n(05::일반)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "전자(세금)계산서 종류\n(05::일반)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 20);
  		sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("작성일자");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//    	sheet.addCell(new jxl.write.Label(colNum, rowNum,   "작성일자", titleFormatTan));
//	    sheet.setColumnView(colNum++, 12);
	    
  		sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 등록번호\n(\"-\" 없이 입력)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 등록번호\n(\"-\" 없이 입력)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자\n종사업장번호");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자\n종사업장번호", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 상호");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 상호", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 성명");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 성명", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(30*256));
  		row2.createCell(colNum).setCellValue("공급자 사업장주소");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 사업장주소", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 30);
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급자 업태");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 업태", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급자 종목");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 종목", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 이메일");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 이메일", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 등록번호\n(\"-\" 없이 입력)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 등록번호\n(\"-\" 없이 입력)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 \n종사업장번호");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 \n종사업장번호", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 상호");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 상호", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 성명");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 성명", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(30*256));
  		row2.createCell(colNum).setCellValue("공급받는자 사업장주소");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 사업장주소", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 30);
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급받는자 업태");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 업태", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급받는자 종목");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 종목", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 이메일1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 이메일1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 이메일2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 이메일2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액", titleFormatTan));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(5*256));
  		row2.createCell(colNum).setCellValue("비고");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "비고", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 5);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("일자1\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자1\n(2자리, 작성년월 제외)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("품목1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액1");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액1", titleFormatTan));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("일자2\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자2\n(2자리, 작성년월 제외)", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("일자3\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자3\n(2자리, 작성년월 제외)", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("일자4\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자4\n(2자리, 작성년월 제외)", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("현금");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현금", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수표");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수표", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("어음");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "어음", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("외상미수금");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "외상미수금", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("영수(01),\n청구(02)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "영수(01),\n청구(02)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 10);
	    
	    ++rowNum;
	}

	private void createDataRow(Sheet sheet, List<AccountDTO> resultList) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			
			for (int i = 0; i < resultList.size(); i++) {
				AccountDTO item = resultList.get(i);
				
				int year = Integer.parseInt(item.getAccount_date().substring(0, 4));
				int month = Integer.parseInt(item.getAccount_date().substring(5, 7));
				
				cal.set(year, month - 1, 0);
				
				String beforeYear = df.format(cal.getTime()).substring(0, 4);
				String beforeMonth = df.format(cal.getTime()).substring(5, 7);
				
				cal.set(Integer.parseInt(beforeYear), (Integer.parseInt(beforeMonth) - 1), 1);
				
				String lastDay = String.valueOf(cal.getActualMaximum(Calendar.DATE));
				String accountDate = beforeYear + beforeMonth + lastDay;
				
				rowNum = createRow(sheet, item, accountDate, rowNum);
//				if(item.getCompany_seq().equals("1") && item.getAccount_type().equals("0") && (item.getAccount_flag().equals("2") || item.getAccount_flag().equals("3"))) {
//					rowNum = createRow(sheet, item, accountDate, rowNum);
//					System.out.println(1);
//				}else {
//					if(item.getAccount_type().equals("1") && item.getAccount_flag().equals("5")) {
//						rowNum = createRow(sheet, item, accountDate, rowNum);
//						System.out.println(2);
//					}else {
//						if(item.getDest_company_seq().equals("13") && item.getAccount_flag().equals("5")) {
//							rowNum = createRow(sheet, item, accountDate, rowNum);
//							System.out.println(3);
//						}
//						System.out.println(4);
//					}
//				}
				
				sheet.createRow(rowNum).setHeight((short)480);
//				sheet.setRowView(rowNum, 480);
			}
		}catch(Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String userAgent = request.getHeader("User-Agent");
		
		if(userAgent.indexOf("MSIE 5.5") >= 0) {
			response.setContentType("doesn/matter");
			response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
		}else {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		}
	}
	
	private String createFileName(String fileName) {
		try {
			// 파일제목 한글깨짐현상 수정 .getBytes("KSC5601"), "8859_1"
			fileName = new String((fileName).getBytes("KSC5601"), "8859_1");
		}catch (Exception e) {
			e.printStackTrace();
			
			fileName = "data.xls";
		}
		
		return fileName;
	}
	
	// 엑셀 로우 그리는 메소드
	private int createRow(Sheet sheet, AccountDTO item, String accountDate, int rowNum){
		int colNum = 0;
		int supplyValue = Integer.parseInt(item.getAccount_price()) + Integer.parseInt(item.getWorker_fee());
		Row row = sheet.createRow(rowNum);
		
		row.createCell(colNum).setCellValue("05");
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(0, rowNum,   "05", cellFormatTan));
		row.createCell(colNum).setCellValue(accountDate);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		//sheet.addCell(new jxl.write.Label(1, rowNum,   accountDate, cellFormatTan));
		
		if(item.getCompany_num() != null) {
			row.createCell(colNum).setCellValue(item.getCompany_num().replace("-", ""));
		}else {
			row.createCell(colNum).setCellValue("");
		}
		
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(2, rowNum,   item.getCompany_num().replace("-", ""), cellFormatTan));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(3, rowNum,   "", cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getBusiness_name());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(4, rowNum,   item.getBusiness_name(), cellFormatTan));
		row.createCell(colNum).setCellValue(item.getCompany_owner());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(5, rowNum,   item.getCompany_owner(), cellFormatTan));
		row.createCell(colNum).setCellValue(item.getCompany_addr());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(6, rowNum,   item.getCompany_addr(), cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getCompany_business());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(7, rowNum,   item.getCompany_business(), cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getCompany_sector());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(8, rowNum,   item.getCompany_sector(), cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getCompany_email());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(9, rowNum,   item.getCompany_email(), cellFormatNormal));
		if(item.getDest_company_num() != null) {
			row.createCell(colNum).setCellValue(item.getDest_company_num().replaceAll("-",  ""));
		}else {
			row.createCell(colNum).setCellValue("");
		}
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(10, rowNum,  item.getDest_company_num().replaceAll("-",  ""), cellFormatTan));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(11, rowNum,   "", cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getDest_business_name());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(12, rowNum,  item.getDest_business_name(), cellFormatTan));
		row.createCell(colNum).setCellValue(item.getDest_company_owner());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(13, rowNum,  item.getDest_company_owner(), cellFormatTan));
		row.createCell(colNum).setCellValue(item.getDest_company_addr());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(14, rowNum,  item.getDest_company_addr(), cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getDest_company_business());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(15, rowNum,  item.getDest_company_business(), cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getDest_company_sector());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(16, rowNum,  item.getDest_company_sector(), cellFormatNormal));
		row.createCell(colNum).setCellValue(item.getDest_company_email());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(17, rowNum,  item.getDest_company_email(), cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(18, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue(supplyValue);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(19, rowNum,  item.getAccount_price(), cellFormatTan));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(20, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue(accountDate.substring(6));
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(21, rowNum,  accountDate.substring(6), cellFormatTan));
		row.createCell(colNum).setCellValue("수수료");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(22, rowNum,  "수수료", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(23, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(24, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(25, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue(supplyValue);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(26, rowNum,  item.getAccount_price(), cellFormatTan));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(27, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(28, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(29, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(30, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(31, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(32, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(33, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(34, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(35, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(36, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(37, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(38, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(39, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(40, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(41, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(42, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(43, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(44, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(45, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(46, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(47, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(48, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(49, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(50, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(51, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
//		sheet.addCell(new jxl.write.Label(52, rowNum,  "", cellFormatNormal));
		row.createCell(colNum).setCellValue("02");
		row.getCell(colNum++).setCellStyle(cellFormatTan);
//		sheet.addCell(new jxl.write.Label(53, rowNum,  "02", cellFormatTan));
		
		rowNum++;
		
		return rowNum;
	}
}	