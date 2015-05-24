package eap.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.xml.transform.StringSource;

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
public class XmlUtil {
	
	private static Map<Class<?>, Unmarshaller> JAXB_UNMARSHALLER_CACHE = new ConcurrentHashMap<Class<?>, Unmarshaller>();
	private static Map<Class<?>, Marshaller> JAXB_MARSHALLER_CACHE = new ConcurrentHashMap<Class<?>, Marshaller>();
	
	public static String getXmlNodeText(String xml, String nodeName) {
		if (StringUtil.isBlank(xml)) {
			return null;
		}
		
		String nodeContent = StringUtil.indexOf(xml, "<"+ nodeName +">", "</"+ nodeName +">");
		if (StringUtil.isNotBlank(nodeContent)) {
			nodeContent = nodeContent.trim();
			if (nodeContent.toUpperCase().startsWith("<![CDATA[")) {
				nodeContent = nodeContent.substring(9);
			}
			if (nodeContent.toLowerCase().endsWith("]]>")) {
				nodeContent = nodeContent.substring(0, nodeContent.length() - 3);
			}
			return nodeContent.trim();
		}
		
		return null;
	}
	
	public static <T> T parseXml(String xml, Class<?> classesToBeBound) {
		return parseXml(xml, classesToBeBound, null);
	}
	public static <T> T parseXml(String xml, Class<?> classesToBeBound, Map<String, Object> unmarshallerProps) {
		if (StringUtil.isBlank(xml)) {
			return null;
		}
		
		try {
			Unmarshaller unmarshaller = getJaxbUnmarshaller(classesToBeBound, unmarshallerProps);
			return (T) unmarshaller.unmarshal(new StringSource(xml));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	public static String toXml(Object jaxbElement) {
		return toXml(jaxbElement, null);
	}
	public static String toXml(Object jaxbElement, Map<String, Object> marshallerProps) {
		try {
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = getJaxbMarshaller(jaxbElement.getClass(), marshallerProps);
			
//			OutputFormat xmlOutputFormat = new OutputFormat();
//			xmlOutputFormat.setCDataElements(new String[] {"^id"});
//			xmlOutputFormat.setPreserveSpace(true);
//			xmlOutputFormat.setIndenting(true);
//			XMLSerializer xmlSerializer = new XMLSerializer(xmlOutputFormat);
//			xmlSerializer.setOutputByteStream(System.out);
//			marshaller.marshal(jaxbElement, xmlSerializer.asContentHandler());
			
			marshaller.marshal(jaxbElement, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	private static Unmarshaller getJaxbUnmarshaller(Class<?> classesToBeBound, Map<String, Object> unmarshallerProps) throws Exception {
		Unmarshaller unmarshaller = JAXB_UNMARSHALLER_CACHE.get(classesToBeBound);
		if (unmarshaller == null) {
			JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
			unmarshaller = jaxbContext.createUnmarshaller();
			if (unmarshallerProps != null && unmarshallerProps.size() > 0) {
				for (Map.Entry<String, Object> prop : unmarshallerProps.entrySet()) {
					unmarshaller.setProperty(prop.getKey(), prop.getValue());
				}
			}
			JAXB_UNMARSHALLER_CACHE.put(classesToBeBound, unmarshaller);
		}
		return unmarshaller;
	}
	private static Marshaller getJaxbMarshaller(Class<?> classesToBeBound, Map<String, Object> marshallerProps) throws Exception {
		Marshaller marshaller = JAXB_MARSHALLER_CACHE.get(classesToBeBound);
		if (marshaller == null) {
			JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
			marshaller = jaxbContext.createMarshaller();
			if (marshallerProps != null && marshallerProps.size() > 0) {
				for (Map.Entry<String, Object> prop : marshallerProps.entrySet()) {
					marshaller.setProperty(prop.getKey(), prop.getValue());
				}
			}
			JAXB_MARSHALLER_CACHE.put(classesToBeBound, marshaller);
		}
		return marshaller;
	}
}