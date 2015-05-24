package eap.comps.shiro;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eap.util.StringUtil;

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
public class PassportService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PassportService.class);
	
	private String usernameParam = "username";
	private String passwordParam = "password";
	private String rememberMeParam = "rememberMe";
	private String vcodeParam = "vcode";
	private String redirectUrlParam = "redirectUrl";
	
	private boolean vcodeEnable = false;
	
	public void login(HttpServletRequest request) throws PassportException {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		boolean rememberMe = obtainRememberMe(request);
		String vcode = obtainVcode(request);
		String redirectUrl = obtainRedirectUrl(request);
		String remoteAddr = obtainRemoteAddr(request);
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		
		if (vcodeEnable) {
			String vcodeInSession = (String) session.getAttribute("login:vcode");
			if (StringUtil.isBlank(vcodeInSession)) {
				throw new PassportException("Passport.InvalidVcode", "InvalidVcode", null);
			}
			if (!vcodeInSession.equals(vcode)) {
				throw new PassportException("Passport.IllegalVcode", "IllegalVcode", null);
			}
		}
		
		try {
			// TODO (email/mobile) => real username 
			//                        FROM cache
			//                          email -> username
			//                          mobile -> username
			
			UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe, remoteAddr);
			currentUser.login(token);
		} catch (UnknownAccountException e) {
			throw new PassportException("Passport.UnknownAccountException", e.getMessage(), e);
		} catch (LockedAccountException e) {
			throw new PassportException("Passport.LockedAccountException", e.getMessage(), e);
		} catch (DisabledAccountException e) {
			throw new PassportException("Passport.DisabledAccountException", e.getMessage(), e);
		} catch (ExcessiveAttemptsException e) {
			throw new PassportException("Passport.ExcessiveAttemptsException", e.getMessage(), e);
		} catch (ConcurrentAccessException e) {
			throw new PassportException("Passport.ConcurrentAccessException", e.getMessage(), e);
		} catch (AccountException e) {
			throw new PassportException("Passport.AccountException", e.getMessage(), e);
		} catch (ExpiredCredentialsException e) {
			throw new PassportException("Passport.ExpiredCredentialsException", e.getMessage(), e);
		} catch (IncorrectCredentialsException e) {
			throw new PassportException("Passport.IncorrectCredentialsException", e.getMessage(), e);
		} catch (CredentialsException e) {
			throw new PassportException("Passport.CredentialsException", e.getMessage(), e);
		} catch (UnsupportedTokenException e) {
			throw new PassportException("Passport.UnsupportedTokenException", e.getMessage(), e);
		} catch (AuthenticationException e) {
			throw new PassportException("Passport.AuthenticationException", e.getMessage(), e);
		} catch (Exception e) {
			throw new PassportException("Passport.Exception", e.getMessage(), e);
		}
	}
	
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}
	
	public void redirectToSavedRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebUtils.redirectToSavedRequest(request, response, obtainRedirectUrl(request));
	}
	
	public String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParam);
	}
	public String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParam);
	}
	public boolean obtainRememberMe(HttpServletRequest request) {
		return StringUtil.isTrue(request.getParameter(rememberMeParam));
	}
	public String obtainVcode(HttpServletRequest request) {
		return request.getParameter(vcodeParam);
	}
	public String obtainRedirectUrl(HttpServletRequest request) {
		return request.getParameter(redirectUrlParam);
	}
	public String obtainRemoteAddr(HttpServletRequest request) {
//		return HttpUtil.getRemoteAddr(request);
		return getRemoteAddr(request);
	}
	
	public void setVcodeEnable(boolean vcodeEnable) {
		this.vcodeEnable = vcodeEnable;
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-ClientIP");
		}
		if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		} 
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		}
		if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
//		if (StringUtils.isNotBlank(ip)) {
//			String[] ipArr = ip.split(" |,");
//			if (ipArr != null && ipArr.length > 0) {
//				return ipArr[ipArr.length - 1];
//			}
//		}
		
		return ip;
	}
}