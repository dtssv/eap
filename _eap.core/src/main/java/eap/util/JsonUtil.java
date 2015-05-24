package eap.util;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	private static ObjectMapper objectMapper = null;
	
	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	public static String toPrettyJson(Object obj) {
		if (obj == null) {
			return null;
		}
		
		try {
			return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	public static <T> T parseJson(String jsonStr, Class<T> valueType) {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		
		try {
			return getObjectMapper().readValue(jsonStr, valueType);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	public static <T> T parseJson(String jsonStr, TypeReference<T> typeReference) {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		
		try {
			return getObjectMapper().readValue(jsonStr, typeReference);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	private static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			try {
				objectMapper = new JacksonObjectMapperFactory().getObject();
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
		
		return objectMapper;
	}
	
	public void setObjectMapper(ObjectMapper om) {
		objectMapper = om;
	}
	
	public static void main(String[] args) throws Exception {
//		ju.setObjectMapper(new JacksonObjectMapperFactory().getObject());
		
//		Object r = parseJson("{\"a\":\"1\", \"b\": 2, \"c\": \"c\"}", HashMap.class);
//		System.out.println(r);
		String data = "{\"app.sendSms\":\"0\",\"jdbc.url\":\"jdbc:oracle:thin:@10.110.1.133:1521:ncimall\",\"ftp.connect.remoteFile\":\"/signature/unsigned/\",\"rocketmq.consumerGroup\":\"COM-ENCI-WWW-CONSUMER\",\"redis.sentinels.2\":\"10.110.1.131:26379\",\"pay.unionpay.props.securityKey_bankPay\":\"\",\"redis.sentinels.1\":\"10.110.1.131:26379\",\"ftp.connect.name\":\"desladmin\",\"pay.allinpay.props.ISSUER_ID\":\"vbank\",\"http.domain.nonssl\":\"http://uatservice.e-nci.com,http://uatwww.e-nci.com,http://uatmy.e-nci.com\",\"rocketmq.namesrvAddr\":\"10.110.1.131:9876\",\"ws.openInsuranceCallbackService.wsdl\":\"http://10.110.1.111:8080/open/gateway/ws/ecpInsuranceCallbackService?wsdl\",\"ftp.connect.password\":\"Windows2009\",\"redis.masterName\":\"redis-A\",\"pay.allinpay.props.MERCHANTID\":\"100020091218001\",\"app.mode\":\"dev\",\"pay.unionpay.props.merId_fastPay\":\"104110548991224\",\"http.domain.static\":\"http://uatstatic.e-nci.com\",\"rocketmq.producerGroup\":\"COM-ENCI-WWW-PRODUCER\",\"pay.unionpay.props.UPOP_BASE_URL\":\"https://www.epay.lxdns.com/UpopWeb/api/\",\"pay.unionpay.props.UPOP_QUERY_BASE_URL\":\"https://www.epay.lxdns.com/UpopWeb/api/\",\"pay.allinpay.props.CER_PATH\":\"/data/TLCert.cer\",\"othercompany.166081458857480b8f4ea028d70e1156.webpayment.key\":\"c\",\"pay.unionpay.props.securityKey_fastPay\":\"88888888\",\"pay.allinpay.props.GATEWAY\":\"http://ceshi.allinpay.com/gateway/index.do\",\"ftp.connect.attachment.ftpFile\":\"/attachment/file/\",\"pay.unionpay.props.UPOP_BSPAY_BASE_URL\":\"https://www.epay.lxdns.com/UpopWeb/api/\",\"ftp.connect.port\":\"21\",\"othercompany.166081458857480b8f4ea028d70e1156.webpayment.notifyUrl\":\"http://www.baidu.com\",\"smssend.wsdl.orgid\":\"80\",\"ftp.connect.signed.remoteFile\":\"/signature/signed/\",\"ftp.connect.url\":\"10.156.3.184\",\"payment.callback.baseAddress\":\"http://uatwww.e-nci.com\",\"preservation.website.loginAddress\":\"https://10.8.5.28:9443/cas/login\",\"mail.base.path\":\"http://uattwww.e-nci.com\",\"pay.allinpay.props.KEY\":\"1234567890\",\"jdbc.password\":\"uat@test\",\"third.epolicyUrl\":\"http://uatwww.e-nci.com\",\"http.domain.ssl\":\"https://uatpassport.e-nci.com,https://uatmy.e-nci.com\",\"refund_nopassword_notify_url\":\"\",\"ftp.connect.rule.ftpFile\":\"/doc/Clause/\",\"jdbc.username\":\"ecpprd\",\"rocketmq.sendMsgTimeout\":\"5000\",\"enciESB.ws.baseUrl\":\"http://esb.e-nci.com:9999\",\"othercompany.166081458857480b8f4ea028d70e1156.webpayment.returnUrl\":\"http://www.baidu.com\",\"preservation.website.loginService\":\"https://10.8.5.28:9443/myxinhua/initmembers.gsp?legalType=10\",\"rocketmq.consumer.enable\":\"off\",\"pay.unionpay.props.merId_bankPay\":\"\",\"redis.database\":\"1\",\"pay.unionpay.props.gateWay\":\"https://www.epay.lxdns.com/UpopWeb/api/Pay.action\",\"pay.unionpay.props.authPayUrl\":\"https://www.epay.lxdns.com/UpopWeb/api/AuthPay.action\",\"pay.unionpay.props.smsUrl\":\"https://www.epay.lxdns.com/UpopWeb/api/Sms.action\",\"magnum.mfeUrl\":\"http://10.110.1.137/MagnumFrontEnd/Default.aspx\",\"magnum.question\":\"HeightCM,WeightKilos,BirthWeightKG,Smoking,SmokingYears,CigarettesPerDay,DrinkingBaijiu,BaijiuPerDay,AvocationAny,CountryAny,PreviouslySubStd,RelationAny,RelationAnyLadyCI,BirthComplications,PregnantNow,ImpFemaleCI1,ImpFemaleCI2,ImpCVS,ImpThyroidLiverKidney,ImpCancerMental,ImpLast5Years,ImpSimple\"}";
		Map envMap = JsonUtil.parseJson(data, TreeMap.class);
		System.out.println(JsonUtil.toJson(envMap));
	}
}