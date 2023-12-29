package com.nemo.kr.excel;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.ibatis.io.Resources;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.util.FileCoder;
//import com.nemo.kr.util.StringUtil;

public class LaborExpensesExcel extends AbstractXlsxView {
	private static final double CELL_DEFAULT_HEIGHT = 50;
	private static final double CELL_DEFAULT_WIDTH = 90;

	private Font subjectFont;
	private Font titleFont;
	private Font cellFont;
	private Font cellFont2;
	
	private CellStyle subjectFormat;
	private CellStyle titleFormat1;
	private CellStyle titleFormat2;
	private CellStyle titleFormat3;
	private CellStyle titleFormat4;
	private CellStyle titleFormat5;
	private CellStyle titleFormat6;
	private CellStyle titleFormat7;
	private CellStyle titleFormat8;
	private CellStyle cellFormat;
	private CellStyle cellFormat1;
	private CellStyle cellFormat2;
	private CellStyle cellFormat3;
	
	private CellStyle numberFormat;
	
	private Sheet sheet1;
	private Sheet sheet2;
	private Sheet sheet3;
	private Sheet sheet4;
  	private Sheet sheet5;
  	private Sheet sheet6;
  	
	private String srh_type = "";
	
	private int rowNum = 0;
	
	private int days = 0;

	// cell 합계
	private int dayTotal[];
	private int daysTotal = 0;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = createFileName((String) model.get("filename"));
		setFileNameToResponse(request, response, fileName);

		IlboDTO info                   = (IlboDTO) model.get("info");
		List<Map<String, Object>> list = (List<Map<String, Object>>) model.get("list");

		String isLawCollege = (String) model.get("isLawCollege");

		this.sheet1 = workbook.createSheet("출역대장(임금+소개요금)");
		this.sheet2 = workbook.createSheet("노임대장(임금-원천징수)");
		this.sheet6 = workbook.createSheet("소개요금(면세)");
		this.sheet3 = workbook.createSheet("주민등록증");
		this.sheet4 = workbook.createSheet("통장사본");
		this.sheet5 = workbook.createSheet("교육이수증");

		srh_type = info.getSrh_type();

		initSheetFormat(workbook);

