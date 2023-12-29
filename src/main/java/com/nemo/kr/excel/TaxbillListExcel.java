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
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.nemo.kr.dto.TaxbillDTO;



public class TaxbillListExcel extends AbstractXlsxStreamingView {
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
	private CellStyle cellFormatNormal;
	private CellStyle cellFormatTan;
	private CellStyle titleFormatLightGreen;
	private CellStyle titleFormatTan2;
	private CellStyle titleFormatTan;
	
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

		this.sheet1 = workbook.createSheet("면세계산서");
		    
		companyName 	= (String) model.get("companyName");
		//style 정의
		initSheetFormat(workbook);

		// 상단 칼럼명 세팅 
		createLabelRow(sheet1);

		//스크랩리스트 셋팅
		createDataRow(sheet1, (List<TaxbillDTO>) model.get("resultList"));
	}

	private void initSheetFormat(Workbook workbook) throws Exception {
		
		titleFont = workbook.createFont();
  		titleFont.setFontName("돋움");
  		titleFont.setFontHeight((short)(11*20));
  		titleFont.setColor(IndexedColors.AQUA.getIndex());
  		
  		cellFont = workbook.createFont();
  		cellFont.setFontName("돋움");
  		cellFont.setFontHeight((short)(11*20));
  		
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
  		
  		cellFormatNormal = workbook.createCellStyle();
  		cellFormatNormal.setFont(cellFont);
  		cellFormatNormal.setAlignment(HorizontalAlignment.LEFT);
  		cellFormatNormal.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormatNormal.setBorderBottom(BorderStyle.THIN);
  		cellFormatNormal.setBorderRight(BorderStyle.DOTTED);
  		
  		cellFormatTan = workbook.createCellStyle();
  		cellFormatTan.setFont(cellFont);
  		cellFormatTan.setAlignment(HorizontalAlignment.CENTER);
  		cellFormatTan.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormatTan.setBorderBottom(BorderStyle.THIN);
  		cellFormatTan.setBorderRight(BorderStyle.DOTTED);
  		cellFormatTan.setFillForegroundColor(IndexedColors.TAN.getIndex());
  		cellFormatTan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		cellFormatTan.setWrapText(true);
  		
  		titleFormatLightGreen = workbook.createCellStyle();
  		titleFormatLightGreen.setFont(cellFont);
  		titleFormatLightGreen.setAlignment(HorizontalAlignment.CENTER);
  		titleFormatLightGreen.setVerticalAlignment(VerticalAlignment.CENTER);
  		titleFormatLightGreen.setBorderTop(BorderStyle.MEDIUM);
  		titleFormatLightGreen.setBorderRight(BorderStyle.THIN);
  		titleFormatLightGreen.setBorderBottom(BorderStyle.THIN);
  		titleFormatLightGreen.setBorderLeft(BorderStyle.THIN);
  		titleFormatLightGreen.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
  		titleFormatLightGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		titleFormatLightGreen.setWrapText(true);
    
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		
	}

	private void createLabelRow(Sheet sheet) throws Exception {
	    int colNum = 0;
	    Row row = sheet.createRow(rowNum++);
	    row.setHeight((short)500);
		rowNum ++;
		rowNum ++;
		rowNum ++;
		rowNum ++;
		
		Row row1 = sheet.createRow(rowNum);
	    row1.setHeight((short)930);
		
	    Cell cell1 = row1.createCell(colNum);
   		cell1.setCellStyle(titleFormatTan);
   		cell1.setCellValue("전자(세금)계산서 종류\n(05::일반)");
   		sheet.setColumnWidth(colNum++, (short)(20*256));
   		
   		sheet.setColumnWidth(colNum, (short)(12*256));
   		row1.createCell(colNum).setCellValue("작성일자");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
	    
   		sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급자 등록번호\n(\"-\" 없이 입력)");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
	    
   		sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급자\n종사업장번호");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
	    
   		sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급자 상호");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 상호", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
   		sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급자 성명");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 성명", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
   		sheet.setColumnWidth(colNum, (short)(30*256));
   		row1.createCell(colNum).setCellValue("공급자 사업장주소");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 사업장주소", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 30);
	    sheet.setColumnWidth(colNum, (short)(12*256));
   		row1.createCell(colNum).setCellValue("공급자 업태");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 업태", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(12*256));
   		row1.createCell(colNum).setCellValue("공급자 종목");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 종목", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급자 이메일");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급자 이메일", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급받는자 등록번호\n(\"-\" 없이 입력)");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 등록번호\n(\"-\" 없이 입력)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급자 \n종사업장번호");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 \n종사업장번호", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급받는자 상호");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 상호", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급받는자 성명");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 성명", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(30*256));
   		row1.createCell(colNum).setCellValue("공급받는자 사업장주소");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 사업장주소", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 30);
	    sheet.setColumnWidth(colNum, (short)(12*256));
   		row1.createCell(colNum).setCellValue("공급받는자 업태");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 업태", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(12*256));
   		row1.createCell(colNum).setCellValue("공급받는자 종목");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 종목", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 12);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급받는자 이메일1");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 이메일1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("공급받는자 이메일2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급받는자 이메일2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("공급가액");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액", titleFormatTan));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(5*256));
   		row1.createCell(colNum).setCellValue("비고");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "비고", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 5);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("일자1\n(2자리, 작성년월 제외)");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자1\n(2자리, 작성년월 제외)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(15*256));
   		row1.createCell(colNum).setCellValue("품목1");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 15);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("규격1");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("수량1");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("단가1");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("공급가액1");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액1", titleFormatTan));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목비고1");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고1", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("일자2\n(2자리, 작성년월 제외)");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자2\n(2자리, 작성년월 제외)", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("규격2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("수량2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("단가2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("공급가액2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목비고2");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고2", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("일자3\n(2자리, 작성년월 제외)");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자3\n(2자리, 작성년월 제외)", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목3");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("규격3");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("수량3");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("단가3");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("공급가액3");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목비고3");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고3", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("일자4\n(2자리, 작성년월 제외)");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "일자4\n(2자리, 작성년월 제외)", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목4");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("규격4");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "규격4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("수량4");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수량4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("단가4");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "단가4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("공급가액4");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "공급가액4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("품목비고4");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "품목비고4", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("현금");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "현금", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("수표");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "수표", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("어음");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "어음", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("외상미수금");
   		row1.getCell(colNum++).setCellStyle(titleFormatLightGreen);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "외상미수금", titleFormatLightGreen));
