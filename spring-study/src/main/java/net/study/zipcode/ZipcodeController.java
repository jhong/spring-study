package net.study.zipcode;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.study.util.StringUtil;

@Controller
@RequestMapping(value={"/zipcode"})
public class ZipcodeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * <pre>
	 * 우편번호 팝업 페이지 조회
	 * </pre>
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(params="command=searchPopup")
	public String searchPopup(
			ModelMap model
			, @RequestParam(value="rtnCallBack",required=false) String rtnCallBack
			, @RequestParam(value="inputYn",required=false) String inputYn
			, @RequestParam(value="zipNo",required=false) String zipNo
			, @RequestParam(value="roadFullAddr",required=false) String roadFullAddr
			, @RequestParam(value="roadAddrPart1",required=false) String roadAddrPart1
			, @RequestParam(value="roadAddrPart2",required=false) String roadAddrPart2
			, @RequestParam(value="engAddr",required=false) String engAddr
			, @RequestParam(value="jibunAddr",required=false) String jibunAddr
			, @RequestParam(value="addrDetail",required=false) String addrDetail
			, @RequestParam(value="admCd",required=false) String admCd
			, @RequestParam(value="rnMgtSn",required=false) String rnMgtSn
			, @RequestParam(value="bdMgtSn",required=false) String bdMgtSn
			, @RequestParam(value="returnUrl",required=false) String returnUrl
			) throws Exception {
    	
		// Call Back 함수가 정의 안되었을 경우 jusoCallBack 함수를 Default로 한다.
		if(StringUtil.isEmpty(rtnCallBack)) {
			rtnCallBack = "zipcodeCallBack";
		}
		
    	Map condition = new HashMap();
		condition.put("rtnCallBack", rtnCallBack);
    	condition.put("inputYn", inputYn);
    	condition.put("zipNo", zipNo);
    	condition.put("roadFullAddr", roadFullAddr);
    	condition.put("roadAddrPart1", roadAddrPart1);
    	condition.put("roadAddrPart2", roadAddrPart2);
    	condition.put("engAddr", engAddr);
    	condition.put("jibunAddr", jibunAddr);
    	condition.put("addrDetail", addrDetail);
    	condition.put("admCd", admCd);
    	condition.put("rnMgtSn", rnMgtSn);
    	condition.put("bdMgtSn", bdMgtSn);
    	condition.put("returnUrl", returnUrl);
		logger.info("searchPopup() condition={}", condition);
		
		model.addAttribute("condition", condition);
		
		return "zipcode/zipcode_popup";
	}

}