		// 상단 칼럼명 세팅
		createLabelRow(sheet1, sheet2, sheet6, (String) model.get("subject"), info, (List) model.get("dayList"));
		createDataRow(sheet1, sheet2, sheet6, (List<Map<String, Object>>) model.get("list"), workbook);
	}

	/**
	  * @Method Name : initSheetFormat
	  * @작성일 : 2021. 2. 26.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : format 초기화
	
	  */
	private void initSheetFormat(Workbook workbook) throws Exception {
		subjectFont = workbook.createFont();
  		subjectFont.setFontName("Arial");
  		subjectFont.setFontHeight((short)(16*20));
  		subjectFont.setBold(true);
  		subjectFont.setColor(IndexedColors.WHITE.getIndex());
  		
  		titleFont = workbook.createFont();
  		titleFont.setFontName("Arial");
  		titleFont.setFontHeight((short)(10*20));
  		
  		cellFont = workbook.createFont();
  		cellFont.setFontName("Arial");
  		cellFont.setFontHeight((short)(10*20));
  		
  		cellFont2 = workbook.createFont();
  		cellFont2.setFontHeight((short)(12*20));
  		cellFont2.setBold(true);
  		cellFont2.setColor(IndexedColors.BROWN.getIndex());
  		
//		subjectFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
//		titleFont = new WritableFont(WritableFont.ARIAL, 10);
//		cellFont = new WritableFont(WritableFont.ARIAL, 10);
  		subjectFormat = workbook.createCellStyle();
  		subjectFormat.setFont(subjectFont);
  		subjectFormat.setAlignment(HorizontalAlignment.CENTER);
  		subjectFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		subjectFormat.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
  		subjectFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		subjectFormat = new WritableCellFormat(subjectFont);
//		subjectFormat.setAlignment(Alignment.CENTRE);                 // 셀 정렬
//		subjectFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 정렬
//		subjectFormat.setBorder(Border.NONE, BorderLineStyle.NONE);   // 보더와 보더라인스타일 설정
//		subjectFormat.setBackground(Colour.SEA_GREEN);              // 배경색 설정

  		titleFormat1 = workbook.createCellStyle();
  		titleFormat1.setFont(titleFont);
  		titleFormat1.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat1.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
  		titleFormat1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		titleFormat1 = new WritableCellFormat(titleFont);
//		titleFormat1.setAlignment(Alignment.CENTRE);                   // 셀 정렬
//		titleFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat1.setBorder(Border.NONE, BorderLineStyle.NONE);     // 보더와 보더라인스타일 설정
//		titleFormat1.setBackground(Colour.GRAY_25);                    // 배경색 설정

  		titleFormat2 = workbook.createCellStyle();
  		titleFormat2.setFont(titleFont);
  		titleFormat2.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat2.setFillForegroundColor(IndexedColors.LIME.getIndex());
  		titleFormat2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		titleFormat2 = new WritableCellFormat(titleFont);
//		titleFormat2.setAlignment(Alignment.CENTRE);                   // 셀 정렬
//		titleFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat2.setBorder(Border.NONE, BorderLineStyle.NONE);      // 보더와 보더라인스타일 설정
//		titleFormat2.setBackground(Colour.LIME);                       // 배경색 설정

  		titleFormat3 = workbook.createCellStyle();
  		titleFormat3.setFont(titleFont);
  		titleFormat3.setAlignment(HorizontalAlignment.RIGHT);
  		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat3.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
  		titleFormat3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		titleFormat3 = new WritableCellFormat(titleFont);
//		titleFormat3.setAlignment(Alignment.RIGHT);                    // 셀 정렬
//		titleFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat3.setBorder(Border.NONE, BorderLineStyle.NONE);     // 보더와 보더라인스타일 설정
//		titleFormat3.setBackground(Colour.VERY_LIGHT_YELLOW);                      // 배경색 설정

  		titleFormat4 = workbook.createCellStyle();
  		titleFormat4.setFont(titleFont);
  		titleFormat4.setAlignment(HorizontalAlignment.CENTER);
  		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat4.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
  		titleFormat4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		titleFormat4 = new WritableCellFormat(titleFont);
//		titleFormat4.setAlignment(Alignment.CENTRE);                   // 셀 정렬
//		titleFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat4.setBorder(Border.NONE, BorderLineStyle.NONE);     // 보더와 보더라인스타일 설정
//		titleFormat4.setBackground(Colour.SEA_GREEN);                      // 배경색 설정

  		titleFormat5 = workbook.createCellStyle();
  		titleFormat5.setFont(titleFont);
  		titleFormat5.setAlignment(HorizontalAlignment.RIGHT);
  		titleFormat5.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat5.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		titleFormat5.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
  		titleFormat5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		titleFormat5 = new WritableCellFormat(titleFont, NumberFormats.THOUSANDS_INTEGER);
//		titleFormat5.setAlignment(Alignment.RIGHT);                    // 셀 정렬
//		titleFormat5.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 정렬
//		titleFormat5.setBorder(Border.NONE, BorderLineStyle.NONE);     // 보더와 보더라인스타일 설정
//		titleFormat5.setBackground(Colour.SEA_GREEN);                   // 배경색 설정

  		titleFormat6 = workbook.createCellStyle();
  		titleFormat6.setFont(titleFont);
  		titleFormat6.setAlignment(HorizontalAlignment.RIGHT);
  		titleFormat6.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat6.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
  		titleFormat6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
  		titleFormat7 = workbook.createCellStyle();
  		titleFormat7.setFont(titleFont);
  		titleFormat7.setAlignment(HorizontalAlignment.RIGHT);
  		titleFormat7.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat7.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
  		titleFormat7.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
  		titleFormat8 = workbook.createCellStyle();
  		titleFormat8.setFont(titleFont);
  		titleFormat8.setAlignment(HorizontalAlignment.RIGHT);
  		titleFormat8.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormat8.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		titleFormat8.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
  		titleFormat8.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
  		cellFormat = workbook.createCellStyle();
  		cellFormat.setFont(cellFont);
  		cellFormat.setAlignment(HorizontalAlignment.LEFT);
  		cellFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		
//		cellFormat = new WritableCellFormat(cellFont);
//		cellFormat.setAlignment(Alignment.LEFT);                      // 셀 정렬
//		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬

  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		numberFormat.setFillForegroundColor(IndexedColors.WHITE.getIndex());
  		numberFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		numberFormat = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//		numberFormat.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//		numberFormat.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
//		numberFormat.setBackground(Colour.WHITE);

  		cellFormat1 = workbook.createCellStyle();
  		cellFormat1.setFont(cellFont);
  		cellFormat1.setAlignment(HorizontalAlignment.RIGHT);
  		cellFormat1.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat1.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		cellFormat1.setFillForegroundColor(IndexedColors.TAN.getIndex());
  		cellFormat1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		cellFormat1 = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//		cellFormat1.setAlignment(Alignment.RIGHT);                      // 셀 정렬
//		cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormat1.setBackground(Colour.TAN);

  		cellFormat2 = workbook.createCellStyle();
  		cellFormat2.setFont(cellFont);
  		cellFormat2.setAlignment(HorizontalAlignment.RIGHT);
  		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		cellFormat2.setFillForegroundColor(IndexedColors.ROSE.getIndex());
  		cellFormat2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		
//		cellFormat2 = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//		cellFormat2.setAlignment(Alignment.RIGHT);                      // 셀 정렬
//		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormat2.setBackground(Colour.ROSE);
  		
  		cellFormat3 = workbook.createCellStyle();
  		cellFormat3.setAlignment(HorizontalAlignment.LEFT);
  		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat3.setFont(cellFont2);
	}

	private void createLabelRow(Sheet sheet, Sheet sheet2, Sheet sheet3, String sSubject, IlboDTO info, List dayList) throws Exception {

  		this.days = dayList.size();
  		dayTotal = new int[days];
//  		sheet.setDefaultColumnWidth((int)CELL_DEFAULT_WIDTH);
//  		sheet.setDefaultRowHeight((short)(CELL_DEFAULT_HEIGHT*20));
  		
  		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum , 0, days + 8));
  		sheet2.addMergedRegion(new CellRangeAddress(rowNum, rowNum , 0, days + 16));
  		sheet3.addMergedRegion(new CellRangeAddress(rowNum, rowNum , 0, days + 8));
  		
  		Row row = sheet.createRow(rowNum);
  		Row secondRow = sheet2.createRow(rowNum);
  		Row thirdRow = sheet3.createRow(rowNum++);
  		Cell cell = row.createCell(0);
  		Cell cell2 = secondRow.createCell(0);
  		Cell cell3 = thirdRow.createCell(0);
  		cell.setCellStyle(subjectFormat);
  		cell2.setCellStyle(subjectFormat);
  		cell3.setCellStyle(subjectFormat);
  		cell.setCellValue(sSubject);
  		cell2.setCellValue("노임대장(임금-원천징수)");
  		cell3.setCellValue("소개요금(면세)");
  		
  		
  		
  		Row row1 = sheet.createRow(rowNum);
  		Row row21 = sheet3.createRow(rowNum);
  		Row row6 = sheet2.createRow(rowNum++);
  		row1.createCell(0).setCellValue("회사명");
  		row21.createCell(0).setCellValue("회사명");
  		row6.createCell(0).setCellValue("회사명");
  		row1.getCell(0).setCellStyle(titleFormat1);
  		row6.getCell(0).setCellStyle(titleFormat1);
  		row21.getCell(0).setCellStyle(titleFormat1);
  		row1.createCell(1).setCellValue(info.getEmployer_name());
  		row6.createCell(1).setCellValue(info.getEmployer_name());
  		row21.createCell(1).setCellValue(info.getEmployer_name());
  		row1.getCell(1).setCellStyle(cellFormat);
  		row6.getCell(1).setCellStyle(cellFormat);
  		row21.getCell(1).setCellStyle(cellFormat);
  		
  		Row row2 = sheet.createRow(rowNum);
  		Row row22 = sheet3.createRow(rowNum);
  		Row row7 = sheet2.createRow(rowNum++);
  		row2.createCell(0).setCellValue("현장명");
  		row7.createCell(0).setCellValue("현장명");
  		row22.createCell(0).setCellValue("현장명");
  		row2.getCell(0).setCellStyle(titleFormat1);
  		row7.getCell(0).setCellStyle(titleFormat1);
  		row22.getCell(0).setCellStyle(titleFormat1);
  		row2.createCell(1).setCellValue(info.getIlbo_work_name());
  		row7.createCell(1).setCellValue(info.getIlbo_work_name());
  		row22.createCell(1).setCellValue(info.getIlbo_work_name());
  		row2.getCell(1).setCellStyle(cellFormat);
  		row7.getCell(1).setCellStyle(cellFormat);
  		row22.getCell(1).setCellStyle(cellFormat);
  		
  		Row row3 = sheet.createRow(rowNum);
  		Row row23 = sheet3.createRow(rowNum);
  		Row row8 = sheet2.createRow(rowNum++);
  		row3.createCell(0).setCellValue("기 간");
  		row8.createCell(0).setCellValue("기 간");
  		row23.createCell(0).setCellValue("기 간");
  		row3.getCell(0).setCellStyle(titleFormat1);
  		row23.getCell(0).setCellStyle(titleFormat1);
  		row8.getCell(0).setCellStyle(titleFormat1);
  		row3.createCell(1).setCellValue(info.getStart_ilbo_date() + " ~ " + info.getEnd_ilbo_date());
  		row23.createCell(1).setCellValue(info.getStart_ilbo_date() + " ~ " + info.getEnd_ilbo_date());
  		row8.createCell(1).setCellValue(info.getStart_ilbo_date() + " ~ " + info.getEnd_ilbo_date());
  		row3.getCell(1).setCellStyle(cellFormat);
  		row23.getCell(1).setCellStyle(cellFormat);
  		row8.getCell(1).setCellStyle(cellFormat);
  		rowNum++;
  		
  		
  		int colNum = 0;
  		Row row4 = sheet.createRow(rowNum);
  		Row row24 = sheet3.createRow(rowNum);
  		Row row9 = sheet2.createRow(rowNum++);
  		if ( "i.employer_seq".equals(srh_type) ) {    // 구인처 기준 구인대장일 경우
	  		sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
	  		sheet2.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
	  		sheet3.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
	  		sheet.setColumnWidth(colNum, (short)(20*256));
	  		sheet2.setColumnWidth(colNum, (short)(20*256));
	  		sheet3.setColumnWidth(colNum, (short)(20*256));
	  		row4.createCell(colNum).setCellValue("현 장");
	  		row24.createCell(colNum).setCellValue("현 장");
	  		row9.createCell(colNum).setCellValue("현 장");
	  		row4.getCell(colNum).setCellStyle(titleFormat1);
	  		row24.getCell(colNum).setCellStyle(titleFormat1);
	  		row9.getCell(colNum++).setCellStyle(titleFormat1);
  		}
  		
  		
  		sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet2.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet3.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet.setColumnWidth(colNum, (short)(15*256));
  		sheet2.setColumnWidth(colNum, (short)(15*256));
  		sheet3.setColumnWidth(colNum, (short)(15*256));
  		row4.createCell(colNum).setCellValue("성 명");
  		row24.createCell(colNum).setCellValue("성 명");
  		row9.createCell(colNum).setCellValue("성 명");
  		row4.getCell(colNum).setCellStyle(titleFormat1);
  		row24.getCell(colNum).setCellStyle(titleFormat1);
  		row9.getCell(colNum++).setCellStyle(titleFormat1);
  		
  		sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet2.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet3.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		sheet2.setColumnWidth(colNum, (short)(20*256));
  		sheet3.setColumnWidth(colNum, (short)(20*256));
  		row4.createCell(colNum).setCellValue("주민번호");
  		row24.createCell(colNum).setCellValue("주민번호");
  		row9.createCell(colNum).setCellValue("주민번호");
  		row4.getCell(colNum).setCellStyle(titleFormat1);
  		row24.getCell(colNum).setCellStyle(titleFormat1);
  		row9.getCell(colNum++).setCellStyle(titleFormat1);
  		
  		sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet2.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet3.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		sheet2.setColumnWidth(colNum, (short)(20*256));
  		sheet3.setColumnWidth(colNum, (short)(20*256));
  		row4.createCell(colNum).setCellValue("전 화");
  		row24.createCell(colNum).setCellValue("전 화");
  		row9.createCell(colNum).setCellValue("전 화");
  		row4.getCell(colNum).setCellStyle(titleFormat1);
  		row24.getCell(colNum).setCellStyle(titleFormat1);
  		row9.getCell(colNum++).setCellStyle(titleFormat1);
  		
  		sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet2.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet3.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet.setColumnWidth(colNum, (short)(20*256));
  		sheet2.setColumnWidth(colNum, (short)(20*256));
  		sheet3.setColumnWidth(colNum, (short)(20*256));
  		row4.createCell(colNum).setCellValue("주 소");
  		row24.createCell(colNum).setCellValue("주 소");
  		row9.createCell(colNum).setCellValue("주 소");
  		row4.getCell(colNum).setCellStyle(titleFormat1);
  		row24.getCell(colNum).setCellStyle(titleFormat1);
  		row9.getCell(colNum++).setCellStyle(titleFormat1);
  		
  		sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet2.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet3.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, colNum , colNum));
  		sheet.setColumnWidth(colNum, (short)(35*256));
  		sheet2.setColumnWidth(colNum, (short)(35*256));
  		sheet3.setColumnWidth(colNum, (short)(35*256));
  		row4.createCell(colNum).setCellValue("계 좌");
  		row24.createCell(colNum).setCellValue("계 좌");
  		row9.createCell(colNum).setCellValue("계 좌");
  		row4.getCell(colNum).setCellStyle(titleFormat1);
  		row24.getCell(colNum).setCellStyle(titleFormat1);
  		row9.getCell(colNum++).setCellStyle(titleFormat1);
  		
  		Row row5 = sheet.createRow(rowNum);
  		Row row25 = sheet.createRow(rowNum);
  		Row row10 = sheet2.createRow(rowNum++);
  		for(int i = 0; i < days; i++) {
  			row5.createCell(colNum).setCellValue("");
  			row25.createCell(colNum).setCellValue("");
  			row10.createCell(colNum).setCellValue("");
  			row5.getCell(colNum).setCellStyle(titleFormat3);
  			row25.getCell(colNum).setCellStyle(titleFormat3);
  			row10.getCell(colNum).setCellStyle(titleFormat3);
  			
  			row4.createCell(colNum).setCellValue( StringUtil.isNullToInt(((String) dayList.get(i)).substring(6, 8)) );
  			row24.createCell(colNum).setCellValue( StringUtil.isNullToInt(((String) dayList.get(i)).substring(6, 8)) );
  			row9.createCell(colNum).setCellValue( StringUtil.isNullToInt(((String) dayList.get(i)).substring(6, 8)) );
  			row4.getCell(colNum).setCellStyle(titleFormat2);
  			row24.getCell(colNum).setCellStyle(titleFormat2);
  			row9.getCell(colNum++).setCellStyle(titleFormat2);
  		}
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		sheet2.setColumnWidth(colNum, (short)(10*256));
  		sheet3.setColumnWidth(colNum, (short)(10*256));
  		row5.createCell(colNum).setCellValue("");
  		row25.createCell(colNum).setCellValue("");
  		row10.createCell(colNum).setCellValue("");
  		row5.getCell(colNum).setCellStyle(titleFormat5);
  		row25.getCell(colNum).setCellStyle(titleFormat5);
  		row10.getCell(colNum).setCellStyle(titleFormat5);
  		row4.createCell(colNum).setCellValue("공수");
  		row24.createCell(colNum).setCellValue("공수");
  		row9.createCell(colNum).setCellValue("공수");
  		row4.getCell(colNum).setCellStyle(titleFormat4);
  		row24.getCell(colNum).setCellStyle(titleFormat4);
  		row9.getCell(colNum++).setCellStyle(titleFormat4);
  		
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		sheet2.setColumnWidth(colNum, (short)(10*256));
  		sheet3.setColumnWidth(colNum, (short)(10*256));
  		row5.createCell(colNum).setCellValue("");
  		row25.createCell(colNum).setCellValue("");
  		row10.createCell(colNum).setCellValue("");
  		row5.getCell(colNum).setCellStyle(titleFormat5);
  		row25.getCell(colNum).setCellStyle(titleFormat5);
  		row10.getCell(colNum).setCellStyle(titleFormat5);
  		row4.createCell(colNum).setCellValue("단가");
  		row24.createCell(colNum).setCellValue("소개요금");
  		row9.createCell(colNum).setCellValue("임금");
  		row4.getCell(colNum).setCellStyle(titleFormat4);
  		row24.getCell(colNum).setCellStyle(titleFormat4);
  		row9.getCell(colNum++).setCellStyle(titleFormat4);
  		
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		sheet2.setColumnWidth(colNum, (short)(10*256));
  		sheet3.setColumnWidth(colNum, (short)(10*256));
  		row5.createCell(colNum).setCellValue("");
  		row25.createCell(colNum).setCellValue("");
  		row10.createCell(colNum).setCellValue("");
  		row5.getCell(colNum).setCellStyle(titleFormat5);
  		row25.getCell(colNum).setCellStyle(titleFormat5);
  		row10.getCell(colNum).setCellStyle(titleFormat5);
  		row4.createCell(colNum).setCellValue("총액");
  		row24.createCell(colNum).setCellValue("총액");
  		row9.createCell(colNum).setCellValue("총액");
  		row4.getCell(colNum).setCellStyle(titleFormat4);
  		row24.getCell(colNum).setCellStyle(titleFormat4);
  		row9.getCell(colNum++).setCellStyle(titleFormat4);
  		
  		row9.createCell(colNum).setCellValue("고용보험");
  		row9.getCell(colNum++).setCellStyle(titleFormat6);
  		row9.createCell(colNum).setCellValue("갑근세");
  		row9.getCell(colNum++).setCellStyle(titleFormat6);
  		row9.createCell(colNum).setCellValue("지방세");
  		row9.getCell(colNum++).setCellStyle(titleFormat6);
  		row9.createCell(colNum).setCellValue("국민연금");
  		row9.getCell(colNum++).setCellStyle(titleFormat6);
  		row9.createCell(colNum).setCellValue("의료보험");
  		row9.getCell(colNum++).setCellStyle(titleFormat6);
  		row9.createCell(colNum).setCellValue("장기요양");
  		row9.getCell(colNum++).setCellStyle(titleFormat6);
  		row9.createCell(colNum).setCellValue("공제총액");
  		row9.getCell(colNum++).setCellStyle(titleFormat7);
  		row9.createCell(colNum).setCellValue("공제후지급액");
  		row9.getCell(colNum++).setCellStyle(titleFormat7);
	}

	private void createDataRow(Sheet sheet, Sheet sheet2, Sheet sheet3, List<Map<String, Object>> list, Workbook workbook) throws Exception {
		int colNum = 0;

  		String sAccount = "";
  		double userDay = 0;
  		double userTotal = 0;
  		double totalPrice = 0;
  		double totalsPrice = 0;
  		double totalsIlboPay = 0;
  		double totalsWorkFee = 0;
  		double totalsTax = 0;
  		double totalIncome = 0;
  		double totalLocal = 0;
  		double totalEmployer = 0;
  		double totalNational = 0;
  		double totalHealth = 0;
  		double totalCare = 0;
  		double totalTaxPrice = 0;
  		double totalTaxPay = 0;

  		String imgURI = "";
  		File imgFile = null;
  		BufferedImage input = null;
  		ByteArrayOutputStream output = new ByteArrayOutputStream();
  		
  		double iWidth = 0;
  		double iHeight = 0;
  		double mediumWidth = 0;
  		double mediumHeight = 0;
  		
  		int juminHeight = 0;
  		int oshHeight = 0;
  		
  		String act = System.getProperty("spring.profiles.active");
		String fileDecryptTemp = "";
		String fileSecretKeyPath = "";
			
		try {
			Properties properties = new Properties();
			Reader reader;
			reader = Resources.getResourceAsReader("properties/" + act + "/common-config.properties");
			properties.load(reader);
			
			fileDecryptTemp 		= properties.getProperty("fileDecryptTemp");
			fileSecretKeyPath		= properties.getProperty("fileSecretKeyPath");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileCoder coder = new FileCoder(fileSecretKeyPath);
		
		for ( int i = 0; i < list.size(); i++ ) {
			colNum = 0;

			Map<String, Object> data = list.get(i);

			if( data.get("worker_bank_name") != null && data.get("worker_bank_account") != null && data.get("worker_bank_owner") != null ) {
  				sAccount = (String) data.get("worker_bank_name") +" "+ (String) data.get("worker_bank_account") +" "+ (String) data.get("worker_bank_owner");
  			}else {
  				sAccount = "";
  			}

			Row row1 = sheet.createRow(rowNum);
			Row row2 = sheet2.createRow(rowNum);
			Row row3 = sheet3.createRow(rowNum);
			
			if ( "i.employer_seq".equals(srh_type) ) {    // 구인처 기준 구인대장일 경우
				row1.createCell(colNum).setCellValue((String) data.get("work_name"));
  				row2.createCell(colNum).setCellValue((String) data.get("work_name"));
  				row3.createCell(colNum).setCellValue((String) data.get("work_name"));
  	  			row1.getCell(colNum).setCellStyle(cellFormat);
  	  			row2.getCell(colNum).setCellStyle(cellFormat);
  	  			row3.getCell(colNum++).setCellStyle(cellFormat);
				//sheet.addCell(new jxl.write.Label(colNum++, rowNum, (String) data.get("work_name"), cellFormat));
			}
			
			row1.createCell(colNum).setCellValue((String) data.get("worker_name"));
  			row2.createCell(colNum).setCellValue((String) data.get("worker_name"));
  			row3.createCell(colNum).setCellValue((String) data.get("worker_name"));
  			row1.getCell(colNum).setCellStyle(cellFormat);
  			row3.getCell(colNum).setCellStyle(cellFormat);
  			row2.getCell(colNum++).setCellStyle(cellFormat);
  			row1.createCell(colNum).setCellValue(StringUtil.juminFormat((String) data.get("worker_jumin")));
  			row2.createCell(colNum).setCellValue(StringUtil.juminFormat((String) data.get("worker_jumin")));
  			row3.createCell(colNum).setCellValue(StringUtil.juminFormat((String) data.get("worker_jumin")));
  			row1.getCell(colNum).setCellStyle(cellFormat);
  			row3.getCell(colNum).setCellStyle(cellFormat);
  			row2.getCell(colNum++).setCellStyle(cellFormat);
  			row1.createCell(colNum).setCellValue(StringUtil.phoneFormat((String) data.get("worker_phone")));
  			row2.createCell(colNum).setCellValue(StringUtil.phoneFormat((String) data.get("worker_phone")));
  			row3.createCell(colNum).setCellValue(StringUtil.phoneFormat((String) data.get("worker_phone")));
  			row1.getCell(colNum).setCellStyle(cellFormat);
  			row2.getCell(colNum).setCellStyle(cellFormat);
  			row2.getCell(colNum++).setCellStyle(cellFormat);
  			row1.createCell(colNum).setCellValue((String) data.get("worker_addr"));
  			row2.createCell(colNum).setCellValue((String) data.get("worker_addr"));
  			row3.createCell(colNum).setCellValue((String) data.get("worker_addr"));
  			row1.getCell(colNum).setCellStyle(cellFormat);
  			row3.getCell(colNum).setCellStyle(cellFormat);
  			row2.getCell(colNum++).setCellStyle(cellFormat);
  			row1.createCell(colNum).setCellValue((String) sAccount);
  			row2.createCell(colNum).setCellValue((String) sAccount);
  			row3.createCell(colNum).setCellValue((String) sAccount);
  			row1.getCell(colNum).setCellStyle(cellFormat);
  			row3.getCell(colNum).setCellStyle(cellFormat);
  			row2.getCell(colNum++).setCellStyle(cellFormat);

  			userTotal = 0;
  			for(int j = 1; j <= days; j++) {
  				userDay          = StringUtil.isNullToDouble(data.get("day_"+ j));
  				userTotal       += userDay;
  				dayTotal[j - 1] += userDay;
  				
  				if(userDay >= 0.5) {
  					row1.createCell(colNum).setCellValue(userDay);
  					row2.createCell(colNum).setCellValue(userDay);
  					row3.createCell(colNum).setCellValue(userDay);
  					row1.getCell(colNum).setCellStyle(cellFormat2);
  					row3.getCell(colNum).setCellStyle(cellFormat2);
  					row2.getCell(colNum++).setCellStyle(cellFormat2);
  				}else {
  					row1.createCell(colNum).setCellValue(userDay);
  					row2.createCell(colNum).setCellValue(userDay);
  					row3.createCell(colNum).setCellValue(userDay);
  					row1.getCell(colNum).setCellStyle(numberFormat);
  					row3.getCell(colNum).setCellStyle(numberFormat);
  					row2.getCell(colNum++).setCellStyle(numberFormat);
  				}
  			}
  			
			daysTotal += userTotal;

			totalPrice = StringUtil.isNullToLong(data.get("total_price"));
			row1.createCell(colNum).setCellValue(userTotal);
  			row2.createCell(colNum).setCellValue(userTotal);
  			row3.createCell(colNum).setCellValue(userTotal);
  			//row1.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row1.getCell(colNum).setCellStyle(numberFormat);
  			row3.getCell(colNum).setCellStyle(numberFormat);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row1.createCell(colNum).setCellValue(StringUtil.isNullToLong(data.get("ilbo_unit_price")));
  			row2.createCell(colNum).setCellValue(StringUtil.isNullToLong(data.get("ilbo_pay")));
  			row3.createCell(colNum).setCellValue(StringUtil.isNullToLong(data.get("work_fee")));
  			//row1.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row1.getCell(colNum).setCellStyle(numberFormat);
  			row3.getCell(colNum).setCellStyle(numberFormat);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row1.createCell(colNum).setCellValue(totalPrice);
  			row2.createCell(colNum).setCellValue(StringUtil.isNullToLong(data.get("ilbo_pay")) * userTotal);
  			row3.createCell(colNum).setCellValue(StringUtil.isNullToLong(data.get("work_fee")) * userTotal);
//  			row1.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
//  			row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
//  			row3.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row1.getCell(colNum).setCellStyle(numberFormat);
  			row3.getCell(colNum).setCellStyle(numberFormat);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			totalsPrice += totalPrice;
  			totalsIlboPay += StringUtil.isNullToLong(data.get("ilbo_pay")) * userTotal;
  			totalsWorkFee += StringUtil.isNullToLong(data.get("work_fee")) * userTotal;

			row2.createCell(colNum).setCellValue(Double.parseDouble(data.get("employer_insurance_price").toString()));
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row2.createCell(colNum).setCellValue(Double.parseDouble(data.get("income_tax_price").toString()));
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row2.createCell(colNum).setCellValue(Double.parseDouble(data.get("local_tax_price").toString()));
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row2.createCell(colNum).setCellValue(Double.parseDouble(data.get("national_pension_price").toString()));
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row2.createCell(colNum).setCellValue(Double.parseDouble(data.get("health_insurance_price").toString()));
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			row2.createCell(colNum).setCellValue(Double.parseDouble(data.get("care_insurance_price").toString()));
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			totalsTax = Double.parseDouble(data.get("income_tax_price").toString()) + Double.parseDouble(data.get("local_tax_price").toString()) + Double.parseDouble(data.get("employer_insurance_price").toString()) + Double.parseDouble(data.get("national_pension_price").toString()) + Double.parseDouble(data.get("health_insurance_price").toString()) + Double.parseDouble(data.get("care_insurance_price").toString());
  			totalIncome += Double.parseDouble(data.get("income_tax_price").toString());
  			totalLocal += Double.parseDouble(data.get("local_tax_price").toString());
  			totalEmployer += Double.parseDouble(data.get("employer_insurance_price").toString());
  			totalNational += Double.parseDouble(data.get("national_pension_price").toString());
  			totalHealth += Double.parseDouble(data.get("health_insurance_price").toString());
  			totalCare += Double.parseDouble(data.get("care_insurance_price").toString());
  			
  			row2.createCell(colNum).setCellValue(totalsTax);
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			double taxPay = (StringUtil.isNullToLong(data.get("ilbo_pay")) * userTotal) - totalsTax;
  			row2.createCell(colNum).setCellValue(taxPay);
  			//row2.getCell(colNum).setCellType(Cell.CELL_TYPE_NUMERIC);
  			row2.getCell(colNum++).setCellStyle(numberFormat);
  			
  			totalTaxPrice += totalsTax;
  			totalTaxPay += taxPay;
  			totalsTax += totalsTax;
			
  			final int RESIZE_WIDTH = 600;
  			final int RESIZE_HEIGHT = 400;
  			// 주민등록증 첨부
  			try {
	  			imgURI = StringUtil.isNullToString(data.get("worker_jumin_img"));
	  			imgFile = new File(imgURI);
	  			if(imgFile.exists()) {
	  				//	파일 복호화
	  				File destFile = new File(fileDecryptTemp + imgFile.getName());
	  				coder.decrypt(imgFile, destFile);
	  				
	  				// 파일 확장자
	  				String ext = imgFile.getName().substring(imgFile.getName().lastIndexOf(".")+1);
	  				
	  				Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
	  				BufferedImage indexedImage = new BufferedImage(RESIZE_WIDTH, RESIZE_HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);
	  				Graphics2D g = indexedImage.createGraphics();
	  				g.drawImage(image, 0,0, RESIZE_WIDTH, RESIZE_HEIGHT,null);
	  				g.dispose();
	  				
//	  				input = ImageIO.read(destFile);
//	  				input = resize(input, 600, 400);
	  				output = new ByteArrayOutputStream();
	  				ImageIO.write(indexedImage,  ext,  output);
	  				
	  				int pictureType = Workbook.PICTURE_TYPE_JPEG;
	  				if( ext.equals("png") ) {
	  					pictureType = Workbook.PICTURE_TYPE_PNG;
	  				}
	  				
	  				int pictureIdx = workbook.addPicture(output.toByteArray(), pictureType);
	  				CreationHelper helper = workbook.getCreationHelper();
	  				Drawing drawing = this.sheet3.createDrawingPatriarch();
	  				ClientAnchor anchor = helper.createClientAnchor();
	  				
	  				iHeight = indexedImage.getHeight();
	  				
	  				//이미지 위치
	  				anchor.setCol1(5);
	  				anchor.setRow1((int) juminHeight + 2);
	  				
	  				//이미지 그리기
	  				Picture pict = drawing.createPicture(anchor, pictureIdx);
	  				pict.resize();
	  				
	  				juminHeight += 2 + (iHeight / 16);
	  				
	  				//decrypt 파일 삭제
	  				//FileUtil.deleteFile(destFile);
	  				if( !destFile.delete() ) {
	  					logger.info("주민등록증 복호화 파일 삭제 실패 !\n path ==>" + fileDecryptTemp + imgFile.getName());
	  				}
	  			}
  			}catch(Exception e) {
  				e.printStackTrace();
  			}
  			try {
  				// 통장사본 첨부
	  			imgURI = StringUtil.isNullToString(data.get("worker_bank_img"));
	  			imgFile = new File(imgURI);
	  			
	  			if(imgFile.exists()) {
	  				
	  				//	파일 복호화
					File destFile = new File(fileDecryptTemp + imgFile.getName());
	  				coder.decrypt(imgFile, destFile);
	  				
	  				// 파일 확장자
	  				String ext = imgFile.getName().substring(imgFile.getName().lastIndexOf(".")+1);
	  				
	  				Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
	  				BufferedImage indexedImage = new BufferedImage(RESIZE_WIDTH, RESIZE_HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);
	  				Graphics2D g = indexedImage.createGraphics();
	  				g.drawImage(image, 0, 0, RESIZE_WIDTH, RESIZE_HEIGHT,null);
	  				g.dispose();
	  				
//	  				input = ImageIO.read(destFile);
//	  				input = resize(input, 600, 400);
	  				output = new ByteArrayOutputStream();
	  				ImageIO.write(indexedImage,  ext,  output);
	  				
	  				int pictureType = Workbook.PICTURE_TYPE_JPEG;
	  				if( ext.equals("png") ) {
	  					pictureType = Workbook.PICTURE_TYPE_PNG;
	  				}
	  				
	  				int pictureIdx = workbook.addPicture(output.toByteArray(), pictureType);
	  				CreationHelper helper = workbook.getCreationHelper();
	  				Drawing drawing = this.sheet4.createDrawingPatriarch();
	  				ClientAnchor anchor = helper.createClientAnchor();
	  				
	  				iHeight = indexedImage.getHeight();
	  				
	  				//이미지 위치
	  				anchor.setCol1(5);
	  				anchor.setRow1((int) oshHeight + 2);
	  				
	  				//이미지 그리기
	  				Picture pict = drawing.createPicture(anchor, pictureIdx);
	  				pict.resize();
	  				
	  				oshHeight += 2 + (iHeight / 16);
	  				
	  				//decrypt 파일 삭제
	  				//FileUtil.deleteFile(destFile);
	  				if( !destFile.delete() ) {
	  					logger.info("통장사본 복호화 파일 삭제 실패 !\n path ==>" + fileDecryptTemp + imgFile.getName());
	  				}
	  			}
  			}catch(Exception e) {
  				e.printStackTrace();
  			}

  			try {
  				// 교육이수증
	  			imgURI = StringUtil.isNullToString(data.get("worker_osh_img"));
	  			imgFile = new File(imgURI);
	  			
	  			if(imgFile.exists()) {
	  				
	  				//	파일 복호화
					File destFile = new File(fileDecryptTemp + imgFile.getName());
	  				coder.decrypt(imgFile, destFile);
	  				
	  				// 파일 확장자
	  				String ext = imgFile.getName().substring(imgFile.getName().lastIndexOf(".")+1);
	  				
	  				Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
	  				BufferedImage indexedImage = new BufferedImage(RESIZE_WIDTH, RESIZE_HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);
	  				Graphics2D g = indexedImage.createGraphics();
	  				g.drawImage(image, 0, 0, RESIZE_WIDTH, RESIZE_HEIGHT,null);
	  				g.dispose();
	  				
//	  				input = ImageIO.read(destFile);
//	  				input = resize(input, 600, 400);
	  				output = new ByteArrayOutputStream();
	  				ImageIO.write(indexedImage,  ext,  output);
	  				
	  				int pictureType = Workbook.PICTURE_TYPE_JPEG;
	  				if( ext.equals("png") ) {
	  					pictureType = Workbook.PICTURE_TYPE_PNG;
	  				}
	  				
	  				int pictureIdx = workbook.addPicture(output.toByteArray(), pictureType);
	  				CreationHelper helper = workbook.getCreationHelper();
	  				Drawing drawing = this.sheet5.createDrawingPatriarch();
	  				ClientAnchor anchor = helper.createClientAnchor();
	  				
	  				iHeight = indexedImage.getHeight();
	  				
	  				//이미지 위치
	  				anchor.setCol1(5);
	  				anchor.setRow1((int) oshHeight + 2);
	  				
	  				//이미지 그리기
	  				Picture pict = drawing.createPicture(anchor, pictureIdx);
	  				pict.resize();
	  				
	  				oshHeight += 2 + (iHeight / 16);
	  				
	  				//decrypt 파일 삭제
	  				//FileUtil.deleteFile(destFile);
	  				if( !destFile.delete() ) {
	  					logger.info("교육이수증 복호화 파일 삭제 실패 !\n path ==>" + fileDecryptTemp + imgFile.getName());
	  				}
	  			}
  			}catch(Exception e) {
  				e.printStackTrace();
  			}
  			
  			rowNum++;
		}

		// 합 계
		colNum = 5;
		rowNum = 6;

		if("i.employer_seq".equals(srh_type)) {    // 구인처 기준 구인대장일 경우
  			colNum = 6;
//  			      rowNum = 5;
  		}
  		Row row = sheet.createRow(rowNum);
  		Row row2 = sheet2.createRow(rowNum);
  		Row row3 = sheet3.createRow(rowNum);
  		
  		for(int i = 0; i < days; i++) {
  			if(dayTotal[i] >= 0.5) {
  				row.createCell(colNum).setCellValue(dayTotal[i]);
  				row2.createCell(colNum).setCellValue(dayTotal[i]);
  				row3.createCell(colNum).setCellValue(dayTotal[i]);
  				row.getCell(colNum).setCellStyle(cellFormat1);
  				row3.getCell(colNum).setCellStyle(cellFormat1);
  				row2.getCell(colNum++).setCellStyle(cellFormat1);
  			}else {
  				row.createCell(colNum).setCellValue(dayTotal[i]);
  				row2.createCell(colNum).setCellValue(dayTotal[i]);
  				row3.createCell(colNum).setCellValue(dayTotal[i]);
  				row.getCell(colNum).setCellStyle(titleFormat3);
  				row3.getCell(colNum).setCellStyle(titleFormat3);
  				row2.getCell(colNum++).setCellStyle(titleFormat3);
  			}
  		}
  		
  		row.createCell(colNum).setCellValue(daysTotal);
  		row2.createCell(colNum).setCellValue(daysTotal);
  		row3.createCell(colNum).setCellValue(daysTotal);
  		row.getCell(colNum).setCellStyle(titleFormat8);
  		row3.getCell(colNum).setCellStyle(titleFormat8);
  		row2.getCell(colNum++).setCellStyle(titleFormat8);
  		
  		row.createCell(colNum).setCellStyle(titleFormat8);
  		row3.createCell(colNum).setCellStyle(titleFormat8);
  		row2.createCell(colNum++).setCellStyle(titleFormat8);
  		
  		row.createCell(colNum).setCellValue(totalsPrice);
  		row2.createCell(colNum).setCellValue(totalsIlboPay);
  		row3.createCell(colNum).setCellValue(totalsWorkFee);
  		row.getCell(colNum).setCellStyle(titleFormat8);
  		row3.getCell(colNum).setCellStyle(titleFormat8);
  		row2.getCell(colNum++).setCellStyle(titleFormat8);
  		
  		row2.createCell(colNum).setCellValue(totalEmployer);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalIncome);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalLocal);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalNational);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalHealth);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalCare);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalTaxPrice);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		row2.createCell(colNum).setCellValue(totalTaxPay);
  		row2.getCell(colNum++).setCellStyle(numberFormat);
  		
  		rowNum += list.size() + 4;
  		
  		Row row6 = sheet.createRow(rowNum);
  		Row row16 = sheet6.createRow(rowNum);
		Row row11 = sheet2.createRow(rowNum++);
  		row6.createCell(0).setCellValue("<출역대장(임금+소개요금) 서류 안내>");
  		row11.createCell(0).setCellValue("<노무비대장(임금-원천징수) 서류 안내>");
  		row16.createCell(0).setCellValue("<소개요금(면세) 서류 안내>");
  		row6.getCell(0).setCellStyle(cellFormat3);
  		row11.getCell(0).setCellStyle(cellFormat3);
  		row16.getCell(0).setCellStyle(cellFormat3);
  		Row row7 = sheet.createRow(rowNum);
  		Row row17 = sheet6.createRow(rowNum);
  		Row row12 = sheet2.createRow(rowNum++);
  		row7.createCell(0).setCellValue("1) 본 출역대장은 현장에서 근로자 임금과 구인자 소개요금을 합산하여 근로자에게 일괄 지급한 경우 그 내역을 정리한 문서입니다.");
  		row12.createCell(0).setCellValue("1) 본 노무비대장은 근로복지공단에 '근로내역확인신고'를 하는 경우 그 내역을 정리한 문서입니다.");
  		row17.createCell(0).setCellValue("1) 소개요금은 귀사에서 일가자에 지급하는 알선서비스 요금입니다.");
  		row7.getCell(0).setCellStyle(cellFormat);
  		row12.getCell(0).setCellStyle(cellFormat);
  		row17.getCell(0).setCellStyle(cellFormat);
  		Row row8 = sheet.createRow(rowNum);
  		Row row18 = sheet6.createRow(rowNum);
  		Row row13 = sheet2.createRow(rowNum++);
  		row8.createCell(0).setCellValue("2) 근로복지공단에 '근로내역확인신고'를 하는 경우는 '노무비대장(임금-원천징수)' Sheet 문서를 활용하세요.");
  		row13.createCell(0).setCellValue("2) 현장에서 근로자 임금과 구인자 소개요금을 합산하여 근로자에게 일괄 지급하는 경우는 '출역대장(임금+소개요금)' Sheet 문서를 활용하세요.");
  		row18.createCell(0).setCellValue("2) 소개요금은 세법상 면세계산서 발행대상이며, '출역대장(임금+소개요금)' 기준으로 근로자에게 지급했다면 일가자에 면세계산서 발행요청하세요.");
  		row8.getCell(0).setCellStyle(cellFormat);
  		row13.getCell(0).setCellStyle(cellFormat);
  		row18.getCell(0).setCellStyle(cellFormat);
  		Row row9 = sheet.createRow(rowNum);
  		Row row19 = sheet6.createRow(rowNum);
  		Row row14 = sheet2.createRow(rowNum++);
  		row9.createCell(0).setCellValue("3) 구인자 소개요금에 대해서는 '소개요금(면세)' Sheet 문서를 활용하세요.");
  		row14.createCell(0).setCellValue("3) 구인자 소개요금에 대해서는 '소개요금(면세)' Sheet 문서를 활용하세요.");
  		row19.createCell(0).setCellValue("3) 소개요금에 대하여 '면세계산서' 발행요청은 일가자 구인자 어플 또는 홈페이지(www.ilgaja.com)에서 본사매니저로 가입 후 신청 가능합니다.");
  		row9.getCell(0).setCellStyle(cellFormat);
  		row14.getCell(0).setCellStyle(cellFormat);
  		row19.getCell(0).setCellStyle(cellFormat);
  		Row row10 = sheet.createRow(rowNum);
  		Row row20 = sheet6.createRow(rowNum); 
  		Row row15 = sheet2.createRow(rowNum++);
  		row10.createCell(0).setCellValue("4) 문의 사항이 있으시면 일가자 고객센터(☏ 1668-1903)로 연락주세요. (운영시간 : 주중 10:00~19:00)");
  		row15.createCell(0).setCellValue("4) 문의 사항이 있으시면 일가자 고객센터(☏ 1668-1903)로 연락주세요. (운영시간 : 주중 10:00~19:00)");
  		row20.createCell(0).setCellValue("4) 문의 사항이 있으시면 일가자 고객센터(☏ 1668-1903)로 연락주세요. (운영시간 : 주중 10:00~19:00)");
  		row10.getCell(0).setCellStyle(cellFormat);
  		row15.getCell(0).setCellStyle(cellFormat);
  		row20.getCell(0).setCellStyle(cellFormat);
  		Row row21 = sheet2.createRow(rowNum++);
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
