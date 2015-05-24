package eap.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eap.comps.webevent.WebEvents;
import eap.comps.webevent.WebEventsHelper;
import eap.util.JsonUtil;

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
public class FreeMarkerView extends org.springframework.web.servlet.view.freemarker.FreeMarkerView {
	
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Object webEvents = request.getAttribute(WebEventsHelper.REQUEST_WEB_EVENTS_KEY);
		if (webEvents instanceof WebEvents) { // 多次转发请求， 只处理一次
			request.setAttribute(WebEventsHelper.REQUEST_WEB_EVENTS_KEY, JsonUtil.toJson(((WebEvents) webEvents).getEvents()));
		}
		
		super.render(model, request, response);
	}
}
