package eap.comps.orm.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * <p> Title: Base DAO</p>
 * <p> Description: 所有DAOImpl必须继承此类</p>
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
public class BaseDAO {
	
	@Autowired
	@Qualifier("eap_comps_orm_mybatis_sqlExecutor")
	protected ISqlExecutor sqlExecutor;
}