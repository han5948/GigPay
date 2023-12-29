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

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AccountingDTO;


public class CompanyAccountingExpensesExcel extends AbstractXlsxStreamingView {
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
		sheet1.setColumnWidth(0, 20);
		sheet1.setColumnWidth(1, 20);
		sheet1.setColumnWidth(2, 20);
		sheet1.setColumnWidth(3, 20);
		sheet1.setColumnWidth(4, 20);
		sheet1.setColumnWidth(5, 20);
		sheet1.setColumnWidth(6, 20);
		sheet1.setColumnWidth(7, 20);
		sheet1.setColumnWidth(8, 20);
		
//		sheet1.setColumnView(0, 20);	 // column width 지정
//		sheet1.setColumnView(1, 20);
//		sheet1.setColumnView(2, 20);
//		sheet1.setColumnView(3, 20);
//		sheet1.setColumnView(4, 20);
//		sheet1.setColumnView(5, 20);
//		sheet1.setColumnView(6, 20);
//		sheet1.setColumnView(7, 20);
//		sheet1.setColumnView(8, 20);
		/*
		 * sheet1.setColumnView(6, 40); sheet1.setColumnView(7, 20);
		 * sheet1.setColumnView(8, 20);
		 */
	  
		companySeq 	= (String) model.get("companySeq");
		companyName 	= (String) model.get("companyName");
	  
		//style 정의
		initSheetFormat(workbook);

		// 상단 칼럼명 세팅 
		createLabelRow(sheet1, (AccountingDTO) model.get("accountingDTO"));

		//스크랩리스트 셋팅
		createDataRow(sheet1,  (AccountingDTO) model.get("accountingDTO"), (List<AccountingDTO>) model.get("resultList"));
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
//		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormat.setWrap(true); 
		
  		cellFormat2 = workbook.createCellStyle();
  		cellFormat2.setFont(cellFont);
  		cellFormat2.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat2.setWrapText(true);
  		
//		cellFormat2 = new WritableCellFormat(cellFont);
//		cellFormat2.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormat2.setWrap(true); 

  		cellFormat3 = workbook.createCellStyle();
  		cellFormat3.setFont(cellFont);
  		cellFormat3.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat3.setWrapText(true);
  		cellFormat3.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
  		cellFormat3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  		cellFormat3.setBorderBottom(BorderStyle.DOUBLE);
  		
//		cellFormat3 = new WritableCellFormat(cellFont);
//		cellFormat3.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		cellFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormat3.setWrap(true); 
//		cellFormat3.setBackground(Colour.PALE_BLUE);                    	// 배경색 설정
//		cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
		
  		cellFormat4 = workbook.createCellStyle();
  		cellFormat4.setFont(cellFont);
  		cellFormat4.setAlignment(HorizontalAlignment.CENTER);
  		cellFormat4.setVerticalAlignment(VerticalAlignment.CENTER);
  		cellFormat4.setWrapText(true);
  		cellFormat4.setBorderBottom(BorderStyle.DOUBLE);
  		
//		cellFormat4 = new WritableCellFormat(cellFont);
//		cellFormat4.setAlignment(Alignment.CENTRE);                      // 셀 정렬
//		cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);    // 셀 수직 정렬
//		cellFormat4.setWrap(true); 
//		cellFormat4.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);   // 보더와 보더라인스타일 설정
		
  		numberFormat = workbook.createCellStyle();
  		numberFormat.setFont(cellFont);
  		numberFormat.setAlignment(HorizontalAlignment.RIGHT);
  		numberFormat.setVerticalAlignment(VerticalAlignment.CENTER);
  		numberFormat.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
  		
//		numberFormat = new WritableCellFormat(cellFont, NumberFormats.THOUSANDS_INTEGER);
//		numberFormat.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//		numberFormat.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
		
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
  		
//		numberFormat2 = new WritableCellFormat(titleFont, NumberFormats.THOUSANDS_INTEGER);
//		numberFormat2.setAlignment(Alignment.RIGHT);                   // 셀 정렬
//		numberFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);     	// 보더와 보더라인스타일 설정
//		numberFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);  // 셀 수직 정렬
//		numberFormat2.setBackground(Colour.GRAY_25);                    	// 배경색 설정
	}

	private void createLabelRow(Sheet sheet,  AccountingDTO accountingDTO) throws Exception {
		String title = accountingDTO.getStartDate().substring(0, 7) +"월 " + companyName;
	  
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 9));
		//sheet.mergeCells(0, rowNum, 7, rowNum); // 시작열, 시작행, 종료열, 종료행
		Row row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue(title);
  		row.getCell(0).setCellStyle(subjectFormat);
		row.setHeight((short)500);
