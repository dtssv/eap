package eap.web.jstl.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

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
public class TagUtil {
	
	public static int getScope(String scope, int defaultScope) throws JspException {
		int pcscope;
		if (scope == null) {
			switch (defaultScope) {
				case 1:
				case 2:
				case 3:
				case 4:
					pcscope = defaultScope;
					break;
				default:
					throw new JspTagException("Invalid default scope: " + defaultScope);
			}
		} else {
			if ("page".equals(scope)) {
				pcscope = 1;
			} else if ("request".equals(scope)) {
					pcscope = 2;
			} else if ("session".equals(scope)) {
					pcscope = 3;
			} else if ("application".equals(scope)) {
				pcscope = 4;
			} else {
				throw new JspTagException("Invalid scope name: " + scope);
			}
		}
		
		return pcscope;
	}
}
