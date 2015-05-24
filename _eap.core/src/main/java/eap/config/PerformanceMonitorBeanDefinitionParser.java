package eap.config;

import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

import eap.EapContext;
import eap.Env;
import eap.util.DomUtil;
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
public class PerformanceMonitorBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);
		Env env = EapContext.getEnv();
		
		boolean proxyTargetClass = element.hasAttribute("proxyTargetClass") ? Boolean.parseBoolean(element.getAttribute("proxyTargetClass")) : false;
		String patterns = element.hasAttribute("patterns") ? element.getAttribute("patterns") : env.getProperty("app.basePackage") + ".*";
		
		RootBeanDefinition dsiDef = new RootBeanDefinition(DruidStatInterceptor.class);
		String dsiId = parserContext.getReaderContext().generateBeanName(dsiDef);
		dsiDef.setSource(source);
		dsiDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		parserContext.getRegistry().registerBeanDefinition(dsiId, dsiDef);
		parserContext.registerComponent(new BeanComponentDefinition(dsiDef, dsiId));
		
		RootBeanDefinition jrmpDef = new RootBeanDefinition(JdkRegexpMethodPointcut.class);
		String jrmpId = parserContext.getReaderContext().generateBeanName(jrmpDef);
		jrmpDef.setSource(source);
		jrmpDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		jrmpDef.setScope("prototype");
		jrmpDef.getPropertyValues().add("patterns", StringUtil.split(patterns, ","));
		parserContext.getRegistry().registerBeanDefinition(jrmpId, jrmpDef);
		parserContext.registerComponent(new BeanComponentDefinition(jrmpDef, jrmpId));
		
		Document doc = DomUtil.newDocument();
		Element configElement = doc.createElementNS("", "config");
		if (proxyTargetClass) {
			configElement.setAttribute("proxy-target-class", "true");
		}
		Element advisorElement = doc.createElementNS("", "advisor");
		advisorElement.setAttribute("advice-ref", dsiId);
		advisorElement.setAttribute("pointcut-ref", jrmpId);
		configElement.appendChild(advisorElement);
		new ConfigBeanDefinitionParser().parse(configElement, parserContext);
		
		return null;
	}
}