//		sheet.addCell(new jxl.write.Label(0, rowNum,   title, subjectFormat));
//		sheet.setRowView(rowNum, 500);
	    
		rowNum ++;
		rowNum ++;
		
		//sheet.mergeCells(0,2,0,3);
		Row row2 = sheet.createRow(rowNum);
		Row row3 = sheet.createRow(3);
		
		row2.createCell(0).setCellValue("번호");
  		row2.getCell(0).setCellStyle(titleFormat1);
  		row3.createCell(0).setCellStyle(titleFormat1);
  		sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
  		sheet.setColumnWidth(0, (short)(20*256));
//	    sheet.addCell(new jxl.write.Label(0, 2,   "번호", titleFormat1));
//	    sheet.setColumnView(0, 20);

  		sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
		row2.createCell(1).setCellValue("날짜");
  		row2.getCell(1).setCellStyle(titleFormat1);
  		row3.createCell(1).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(1, (short)(25*256));
//	    sheet.mergeCells(1,2,1,3);
//	    sheet.addCell(new jxl.write.Label(1, 2,   "날짜", titleFormat1));
//	    sheet.setColumnView(1, 25);
	    
  		sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
		row2.createCell(2).setCellValue("정산 지점");
  		row2.getCell(2).setCellStyle(titleFormat1);
  		row3.createCell(2).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(2, (short)(50*256));
//	    sheet.mergeCells(2,2,2,3);
//	    sheet.addCell(new jxl.write.Label(2, 2,   "정산 지점", titleFormat1));
//	    sheet.setColumnView(2, 50);
	    
	    sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
		row2.createCell(3).setCellValue("구인제공 정산");
  		row2.getCell(3).setCellStyle(titleFormat1);
  		row2.createCell(4).setCellStyle(titleFormat1);
  		row2.createCell(5).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(3, (short)(40*256));
//	    sheet.mergeCells(3,2,4,2);
//	    sheet.addCell(new jxl.write.Label(3, 2,   "구인제공 정산", titleFormat1));
//	    sheet.setColumnView(3, 40);
	    
	    sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 8));
		row2.createCell(6).setCellValue("구직제공 정산");
  		row2.getCell(6).setCellStyle(titleFormat1);
  		row2.createCell(7).setCellStyle(titleFormat1);
  		row2.createCell(8).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(6, (short)(40*256));
//	    sheet.mergeCells(5,2,6,2);
//	    sheet.addCell(new jxl.write.Label(5, 2,   "구직제공 정산", titleFormat1));
//	    sheet.setColumnView(6, 40);
	    
	    sheet.addMergedRegion(new CellRangeAddress(2, 3, 9, 9));
		row2.createCell(9).setCellValue("정산금");
  		row2.getCell(9).setCellStyle(titleFormat1);
  		row3.createCell(9).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(9, (short)(40*256));
//	    sheet.mergeCells(7,2,7,3);
//	    sheet.addCell(new jxl.write.Label(7, 2,   "정산금", titleFormat1));
//	    sheet.setColumnView(9, 40);
	    
//	    sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 3));
	    row3.createCell(3).setCellValue("수입금");
	    row3.getCell(3).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(3, (short)(40*256));
  		
  		row3.createCell(4).setCellValue("지급금");
	    row3.getCell(4).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(4, (short)(40*256));
//	    sheet.mergeCells(3,3,3,3);
//	    sheet.addCell(new jxl.write.Label(3, 3,   "수입금", titleFormat1));
//	    sheet.setColumnView(4, 40);
	    
//  		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 4));
	    row3.createCell(5).setCellValue("정산 수수료");
	    row3.getCell(5).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(5, (short)(40*256));
//	    sheet.mergeCells(4,3,4,3);
//	    sheet.addCell(new jxl.write.Label(4, 3,   "정산 수수료", titleFormat1));
//	    sheet.setColumnView(5, 40);
	    
//  		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 5));
  		row3.createCell(6).setCellValue("수입금");
	    row3.getCell(6).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(6, (short)(40*256));
  		
	    row3.createCell(7).setCellValue("지급금");
	    row3.getCell(7).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(7, (short)(40*256));
  		