//	    sheet.setColumnView(colNum++, 10);
	    sheet.setColumnWidth(colNum, (short)(10*256));
   		row1.createCell(colNum).setCellValue("영수(01),\n청구(02)");
   		row1.getCell(colNum++).setCellStyle(titleFormatTan);
//	    sheet.addCell(new jxl.write.Label(colNum, rowNum,   "영수(01),\n청구(02)", titleFormatTan));
//	    sheet.setColumnView(colNum++, 10);
	    
	    ++rowNum;
	}

	private void createDataRow(Sheet sheet, List<TaxbillDTO> resultList) throws Exception {
		try {
			for (int i = 0; i < resultList.size(); i++) {
				TaxbillDTO item = resultList.get(i);
				Row row = sheet.createRow(rowNum);
				
				row.createCell(0).setCellValue("05");
				row.getCell(0).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(0, rowNum,   "05", cellFormatTan));
				row.createCell(1).setCellValue(item.getWrite_date());
				row.getCell(1).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(1, rowNum,   item.getWrite_date(), cellFormatTan));
				row.createCell(2).setCellValue(item.getCompany_num().replace("-", ""));
				row.getCell(2).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(2, rowNum,   item.getCompany_num().replace("-", ""), cellFormatTan));
				row.createCell(3).setCellValue("");
				row.getCell(3).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(3, rowNum,   "", cellFormatNormal));
				row.createCell(4).setCellValue(item.getBusiness_name());
				row.getCell(4).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(4, rowNum,   item.getBusiness_name(), cellFormatTan));
				row.createCell(5).setCellValue(item.getCompany_owner());
				row.getCell(5).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(5, rowNum,   item.getCompany_owner(), cellFormatTan));
				row.createCell(6).setCellValue(item.getCompany_addr());
				row.getCell(6).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(6, rowNum,   item.getCompany_addr(), cellFormatNormal));
				row.createCell(7).setCellValue(item.getCompany_business());
				row.getCell(7).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(7, rowNum,   item.getCompany_business(), cellFormatNormal));
				row.createCell(8).setCellValue(item.getCompany_sector());
				row.getCell(8).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(8, rowNum,   item.getCompany_sector(), cellFormatNormal));
				row.createCell(9).setCellValue(item.getCompany_email());
				row.getCell(9).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(9, rowNum,   item.getCompany_email(), cellFormatNormal));
				row.createCell(10).setCellValue(item.getEmployer_num());
				row.getCell(10).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(10, rowNum,  item.getEmployer_num(), cellFormatTan));
				row.createCell(11).setCellValue("");
				row.getCell(11).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(11, rowNum,   "", cellFormatNormal));
				row.createCell(12).setCellValue(item.getEmployer_name());
				row.getCell(12).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(12, rowNum,  item.getEmployer_name(), cellFormatTan));
				row.createCell(13).setCellValue(item.getEmployer_owner());
				row.getCell(13).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(13, rowNum,  item.getEmployer_owner(), cellFormatTan));
				row.createCell(14).setCellValue(item.getEmployer_addr());
				row.getCell(14).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(14, rowNum,  item.getEmployer_addr(), cellFormatNormal));
				row.createCell(15).setCellValue(item.getEmployer_business());
				row.getCell(15).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(15, rowNum,  item.getEmployer_business(), cellFormatNormal));
				row.createCell(16).setCellValue(item.getEmployer_sector());
				row.getCell(16).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(16, rowNum,  item.getEmployer_sector(), cellFormatNormal));
				row.createCell(17).setCellValue(item.getEmployer_email());
				row.getCell(17).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(17, rowNum,  item.getEmployer_email(), cellFormatNormal));
				row.createCell(18).setCellValue("");
				row.getCell(18).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(18, rowNum,  "", cellFormatNormal));
				row.createCell(19).setCellValue(item.getSupply_price());
				row.getCell(19).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(19, rowNum,  item.getSupply_price(), cellFormatTan));
				row.createCell(20).setCellValue("");
				row.getCell(20).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(20, rowNum,  "", cellFormatNormal));
				row.createCell(21).setCellValue(item.getWrite_date().substring(6));
				row.getCell(21).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(21, rowNum,  item.getWrite_date().substring(6), cellFormatTan));
				row.createCell(22).setCellValue(item.getSubject());
				row.getCell(22).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(22, rowNum,  item.getSubject(), cellFormatNormal));
				row.createCell(23).setCellValue("");
				row.getCell(23).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(23, rowNum,  "", cellFormatNormal));
				row.createCell(24).setCellValue("");
				row.getCell(24).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(24, rowNum,  "", cellFormatNormal));
				row.createCell(25).setCellValue("");
				row.getCell(25).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(25, rowNum,  "", cellFormatNormal));
				row.createCell(26).setCellValue(item.getSupply_price());
				row.getCell(26).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(26, rowNum,  item.getSupply_price(), cellFormatTan));
				row.createCell(27).setCellValue("");
				row.getCell(27).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(27, rowNum,  "", cellFormatNormal));
				row.createCell(28).setCellValue("");
				row.getCell(28).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(28, rowNum,  "", cellFormatNormal));
				row.createCell(29).setCellValue("");
				row.getCell(29).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(29, rowNum,  "", cellFormatNormal));
				row.createCell(30).setCellValue("");
				row.getCell(30).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(30, rowNum,  "", cellFormatNormal));
				row.createCell(31).setCellValue("");
				row.getCell(31).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(31, rowNum,  "", cellFormatNormal));
				row.createCell(32).setCellValue("");
				row.getCell(32).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(32, rowNum,  "", cellFormatNormal));
				row.createCell(33).setCellValue("");
				row.getCell(33).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(33, rowNum,  "", cellFormatNormal));
				row.createCell(34).setCellValue("");
				row.getCell(34).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(34, rowNum,  "", cellFormatNormal));
				row.createCell(35).setCellValue("");
				row.getCell(35).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(35, rowNum,  "", cellFormatNormal));
				row.createCell(36).setCellValue("");
				row.getCell(36).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(36, rowNum,  "", cellFormatNormal));
				row.createCell(37).setCellValue("");
				row.getCell(37).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(37, rowNum,  "", cellFormatNormal));
				row.createCell(38).setCellValue("");
				row.getCell(38).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(38, rowNum,  "", cellFormatNormal));
				row.createCell(39).setCellValue("");
				row.getCell(39).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(39, rowNum,  "", cellFormatNormal));
				row.createCell(40).setCellValue("");
				row.getCell(40).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(40, rowNum,  "", cellFormatNormal));
				row.createCell(41).setCellValue("");
				row.getCell(41).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(41, rowNum,  "", cellFormatNormal));
				row.createCell(42).setCellValue("");
				row.getCell(42).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(42, rowNum,  "", cellFormatNormal));
				row.createCell(43).setCellValue("");
				row.getCell(43).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(43, rowNum,  "", cellFormatNormal));
				row.createCell(44).setCellValue("");
				row.getCell(44).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(44, rowNum,  "", cellFormatNormal));
				row.createCell(45).setCellValue("");
				row.getCell(45).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(45, rowNum,  "", cellFormatNormal));
				row.createCell(46).setCellValue("");
				row.getCell(46).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(46, rowNum,  "", cellFormatNormal));
				row.createCell(47).setCellValue("");
				row.getCell(47).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(47, rowNum,  "", cellFormatNormal));
				row.createCell(48).setCellValue("");
				row.getCell(48).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(48, rowNum,  "", cellFormatNormal));
				row.createCell(49).setCellValue("");
				row.getCell(49).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(49, rowNum,  "", cellFormatNormal));
				row.createCell(50).setCellValue("");
				row.getCell(50).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(50, rowNum,  "", cellFormatNormal));
				row.createCell(51).setCellValue("");
				row.getCell(51).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(51, rowNum,  "", cellFormatNormal));
				row.createCell(52).setCellValue("");
				row.getCell(52).setCellStyle(cellFormatNormal);
				//sheet.addCell(new jxl.write.Label(52, rowNum,  "", cellFormatNormal));
				row.createCell(53).setCellValue("영수".equals(item.getClaim()) ? "01" : "02");
				row.getCell(53).setCellStyle(cellFormatTan);
				//sheet.addCell(new jxl.write.Label(53, rowNum,  "영수".equals(item.getClaim()) ? "01" : "02", cellFormatTan));
				
				row.setHeight((short)400);
				//sheet.setRowView(rowNum, 480);
				rowNum++;	  
			}
		  
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
