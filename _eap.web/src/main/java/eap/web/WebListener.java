package eap.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;

import eap.EapContext;
import eap.EapStartupLogger;
import eap.Env;
import eap.TopicManager;
import eap.WebEnv;
import eap.config.ConfigManager;
import eap.log.LogManager;
import eap.util.BeanUtil;
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
public class WebListener extends ContextLoaderListener implements ServletContextListener, HttpSessionListener, ServletRequestListener {
	
	static { // top init
		Properties envProps = new Properties();
		InputStream envInputStream = WebListener.class.getResourceAsStream("/env.properties");
		if (envInputStream != null) {
			try {
				envProps.load(envInputStream);
				
				if (StringUtil.isBlank(System.getProperty("app.name"))) {
					System.setProperty("app.name", envProps.getProperty("app.name"));
				}
				if (StringUtil.isBlank(System.getProperty("app.version"))) {
					System.setProperty("app.version", envProps.getProperty("app.version"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					envInputStream.close();
				} catch (Exception e) {}
			}
		}
	}
	
	private Logger logger = LoggerFactory.getLogger(WebListener.class);
	
	private Env env = null;
	
	private List<ServletContextListener> servletContextListeners = new ArrayList<ServletContextListener>();
	private List<HttpSessionListener> httpSessionListeners = new ArrayList<HttpSessionListener>();
	private List<ServletRequestListener> servletRequestListeners = new ArrayList<ServletRequestListener>();
	
	// START: ServletContextListener
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.gc();
		System.setProperty("java.awt.headless", "true");
		
		EapStartupLogger.printStartingMessage();
		
		WebEapContextHolder contextHolder = new WebEapContextHolder();
		contextHolder.setEnv(new WebEnv());
		contextHolder.setTopicManager(new TopicManager());
		
		EapContext.init(contextHolder);
		env = EapContext.getEnv();
		refreshEnv();
		
		EapContext.publish("$context.initializing", event);
		
		ServletContext servletContext = event.getServletContext();
		WebEnv.webContextPath = servletContext.getContextPath();
		if (!env.containsKey("app.web.rootPath")) {
			env.getEnvProperties().put("app.web.rootPath", servletContext.getRealPath(""));
		}
		
		this.setInitParameters(servletContext);
		this.setListeners(servletContext);
		this.setFilters(servletContext);
		this.setSerlvets(servletContext);
		super.contextInitialized(event);
		
		for (int i = 0; i < servletContextListeners.size(); i++) {
			((ServletContextListener) servletContextListeners.get(i)).contextInitialized(event);
		}
		
		EapContext.publish("$context.initialized", event);
		
		refreshLog();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		EapContext.publish("$context.destroying", event);
		for (int i = servletContextListeners.size() - 1; i >= 0 ; i--) {
			((ServletContextListener) servletContextListeners.get(i)).contextDestroyed(event);
		}
		super.contextDestroyed(event);
		EapContext.publish("$context.destroyed", event);
		ConfigManager.stop();
		WebEnv.webContextPath = null;
	}
	private void setInitParameters(ServletContext servletContext) {
		Map<String, Object> initParameters = env.filterForPrefix("app.web.initParameter.");
		for (Map.Entry<String, Object> entry : initParameters.entrySet()) {
			if (StringUtil.isBlank(servletContext.getInitParameter(entry.getKey())) && entry.getValue() != null && entry.getValue().toString().length() > 0) {
				servletContext.setInitParameter(entry.getKey(), entry.getValue().toString());
			}
		}
	}
	private void setListeners(ServletContext servletContext) {
		String[] listenerNames = StringUtil.split(env.getProperty("app.web.listener"), WebEnv.VALUES_SEPARATOR);
		if (listenerNames != null && listenerNames.length > 0) {
			for (String listenerName : listenerNames) {
				String listenerClass = env.getProperty("app.web.listener." + listenerName);
				Object listener = null;
				try {
					listener = Class.forName(listenerClass).newInstance();
				} catch (Exception e) {
					throw new IllegalArgumentException("listener class error: " + listenerClass);
				}
				if (listener instanceof ServletContextListener) {
					servletContextListeners.add((ServletContextListener) listener);
				} else if (listener instanceof HttpSessionListener) {
					httpSessionListeners.add((HttpSessionListener) listener);
				} else if (listener instanceof ServletRequestListener) {
					servletRequestListeners.add((ServletRequestListener) listener);
				}
			}
		}
	}
	private void setFilters(ServletContext servletContext) {
		String[] filterNames = StringUtil.split(env.getProperty("app.web.filter"), WebEnv.VALUES_SEPARATOR);
		if (filterNames != null && filterNames.length > 0) {
			for (String filterName : filterNames) {
//				if (StringUtil.isTrue((String) env.get("app.web.filter." + filterName + ".enable"))) {
					if (servletContext.getFilterRegistration(filterName) != null) {
						continue;
					}
					
					String filterClass = (String) env.get("app.web.filter." + filterName);
					Filter filter = null;
					if (filterClass.startsWith("proxy:")) {
						filter = new DelegatingFilterProxy(filterClass.substring("proxy:".length()));
					} else {
						try {
							filter = (Filter) Class.forName(filterClass).newInstance();
						} catch (Exception e) {
							throw new IllegalArgumentException("filter class error: " + filterClass);
						}
					}
					
					FilterRegistration.Dynamic filterDinamic = servletContext.addFilter(filterName, filter);
					EnumSet<DispatcherType> dispatcherEnumSet = null;
					Map<String, Object> filterConfigs = env.filterForPrefix("app.web.filter." + filterName + ".");
					for (Map.Entry<String, Object> filterConfig : filterConfigs.entrySet()) {
						String configKey = filterConfig.getKey();
						String configValue = (String) filterConfig.getValue();
						if (configValue == null || configValue.length() == 0) {
							continue;
						}
						
						if (configKey.startsWith("props.")) { // 0..N
							BeanUtil.setProperty(filter, configKey.substring("props.".length()), configValue);
						} 
						else if (configKey.startsWith("initParameter.")) { // 0..N
							filterDinamic.setInitParameter(configKey.substring("initParameter.".length()), configValue);
						}
						else if (configKey.equals("dispatchers")) { // 0..1
							String[] dispatchers = StringUtil.split(configValue, WebEnv.VALUES_SEPARATOR);
							if (dispatchers != null && dispatchers.length > 0) {
								dispatcherEnumSet = EnumSet.noneOf(DispatcherType.class);
								for (String dispatcher : dispatchers) {
									dispatcherEnumSet.add(DispatcherType.valueOf(dispatcher));
								}
							}
						}
					}
					String[] urlPatterns = StringUtil.split((String) filterConfigs.get("urlPatterns"), WebEnv.VALUES_SEPARATOR); // 0..1
					if (dispatcherEnumSet == null) {
						dispatcherEnumSet = EnumSet.allOf(DispatcherType.class);
					}
					filterDinamic.addMappingForUrlPatterns(dispatcherEnumSet, true, urlPatterns);
				}
//			}
		}
	}
	private void setSerlvets(ServletContext servletContext) {
		String[] servletNames = StringUtil.split(env.getProperty("app.web.servlet"), WebEnv.VALUES_SEPARATOR);
		if (servletNames != null && servletNames.length > 0) {
			for (String servletName : servletNames) {
//				if (StringUtil.isTrue((String) env.getProperty("app.web.servlet." + servletName + ".enable"))) {
					if (servletContext.getServletRegistration(servletName) != null) {
						continue;
					}
					
					String servletClass = (String) env.getProperty("app.web.servlet." + servletName);
					Servlet serlvet = null;
					try {
//						if ("org.springframework.web.servlet.DispatcherServlet".equals(value)) {
//							serlvet = (Servlet) Class.forName(value).getConstructor(WebApplicationContext.class).newInstance(getCurrentWebApplicationContext());
//						} else {
							serlvet = (Servlet) Class.forName(servletClass).newInstance();
//						}
					} catch (Exception e) {
						throw new IllegalArgumentException("serlvet class error: " + servletClass);
					}
					
					ServletRegistration.Dynamic servletDynamic = servletContext.addServlet(servletName, serlvet);
					Map<String, Object> serlvetConfigs = env.filterForPrefix("app.web.servlet." + servletName + ".");
					for (Map.Entry<String, Object> serlvetConfig : serlvetConfigs.entrySet()) {
						String configKey = serlvetConfig.getKey();
						String configValue = (String) serlvetConfig.getValue();
						if (configValue == null || configValue.length() == 0) {
							continue;
						}
						
						if (configKey.startsWith("props.")) { // 0..N
							BeanUtil.setProperty(serlvet, configKey.substring("props.".length()), configValue);
						}
						else if (configKey.startsWith("initParameter.")) { // 0..N
							servletDynamic.setInitParameter(configKey.substring("initParameter.".length()), configValue);
						}
						else if (configKey.equals("loadOnStartup")) { // 0..1
							servletDynamic.setLoadOnStartup(Integer.parseInt(configValue));
						}
					}
					String[] mapping = StringUtil.split((String) serlvetConfigs.get("mapping"), WebEnv.VALUES_SEPARATOR); // 0..1
					servletDynamic.addMapping(mapping);
//				}
			}
		}
	}
	// END: ServletContextListener
	
	
	// START: HttpSessionListener
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		EapContext.publish("$session.creating", event);
		String sessionTimeout = env.getProperty("app.web.session.timeout");
		if (StringUtil.isNotBlank(sessionTimeout)) {
			event.getSession().setMaxInactiveInterval(new Integer(sessionTimeout) * 60); // seconds
		}
		
		for (int i = 0; i < httpSessionListeners.size(); i++) {
			((HttpSessionListener) httpSessionListeners.get(i)).sessionCreated(event);
		}
		
		EapContext.publish("$session.created", event);
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		EapContext.publish("$session.destroying", event);
		for (int i = httpSessionListeners.size() - 1; i >= 0 ; i--) {
			((HttpSessionListener) httpSessionListeners.get(i)).sessionDestroyed(event);
		}
		EapContext.publish("$session.destroyed", event);
	}
	// END: HttpSessionListener
	
	
	// START: ServletRequestListener
	@Override
	public void requestInitialized(ServletRequestEvent event) {
		EapContext.publish("$request.initializing", event);
		for (int i = 0; i < servletRequestListeners.size(); i++) {
			((ServletRequestListener) servletRequestListeners.get(i)).requestInitialized(event);
		}
		EapContext.publish("$request.initialized", event);
	}
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		EapContext.publish("$request.destroying", event);
		for (int i = servletRequestListeners.size() - 1; i >= 0 ; i--) {
			((ServletRequestListener) servletRequestListeners.get(i)).requestDestroyed(event);
		}
		EapContext.publish("$request.destroyed", event);
	}
	// END: ServletRequestListener
	
	private void refreshEnv() {
		if (ConfigManager.isEnabled()) {
			try {
				if (!ConfigManager.isStarted()) {
					ConfigManager.start();
				}
				
				final CountDownLatch firstLoadedLatch = new CountDownLatch(1); // first loaded form UM
				ConfigManager.addListener(ConfigManager.envPath, new ConfigManager.NodeListener() {
					public void nodeChanged(CuratorFramework client, ChildData childData) throws Exception {
						byte[] data = childData != null ? childData.getData() : null;
						Map<String, String> envMap = new LinkedHashMap<String, String>();
						if (data != null && data.length > 0) {
							 Map<String,Object> _envMap = Env.ResourcePropertySource.loadPropertiesForResource(new ByteArrayResource(data));
							 for (Map.Entry<String, Object> _envItem : _envMap.entrySet()) {
								 Object value = _envItem.getValue();
								 envMap.put(_envItem.getKey(), value != null ? value.toString() : null);
							 }
						}
						env.refresh(envMap);
						firstLoadedLatch.countDown();
					}
				});
				firstLoadedLatch.await();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return;
			}
		} else {
			env.refresh(null);
		}
	}
	
	private void refreshLog() {
		if (ConfigManager.isEnabled()) {
			try {
				if (!ConfigManager.isStarted()) {
					ConfigManager.start();
				}
				
				if (ConfigManager.getClient().checkExists().forPath(ConfigManager.cmConfigNS + "/log") != null) {
					final CountDownLatch firstLoadedLatch = new CountDownLatch(1);
					ConfigManager.addListener(ConfigManager.cmConfigNS + "/log", new ConfigManager.NodeListener() {
						public void nodeChanged(CuratorFramework client, ChildData childData) throws Exception {
							byte[] data = childData != null ? childData.getData() : null;
							if (data != null && data.length > 0) {
								ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
								LogManager.reconfiguring(inputStream);
							} else {
								LogManager.reconfiguring(null);
							}
							firstLoadedLatch.countDown();
						}
					});
					firstLoadedLatch.await();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return;
			}
		}
	}
}