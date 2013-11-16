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
	 * List 반환 (페이징 적용)
	 * </pre>
	 * 
	 * @param condition
	 * @return
	 */
	public List selectListCondition(Map condition) {
		List list = this.getSqlMapClientTemplate().
				queryForList("common.code.selectList", condition);		
		return list;
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

	/**
	 * <pre>
	 * 상세 조회
	 * </pre>
	 *
	 * @param condition
	 * @return CodeVo
	 */
	public CodeVo selectDetail(Map condition) throws Exception {
		CodeVo data = (CodeVo)this.getSqlMapClientTemplate()
				.queryForObject("common.code.selectDetail", condition);
		return data;
	}

	/**
	 * <pre>
	 * 코드 등록
	 * </pre>
	 *
	 * @param codeVo
	 * @return int
	 * @throws Exception
	 */
	public int insert(CodeVo codeVo) throws Exception {
		Object result = this.getSqlMapClientTemplate().insert("common.code.insert", codeVo);
		if (result == null) return 0;
		else return new Integer(result.toString());
	}

	/**
	 * <pre>
	 * 코드 수정
	 * </pre>
	 *
	 * @param codeVo
	 * @return int
	 * @throws Exception
	 */
	public int update(CodeVo codeVo) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().update("common.code.update", codeVo);
		return result;
	}

	/**
	 * <pre>
	 * 코드 삭제
	 * </pre>
	 *
	 * @param param
	 * @return int
	 * @throws Exception
	 */
	public int delete(Map param) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().delete("common.code.delete", param);
		return result;
	}

}
