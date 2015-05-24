package eap.comps.shiro.cas;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.io.SerializationException;
import org.apache.shiro.io.Serializer;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import eap.util.JsonUtil;
import eap.util.ReflectUtil;

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
public class JsonPrincipalSerializer implements Serializer<PrincipalCollection> {

	@Override
	public byte[] serialize(PrincipalCollection o) throws SerializationException {
		Map<String, Set<Object>> realmPrincipals = (Map<String, Set<Object>>) ReflectUtil.getFieldValue(o, "realmPrincipals");
		Object[] data = new Object[realmPrincipals.size()];
		int dataIndex = 0;
		for (Map.Entry<String, Set<Object>> realmPrincipal : realmPrincipals.entrySet()) {
			Object[] row = new Object[3];
			
			String realm = realmPrincipal.getKey();
			row[0] = realm;
			Set<Object> principal = realmPrincipal.getValue();
			if (principal != null && principal.size() > 0) {
				Iterator<Object> it = principal.iterator();
				if (it.hasNext()) {
					String username = (String) it.next();
					row[1] = username;
				}
				if (it.hasNext()) {
					Map<String, String> attrs = (Map<String, String>) it.next();
					row[2] = attrs;
				}
			}
			
			data[dataIndex] = row;
		}
		
		try {
			return JsonUtil.toJson(data).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}

	@Override
	public PrincipalCollection deserialize(byte[] serialized) throws SerializationException {
		Object[][] data = null;
		try {
			data = JsonUtil.parseJson(new String(serialized, "UTF-8"), Object[][].class);
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException(e.getMessage(), e);
		}
		
		SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
		for (Object[] row : data) {
			String realmName = (String) row[0];
			String username = (String) row[1];
			HashMap<String, String> attrs = (HashMap<String, String>) row[2];
			
//			Set principal = new LinkedHashSet();
//			principal.add(username);
//			principal.add(attrs);
			
			principalCollection.add(username, realmName);
			principalCollection.add(attrs, realmName);
		}
		
		return principalCollection;
//		return JsonUtil.parseJson(new String(serialized, "UTF-8"), SimplePrincipalCollection.class);
//		return JSON.parseObject(serialized, SimplePrincipalCollection.class);
	}
	
	public static void main(String[] args) {
//		SimplePrincipalCollection c = new SimplePrincipalCollection
		String json = "{\"eap.cas.client.ShiroCasRealm_0\":[\"chiknin\",{\"rememberMe\":\"true\",\"userId\":\"1\"}]}"; // JSON.toJSONString(ReflectUtil.getFieldValue(o, "realmPrincipals"));
//		Map<String, LinkedHashSet<String>> s = new LinkedHashMap<String, LinkedHashSet<String>>(0);
		Object o = JSON.parseObject(json, new TypeReference<LinkedHashMap<String, LinkedHashSet>>(){});
		System.out.println(o);
				
	}
}