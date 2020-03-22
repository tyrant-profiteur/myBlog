package com.ywknight.blog.controller.admin;

import com.ywknight.blog.po.Blog;
import com.ywknight.blog.po.User;
import com.ywknight.blog.service.BlogService;
import com.ywknight.blog.service.TagService;
import com.ywknight.blog.service.TypeService;
import com.ywknight.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/blogs")
public class BlogsController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs-list";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public String bloglist(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.getTypeList());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs-list :: blogList";
    }

    @GetMapping("/edit")
    public String input(Model model){
        model.addAttribute("types",typeService.getTypeList());
        model.addAttribute("tags",tagService.getTagList());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.getTypeList());
        model.addAttribute("tags",tagService.getTagList());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping
    public String post(@Valid Blog blog, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return INPUT;
        }
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.getTagList(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            //没保存成功
            redirectAttributes.addFlashAttribute("fail", "操作失败！");
        } else {
            redirectAttributes.addFlashAttribute("success", "操作成功！");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("success", "删除成功！");
        return REDIRECT_LIST;
    }
}
