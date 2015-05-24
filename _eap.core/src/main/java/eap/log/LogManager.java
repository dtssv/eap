package eap.log;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.status.StatusUtil;

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
public class LogManager {

	public static void reconfiguring(InputStream inputStream) {
		LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
		JoranConfigurator jc = new JoranConfigurator();
		jc.setContext(lc);
		StatusUtil statusUtil = new StatusUtil(lc);
		List<SaxEvent> eventList = jc.recallSafeConfiguration();
		URL mainURL = ConfigurationWatchListUtil.getMainWatchURL(lc);
		lc.reset();
		long threshold = System.currentTimeMillis();
		try {
			if (inputStream != null) {
				jc.doConfigure(inputStream);
			} else if (mainURL != null) {
				jc.doConfigure(mainURL);
			}
			if (statusUtil.hasXMLParsingErrors(threshold)) {
				fallbackConfiguration(lc, eventList, mainURL);
			}
		} catch (JoranException e) {
			e.printStackTrace();
			fallbackConfiguration(lc, eventList, mainURL);
		}
		ConfigurationWatchListUtil.setMainWatchURL(lc, mainURL);
	}

	private static void fallbackConfiguration(LoggerContext lc, List<SaxEvent> eventList, URL mainURL) {
		JoranConfigurator joranConfigurator = new JoranConfigurator();
		joranConfigurator.setContext(lc);
		if (eventList != null) {
			System.out.println("Falling back to previously registered safe configuration.");
			try {
				lc.reset();
				joranConfigurator.informContextOfURLUsedForConfiguration(lc, mainURL);
				joranConfigurator.doConfigure(eventList);
				System.out.println("Re-registering previous fallback configuration once more as a fallback configuration point");
				joranConfigurator.registerSafeConfiguration();
			} catch (JoranException e) {
				System.err.println("Unexpected exception thrown by a configuration considered safe.");
				e.printStackTrace();
			}
		} else {
			System.out.println("No previous configuration to fall back on.");
		}
	}
}