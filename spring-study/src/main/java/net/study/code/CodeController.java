package net.study.code;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			) throws Exception {
    	
    	Map condition = new HashMap();
    	condition.put("codecategorykey", codecategorykey);
    	condition.put("code", code);
    	logger.info("viewList() condition={}", condition);
    	
    	Map result = facade.findList(condition);
    	logger.info("viewList() totalRow={}", result.get("totalRow"));
    	
    	model.addAttribute("codeName", "abcde");
    	model.addAttribute("totalRow", result.get("totalRow"));
    	model.addAttribute("bizList", result.get("bizList"));
    	
		return "code/code_list";
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
		
		return "code/code_edit";
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
		
		return "code/code_edit";
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
			HttpServletRequest request
			, @ModelAttribute("codeVo") CodeVo codeVo
			, BindingResult bindingResult 
			, RedirectAttributes redirectAttributes
			) throws Exception {    

		
		// 정보 등록
		CodeVo bizResult = facade.regist(codeVo, null);
		redirectAttributes.addFlashAttribute("codeVo", bizResult);
		
		return "redirect:/code.do?command=findDetail&codecategorykey="+codeVo.getCodecategorykey()+"&code="+codeVo.getCode();
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
			HttpServletRequest request
			, @ModelAttribute("codeVo") CodeVo codeVo
			, BindingResult bindingResult
			, ModelMap model
			) throws Exception {
		
		// 정보 수정
    	CodeVo bizResult = facade.modify(codeVo, null);

		return "code/code_edit";
    }
    
//	/**
//	 * <pre>
//	 * 코드 삭제
// 	 * </pre>
//	 *
//	 * @param request
//	 * @param model
//	 * @return 
//	 * @throws Exception
//	 */
//    @RequestMapping(params="command=delete")
//	public void delete (
//			HttpServletRequest request
//			, HttpServletResponse response
//			) throws Exception {
//
//		String selectedData = request.getParameter("selectedData");
//		List selList = JSONUtil.jsonArray2List(selectedData);
//
//		// 정보 삭제
//		BizResult bizResult = facade.delete(selList, condition);
//
//		String message = bizMessageSource.getBizMessage(bizResult.getMessageCode(), bizResult.getMessageArgs(), BizUtil.getLocale(request));
//		logger.info("delete() message={}", message);
//
//		// 한글깨짐 방지 위해 String return 하지 않고 PrintWriter 이용함
//		response.setContentType("text/html;charset="+BizConst.ENCODING_DEFAULT);
//		PrintWriter out = response.getWriter();
//		out.print(message);
//		out.close();
//	}

}
