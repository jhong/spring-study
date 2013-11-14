package net.study.code;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/code"})
public class CodeController {

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
	public String viewList (ModelMap model) throws Exception {
    	Map result = facade.findList(null);
    	
    	model.addAttribute("codeName", "abcde");
    	model.addAttribute("totalRow", result.get("totalRow"));
    	
		return "code/code_list";
	}

}
