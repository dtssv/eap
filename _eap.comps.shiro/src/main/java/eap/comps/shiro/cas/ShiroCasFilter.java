package eap.comps.shiro.cas;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ShiroCasFilter extends CasFilter {
	
	private static final Logger LOG = LoggerFactory.getLogger(ShiroCasFilter.class);
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		String loginStyle = request.getParameter("style");
		if ("frame".equals(loginStyle)) {
			StringBuilder html = new StringBuilder();
			html.append("<script type=\"text/javascript\">");
			html.append("window.parent.parent.loginSuccessCallback();"); // window.top
			html.append("</script>");
			writeToReponse(response, html.toString());
			return false;
		}
		
		String callback = request.getParameter("callback");
		if (callback != null && callback.length() > 0 && callback.matches("[a-zA-Z0-9_]+")) {
			response.setContentType("text/javascript; charset=UTF-8");
			String html = String.format("%s(%s)", callback, "true");
			writeToReponse(response, html.toString());
			return false;
		}
		
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request, ServletResponse response) {
		if (token instanceof CasToken) {
			CasToken cToken = (CasToken) token;
			if (cToken.getCredentials() == null && ae instanceof UnknownAccountException) {
				return false;
			}
		}
		LOG.error(ae.getMessage(), ae);
		
		String loginStyle = request.getParameter("style");
		if ("frame".equals(loginStyle)) {
			StringBuilder html = new StringBuilder();
			html.append("<script type=\"text/javascript\">");
			html.append("window.parent.parent.loginFailureCallback();"); // window.top
			html.append("</script>");
			writeToReponse(response, html.toString());
			return false;
		}
		
		String callback = request.getParameter("callback");
		if (callback != null && callback.length() > 0 && callback.matches("[a-zA-Z0-9_]+")) {
			response.setContentType("text/javascript; charset=UTF-8");
			String html = String.format("%s(%s)", callback, "false");
			writeToReponse(response, html.toString());
			return false;
		}
		
		return super.onLoginFailure(token, ae, request, response);
	}
	
	private void writeToReponse(ServletResponse response, String html) {
		HttpServletResponse hsResponse = (HttpServletResponse) response;
		
		PrintWriter pw = null;
		try {
			hsResponse.setHeader("Cache-Control", "no-cache");
			hsResponse.setHeader("Cache-Control", "no-store");
			hsResponse.setHeader("Pragma", "no-cache");
			hsResponse.setDateHeader("Expires", 0);
			
			pw = hsResponse.getWriter();
			pw.write(html);
			pw.flush();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}