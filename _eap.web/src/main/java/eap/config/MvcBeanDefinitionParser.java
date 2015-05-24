package eap.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.w3c.dom.Element;

import eap.EapContext;
import eap.WebEnv;
import eap.util.ResourceUtil;
import eap.util.StringUtil;
import eap.util.propertyeditor.BigDecimalEditor;
import eap.util.propertyeditor.DateEditor;
import eap.web.ConfigurableWebBindingInitializer;
import eap.web.FreeMarkerPackageViewResolver;
import eap.web.HandlerExceptionResolver;
import eap.web.InternalResourcePackageViewResolver;
import eap.web.RequestToViewNameTranslator;
import eap.web.WebArgumentResolver;
import eap.web.interceptor.TokenSessionStoreInterceptor;
import eap.web.interceptor.WebEventsHandlerInterceptor;
import eap.web.json.JsonHttpMessageConverter;

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
public class MvcBeanDefinitionParser implements BeanDefinitionParser {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);
		WebEnv env = (WebEnv) EapContext.getEnv();
		
		/* localeResolver */
		if (Boolean.parseBoolean(env.getProperty("app.locale.cookie", "false"))) {
			RootBeanDefinition localeResolverDef = new RootBeanDefinition(CookieLocaleResolver.class);
			String localeResolverId = "localeResolver";
			localeResolverDef.setSource(source);
			localeResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			localeResolverDef.getPropertyValues().add("defaultLocale", env.getProperty("app.locale", "zh_CN"));
			localeResolverDef.getPropertyValues().add("cookieName", env.getProperty("app.locale.cookie.name", "locale"));
			localeResolverDef.getPropertyValues().add("cookieMaxAge", env.getProperty("app.locale.cookie.maxAgeSeconds", "7776000")); // 60 * 60 * 24 * 90 = 90day
			parserContext.getRegistry().registerBeanDefinition(localeResolverId, localeResolverDef);
			parserContext.registerComponent(new BeanComponentDefinition(localeResolverDef, localeResolverId));
		}
		
		/* viewNameTranslator */
		RootBeanDefinition viewNameTranslatorDef = new RootBeanDefinition(RequestToViewNameTranslator.class);
		String viewNameTranslatorId = "viewNameTranslator";
		viewNameTranslatorDef.setSource(source);
		viewNameTranslatorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		parserContext.getRegistry().registerBeanDefinition(viewNameTranslatorId, viewNameTranslatorDef);
		parserContext.registerComponent(new BeanComponentDefinition(viewNameTranslatorDef, viewNameTranslatorId));
		
		/* handlerExceptionResolver */
		RootBeanDefinition handlerExceptionResolverDef = new RootBeanDefinition(HandlerExceptionResolver.class);
		String handlerExceptionResolverId = parserContext.getReaderContext().generateBeanName(handlerExceptionResolverDef);
		handlerExceptionResolverDef.setSource(source);
		handlerExceptionResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		parserContext.getRegistry().registerBeanDefinition(handlerExceptionResolverId, handlerExceptionResolverDef);
		parserContext.registerComponent(new BeanComponentDefinition(handlerExceptionResolverDef, handlerExceptionResolverId));
		
		/* viewResolver */
		boolean packageView = StringUtil.isTrue(env.getProperty("app.web.viewResolver.packageView", "true"));
		if ("freemarker".equalsIgnoreCase(env.getProperty("app.web.viewResolver"))) {
			RootBeanDefinition viewResolverDef = new RootBeanDefinition(packageView ? FreeMarkerPackageViewResolver.class : FreeMarkerViewResolver.class);
			String viewResolverId = "viewResolver";
			viewResolverDef.setSource(source);
			viewResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			viewResolverDef.getPropertyValues().add("viewClass", "eap.web.FreeMarkerView"); // org.springframework.web.servlet.view.freemarker.FreeMarkerView
			viewResolverDef.getPropertyValues().add("prefix", packageView ? "" : env.getProperty("app.web.viewResolver.prefix", ""));
			viewResolverDef.getPropertyValues().add("suffix", packageView ? "" : env.getProperty("app.web.viewResolver.suffix", ""));
			viewResolverDef.getPropertyValues().add("cache", env.getProperty("app.web.viewResolver.cache", env.isProMode() ? "true" : "false"));
			viewResolverDef.getPropertyValues().add("contentType", env.getProperty("app.web.viewResolver.contentType", "text/html;charset=" + env.getEncoding()));
			viewResolverDef.getPropertyValues().add("exposeSpringMacroHelpers", env.getProperty("app.web.viewResolver.exposeSpringMacroHelpers", "true"));
			viewResolverDef.getPropertyValues().add("exposeRequestAttributes", env.getProperty("app.web.viewResolver.exposeRequestAttributes", "false"));
			viewResolverDef.getPropertyValues().add("exposeSessionAttributes", env.getProperty("app.web.viewResolver.exposeSessionAttributes", "false"));
			viewResolverDef.getPropertyValues().add("requestContextAttribute", env.getProperty("app.web.viewResolver.requestContextAttribute", "RC"));
			parserContext.getRegistry().registerBeanDefinition(viewResolverId, viewResolverDef);
			parserContext.registerComponent(new BeanComponentDefinition(viewResolverDef, viewResolverId));
			
			RootBeanDefinition freeMarkerConfigurerDef = new RootBeanDefinition(FreeMarkerConfigurer.class);
			String freeMarkerConfigurerId = "freeMarkerConfigurer";
			freeMarkerConfigurerDef.setSource(source);
			freeMarkerConfigurerDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			freeMarkerConfigurerDef.getPropertyValues().add("templateLoaderPath", packageView ? "/" : env.getProperty("app.web.freeMarkerConfigurer.templateLoaderPath", "/"));
//			freeMarkerConfigurerDef.getPropertyValues().add("classicCompatible", true);
			freeMarkerConfigurerDef.getPropertyValues().add("configLocation", ResourceUtil.getResource(env.getProperty("app.web.freeMarkerConfigurer.configLocation", "classpath*:/freemarker_default.properties")));
//			Properties freemarkerSettings = new Properties();
//			freemarkerSettings.put("template_update_delay", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.template_update_delay", env.isProMode() ? "3600" : "1"));
//			freemarkerSettings.put("whitespace_stripping", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.whitespace_stripping", "true"));
//			freemarkerSettings.put("default_encoding", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.default_encoding", env.getEncoding()));
//			freemarkerSettings.put("locale", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.locale", "zh_CN"));
//			freemarkerSettings.put("number_format", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.number_format", "#.#"));
//			freemarkerSettings.put("time_format", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.time_format", "HH:mm:ss"));
//			freemarkerSettings.put("date_format", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.date_format", "yyyy-MM-dd"));
//			freemarkerSettings.put("datetime_format", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.datetime_format", "yyyy-MM-dd HH:mm:ss"));
//			freemarkerSettings.put("template_exception_handler", "ignore"); 
//			freemarkerSettings.put("object_wrapper", "beans");
//			freemarkerSettings.put("output_encoding", env.getProperty("app.web.freeMarkerConfigurer.freemarkerSettings.output_encoding", env.getEncoding()));
//			freeMarkerConfigurerDef.getPropertyValues().add("freemarkerSettings", freemarkerSettings);
			parserContext.getRegistry().registerBeanDefinition(freeMarkerConfigurerId, freeMarkerConfigurerDef);
			parserContext.registerComponent(new BeanComponentDefinition(freeMarkerConfigurerDef, freeMarkerConfigurerId));
		} else {
			RootBeanDefinition viewResolverDef = new RootBeanDefinition(packageView ? InternalResourcePackageViewResolver.class : InternalResourceViewResolver.class);
			String viewResolverId = "viewResolver";
			viewResolverDef.setSource(source);
			viewResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			viewResolverDef.getPropertyValues().add("viewClass", "org.springframework.web.servlet.view.JstlView");
			viewResolverDef.getPropertyValues().add("prefix", packageView ? "" : env.getProperty("app.web.viewResolver.prefix", ""));
			viewResolverDef.getPropertyValues().add("suffix", packageView ? "" : env.getProperty("app.web.viewResolver.suffix", ""));
			parserContext.getRegistry().registerBeanDefinition(viewResolverId, viewResolverDef);
			parserContext.registerComponent(new BeanComponentDefinition(viewResolverDef, viewResolverId));
		}
		
		/* multipartResolver */
		RootBeanDefinition multipartResolverDef = new RootBeanDefinition(CommonsMultipartResolver.class);
		String multipartResolverId = "multipartResolver";
		multipartResolverDef.setSource(source);
		multipartResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		multipartResolverDef.getPropertyValues().add("defaultEncoding", env.getEncoding());
		multipartResolverDef.getPropertyValues().add("maxUploadSize", env.getProperty("app.web.form.upload.maxUploadSize", "1048576")); // 1024 * 1024 * 1 = 1M
		multipartResolverDef.getPropertyValues().add("maxInMemorySize", env.getProperty("app.web.form.upload.maxInMemorySize", "4096")); // 1024 * 4 = 4KB
		parserContext.getRegistry().registerBeanDefinition(multipartResolverId, multipartResolverDef);
		parserContext.registerComponent(new BeanComponentDefinition(multipartResolverDef, multipartResolverId));
		
		
		boolean useAnnotation = element.hasAttribute("useAnnotation") ? new Boolean(element.getAttribute("useAnnotation")) : true;
		
		/* handlerMapping */
		RootBeanDefinition handlerMappingDef = new RootBeanDefinition(useAnnotation ? DefaultAnnotationHandlerMapping.class : RequestMappingHandlerMapping.class);
		String annotationHandlerMappingId = parserContext.getReaderContext().generateBeanName(handlerMappingDef);
		handlerMappingDef.setSource(source);
		handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		
		List interceptors = new ArrayList();
		interceptors.add(new WebEventsHandlerInterceptor()); // TODO 顺序
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(env.getProperty("app.locale.name"));
		interceptors.add(localeChangeInterceptor);
		if (env.isFormTokenEnable()) {
			TokenSessionStoreInterceptor tokenSessionStoreInterceptor = new TokenSessionStoreInterceptor();
			tokenSessionStoreInterceptor.setExcludeUrlPatterns(env.getProperty("app.web.form.token.excludeUrlPatterns"));
			interceptors.add(tokenSessionStoreInterceptor);
		}
		if (packageView) {
			if ("freemarker".equalsIgnoreCase(env.getProperty("app.web.viewResolver"))) {
				interceptors.add(new FreeMarkerPackageViewResolver());
			} else {
				interceptors.add(new InternalResourcePackageViewResolver()); // interceptors.add(new RuntimeBeanReference(viewResolverId));
			}
		}
		handlerMappingDef.getPropertyValues().add("interceptors", interceptors);
		
		parserContext.getRegistry().registerBeanDefinition(annotationHandlerMappingId, handlerMappingDef);
		parserContext.registerComponent(new BeanComponentDefinition(handlerMappingDef, annotationHandlerMappingId));
		
		/* methodHandlerAdapter */
		RootBeanDefinition methodHandlerAdapterDef = new RootBeanDefinition(useAnnotation ? AnnotationMethodHandlerAdapter.class : RequestMappingHandlerAdapter.class);
		String methodHandlerAdapterId = parserContext.getReaderContext().generateBeanName(handlerExceptionResolverDef);
		methodHandlerAdapterDef.setSource(source);
		methodHandlerAdapterDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		
		RootBeanDefinition webBindingInitializerDef = new RootBeanDefinition(ConfigurableWebBindingInitializer.class);
		String webBindingInitializerId = parserContext.getReaderContext().generateBeanName(webBindingInitializerDef);
		webBindingInitializerDef.setSource(source);
		webBindingInitializerDef.setRole(BeanDefinition.ROLE_SUPPORT);
		webBindingInitializerDef.getPropertyValues().add("messageCodesResolver", new RuntimeBeanReference("validateMessageCodesResolver"));
		webBindingInitializerDef.getPropertyValues().add("validator", new RuntimeBeanReference("validator"));
		Map<String, Object> propertyEditors = new HashMap<String, Object>();
		propertyEditors.put("java.lang.String", new StringTrimmerEditor(true)); // new HtmlEscapePropertyEditor()
		propertyEditors.put("java.lang.String[]", new StringArrayPropertyEditor());
		propertyEditors.put("java.util.Date", new DateEditor());
		propertyEditors.put("java.math.BigDecimal", new BigDecimalEditor());
		webBindingInitializerDef.getPropertyValues().add("propertyEditors", propertyEditors);
		parserContext.getRegistry().registerBeanDefinition(webBindingInitializerId, webBindingInitializerDef);
		parserContext.registerComponent(new BeanComponentDefinition(webBindingInitializerDef, webBindingInitializerId));
		methodHandlerAdapterDef.getPropertyValues().add("webBindingInitializer", new RuntimeBeanReference(webBindingInitializerId));
		if (useAnnotation) {
			methodHandlerAdapterDef.getPropertyValues().add("customArgumentResolver", new WebArgumentResolver());
		}
		
