package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
		
	public boolean join(UserVo vo) {
		Map<String,Object> map = new HashMap<>();
		map.put("vo", vo);
		int count= sqlSession.insert("user.insert",vo);
		sqlSession.insert("user.bloginsert",vo);
		sqlSession.insert("user.categoryinsert", map);
		sqlSession.insert("user.postinsert", map);
		return count==1;
	}

	public UserVo getUser(String id, String password) {
		 Map<String, String> map = new HashMap<>();
	        map.put("id", id);
	        map.put("password", password);
	        return sqlSession.selectOne("user.getUser", map);
	}

	public UserVo getUserById(String id) {
		return sqlSession.selectOne("user.getUserById",id);
	}

	// 아이디 중복 체크
	public int checkID(String id) {
		return sqlSession.selectOne("user.checkID", id);
		}
}
