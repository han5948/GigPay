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
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.nemo.kr.dto.AccountDTO;

public class JnpTaxExcel extends AbstractXlsxStreamingView {
	private Font titleFont;
	private Font cellFont;
	private CellStyle titleFormatTan;
	private CellStyle titleFormatTan2;
	private CellStyle cellFormatNormal;
	private CellStyle cellFormatTan;
	private CellStyle titleFormatLightGreen;
	private CellStyle numberFormat;
	private Sheet sheet1;
	private int rowNum = 0;
	private AccountDTO accountDTO;
  
	@SuppressWarnings("unchecked")
	@Override
  	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = createFileName((String) model.get("fileName"));
		accountDTO = (AccountDTO)model.get("accountDTO");
				
		setFileNameToResponse(request, response, fileName);

		this.sheet1 = workbook.createSheet(accountDTO.getSrh_tax_type_kr());

		// style 정의
		initSheetFormat(workbook);
	  
		// 상단 칼럼명 세팅 
		createLabelRow(sheet1);

		// 계산서 셋팅
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
  		cellFormatTan.setFillForegroundColor(IndexedColors.TAN.getIndex());
  		cellFormatTan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		cellFormatTan.setBorderBottom(BorderStyle.THIN);
  		cellFormatTan.setBorderLeft(BorderStyle.THIN);
  		cellFormatTan.setBorderLeft(BorderStyle.DOTTED);
  		cellFormatTan.setWrapText(true);
		
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
  		
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		
	}
	
	private void createLabelRow(Sheet sheet) throws Exception {
		int colNum = 0;
		Row row = sheet.createRow(rowNum);
		row.setHeight((short)500);
		rowNum ++;
		rowNum ++;
		rowNum ++;
		rowNum ++;
		rowNum ++;
		Row row2 = sheet.createRow(rowNum);
		row2.setHeight((short)930);
		
		sheet.setColumnWidth(colNum, (short)(20*256));
  		row2.createCell(colNum).setCellValue("전자(세금)계산서 종류\n(01:일반, 02:영세율)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		sheet.setColumnWidth(colNum, (short)(12*256));
  		
  		row2.createCell(colNum).setCellValue("작성일자");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
	    
  		sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 등록번호\n(\"-\" 없이 입력)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
	    
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자\n종사업장번호");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
	    
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 상호");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 성명");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(30*256));
  		row2.createCell(colNum).setCellValue("공급자 사업장주소");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급자 업태");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급자 종목");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급자 이메일");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 등록번호\n(\"-\" 없이 입력)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 \n종사업장번호");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 상호");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 성명");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(30*256));
  		row2.createCell(colNum).setCellValue("공급받는자 사업장주소");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급받는자 업태");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(12*256));
  		row2.createCell(colNum).setCellValue("공급받는자 종목");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 이메일1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("공급받는자 이메일2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);

	    sheet.setColumnWidth(colNum, (short)(7*256));
  		row2.createCell(colNum).setCellValue("세액");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(5*256));
  		row2.createCell(colNum).setCellValue("비고");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("일자1\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(15*256));
  		row2.createCell(colNum).setCellValue("품목1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
  		sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액1");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
  		sheet.setColumnWidth(colNum, (short)(7*256));
  		row2.createCell(colNum).setCellValue("세액1");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고1");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("일자2\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
  		sheet.setColumnWidth(colNum, (short)(7*256));
  		row2.createCell(colNum).setCellValue("세액2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고2");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("일자3\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
  		sheet.setColumnWidth(colNum, (short)(7*256));
  		row2.createCell(colNum).setCellValue("세액3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고3");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("일자4\n(2자리, 작성년월 제외)");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("규격4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수량4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("단가4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("공급가액4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
  		sheet.setColumnWidth(colNum, (short)(7*256));
  		row2.createCell(colNum).setCellValue("세액4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("품목비고4");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("현금");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("수표");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("어음");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("외상미수금");
  		row2.getCell(colNum++).setCellStyle(titleFormatLightGreen);
  		
	    sheet.setColumnWidth(colNum, (short)(10*256));
  		row2.createCell(colNum).setCellValue("영수(01),\n청구(02)");
  		row2.getCell(colNum++).setCellStyle(titleFormatTan);
	    
	    ++rowNum;
	}

	private void createDataRow(Sheet sheet, List<AccountDTO> resultList) throws Exception {
		try {
			for (int i = 0; i < resultList.size(); i++) {
				AccountDTO item = resultList.get(i);
				rowNum = createRow(sheet, item, rowNum);
				sheet.createRow(rowNum).setHeight((short)480);
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
	private int createRow(Sheet sheet, AccountDTO item, int rowNum){
		int colNum = 0;
		Row row = sheet.createRow(rowNum);
		String accountDays = item.getAccount_date().substring(item.getAccount_date().length()-2); 
		
		//공급가액,세액(부가세) - 원단위 절사
		String accountPrice = item.getAccount_price().substring(0, item.getAccount_price().length() - 1) + "0";
		String taxAmount = item.getTax_amount().substring(0, item.getTax_amount().length() - 1) + "0";
		
		row.createCell(colNum).setCellValue("01");
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(item.getAccount_date());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		
		if(item.getCompany_num() != null) {
			row.createCell(colNum).setCellValue(item.getCompany_num().replace("-", ""));
		}else {
			row.createCell(colNum).setCellValue("");
		}
		
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getBusiness_name());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(item.getCompany_owner());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(item.getCompany_addr());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getCompany_business());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getCompany_sector());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getCompany_email());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		if(item.getDest_company_num() != null) {
			row.createCell(colNum).setCellValue(item.getDest_company_num().replaceAll("-",  ""));
		}else {
			row.createCell(colNum).setCellValue("");
		}
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getDest_business_name());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(item.getDest_company_owner());
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(item.getDest_company_addr());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getDest_company_business());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getDest_company_sector());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(item.getDest_company_email());
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(accountPrice);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(taxAmount);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(accountDays);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(item.getDest_business_name() + "/" + accountDTO.getSrh_tax_type_kr().substring(0, 3));
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue(accountPrice);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue(taxAmount);
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("");
		row.getCell(colNum++).setCellStyle(cellFormatNormal);
		row.createCell(colNum).setCellValue("02");
		row.getCell(colNum++).setCellStyle(cellFormatTan);
		
		rowNum++;
		
		return rowNum;
	}
}	