//		List<Object> messageConverters = new ArrayList<Object>();
		RootBeanDefinition jsonHttpMessageConverterDef = new RootBeanDefinition(JsonHttpMessageConverter.class);
		String jsonHttpMessageConverterId = parserContext.getReaderContext().generateBeanName(jsonHttpMessageConverterDef);
		jsonHttpMessageConverterDef.setSource(source);
		jsonHttpMessageConverterDef.setRole(BeanDefinition.ROLE_SUPPORT);
		jsonHttpMessageConverterDef.getPropertyValues().add("objectMapper", new RuntimeBeanReference("jacksonObjectMapper"));
		parserContext.getRegistry().registerBeanDefinition(jsonHttpMessageConverterId, jsonHttpMessageConverterDef);
		parserContext.registerComponent(new BeanComponentDefinition(jsonHttpMessageConverterDef, jsonHttpMessageConverterId));
//		messageConverters.add(new RuntimeBeanReference(jsonHttpMessageConverterId));
		methodHandlerAdapterDef.getPropertyValues().add("messageConverters", new RuntimeBeanReference(jsonHttpMessageConverterId));
		
		parserContext.getRegistry().registerBeanDefinition(methodHandlerAdapterId, methodHandlerAdapterDef);
		parserContext.registerComponent(new BeanComponentDefinition(methodHandlerAdapterDef, methodHandlerAdapterId));
		
		return null;
	}

}
