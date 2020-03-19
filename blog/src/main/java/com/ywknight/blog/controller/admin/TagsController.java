package com.ywknight.blog.controller.admin;

import com.ywknight.blog.po.Tag;
import com.ywknight.blog.service.TagService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public String tags(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags-list";
    }

    @GetMapping("/edit")
    public String addTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }
    @GetMapping("/edit/{id}")
    public String editTag(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping
    public String submit(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
        //重复数据校验
        Tag tagVerify = tagService.getTagByName(tag.getName());
        if (tagVerify != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            //没保存成功
            redirectAttributes.addFlashAttribute("fail", "新增失败！");
        } else {
            redirectAttributes.addFlashAttribute("success", "操作成功！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/{id}")
    public String edit(@Valid Tag tag, BindingResult result,@PathVariable Long id,RedirectAttributes redirectAttributes){
        //重复数据校验
        Tag tagVerify = tagService.getTagByName(tag.getName());
        if (tagVerify != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null) {
            //没保存成功
            redirectAttributes.addFlashAttribute("fail", "更新失败！");
        } else {
            redirectAttributes.addFlashAttribute("success", "操作成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("success", "删除成功！");
        return "redirect:/admin/tags";
    }
}
