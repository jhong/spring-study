package net.study.code;

import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Repository("codeValidator")
public class CodeValidator implements Validator {

	/**
	 * supports
	 */
	public boolean supports(Class clazz) {
		return clazz.equals(CodeVo.class);
	}
    
	/**
	 * validate
	 */
	public void validate(Object obj, Errors errors) {
		CodeVo vo = (CodeVo) obj;

		// 필수입력 체크
		ValidationUtils.rejectIfEmpty(errors, "codecategorykey", "errors.required", "codecategorykey is required."); // CODECATEGORYKEY
		ValidationUtils.rejectIfEmpty(errors, "code", "errors.required", "code is required."); // CODE
//		ValidationUtils.rejectIfEmpty(errors, "codeexplain", "errors.required", "codeexplain is required."); // CODEEXPLAIN
//		ValidationUtils.rejectIfEmpty(errors, "codename", "errors.required", "codename is required."); // CODENAME
//		ValidationUtils.rejectIfEmpty(errors, "codeengname", "errors.required", "codeengname is required."); // CODEENGNAME
		ValidationUtils.rejectIfEmpty(errors, "status", "errors.required", "status is required."); // STATUS
//		ValidationUtils.rejectIfEmpty(errors, "sortorder", "errors.required", "sortorder is required."); // SORTORDER

	}
}
