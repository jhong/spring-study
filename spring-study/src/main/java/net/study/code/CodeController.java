package net.study.code;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value={"/code"})
public class CodeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "codeFacade")
    private CodeFacade facade;

    /**
	 * <pre>
	 * 목록 페이지 조회
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
		
//		return "code/code_edit";
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
		
//		return "code/code_edit";
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
			, ModelMap model
			) throws Exception {    

		
		// 정보 등록
		CodeVo bizResult = facade.regist(codeVo, null);
		model.addAttribute("codeVo", bizResult);
		
//		return "code/code_edit";
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
		
		// 정보 수정
    	CodeVo bizResult = facade.modify(codeVo, null);

//		return "code/code_edit";
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

}
