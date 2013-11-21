package net.study.code;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.json.JSONArray;
import net.study.common.BizCondition;
import net.study.common.GridResponse;
import net.study.common.Properties;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/code"})
public class CodeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "codeFacade")
    private CodeFacade facade;

	/**
	 * codeValidator
	 */
	@Resource(name = "codeValidator")
	private CodeValidator codeValidator;

    /**
	 * <pre>
	 * 목록 페이지 조회 (map list 반환)
 	 * </pre>
	 *
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=viewList")
	public String viewList (
			ModelMap model
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			, @RequestParam(value="firstRowIndex",required=false) Integer firstRowIndex		// 페이징 위한 검색조건
			, @RequestParam(value="rowCountPerPage",required=false) Integer rowCountPerPage	// 페이징 위한 검색조건
			) throws Exception {
    	
    	Map condition = new HashMap();
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
    	condition.put("firstRowIndex", firstRowIndex);		// 페이징 위한 검색조건
    	condition.put("rowCountPerPage", rowCountPerPage);	// 페이징 위한 검색조건
    	logger.info("viewList() condition={}", condition);
    	
    	Map result = facade.findList(condition);
    	logger.info("viewList() totalRow={}", result.get("totalRow"));
    	
    	model.addAttribute("codeName", "abcde");
    	model.addAttribute("totalRow", result.get("totalRow"));
    	model.addAttribute("bizList", result.get("bizList"));
    	
//		return "code/code_list";
    	return "list/code/code_list.tiles";
	}

    /**
	 * <pre>
	 * 목록 페이지 조회 (json list 반환)
 	 * </pre>
	 *
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=viewListJson")
	public String viewListJson (
			ModelMap model
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			, @RequestParam(value="firstRowIndex",required=false) Integer firstRowIndex		// 페이징 위한 검색조건
			, @RequestParam(value="rowCountPerPage",required=false) Integer rowCountPerPage	// 페이징 위한 검색조건
			) throws Exception {
    	
    	Map condition = new HashMap();
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
    	condition.put("firstRowIndex", firstRowIndex);		// 페이징 위한 검색조건
    	condition.put("rowCountPerPage", rowCountPerPage);	// 페이징 위한 검색조건
    	logger.info("viewListJson() condition={}", condition);
    	
    	Map result = facade.findList(condition);
    	
    	model.addAttribute("totalRow", result.get("totalRow"));
//    	model.addAttribute("bizList", result.get("bizList"));
    	
    	List mapList = (List)result.get("bizList");
    	JSONArray jsonArray = JSONArray.fromObject(mapList); // map list 를 json array로 변환
    	logger.info("viewListJson() jsonArray={}", jsonArray);
    	model.addAttribute("bizList", jsonArray);
    	
    	return "list/code/code_list_json.tiles";
	}

    /**
	 * <pre>
	 * 목록 조회 (json list string 반환)
 	 * </pre>
	 *
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=findListJson")
	public void findListJson (
			HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			, @RequestParam(value="firstRowIndex",required=false) Integer firstRowIndex		// 페이징 위한 검색조건
			, @RequestParam(value="rowCountPerPage",required=false) Integer rowCountPerPage	// 페이징 위한 검색조건
			) throws Exception {
    	
    	Map condition = new HashMap();
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
    	condition.put("firstRowIndex", firstRowIndex);		// 페이징 위한 검색조건
    	condition.put("rowCountPerPage", rowCountPerPage);	// 페이징 위한 검색조건
    	logger.info("findListJson() condition={}", condition);
    	
    	Map result = facade.findList(condition);
    	
    	List mapList = (List)result.get("bizList");
    	JSONArray jsonArray = JSONArray.fromObject(mapList); // map list 를 json array로 변환
    	logger.info("findListJson() jsonArray={}", jsonArray);
    	
    	
		// 한글깨짐 방지 위해 String return 하지 않고 PrintWriter 이용함
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonArray.toString());
		out.close();
	}

    /**
	 * <pre>
	 * 목록 페이지 조회 (jqGrid)
 	 * </pre>
	 *
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=viewListGrid")
	public String viewListGrid (
			HttpServletRequest request
			, ModelMap model
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			) throws Exception {
    	
    	BizCondition condition = new BizCondition(request);
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
    	logger.info("viewListGrid() condition={}", condition);
    	
    	model.addAttribute("condition", condition);

    	return "list/code/code_list_grid.tiles";
	}

    /**
	 * <pre>
	 * 목록 조회 (jqGrid)
 	 * </pre>
	 *
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=findListGrid")
	public @ResponseBody GridResponse findListGrid (
			HttpServletRequest request
			, HttpServletResponse response
			, ModelMap model
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			) throws Exception {
    	
    	BizCondition condition = new BizCondition(request);
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
    	logger.info("findListGrid() condition={}", condition);
    	
    	Map result = facade.findList(condition);
    	model.addAttribute("condition", condition);
    	
    	List bizList = (List)result.get("bizList");
    	return makeGridResponseData(bizList, condition); // grid response
	}

	/**
	 * <pre>
	 * Grid Response 생성
	 * </pre>
	 * @param bizResult
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	GridResponse makeGridResponseData(List bizList, BizCondition condition) throws Exception {
        // grid response
        GridResponse response = new GridResponse();
        if (bizList != null && bizList.size() > 0)
        	response.setData(bizList);
        if (condition != null) {
	        response.setRecords(String.valueOf(condition.getTotalRow())); // total number of records
	        response.setPage(String.valueOf(condition.getPage())); // page
	        response.setTotal(String.valueOf(condition.getTotalPage())); // total pages
        }

        return response;
	}

	/**
	 * <pre>
	 * 상세조회
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=findDetail")
 	public String findDetail (
 			ModelMap model
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
 			) throws Exception {
 		
    	Map condition = new HashMap();
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);

    	CodeVo codeVo = facade.findDetail(condition);
		model.put("codeVo", codeVo);
		
//		return "edit/code/code_edit.tiles";
		return "edit/code/code_edit.tiles";
	}

	/**
	 * <pre>
	 * 코드 등록화면
 	 * </pre>
	 *
	 * @param request
	 * @param codeVo
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=entry")
 	public String entry (
 			@ModelAttribute("codeVo") CodeVo codeVo
 			, ModelMap model
 			) throws Exception {
 			
		CodeVo result = facade.entry(null);
		model.addAttribute("codeVo", result);
		model.addAttribute("dbmode", "C"); // 등록
		
//		return "edit/code/code_edit.tiles";
		return "edit/code/code_edit.tiles";
	}
   
	/**
	 * <pre>
	 * 코드 등록
 	 * </pre>
	 *
	 * @param request
	 * @param codeVo
	 * @param bindingResult
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=regist")
	public String regist (
			@ModelAttribute("codeVo") CodeVo codeVo
			, BindingResult bindingResult 
			, ModelMap model
			) throws Exception {    

    	// validation
		this.codeValidator.validate(codeVo, bindingResult);
		if (!bindingResult.hasErrors()) {
		
			// 정보 등록
			CodeVo bizResult = facade.regist(codeVo, null);
			model.addAttribute("codeVo", bizResult);
		}
		
//		return "edit/code/code_edit.tiles";
		return "edit/code/code_edit.tiles";
    }

	/**
	 * <pre>
	 * 코드 수정
 	 * </pre>
	 *
	 * @param request
	 * @param codeVo
	 * @param bindingResult
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=modify")
	public String modify (
			@ModelAttribute("codeVo") CodeVo codeVo
			, BindingResult bindingResult
			, ModelMap model
			) throws Exception {
		
    	// validation
		this.codeValidator.validate(codeVo, bindingResult);
		if (!bindingResult.hasErrors()) {
			
			// 정보 수정
	    	CodeVo bizResult = facade.modify(codeVo, null);
		}

//		return "edit/code/code_edit.tiles";
    	return "edit/code/code_edit.tiles";
    }
    
	/**
	 * <pre>
	 * 코드 삭제
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=delete")
	public String delete (
			ModelMap model
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			) throws Exception {

    	Map condition = new HashMap();
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);

		// 정보 삭제
		int count = facade.delete(condition);
		
		// 리턴할 목록 조회
    	Map result = facade.findList(null);
    	
    	model.addAttribute("totalRow", result.get("totalRow"));
    	model.addAttribute("bizList", result.get("bizList"));

//		return "code/code_list";
    	return "list/code/code_list.tiles";
	}

	/**
	 * <pre>
	 * 코드로 조회하여 코드 및 설명정보 조회
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(params="command=getCodeValue")
	public void getCodeValue(
			HttpServletRequest request
			, HttpServletResponse response
			) throws Exception {
    	
		Map condition = new HashMap();
		condition.put("codecategorykey", request.getParameter("codecategorykey"));
		condition.put("code", request.getParameter("code"));

		String bizResult = facade.getCodeValue(condition);

		// 한글깨짐 방지 위해 String return 하지 않고 PrintWriter 이용함
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(bizResult);
		out.close();
	}

    /**
	 * <pre>
	 * messages
 	 * </pre>
	 *
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=viewMsg")
	public String viewMsg (
			ModelMap model
			) throws Exception {
    	
    	return "/sample/messages_test.jsp";
	}

    /**
	 * <pre>
	 * 엑셀 다운로드
 	 * </pre>
	 *
     * @param request
     * @param response
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=excelDownload")
	public ModelAndView excelDownload (
			HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
			, ModelMap model
			) throws Exception {

		BizCondition condition = new BizCondition(request);
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);

    	List bizResult = facade.findListExcel(condition);
        
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("resultList", bizResult);
		
    	String contentType = "application/excel";
    	String filename = "code_list.xls";
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");

        return new ModelAndView("codeExcelView", result);
	}

	/**
	 * <pre>
	 * 인쇄 화면
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=report")
 	public String report (
 			HttpServletRequest request
 			, HttpServletResponse response
			, @RequestParam(value="codecategorykey",required=false) String codecategorykey
			, @RequestParam(value="code",required=false) String code
 			, ModelMap model
 			) throws Exception {
 		
 		BizCondition condition = new BizCondition(request);
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
 		
		String xsltFilePath = Properties.get("path.report.xsl")+"CODE.xsl"; // XML 파일명 대문자임!!
    	String fileName = "code";

    	Document root = facade.findDetailXml(condition);
		printHtml(response, root, xsltFilePath);
		
		return "common/print_body";
	}

	/**
	 * <pre>
	 * html report 출력
	 * </pre>
	 * 
	 * @param response
	 * @param document
	 * @param xsltFilePath
	 * @throws Exception
	 */
	void printHtml(HttpServletResponse response, Document document, String xsltFilePath) throws Exception {

		if (document == null) return;
		String xmlStr = (document.asXML());

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Content-Type", "text/html");

		File file = ResourceUtils.getFile(xsltFilePath);
		StreamSource xsl = new StreamSource(file);
		StreamSource xml = new StreamSource(new StringReader(xmlStr));

		StreamResult streamResult = new StreamResult(response.getWriter());

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl);
		transformer.transform(xml, streamResult);
	}

}
