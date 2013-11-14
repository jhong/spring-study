package net.study.code;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("codeDao")
public class CodeDao extends SqlMapClientDaoSupport {

	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

	/**
	 * <pre>
	 * List Count 반환
	 * </pre>
	 * 
	 * @param condition
	 * @return
	 */
	public int selectListCount(Map condition) {
		Integer count = (Integer)this.getSqlMapClientTemplate().
				queryForObject("common.code.selectListCount", condition);
		
		return count.intValue();
	}

	/**
	 * <pre>
	 * List 반환
	 * </pre>
	 * 
	 * @param condition
	 * @return
	 */
	public List selectListAll(Map condition) {
		List list = this.getSqlMapClientTemplate().
				queryForList("common.code.selectList.all", condition);		
		return list;
	}

}
