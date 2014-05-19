package net.study.attach;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.study.common.BizCondition;
import net.study.common.BizConst;
import net.study.common.BizMessageSource;
import net.study.common.BizResult;
import net.study.common.GridResponse;
import net.study.common.Properties;
import net.study.util.BizUtil;
import net.study.util.JSONUtil;
import net.study.util.StringUtil;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**  
 * <pre>
 * 첨부파일 Controller
 * </pre>
 *
 * @version 2014.05.13
 * @author 김지홍 
 */
@Controller
@RequestMapping(value="/attach")
public class AttachController {

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
	 * 첨부파일 목록화면 표시
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
        
		return "list/attach/attach_list.tiles";
	}

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
	 * 첨부파일 등록화면
 	 * </pre>
	 *
	 * @param request
	 * @param attachVo
	 * @param model
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=entry")
 	public String entry (
 			HttpServletRequest request
 			, @ModelAttribute("attachVo") AttachVo attachVo
 			, ModelMap model
 			) throws Exception {
 			
		BizCondition condition = new BizCondition(request);
		condition.put("dbmode", BizConst.CODE_DB_INSERT); // 입력

		BizResult bizResult = facade.entry(condition);
		model.addAttribute("condition", bizResult.getCondition()); // 검색조건 유지
		model.addAttribute("attachVo", bizResult.getBizData());
		
		return "edit/attach/attach_edit.tiles";
	}
   
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
    @RequestMapping(params="command=regist")
	public String regist (
			HttpServletRequest request
			, @ModelAttribute("attachVo") AttachVo attachVo
			, BindingResult bindingResult 
			, RedirectAttributes redirectAttributes
			) throws Exception {    

		BizCondition condition = new BizCondition(request);
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!
		
		// 정보 등록
		MultipartHttpServletRequest multiPart = (MultipartHttpServletRequest)request;
		Iterator itr = multiPart.getFileNames();

		BizResult bizResult = new BizResult();
		while (itr.hasNext()) {
			MultipartFile formFile = multiPart.getFile((String)itr.next());
			if (formFile.getSize() > 0) {
				AttachVo file = new AttachVo();
				
				// 화면에서 filekey 넘어오면 그대로 사용하도록 수정 by jhong (2013.04.22) - TODO: filekey 중복 체크...
				if (StringUtil.isNotEmpty(request.getParameter("filekey")))
					file.setFilekey(request.getParameter("filekey"));
				else
					file.setFilekey(UUID.randomUUID().toString());
				
				file.setFilename(formFile.getOriginalFilename());
				file.setMimetype(formFile.getContentType());
				file.setFilesize(new BigDecimal(formFile.getSize()));
				file.setFiledesc(request.getParameter("filedesc"));

				// 파일저장
				this.facade.uploadFile(formFile, file);

				// 파일첨부정보 등록
				bizResult = facade.regist(file, condition);

				condition.setString("filekey", file.getFilekey());
			}
		}


		redirectAttributes.addFlashAttribute("bizMessage", bizResult.getMessage());
		redirectAttributes.addFlashAttribute("bizMessageCode", bizResult.getMessageCode());

		redirectAttributes.addFlashAttribute("condition", condition); // 검색조건 유지
		redirectAttributes.addFlashAttribute("attachVo", bizResult.getBizData());
		
		return "redirect:/attach.do?command=findDetail&selected_key="+condition.getString("filekey");
    }

	/**
	 * <pre>
	 * 첨부파일 등록 (다음에디터 등록시 사용)
 	 * </pre>
	 *
	 * @param request
	 * @param attachVo
	 * @param bindingResult
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=registDaum")
	public String registDaum (
			HttpServletRequest request
			, @ModelAttribute("attachVo") AttachVo attachVo
			, BindingResult bindingResult 
			, ModelMap model
 			, @RequestParam(value="attachType",required=false) String attachType
			) throws Exception {    

		BizCondition condition = new BizCondition(request);
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!
		
		// 정보 등록
		MultipartHttpServletRequest multiPart = (MultipartHttpServletRequest)request;
		Iterator itr = multiPart.getFileNames();

		BizResult bizResult = new BizResult();
		while (itr.hasNext()) {
			MultipartFile formFile = multiPart.getFile((String)itr.next());
			if (formFile.getSize() > 0) {
				AttachVo file = new AttachVo();
				
				// 화면에서 filekey 넘어오면 그대로 사용하도록 수정 by jhong (2013.04.22) - TODO: filekey 중복 체크...
				if (StringUtil.isNotEmpty(request.getParameter("filekey")))
					file.setFilekey(request.getParameter("filekey"));
				else
					file.setFilekey(UUID.randomUUID().toString());
				
				file.setFilename(formFile.getOriginalFilename());
				file.setMimetype(formFile.getContentType());
				file.setFilesize(new BigDecimal(formFile.getSize()));
				file.setFiledesc(request.getParameter("filedesc"));

				// 파일저장
				this.facade.uploadFile(formFile, file);

				// 파일첨부정보 등록
				bizResult = facade.regist(file, condition);

				condition.setString("filekey", file.getFilekey());
			}
		}


		model.addAttribute("bizMessage", bizResult.getMessage());
		model.addAttribute("bizMessageCode", bizResult.getMessageCode());

		model.addAttribute("condition", condition); // 검색조건 유지
		model.addAttribute("attachVo", bizResult.getBizData());
		
		//return "/WEB-INF/jsp/editor/daumeditor_file_popup.jsp";
		//return  "blank/editor/daumeditor_file_popup.tiles";
		
		String viewName = "blank/editor/daumeditor_file_popup.tiles";
		if ("image".equals(attachType)) {
			viewName = "blank/editor/daumeditor_image_popup.tiles";
			
		} else if ("file".equals(attachType)) {
			viewName = "blank/editor/daumeditor_file_popup.tiles";
		}
		return viewName;
    }

	/**
	 * <pre>
	 * 첨부파일 등록 (스마트에디터 등록시 사용)
 	 * </pre>
	 *
	 * @param request
	 * @param attachVo
	 * @param bindingResult
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=registSmart")
	public String registSmart (
			HttpServletRequest request
			, @ModelAttribute("attachVo") AttachVo attachVo
			, BindingResult bindingResult 
			, ModelMap model
 			, @RequestParam(value="callback_func",required=false) String callbackFunction
			) throws Exception {    

		BizCondition condition = new BizCondition(request);
		condition.put("callback_func", callbackFunction);
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!
		
		// 정보 등록
		MultipartHttpServletRequest multiPart = (MultipartHttpServletRequest)request;
		Iterator itr = multiPart.getFileNames();

		BizResult bizResult = new BizResult();
		while (itr.hasNext()) {
			MultipartFile formFile = multiPart.getFile((String)itr.next());
			if (formFile.getSize() > 0) {
				AttachVo file = new AttachVo();
				
				// 화면에서 filekey 넘어오면 그대로 사용하도록 수정 by jhong (2013.04.22) - TODO: filekey 중복 체크...
				if (StringUtil.isNotEmpty(request.getParameter("filekey")))
					file.setFilekey(request.getParameter("filekey"));
				else
					file.setFilekey(UUID.randomUUID().toString());
				
				file.setFilename(formFile.getOriginalFilename());
				file.setMimetype(formFile.getContentType());
				file.setFilesize(new BigDecimal(formFile.getSize()));
				file.setFiledesc(request.getParameter("filedesc"));

				// 파일저장
				this.facade.uploadFile(formFile, file);

				// 파일첨부정보 등록
				bizResult = facade.regist(file, condition);

				condition.setString("filekey", file.getFilekey());
			}
		}


		model.addAttribute("bizMessage", bizResult.getMessage());
		model.addAttribute("bizMessageCode", bizResult.getMessageCode());

		model.addAttribute("condition", condition); // 검색조건 유지
		model.addAttribute("attachVo", bizResult.getBizData());
		
		return  "blank/editor/smarteditor_image_callback.tiles";
    }

	/**
	 * <pre>
	 * 첨부파일 등록 (CKEditor 등록시 사용)
 	 * </pre>
	 *
	 * @param request
	 * @param attachVo
	 * @param bindingResult
	 * @return 
	 * @throws Exception
	 */
    @RequestMapping(params="command=registCk")
	public String registCk (
			HttpServletRequest request
			, @ModelAttribute("attachVo") AttachVo attachVo
			, BindingResult bindingResult 
			, ModelMap model
 			, @RequestParam(value="CKEditorFuncNum",required=false) String funcNum
			) throws Exception {    

		BizCondition condition = new BizCondition(request);
		condition.put("funcNum", funcNum);
		logger.info("registCk() funcNum={}", funcNum);
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!
		
		// 정보 등록
		MultipartHttpServletRequest multiPart = (MultipartHttpServletRequest)request;
		Iterator itr = multiPart.getFileNames();

		BizResult bizResult = new BizResult();
		while (itr.hasNext()) {
			MultipartFile formFile = multiPart.getFile((String)itr.next());
			if (formFile.getSize() > 0) {
				AttachVo file = new AttachVo();
				
				// 화면에서 filekey 넘어오면 그대로 사용하도록 수정 by jhong (2013.04.22) - TODO: filekey 중복 체크...
				if (StringUtil.isNotEmpty(request.getParameter("filekey")))
					file.setFilekey(request.getParameter("filekey"));
				else
					file.setFilekey(UUID.randomUUID().toString());
				
				file.setFilename(formFile.getOriginalFilename());
				file.setMimetype(formFile.getContentType());
				file.setFilesize(new BigDecimal(formFile.getSize()));
				file.setFiledesc(request.getParameter("filedesc"));

				// 파일저장
				this.facade.uploadFile(formFile, file);

				// 파일첨부정보 등록
				bizResult = facade.regist(file, condition);

				condition.setString("filekey", file.getFilekey());
			}
		}


		model.addAttribute("bizMessage", bizResult.getMessage());
		model.addAttribute("bizMessageCode", bizResult.getMessageCode());

		model.addAttribute("condition", condition); // 검색조건 유지
		model.addAttribute("attachVo", bizResult.getBizData());
		
		return  "blank/editor/ckeditor_image_callback.tiles";
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
    @RequestMapping(params="command=modify")
	public String modify (
			HttpServletRequest request
			, @ModelAttribute("attachVo") AttachVo attachVo
			, BindingResult bindingResult
			, ModelMap model
			) throws Exception {
		
		BizCondition condition = new BizCondition(request);
		
		attachVo = facade.prepareData(attachVo); // validation 전에 수행!!

		// 정보 수정
    	BizResult bizResult = facade.modify(attachVo, condition);
    	model.addAttribute("bizMessage", bizResult.getMessage());
    	model.addAttribute("bizMessageCode", bizResult.getMessageCode());
    	model.addAttribute("condition", condition); // 검색조건 유지

		return "edit/attach/attach_edit.tiles";
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
    @RequestMapping(params="command=delete")
	public void delete (
			HttpServletRequest request
			, HttpServletResponse response
			) throws Exception {

		BizCondition condition = new BizCondition(request);
		String selectedData = request.getParameter("selectedData");
		List selList = JSONUtil.jsonArray2List(selectedData);

		// 정보 삭제
		BizResult bizResult = facade.deleteList(selList, condition);

		String message = bizMessageSource.getBizMessage(bizResult.getMessageCode(), bizResult.getMessageArgs(), BizUtil.getLocale(request));

		// 한글깨짐 방지 위해 String return 하지 않고 PrintWriter 이용함
		response.setContentType("text/html;charset="+BizConst.ENCODING_DEFAULT);
		PrintWriter out = response.getWriter();
		out.print(message);
		out.close();
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
    @RequestMapping(params="command=findDetail")
 	public String findDetail (
 			HttpServletRequest request
 			, ModelMap model
 			) throws Exception {
 		
 		BizCondition condition = new BizCondition(request);
 		
		BizResult bizResult = facade.findDetail(condition);
		model.put("attachVo", bizResult.getBizData());
		model.addAttribute("condition", condition); // 검색조건 유지
		model.put(BindingResult.MODEL_KEY_PREFIX + "attachVo", model.get("errors")); // from redirectAttributes
		
		return "edit/attach/attach_edit.tiles";
	}

	/**
	 * <pre>
	 * 다운로드
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(params="command=download")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filekey = request.getParameter("filekey");
		BizCondition condition = new BizCondition();
		condition.setCondition(request);
		AttachVo attachFile = this.facade.findFile(condition.getLoginSession(), filekey);
		
		if (attachFile == null) { // null 일 경우 by jhong (2012.11.23)
			logger.warn("file is not exist!! filekey={}", filekey);
			
			// 한글깨짐 방지 위해 String return 하지 않고 PrintWriter 이용함
			response.setContentType("text/html;charset="+BizConst.ENCODING_DEFAULT);
			PrintWriter out = response.getWriter();
			out.print("해당 파일이 존재하지 않습니다.");
			out.close();
			
			return null;
		}

		String agentType = request.getHeader("Accept-Encoding");
        boolean flag = false;
        if (agentType != null && agentType.indexOf("gzip") >= 0)
            flag = true;

        flag = false;

        javax.servlet.ServletOutputStream servletoutputstream = null;
        try {
	        if (flag) {
	            response.setHeader("Content-Encoding", "gzip");
	            response.setHeader("Content-disposition",
	                    "attachment;filename=" + StringUtil.korean2ascii(attachFile.getFilename()));
	            servletoutputstream = response.getOutputStream();
	            java.util.zip.GZIPOutputStream gzipoutputstream = new java.util.zip.GZIPOutputStream(
	                    servletoutputstream);
	            dumpFile(attachFile.getFile(), gzipoutputstream);
	            gzipoutputstream.close();
	            if (servletoutputstream != null) servletoutputstream.flush();
	            
	        } else {
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-disposition",
	                    "attachment;filename=" + StringUtil.korean2ascii(attachFile.getFilename()));
	            servletoutputstream = response.getOutputStream();
	            dumpFile(attachFile.getFile(), servletoutputstream);
	            if (servletoutputstream != null) servletoutputstream.flush();
	        }
        } catch (Exception e) {
        	String exceptionStr = e.toString();
        	if (StringUtil.isNotEmpty(exceptionStr) 
        			&& ((exceptionStr.indexOf("Broken pipe") >= 0) || (exceptionStr.indexOf("Connection reset by peer") >= 0))) {
        		/*
        		 * 사용자가 다운로드 [취소] 선택시에 발생함.. log 만 남기고 error 메일 발송 등은 하지 않기 (2013.12.30)
        		 * ClientAbortException:  java.net.SocketException: Broken pipe
        		 */
        		logger.error("fail in download... attachFile="+attachFile, e);
        		
        	} else {
            	logger.warn("fail in download... exceptionStr={}", exceptionStr);
        		throw e;
        	}
        	
        } finally {
        	if (servletoutputstream != null) {
        		try { servletoutputstream.close(); } catch (Exception e) { logger.error("fail in close servletoutputstream()", e); }
        	}
        }

		return null;
	}

    /**
     * <pre>
     * real 파일에서 response의 writer에 쓴다.
     * </pre>
     *
     * @param realFile
     * @param outputstream
     */
    private void dumpFile(File realFile, OutputStream outputstream) {
        byte readByte[] = new byte[4096];
        try {
        	FileInputStream fstream = new FileInputStream(realFile);
            BufferedInputStream bufferedinputstream = new BufferedInputStream(fstream);
            int i;
            while ((i = bufferedinputstream.read(readByte, 0, 4096)) != -1)
                outputstream.write(readByte, 0, i);
            bufferedinputstream.close();
            fstream.close();
            
        } catch (Exception e) {
        	logger.error("fail in dumpFile() realFile="+realFile, e);
        }
    }

    /**
     * 이미지 출력
     * @param request
     * @param response
     * @param filekey
     * @throws Exception
     */
    @RequestMapping(params="command=downloadImage")
    public void downloadImage(
    		HttpServletRequest request
    		, HttpServletResponse response
    		, @RequestParam(value="filekey") String filekey
    		) throws Exception {
    	
	    File file = new File(Properties.get("path.attach.upload"), filekey);
    	logger.info("downloadImage() file={}", file);
    	if (!file.isFile()) {
    		logger.warn("File is not exist!! file={}", file);
    		//return;
    		String noimgFileName = "noimg_s.gif";
    		file = new File(Properties.get("path.attach.upload"), noimgFileName);
    	}

		BufferedInputStream in = null;
		ServletOutputStream out = null;
		try {
		    in = new BufferedInputStream(new FileInputStream(file));
		    byte[] fileData = IOUtils.toByteArray(in);
			String contentType = "application/octet-stream";

			out = response.getOutputStream();
			response.reset();
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;\"");

			out.write(fileData);
			
			out.flush();
			out.close();
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		} finally {
			try { if (out != null) out.close(); } catch (IOException e) { logger.error("fail in download...", e); }
		}
    }

}
