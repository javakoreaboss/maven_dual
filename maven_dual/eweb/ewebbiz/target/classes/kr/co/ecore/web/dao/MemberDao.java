package kr.co.ecore.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	
	protected final static Logger logger = Logger.getLogger(MemberDao.class);

	@Autowired
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMysql;	
	
	@SuppressWarnings("rawtypes")
	public List list(String mapperId) {
		return this.sqlSession.selectList("ecore.templet.selectMember");
	}
	
	@SuppressWarnings("rawtypes")
	public List listMysql(String mapperId) {
		return this.sqlSessionMysql.selectList("ecore2.templet.selectMember2");
	}	
}