//	    sheet.mergeCells(5,3,5,3);
//	    sheet.addCell(new jxl.write.Label(5, 3,   "지급금", titleFormat1));
//	    sheet.setColumnView(7, 40);
	    
//	    sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 6));
	    row3.createCell(8).setCellValue("정산 수수료");
	    row3.getCell(8).setCellStyle(titleFormat1);
  		sheet.setColumnWidth(8, (short)(40*256));
//	    sheet.mergeCells(6,3,6,3);
//	    sheet.addCell(new jxl.write.Label(6, 3,   "정산 수수료", titleFormat1));
//	    sheet.setColumnView(8, 40);
	    
	    //sheet.setRowView(rowNum, 500);
	    
	    ++rowNum;
	}

	private void createDataRow(Sheet sheet, AccountingDTO accountingDTO, List<AccountingDTO> resultList) throws Exception {
		int colNum = 0;
		String nal = accountingDTO.getStartDate().substring(0, 7);
		
//		int totalIlboFee = 0;
//		int totalWorkFee = 0;
//		int totalShareFee =0;
//		int totalWorkerFee = 0;
		int totalFee = 0;
		int totalWorkIncome = 0;
		int totalWorkPayment = 0;
		int totalWorkPaymentWorkerFee = 0;
		double totalWorkSettlementFee = 0;
		int totalWorkerIncome = 0;
		int totalWorkerIncomeWorkerFee = 0;
		int totalWorkerPayment = 0;
		double totalWorkerSettlementFee = 0;
		double totalAcc = 0;
		
		//구인제공(0)
		//float work_fees = (float) (Integer.parseInt(Const.work_fees) * 0.01);
		//구직제공(20)
		//float worker_fees = (float) (Integer.parseInt(Const.worker_fees) * 0.01);
	  
		++rowNum;
	  
		for (int i = 0; i < resultList.size(); i++) {
			AccountingDTO item = resultList.get(i);
			Row row = sheet.createRow(rowNum);
			
			if(!"0".equals(accountingDTO.getCompany_seq())){
				nal = item.getIlbo_date();
			}
			int workIncome = Integer.parseInt(item.getWork_income());
			int workPayment = Integer.parseInt(item.getWork_payment());
			int workPaymentWorkerFee = Integer.parseInt(item.getWork_payment_worker_fee());
			double workSettlementFee = (workIncome + workPayment) * (Integer.parseInt(accountingDTO.getWork_fee_rate()) * 0.01);
			int workerIncome = Integer.parseInt(item.getWorker_income());
			int workerIncomeWorkerFee = Integer.parseInt(item.getWorker_income_worker_fee());
			int workerPayment = Integer.parseInt(item.getWorker_payment());
			double workerSettlementFee = (workerIncome + workerPayment) * (Integer.parseInt(accountingDTO.getWorker_fee_rate()) * 0.01);
			
			
			//totalAcc = (Integer.parseInt(item.getShare_fee()) + (Integer.parseInt(item.getShare_fee()) * work_fees)) - (Integer.parseInt(item.getIlbo_fee()) + (Integer.parseInt(item.getIlbo_fee()) * worker_fees));
			totalAcc = workIncome + (workerIncome - workerIncomeWorkerFee) - (workPayment - workPaymentWorkerFee) - workSettlementFee - workerPayment - workerSettlementFee;
			totalFee += totalAcc;
			row.createCell(colNum).setCellValue((i+1));
			row.getCell(colNum++).setCellStyle(numberFormat);
			//sheet.addCell(new jxl.write.Number(colNum, rowNum,   (i+1), numberFormat));
			row.createCell(colNum).setCellValue(nal);
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   nal, cellFormat2));
			row.createCell(colNum).setCellValue(item.getCompany_name());
			row.getCell(colNum++).setCellStyle(cellFormat2);
			//sheet.addCell(new jxl.write.Label(++colNum, rowNum,   item.getCompany_name(), cellFormat2));
			//row.createCell(colNum).setCellValue(Integer.parseInt(item.getShare_fee()));
			//구인제공 수입금
			row.createCell(colNum).setCellValue(workIncome);
			row.getCell(colNum++).setCellStyle(numberFormat);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getShare_fee()), numberFormat));
			//row.createCell(colNum).setCellValue(Integer.parseInt(item.getShare_fee()) * work_fees);
			//구인제공 지급금
			row.createCell(colNum).setCellValue(workPayment - workPaymentWorkerFee);
			row.getCell(colNum++).setCellStyle(numberFormat);
			//구인제공 정산수수료
			row.createCell(colNum).setCellValue(workSettlementFee);
			row.getCell(colNum++).setCellStyle(numberFormat);
			
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getShare_fee()) * work_fees, numberFormat));
			//row.createCell(colNum).setCellValue(Integer.parseInt(item.getIlbo_fee()));
			//구직제공 수입금
			row.createCell(colNum).setCellValue(workerIncome - workerIncomeWorkerFee);
			row.getCell(colNum++).setCellStyle(numberFormat);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getIlbo_fee()), numberFormat));
			//row.createCell(colNum).setCellValue(Integer.parseInt(item.getIlbo_fee()) * worker_fees);
			//구직제공 지급금
			row.createCell(colNum).setCellValue(workerPayment);
			row.getCell(colNum++).setCellStyle(numberFormat);
			//구직제공 정산수수료
			row.createCell(colNum).setCellValue(workerSettlementFee);
			row.getCell(colNum++).setCellStyle(numberFormat);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   Integer.parseInt(item.getIlbo_fee()) * worker_fees, numberFormat));
			row.createCell(colNum).setCellValue(totalAcc);
			row.getCell(colNum++).setCellStyle(numberFormat);
			//sheet.addCell(new jxl.write.Number(++colNum, rowNum,   totalAcc, numberFormat));
			row.setHeight((short)400);
			//sheet.setRowView(rowNum, 400);
			
			colNum = 0;
			
			totalWorkIncome += workIncome;
			totalWorkPayment += workPayment;
			totalWorkPaymentWorkerFee += workPaymentWorkerFee;
			totalWorkSettlementFee += workSettlementFee;
			totalWorkerIncome += workerIncome;
			totalWorkerIncomeWorkerFee += workerIncomeWorkerFee;
			totalWorkerPayment += workerPayment;
			totalWorkerSettlementFee += workerSettlementFee;
