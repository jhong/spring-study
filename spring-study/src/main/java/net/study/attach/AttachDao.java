package net.study.attach;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.study.common.DataObject;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

/**  
 * <pre>
 * 첨부파일 Dao
 * </pre>
 
 * @version 2014.05.13
 * @author 김지홍 
 */
@Repository("attachDao")
public class AttachDao extends SqlMapClientDaoSupport {

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
				queryForObject("common.attach.selectListCount", condition);
		
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
				queryForList("common.attach.selectList", condition);		
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
				queryForList("common.attach.selectList.all", condition);		
		return list;
	}

	/**
	 * <pre>
	 * 상세 조회
	 * </pre>
	 *
	 * @param condition
	 * @return AttachVo
	 */
	public AttachVo selectDetail(Map condition) throws Exception {
		AttachVo data = (AttachVo)this.getSqlMapClientTemplate()
				.queryForObject("common.attach.selectDetail", condition);
		return data;
	}

	/**
	 * <pre>
	 * 첨부파일 등록
	 * </pre>
	 *
	 * @param attachVo
	 * @return int
	 * @throws Exception
	 */
	public int insert(AttachVo attachVo) throws Exception {
		Object result = this.getSqlMapClientTemplate().insert("common.attach.insert", attachVo);
		if (result == null) return 0;
		else return new Integer(result.toString());
	}

	/**
	 * <pre>
	 * 첨부파일 수정
	 * </pre>
	 *
	 * @param attachVo
	 * @return int
	 * @throws Exception
	 */
	public int update(AttachVo attachVo) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().update("common.attach.update", attachVo);
		return result;
	}

	/**
	 * <pre>
	 * 첨부파일 수정 (REF_NO, REF_NO2, REF_NO_SEQ 수정)
	 * </pre>
	 *
	 * @param attachVo
	 * @return int
	 * @throws Exception
	 */
	public int updateRefNo(DataObject bizData) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().update("common.attach.updateRefNo", bizData);
		return result;
	}

	/**
	 * <pre>
	 * 첨부파일 삭제
	 * </pre>
	 *
	 * @param param
	 * @return int
	 * @throws Exception
	 */
	public int delete(Map param) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().delete("common.attach.delete", param);
		return result;
	}

}
