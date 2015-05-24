package eap.web.jstl.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import eap.EapContext;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * @作者 chiknin@gmail.com
 * @创建时间 
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class UseEnvTag extends TagSupport {
	
	private String var;
	
	private String scope;
	
	public int doStartTag() throws JspException {
		int scope = TagUtil.getScope(this.scope, 1);
		this.pageContext.setAttribute(this.var, EapContext.getEnv(), scope);
		
		return SKIP_BODY;
	}
	
	public void release() {
		super.release();
		this.var = null;
		this.scope = null;
	}

	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
}