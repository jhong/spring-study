package net.study.editor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("editorDao")
public class EditorDao  extends SqlMapClientDaoSupport {

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
				queryForObject("common.editor.selectListCount", condition);
		
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
				queryForList("common.editor.selectList", condition);		
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
				queryForList("common.editor.selectList.all", condition);		
		return list;
	}

	/**
	 * <pre>
	 * 상세 조회
	 * </pre>
	 *
	 * @param condition
	 * @return EditorVo
	 */
	public EditorVo selectDetail(Map condition) throws Exception {
		EditorVo data = (EditorVo)this.getSqlMapClientTemplate()
				.queryForObject("common.editor.selectDetail", condition);
		return data;
	}

	/**
	 * <pre>
	 * 게시글 등록
	 * </pre>
	 *
	 * @param editorVo
	 * @return int
	 * @throws Exception
	 */
	public int insert(EditorVo editorVo) throws Exception {
		Object result = this.getSqlMapClientTemplate().insert("common.editor.insert", editorVo);
		if (result == null) return 0;
		else return new Integer(result.toString());
	}

	/**
	 * <pre>
	 * 게시글 수정
	 * </pre>
	 *
	 * @param editorVo
	 * @return int
	 * @throws Exception
	 */
	public int update(EditorVo editorVo) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().update("common.editor.update", editorVo);
		return result;
	}

	/**
	 * <pre>
	 * 게시글 삭제
	 * </pre>
	 *
	 * @param param
	 * @return int
	 * @throws Exception
	 */
	public int delete(Map param) throws Exception {
		int result = (int)this.getSqlMapClientTemplate().delete("common.editor.delete", param);
		return result;
	}

}
