package net.study.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.study.common.BizExcelView;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("codeExcelView")
public class CodeExcel extends BizExcelView {

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 엑셀파일을 설정하고 생성한다.
	 * @param model
	 * @param wb
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String title = "코드";
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultRowHeight(CELL_HEIGHT_BASIC);
		doBuild(sheet, wb, model, title);
	}

	/**
	 * key List 생성
	 * Label / key / size
	 * @return
	 * @throws Exception
	 */
	protected List<String[]> makeKeyList() throws Exception {
		List<String[]> keyList = new ArrayList<String[]>();
		keyList.add(new String[]{"코드카테고리", "CODECATEGORYKEY", "25"});
		keyList.add(new String[]{"코드", "CODE", "25"});
		keyList.add(new String[]{"코드설명", "CODEEXPLAIN", "25"});
		keyList.add(new String[]{"코드명", "CODENAME", "25"});
		keyList.add(new String[]{"코드명 (영문)", "CODEENGNAME", "25"});
		keyList.add(new String[]{"상태", "STATUS", "5"});
		keyList.add(new String[]{"정렬순서", "SORTORDER", "25"});
		return keyList;
	}
	
}
