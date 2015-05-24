package eap.comps.vcode;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import eap.EapContext;
import eap.Env;
import eap.comps.datastore.IDataScope;
import eap.util.PropertiesUtil;
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
public class VcodeManager {
	
	public static final String VCODE_KEY_PREFIX = "__vcode_";
	
	private static DefaultKaptcha vcodeProducer;
	
	public static String createText() {
		return getVcodeProducer().createText();
	}
	
	public static BufferedImage createImage() {
		return getVcodeProducer().createImage(createText());
	}
	
	public static BufferedImage createImage(String text) {
		return getVcodeProducer().createImage(text);
	}
	
	public static BufferedImage createImage(String text, String vcodeSence, IDataScope dataScope) {
		BufferedImage img = getVcodeProducer().createImage(text);
		dataScope.set(dataScope + vcodeSence, text);
		return img;
	}
	
	public static boolean isValid(String text, String vcodeSence, IDataScope dataScope) {
		if (StringUtil.isBlank(text)) {
			return false;
		}
		
		return text.equalsIgnoreCase((String)dataScope.get(VCODE_KEY_PREFIX + vcodeSence));
	}
	public static boolean pass(String text, String vcodeSence, IDataScope dataScope) {
		boolean result = isValid(text, vcodeSence, dataScope);
		if (result) {
			dataScope.delete(VCODE_KEY_PREFIX + vcodeSence);
		}
		
		return result;
	}
	
	private static Producer getVcodeProducer() {
		if (vcodeProducer == null) {
			Env env = EapContext.getEnv();
			
			vcodeProducer = new DefaultKaptcha();
			vcodeProducer.setConfig(new Config(PropertiesUtil.from(env.filterForPrefix("vcode.config."))));
		}
		
		return vcodeProducer;
	}
}