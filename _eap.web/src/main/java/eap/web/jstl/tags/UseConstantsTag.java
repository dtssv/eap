package eap.web.jstl.tags;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import eap.util.ReflectUtil;

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
 * @see org.apache.taglibs.unstandard.UseConstantsTag
 */
public class UseConstantsTag extends TagSupport {

	private String className;
	private String scope;
	private String var;

	public int doStartTag() throws JspException {
		int scope = TagUtil.getScope(this.scope, 1);
		if ((this.className != null) && (this.var != null)) {
			Map constants = ReflectUtil.getClassConstants(this.className);
			if ((constants != null) && (!constants.isEmpty())) {
				this.pageContext.setAttribute(this.var, constants, scope);
			}
		}
		
		return SKIP_BODY;
	}
	
	public void release() {
		super.release();
		this.className = null;
		this.scope = null;
		this.var = null;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
}
