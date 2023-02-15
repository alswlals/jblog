package com.douzone.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/jblog/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private FileuploadService fileuploadService;

	@RequestMapping({ "", "/{pathNo1}", "/{pathNo1}/{pathNo2}" })
	public String index(@PathVariable("id") String id, @PathVariable("pathNo1") Optional<Long> pathNo1,
			@PathVariable("pathNo2") Optional<Long> pathNo2, Model model) {

		BlogVo blogvo = blogService.findById(id);

		Long categoryNo = 0L;
		Long postNo = 0L;

		if (pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if (pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		} else {
			categoryNo = blogService.categoryNoDefault(id);
		}

		PostVo postvo = null;
		if (postNo == 0L) {
			postvo = blogService.findDefaultPost(categoryNo);
		} else {
			postvo = blogService.findPost(categoryNo, postNo);
		}

		List<CategoryVo> categorylist = new ArrayList<>();
		categorylist = blogService.categoryList(id);
		List<PostVo> postlist = new ArrayList<>();
		postlist = blogService.postList(categoryNo);

		model.addAttribute("id", id);
		model.addAttribute("blogvo", blogvo);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("postlist", postlist);
		model.addAttribute("postvo", postvo);

		return "/blog/main";
	}

	@Auth
	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id, Model model) {

		BlogVo blogvo = blogService.findById(id);

		model.addAttribute("id", id);
		model.addAttribute("blogvo", blogvo);

		return "/blog/admin-basic";
	}

	@Auth
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public String update(@PathVariable("id") String id, BlogVo blogvo, MultipartFile file, Model model) {
		String url = fileuploadService.restore(file);

		if (url != null) {
			blogvo.setProfile(url);
		}

		blogService.update(blogvo);

		return "redirect:/jblog/" + id + "/admin/basic";
	}

	@Auth
	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public String category(@PathVariable("id") String id, Model model) {
		BlogVo blogvo = blogService.findById(id);

		List<CategoryVo> vo = new ArrayList<>();
		vo = blogService.categoryList(id);

		model.addAttribute("id", id);
		model.addAttribute("vo", vo);
		model.addAttribute("blogvo", blogvo);

		return "blog/admin-category";
	}

	@Auth
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String categoryInsert(@PathVariable("id") String id, CategoryVo vo, Model model) {

		vo.setId(id);

		blogService.categoryInsert(vo);

		return "redirect:/jblog/" + id + "/admin/category";
	}

	@Auth
	@RequestMapping(value = "/admin/category/delete/{no}", method = { RequestMethod.GET, RequestMethod.POST })
	public String categoryDelete(@PathVariable("id") String id, @PathVariable("no") Long no, Model model) {

		blogService.categoryDelete(no);
		BlogVo blogvo = blogService.findById(id);

		List<CategoryVo> list = new ArrayList<>();
		list = blogService.categoryList(id);

		model.addAttribute("id", id);
		model.addAttribute("vo", list);
		model.addAttribute("blogvo", blogvo);
		return "redirect:/jblog/" + id + "/admin/category";
	}

	// 블로그 글쓰기 관리
	@Auth
	@RequestMapping("/admin/write")
	public String write(@PathVariable("id") String id, Model model) {

		List<CategoryVo> list = new ArrayList<>();
		list = blogService.categoryList(id);

		BlogVo blogvo = blogService.findById(id);

		model.addAttribute("id", id);
		model.addAttribute("list", list);
		model.addAttribute("blogvo", blogvo);

		return "blog/admin-write";
	}

	@Auth
	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String writeInsert(@ModelAttribute @Valid PostVo vo, @PathVariable("id") String id, BindingResult result,
			String title, Model model) {

//		if(result.hasErrors()) {
//			model.addAllAttributes(result.getModel());
//			return "blog/admin-write";
//		}

		if (vo.getTitle().equals("")) {
			return "redirect:/jblog/" + id + "/admin/write";
		}

		blogService.write(vo);

		model.addAttribute("id", id);

		return "redirect:/jblog/" + id;
	}

}
