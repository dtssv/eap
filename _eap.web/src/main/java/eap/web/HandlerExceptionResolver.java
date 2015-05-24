package eap.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import eap.EapContext;
import eap.Env;
import eap.base.BaseController;
import eap.comps.token.TokenExpiredException;
import eap.comps.webevent.WebEventsHelper;
import eap.exception.BizException;
import eap.util.ExceptionUtil;
import eap.util.HttpUtil;
import eap.util.JsonUtil;
import eap.util.StringUtil;
import eap.web.json.JsonResponse;

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
public class HandlerExceptionResolver implements org.springframework.web.servlet.HandlerExceptionResolver { // not bizException;   all RuntimEexception
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerExceptionResolver.class);
	
//	private String errorViewPath = "/WEB-INF/views/common/error/%s.jsp";
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) 
	{
		if (e.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException")) { // Tomcat Server // ClientAbortException:  java.net.SocketException: Software caused connection abort: socket write error
			logger.debug(e.getMessage(), e);
			return null;
		}
		
		Env env = EapContext.getEnv();
		
		Throwable cause = ExceptionUtil.getRootCause(e);
//		if (!(cause instanceof BizException)) {
//			logger.error(cause.getMessage(), cause);
//		}
		
		// 表单token过期异常处理
		if (e instanceof TokenExpiredException) {
			try {
				if (HttpUtil.isAjaxRequest(request)) {
					JsonResponse jsonResponse = new JsonResponse();
					jsonResponse.setStatus("409");
					response.getWriter().write(JsonUtil.toJson(jsonResponse));
					return new ModelAndView();
				} else {
					String referer = request.getHeader("referer");
					if (StringUtil.isNotBlank(referer) && !referer.equals(request.getRequestURL().toString())) {
						response.sendRedirect(referer);
						return new ModelAndView();
					}
				}
			} catch (Exception e1) {
				logger.debug(e1.getMessage(), e1);
			}
			
			return new ModelAndView("forward:" + env.getProperty("app.errorPage.409", "/409.htm"));
		}
		
		// 参数校验失败异常、业务异常处理
		ModelAndView mnv = null;
		if (handler instanceof BaseController) {
			BaseController bc = (BaseController) handler;
			
			boolean internalServerError = false;
			if (e instanceof BindException) {
				bc.logger.debug(e.getMessage(), cause);
				mnv = bc.validateError(request, response, (BindException) e);
			} else if (e instanceof BizException) {
				String viewName = bc.setException(e); // in setException ->  log error
				mnv = new ModelAndView(viewName, ((BizException) e).getModel());
			} 
//			else if (e instanceof HttpRequestMethodNotSupportedException) {
//				String referer = request.getHeader("referer");
//				if (StringUtil.isNotBlank(referer) && !referer.equals(request.getRequestURL().toString())) {
//					try {
//						response.sendRedirect(referer);
//						return new ModelAndView();
//					} catch (IOException sre) {
//						logger.error(e.getMessage(), e);
//					}
//				}
//			}
			else {
				bc.logger.error(e.getMessage(), cause);
				internalServerError = true;
			}
			
			if (HttpUtil.isAjaxRequest(request)) {
				if (internalServerError) {
					response.setStatus(500);
				} else {
					JsonResponse jsonResponse = new JsonResponse();
					jsonResponse.setEvents(WebEventsHelper.getWebEvents(request).getEvents());
					try {
						response.getWriter().write(JsonUtil.toJson(jsonResponse));
					} catch (IOException e1) {
						logger.debug(e1.getMessage(), e1);
					}
				}
				return new ModelAndView();
			} else {
				return new ModelAndView("forward:" + env.getProperty("app.errorPage.500", "/500.htm"));
			}
		} 
		else { // 未知异常
			logger.error(e.getMessage(), cause);
			if (HttpUtil.isAjaxRequest(request)) {
				response.setStatus(500);
				return new ModelAndView();
			} else {
				return new ModelAndView("forward:" + env.getProperty("app.errorPage.500", "/500.htm"));
			}
		}
	}
	
//	public void setErrorViewPath(String errorViewPath) {
//		this.errorViewPath = errorViewPath;
//	}
}