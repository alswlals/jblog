package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;

	public BlogVo findById(String id) {
		return blogRepository.findById(id);
	}

	public PostVo findPost(Long categoryNo, Long postNo) {
		PostVo vo = blogRepository.findPost(postNo);
		return vo;
	}

	public List<CategoryVo> categoryList(String id) {
		List<CategoryVo> list = blogRepository.categoryList(id);
		return list;
	}

	public List<PostVo> postList(Long categoryNo) {
		List<PostVo> list = blogRepository.postList(categoryNo);
		return list;
	}

	public void update(BlogVo vo) {
		blogRepository.update(vo);
	}

//	public List<Integer> count(String id) {
//
//		List<Integer> categoryNo = new ArrayList<>();
//		categoryNo = blogRepository.categoryNo(id);
//
//		List<Integer> count = new ArrayList<>();
//
//		for (int i = 0; i < categoryNo.size(); i++) {
//			count.add(blogRepository.count(categoryNo.get(i)));
//		}
//		return count;
//	}

	public void categoryInsert(CategoryVo vo) {
		blogRepository.categoryInsert(vo);

	}

	public void categoryDelete(Long no) {
		blogRepository.categoryDelete(no);
	}

	public void write(PostVo vo) {

		blogRepository.write(vo);
	}
	
	public long categoryNoDefault (String id) {
		
		return blogRepository.categoryNoDefault(id);
	}

}
