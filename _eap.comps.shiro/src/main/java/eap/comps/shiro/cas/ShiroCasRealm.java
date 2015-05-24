package eap.comps.shiro.cas;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import eap.util.StringUtil;

/**
 * <p> Title: </p>
 * <p> Description: loginFrameCallback </p>
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
public class ShiroCasRealm extends CasRealm {
	
	@Override
	public String getCasService() {
		
		Subject subject = SecurityUtils.getSubject();
		if (subject instanceof WebDelegatingSubject) {
			WebDelegatingSubject wdSubject = (WebDelegatingSubject) subject;
			HttpServletRequest request = (HttpServletRequest) wdSubject.getServletRequest();
			String loginStyle = request.getParameter("style");
			
			if (StringUtil.isNotBlank(loginStyle)) {
				return super.getCasService() + "?style=" + loginStyle;
			}
		}
		
		return super.getCasService();
	}
}
