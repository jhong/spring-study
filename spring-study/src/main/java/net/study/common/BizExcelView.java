package net.study.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.study.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**  
 * <pre>
 * BizExcelView
 * </pre>
 *
 * @version 2013.01.16
 * @author 김지홍 
 */
public abstract class BizExcelView extends AbstractExcelView {
	
	/**
	 * 엑셀파일을 설정하고 생성한다.
	 * @param model
	 * @param wb
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected abstract void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * key List 생성
	 * Label / key / size
	 * @return
	 * @throws Exception
	 */
	protected abstract List<String[]> makeKeyList() throws Exception;

	/**
	 * 기본 너비 & 높이
	 */
	protected short CELL_WIDTH_BASIC = 12;
	protected short CELL_HEIGHT_BASIC = 400;
	
	
	/**
	 * doBuild
	 * @param sheet
	 * @param wb
	 * @param model
	 * @param title 
	 * @throws Exception
	 */
	protected void doBuild(HSSFSheet sheet, HSSFWorkbook wb, Map<String, Object> model, String title) throws Exception {
		//sheet.setDefaultColumnWidth(CELL_WIDTH_BASIC);
		 
		HSSFCellStyle style = getDataCellStyleBasic(wb);
		List<String[]> keyList = makeKeyList();
		List resultList = (List)model.get("resultList");

		setTopTitle(sheet, keyList, wb, title);
		
		for (int i = 0; i < resultList.size(); i++) {
//			DataObject data = (DataObject)resultList.get(i);
			Map data = (HashMap)resultList.get(i);
			// labels
			if (i == 0) {
				setLabelsBasic(sheet, keyList, wb);
			} else {
				HSSFRow row = sheet.getRow(i+1);
				row.setHeight(CELL_HEIGHT_BASIC);
			}
 
			// data
			setData(keyList, sheet, i+1, data, style);
		}
	}
	
	/**
	 * TOP 셀 스타일 반환
	 * @param wb
	 * @return
	 * @throws Exception
	 */
	protected HSSFCellStyle getTopCellStyleBasic(HSSFWorkbook wb) throws Exception {
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)20);
		font.setFontName("맑은 고딕");

		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		style.setWrapText(true);
		
		return style;
	}
	
	/**
	 * 제목 셀 스타일 반환
	 * @param wb
	 * @return
	 * @throws Exception
	 */
	protected HSSFCellStyle getTitleCellStyleBasic(HSSFWorkbook wb) throws Exception {
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)11);
		font.setFontName("맑은 고딕");

		HSSFCellStyle style = wb.createCellStyle();
//		style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
//		style.setFillPattern(HSSFCellStyle.FINE_DOTS);
		//style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		style.setWrapText(true);
		
		return style;
	}

	/**
	 * 데이터 셀 스타일 반환
	 * @param wb
	 * @return
	 * @throws Exception
	 */
	protected HSSFCellStyle getDataCellStyleBasic(HSSFWorkbook wb) throws Exception {
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)11);
		font.setFontName("맑은 고딕");
		 
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		style.setWrapText(true); // \n 줄바꿈 처리!!
		
		return style;
	}

	/**
	 * Top Title 세팅
	 * @param sheet
	 * @param result
	 * @throws Exception
	 */
	protected void setTopTitle(HSSFSheet sheet, List<String[]> keyList, HSSFWorkbook wb, String title) throws Exception {
		/*HSSFCellStyle style = getTopCellStyleBasic(wb);
		HSSFCell cell = null;
		cell.setCellStyle(style);
		cell = getCell(sheet, 0, 1);
		sheet.addMergedRegion(new Region(1,(short)1,1,(short)9)); //가로병합
		setText(cell, title);
		HSSFRow row = sheet.getRow(0);
		row.setHeight((short)500);*/

		HSSFCellStyle style = getTopCellStyleBasic(wb);
		HSSFCell cell = null;
			cell = getCell(sheet, 0, 1);
			cell.setCellStyle(style);
			setText(cell, title);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 개행문자 적용
			sheet.addMergedRegion(new Region(0,(short)1,0,(short)9));
			System.out.println("title ---------- "+title);
			
		HSSFRow row = sheet.getRow(0);
		row.setHeight((short)800);
	}
	
	/**
	 * Label 세팅
	 * @param sheet
	 * @param result
	 * @throws Exception
	 */
	protected void setLabelsBasic(HSSFSheet sheet, List<String[]> keyList, HSSFWorkbook wb) throws Exception {

		HSSFCellStyle style = getTitleCellStyleBasic(wb);
		HSSFCell cell = null;
		for (int i=0; i<keyList.size(); i++) {
			cell = getCell(sheet, 1, i);
			cell.setCellStyle(style);
			setText(cell, keyList.get(i)[0]);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 개행문자 적용

			sheet.setColumnWidth(i, new Integer(keyList.get(i)[2])*265);    // Column 넓이 설정
		}
		
		//HSSFRow row = sheet.getRow(0);
		//row.setHeight((short)500);
	}
	
	/**
	 * 데이터 세팅
	 * @param keyList
	 * @param sheet
	 * @param idx
	 * @param data
	 * @param style
	 */
	protected void setData(List<String[]> keyList, HSSFSheet sheet, int idx, Map data, HSSFCellStyle style) {
		HSSFCell cell = null;
		for (int j=0; j<keyList.size(); j++) {
			
			cell = getCell(sheet, 1+idx, j);
			cell.setCellStyle(style);

			String fieldName = keyList.get(j)[1];
			String value = StringUtil.getStr(data, fieldName);
			
			// CRLF 제거 (줄바꿈 모두 제거햐여 특수문자 출력되지 않도록)
			if (!StringUtils.isEmpty(value) && 
					(value.indexOf("\r") >= 0 || value.indexOf("\n") >= 0)) 
				value = StringUtil.replaceCRLF(value);
			
			setText(cell, value);
		}
	}


}
