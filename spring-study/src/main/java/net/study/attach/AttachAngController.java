package net.study.attach;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.study.common.BizCondition;
import net.study.common.BizMessageSource;
import net.study.common.BizResult;
import net.study.common.DataObject;
import net.study.common.GridResponse;
import net.study.util.BizUtil;
import net.study.util.StringUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**  
 * <pre>
 * 첨부파일 Controller (angularjs 사용)
 * </pre>
 *
 * @version 2014.05.22
 * @author 김지홍 
 */
@Controller
@RequestMapping(value="/attaches")
public class AttachAngController {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * facade
	 */
	@Autowired
	private AttachFacade facade;
    
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
	public void setAttachFacade(AttachFacade facade) {
		this.facade = facade;
	}

    /**
	 * <pre>
	 * 첨부파일 angular index 페이지
 	 * </pre>
	 *
     * @param request
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
	public String index (
			HttpServletRequest request
			, ModelMap model
			) throws Exception {

		BizCondition condition = new BizCondition(request, "monthperiod3to1");
		
		BizResult bizResult = facade.viewList(condition);
        model.addAttribute("condition", bizResult.getCondition());
        
		return "angular/attach/attach_ang.tiles";
	}

//    /**
//	 * <pre>
//	 * 첨부파일 목록화면 표시
// 	 * </pre>
//	 *
//     * @param request
//     * @param model
//     * @return 
//     * @throws Exception
//     */
//    @RequestMapping(params="command=viewList")
//	public String viewList (
//			HttpServletRequest request
//			, ModelMap model
//			) throws Exception {
//
//		BizCondition condition = new BizCondition(request, "monthperiod3to1");
//		
//		BizResult bizResult = facade.viewList(condition);
//        model.addAttribute("condition", bizResult.getCondition());
//        
//		return "angular/attach/attach_ang.tiles";
//	}

