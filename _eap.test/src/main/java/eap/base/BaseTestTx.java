package eap.base;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import eap.util.StringUtil;

@ContextConfiguration(locations={"classpath*:META-INF/spring/eap_beans.xml", "classpath:AC.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class BaseTestTx extends AbstractTransactionalTestNGSpringContextTests {
	static {
		InputStream envInputStream = null;
		try {
			URL envUrl = BaseTestTx.class.getResource("/env.properties");
			if (envUrl != null) {
				envInputStream = new FileInputStream(envUrl.getPath());
				
				Properties envProps = new Properties();
				envProps.load(envInputStream);
				if (StringUtil.isBlank(System.getProperty("app.name"))) {
					System.setProperty("app.name", envProps.getProperty("app.name"));
				}
				if (StringUtil.isBlank(System.getProperty("app.version"))) {
					System.setProperty("app.version", envProps.getProperty("app.version"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (envInputStream != null) {
					envInputStream.close();
				}
			} catch (Exception e) {}
		}
		
		if (System.getProperty("app.id") == null) {
			System.setProperty("app.id", "1");
		}
	}
}