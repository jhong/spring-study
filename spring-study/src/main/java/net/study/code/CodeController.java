package net.study.code;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public String viewList (ModelMap model
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

}
