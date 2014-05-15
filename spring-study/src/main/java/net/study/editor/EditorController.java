package net.study.editor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.study.common.BizCondition;
import net.study.common.BizConst;
import net.study.common.BizMessageSource;
import net.study.common.BizResult;
import net.study.common.GridResponse;
import net.study.util.BizUtil;
import net.study.util.JSONUtil;
import net.study.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**  
 * <pre>
 * 에디터 Controller
 * </pre>
 *
 * @version 2014.05.12
 * @author 김지홍 
 */
@Controller
@RequestMapping(value="/editor")
public class EditorController {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * facade
	 */
	@Autowired
	private EditorFacade facade;

	/**
	 * bizMessageSource
	 */
	@Autowired
	private BizMessageSource bizMessageSource;

	/**
	 * <pre>
	 * facade setter
	 * </pre>
	 *
	 * @param facade
	 */
	public void setEditorFacade(EditorFacade facade) {
		this.facade = facade;
	}

    /**
	 * <pre>
	 * 에디터 목록화면 표시
 	 * </pre>
	 *
     * @param request
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=viewList")
	public String viewList (
			HttpServletRequest request
			, ModelMap model
			) throws Exception {

		BizCondition condition = new BizCondition(request, "monthperiod3to1");
		
		BizResult bizResult = facade.viewList(condition);
        model.addAttribute("condition", bizResult.getCondition());
        
		return "list/editor/editor_list.tiles";
	}

    /**
	 * <pre>
	 * 에디터 목록
 	 * </pre>
	 *
     * @param request
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(params="command=findList")
	public @ResponseBody GridResponse findList (
			HttpServletRequest request
			, ModelMap model
			) throws Exception {

		BizCondition condition = new BizCondition(request);
		
		BizResult bizResult = facade.findList(condition);
		condition = bizResult.getCondition();
        model.addAttribute("condition", condition);
        
        return BizUtil.makeGridResponseData(bizResult.getBizList(), condition); // grid response
	}

	/**
	 * <pre>
	 * 에디터 등록화면
 	 * </pre>
	 *
	 * @param request
	 * @param editorVo
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=entry")
 	public String entry (
 			HttpServletRequest request
 			, @ModelAttribute("editorVo") EditorVo editorVo
 			, ModelMap model
 			, @RequestParam(value="editorType",required=false) String editorType
 			) throws Exception {
 			
		BizCondition condition = new BizCondition(request);
		condition.put("dbmode", BizConst.CODE_DB_INSERT); // 입력
		condition.put("editorType", editorType);

		BizResult bizResult = facade.entry(condition);
		model.addAttribute("condition", bizResult.getCondition()); // 검색조건 유지
		model.addAttribute("editorVo", bizResult.getBizData());
		
		String viewName = "edit/editor/editor_edit.tiles";
		if (StringUtil.isNotEmpty(editorType)) {
			if ("daumeditor".equals(editorType)) viewName = "edit/editor/editor_edit_daumeditor.tiles";
			if ("smarteditor".equals(editorType)) viewName = "edit/editor/editor_edit_smarteditor.tiles";
		}
		return viewName;
	}

	/**
	 * <pre>
	 * 첨부파일 등록 팝업
 	 * </pre>
	 *
	 * @param request
	 * @param editorVo
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=attachPopup")
 	public String attachPopup (
 			HttpServletRequest request
 			, @ModelAttribute("editorVo") EditorVo editorVo
 			, ModelMap model
 			, @RequestParam(value="editorType",required=false) String editorType
 			, @RequestParam(value="attachType",required=false) String attachType
 			) throws Exception {
 			
		BizCondition condition = new BizCondition(request);
		condition.put("dbmode", BizConst.CODE_DB_INSERT); // 입력
		condition.put("editorType", editorType);
		condition.put("attachType", attachType);

		BizResult bizResult = facade.entry(condition);
		model.addAttribute("condition", bizResult.getCondition()); // 검색조건 유지
		model.addAttribute("editorVo", bizResult.getBizData());
		
		String viewName = "blank/editor/daumeditor_file_popup.tiles";
		if (StringUtil.isNotEmpty(editorType)) {
			if ("daumeditor".equals(editorType)) {
				if ("image".equals(attachType)) {
					viewName = "blank/editor/daumeditor_image_popup.tiles";
					
				} else if ("file".equals(attachType)) {
					viewName = "blank/editor/daumeditor_file_popup.tiles";
				}
			}
			if ("smarteditor".equals(editorType)) {
				if ("image".equals(attachType)) {
					viewName = "blank/editor/smarteditor_image_popup.tiles";
				}
			}
		}
		logger.info("attachPopup() editorType={}", editorType+", attachType="+attachType+", viewName="+viewName);
		return viewName;
	}

	/**
	 * <pre>
	 * 에디터 등록
 	 * </pre>
	 *
	 * @param request
	 * @param editorVo
	 * @param bindingResult
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=regist")
	public String regist (
			HttpServletRequest request
			, @ModelAttribute("editorVo") EditorVo editorVo
			, BindingResult bindingResult 
			, RedirectAttributes redirectAttributes
			, @RequestParam(value="editorType",required=false) String editorType
			) throws Exception {    

		BizCondition condition = new BizCondition(request);
		
		editorVo = facade.prepareData(editorVo); // validation 전에 수행!!
		
		// 정보 등록
		BizResult bizResult = facade.regist(editorVo, condition);
		redirectAttributes.addFlashAttribute("bizMessage", bizResult.getMessage());
		redirectAttributes.addFlashAttribute("bizMessageCode", bizResult.getMessageCode());

		redirectAttributes.addFlashAttribute("condition", condition); // 검색조건 유지
		redirectAttributes.addFlashAttribute("editorVo", bizResult.getBizData());
		
		return "redirect:/editor.do?command=findDetail&selected_key="+editorVo.getBbskey()+"&editorType="+editorType;
    }

	/**
	 * <pre>
	 * 에디터 수정
 	 * </pre>
	 *
	 * @param request
	 * @param editorVo
	 * @param bindingResult
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=modify")
	public String modify (
			HttpServletRequest request
			, @ModelAttribute("editorVo") EditorVo editorVo
			, BindingResult bindingResult
			, ModelMap model
 			, @RequestParam(value="editorType",required=false) String editorType
			) throws Exception {
		
		BizCondition condition = new BizCondition(request);
		condition.put("editorType", editorType);
		
		editorVo = facade.prepareData(editorVo); // validation 전에 수행!!

		// 정보 수정
    	BizResult bizResult = facade.modify(editorVo, condition);
    	
    	// 단건조회
    	BizCondition param = new BizCondition();
    	param.setSelectedKey(editorVo.getBbskey());
		BizResult resResult = facade.findDetail(param);
		model.put("editorVo", resResult.getBizData());
		model.put("attachList", resResult.getBizList());

    	model.addAttribute("bizMessage", bizResult.getMessage());
    	model.addAttribute("bizMessageCode", bizResult.getMessageCode());
    	model.addAttribute("condition", condition); // 검색조건 유지

		String viewName = "edit/editor/editor_edit.tiles";
		if (StringUtil.isNotEmpty(editorType)) {
			if ("daumeditor".equals(editorType)) viewName = "edit/editor/editor_edit_daumeditor.tiles";
			if ("smarteditor".equals(editorType)) viewName = "edit/editor/editor_edit_smarteditor.tiles";
		}
		return viewName;
    }
    
	/**
	 * <pre>
	 * 에디터 삭제
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=delete")
	public void delete (
			HttpServletRequest request
			, HttpServletResponse response
			) throws Exception {

		BizCondition condition = new BizCondition(request);
		String selectedData = request.getParameter("selectedData");
		List selList = JSONUtil.jsonArray2List(selectedData);

		// 정보 삭제
		BizResult bizResult = facade.delete(selList, condition);

		String message = bizMessageSource.getBizMessage(bizResult.getMessageCode(), bizResult.getMessageArgs(), BizUtil.getLocale(request));

		// 한글깨짐 방지 위해 String return 하지 않고 PrintWriter 이용함
		response.setContentType("text/html;charset="+BizConst.ENCODING_DEFAULT);
		PrintWriter out = response.getWriter();
		out.print(message);
		out.close();
	}

	/**
	 * <pre>
	 * 에디터 수정화면
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=findDetail")
 	public String findDetail (
 			HttpServletRequest request
 			, ModelMap model
 			, @RequestParam(value="editorType",required=false) String editorType
 			) throws Exception {
 		
 		BizCondition condition = new BizCondition(request);
		condition.put("editorType", editorType);
 		
		BizResult bizResult = facade.findDetail(condition);
		model.put("editorVo", bizResult.getBizData());
		model.put("attachList", bizResult.getBizList());
		model.addAttribute("condition", condition); // 검색조건 유지
		model.put(BindingResult.MODEL_KEY_PREFIX + "editorVo", model.get("errors")); // from redirectAttributes
		
		String viewName = "edit/editor/editor_edit.tiles";
		if (StringUtil.isNotEmpty(editorType)) {
			if ("daumeditor".equals(editorType)) viewName = "edit/editor/editor_edit_daumeditor.tiles";
			if ("smarteditor".equals(editorType)) viewName = "edit/editor/editor_edit_smarteditor.tiles";
		}
		return viewName;
	}

	/**
	 * <pre>
	 * 에디터 상세화면
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=view")
 	public String view (
 			HttpServletRequest request
 			, ModelMap model
 			) throws Exception {
 		
 		BizCondition condition = new BizCondition(request);
 		
		BizResult bizResult = facade.findDetail(condition);
		model.put("editorVo", bizResult.getBizData());
		model.put("attachList", bizResult.getBizList());
		model.addAttribute("condition", condition); // 검색조건 유지
		
		return "edit/editor/editor_view.tiles";
	}

}