//			totalIlboFee += Integer.parseInt(item.getShare_fee());
//			totalWorkFee += (Integer.parseInt(item.getShare_fee()) * work_fees);
//			totalShareFee += Integer.parseInt(item.getIlbo_fee());
//			totalWorkerFee += (Integer.parseInt(item.getIlbo_fee()) * worker_fees);
			rowNum++;	  
		}
	  
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
		//sheet.mergeCells(0, rowNum, 2, rowNum); // 시작열, 시작행, 종료열, 종료행
		Row row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue("합계");
		row.getCell(0).setCellStyle(titleFormat1);
		row.createCell(1).setCellStyle(titleFormat1);
		row.createCell(2).setCellStyle(titleFormat1);
		//sheet.addCell(new jxl.write.Label(0, rowNum,   "합계", titleFormat1));
		
		row.createCell(3).setCellValue(totalWorkIncome);
		row.getCell(3).setCellStyle(numberFormat2);
		//sheet.addCell(new jxl.write.Number(3, rowNum,   totalIlboFee, numberFormat2));
		row.createCell(4).setCellValue(totalWorkPayment - totalWorkPaymentWorkerFee);
		row.getCell(4).setCellStyle(numberFormat2);
		row.createCell(5).setCellValue(totalWorkSettlementFee);
		row.getCell(5).setCellStyle(numberFormat2);
		//sheet.addCell(new jxl.write.Number(4, rowNum,   totalWorkFee, numberFormat2));
		row.createCell(6).setCellValue(totalWorkerIncome - totalWorkerIncomeWorkerFee);
		row.getCell(6).setCellStyle(numberFormat2);
		//sheet.addCell(new jxl.write.Number(5, rowNum,   totalShareFee, numberFormat2));
		row.createCell(7).setCellValue(totalWorkerPayment);
		row.getCell(7).setCellStyle(numberFormat2);
		row.createCell(8).setCellValue(totalWorkerSettlementFee);
		row.getCell(8).setCellStyle(numberFormat2);
		//sheet.addCell(new jxl.write.Number(6, rowNum,   totalWorkerFee, numberFormat2));
		row.createCell(9).setCellValue(totalFee);
		row.getCell(9).setCellStyle(numberFormat2);
		//sheet.addCell(new jxl.write.Number(7, rowNum,   totalFee, numberFormat2));
		row.setHeight((short)400);
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
