package eap.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import eap.EapContext;
import eap.Env;
import eap.util.ResourceUtil;
import eap.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerDecoratorServlet extends com.opensymphony.module.sitemesh.freemarker.FreemarkerDecoratorServlet {
	
//	@Override
//	public void init(ServletConfig config) throws ServletException {
//		super.init(config);
//		
//		getConfiguration()
//	}
	
	@Override
	protected Configuration createConfiguration() {
		try {
//			ObjectFactory.getObject("spring freeMarkerConfigurer")
			
			Env env = EapContext.getEnv();
			FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
			boolean packageView = StringUtil.isTrue(env.getProperty("app.web.viewResolver.packageView", "true"));
			freeMarkerConfigurer.setTemplateLoaderPath(packageView ? "/" : env.getProperty("app.web.freeMarkerConfigurer.templateLoaderPath", "/"));
			freeMarkerConfigurer.setConfigLocation(ResourceUtil.getResource(env.getProperty("app.web.freeMarkerConfigurer.configLocation", "classpath*:/freemarker_default.properties")));
			freeMarkerConfigurer.afterPropertiesSet();
			return freeMarkerConfigurer.getConfiguration();
		} catch (Exception e) {
			log(e.getMessage(), e);
			e.printStackTrace();
			return super.createConfiguration();
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.getConfiguration().setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}
}