    /**
	 * <pre>
	 * 첨부파일 목록
 	 * </pre>
	 *
     * @param request
     * @param model
     * @return 
     * @throws Exception
     */
    @RequestMapping(value="/list", method = RequestMethod.GET)
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

//	/**
//	 * <pre>
//	 * 첨부파일 등록화면
// 	 * </pre>
//	 *
//	 * @param request
//	 * @param attachVo
//	 * @param model
//	 * @return 
//	 * @throws Exception
//	 */
//    @RequestMapping(params="command=entry", method = RequestMethod.GET)
// 	public String entry (
// 			HttpServletRequest request
// 			, @ModelAttribute("attachVo") AttachVo attachVo
// 			, ModelMap model
// 			) throws Exception {
// 			
//		BizCondition condition = new BizCondition(request);
//		condition.put("dbmode", BizConst.CODE_DB_INSERT); // 입력
//
//		BizResult bizResult = facade.entry(condition);
//		model.addAttribute("condition", bizResult.getCondition()); // 검색조건 유지
//		model.addAttribute("attachVo", bizResult.getBizData());
//		
//		return "angular/attach/attach_edit_ang.tiles";
//	}
//   
	/**
	 * <pre>
	 * 첨부파일 등록
 	 * </pre>
	 *
	 * @param request
	 * @param attachVo
	 * @param bindingResult
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(value="/", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> regist (
			HttpServletRequest request
			, @RequestBody AttachVo attachVo
			) throws Exception {    
    	logger.info("[AttachAngController] regist() start............ attachVo={}", attachVo);
		BizCondition condition = new BizCondition(request);

		logger.info("[AttachAngController] regist() start............ request.getParameter(\"data\")={}", request.getParameter("data"));
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!
		
		// 정보 등록
//		MultipartHttpServletRequest multiPart = (MultipartHttpServletRequest)request;
//		Iterator itr = multiPart.getFileNames();
//
//		BizResult bizResult = new BizResult();
//		while (itr.hasNext()) {
//			MultipartFile formFile = multiPart.getFile((String)itr.next());
//			if (formFile.getSize() > 0) {
//				AttachVo file = new AttachVo();
//				
//				// 화면에서 filekey 넘어오면 그대로 사용하도록 수정 by jhong (2013.04.22) - TODO: filekey 중복 체크...
//				if (StringUtil.isNotEmpty(request.getParameter("filekey")))
//					file.setFilekey(request.getParameter("filekey"));
//				else
//					file.setFilekey(UUID.randomUUID().toString());
//				
//				file.setFilename(formFile.getOriginalFilename());
//				file.setMimetype(formFile.getContentType());
//				file.setFilesize(new BigDecimal(formFile.getSize()));
//				file.setFiledesc(request.getParameter("filedesc"));
//
//				// 파일저장
//				this.facade.uploadFile(formFile, file);
//
//				// 파일첨부정보 등록
//				bizResult = facade.regist(file, condition);
//
//				condition.setString("filekey", file.getFilekey());
//			}
//		}
		
		BizResult bizResult = facade.regist(attachVo, condition);


//		redirectAttributes.addFlashAttribute("bizMessage", bizResult.getMessage());
//		redirectAttributes.addFlashAttribute("bizMessageCode", bizResult.getMessageCode());
//
//		redirectAttributes.addFlashAttribute("condition", condition); // 검색조건 유지
//		redirectAttributes.addFlashAttribute("attachVo", bizResult.getBizData());
		
//		return "redirect:/attachAng.do?command=findDetail&selected_key="+condition.getString("filekey");
		return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

	/**
	 * <pre>
	 * 첨부파일 수정
 	 * </pre>
	 *
	 * @param request
	 * @param attachVo
	 * @param bindingResult
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(value="/{filekey}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> modify (
			HttpServletRequest request
			, @RequestBody AttachVo attachVo
 			, @PathVariable String filekey
			, ModelMap model
			) throws Exception {
		
		BizCondition condition = new BizCondition(request);
 		logger.info("findDetail() filekey={}", filekey+", condition="+condition+", attachVo="+attachVo);
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!

		// 정보 수정
    	BizResult bizResult = facade.modify(attachVo, condition);
//    	model.addAttribute("bizMessage", bizResult.getMessage());
//    	model.addAttribute("bizMessageCode", bizResult.getMessageCode());
//    	model.addAttribute("condition", condition); // 검색조건 유지
//
//		return "angular/attach/attach_edit_ang.tiles";
		return new ResponseEntity<String>("", HttpStatus.OK);
    }
    
	/**
	 * <pre>
	 * 첨부파일 삭제
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(value="/{filekey}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete (
			HttpServletRequest request
 			, @PathVariable String filekey
			) throws Exception {
    	logger.info("delete() start.... filekey={}", filekey);

		String[] filekeyArr = null;
		if (StringUtil.isNotEmpty(filekey)) filekeyArr = filekey.split(",");

		BizCondition condition = new BizCondition(request);
		/*
		String selectedData = request.getParameter("selectedData");
		List selList = JSONUtil.jsonArray2List(selectedData);
		*/
		List selList = new ArrayList();
		for (int i=0; filekeyArr != null && i<filekeyArr.length; i++) {
			DataObject selectedData = new DataObject();
			selectedData.put("filekey", filekeyArr[i]);
			selList.add(selectedData);
		}

		// 정보 삭제
		BizResult bizResult = facade.deleteList(selList, condition);

		String message = bizMessageSource.getBizMessage(bizResult.getMessageCode(), bizResult.getMessageArgs(), BizUtil.getLocale(request));
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * <pre>
	 * 첨부파일 수정화면
 	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(value="/{filekey}", method = RequestMethod.GET)
 	public @ResponseBody ResponseEntity<?> findDetail (
 			HttpServletRequest request
 			, @PathVariable String filekey
 			) throws Exception {
 		
 		BizCondition condition = new BizCondition(request);
 		condition.setSelectedKey(filekey);
 		logger.info("findDetail() filekey={}", filekey+", condition="+condition);
 		
		BizResult bizResult = facade.findDetail(condition);
//		model.put("attachVo", bizResult.getBizData());
//		model.addAttribute("condition", condition); // 검색조건 유지
//		model.put(BindingResult.MODEL_KEY_PREFIX + "attachVo", model.get("errors")); // from redirectAttributes
//		
//		return "angular/attach/attach_edit_ang.tiles";
		
		ObjectMapper mapper = new ObjectMapper();
		String bizStr = mapper.writeValueAsString(bizResult.getBizData());
		logger.info("findDetail() bizStr={}", bizStr+", bizData="+bizResult.getBizData());
		return new ResponseEntity<String>(bizStr, HttpStatus.OK);
	}

}
