package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository

public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;

	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}

	public PostVo findPost(Long postNo) {
		return sqlSession.selectOne("blog.findPost", postNo);
	}

	public List<CategoryVo> categoryList(String id) {
		List<CategoryVo> list = sqlSession.selectList("blog.categoryList", id);
		return list;
	}

	public List<PostVo> postList(Long categoryNo) {
		List<PostVo> list = sqlSession.selectList("blog.postList", categoryNo);
		return list;
	}

	public List<Integer> categoryNo(String id) {
		List<Integer> list = sqlSession.selectList("blog.categoryNo", id);
		return list;
	}

	public void update(BlogVo blogvo) {
		sqlSession.update("blog.update", blogvo);
	}

//	public int count(int no) {
//		return sqlSession.selectOne("blog.count", no);
//	}

	public void categoryInsert(CategoryVo vo) {
		sqlSession.insert("blog.categoryInsert", vo);
	}

	public void categoryDelete(Long no) {
		sqlSession.delete("blog.categoryDelete", no);
	}

	public void write(PostVo vo) {
		sqlSession.insert("blog.write", vo);
	}

	public int categoryMin(String id) {
		return sqlSession.selectOne("blog.categoryMin", id);
	}

	public int postMin(Long categoryNo) {
		return sqlSession.selectOne("blog.postMin", categoryNo);
	}

	public long categoryNoDefault(String id) {
		return sqlSession.selectOne("blog.categoryNoDefault", id);
	}

	public PostVo getDefaultPost(Long categoryNo) {
		return sqlSession.selectOne("blog.getDefaultPost", categoryNo);
	}
}
