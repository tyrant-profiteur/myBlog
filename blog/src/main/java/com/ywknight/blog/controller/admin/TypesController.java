package com.ywknight.blog.controller.admin;

import com.ywknight.blog.po.Type;
import com.ywknight.blog.service.TypeService;
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
@RequestMapping("/admin/types")
public class TypesController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types-list";
    }

    @GetMapping("/edit")
    public String addType(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }
    @GetMapping("/edit/{id}")
    public String editType(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping
    public String submit(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        //重复数据校验
        Type typeVerify = typeService.getTypeByName(type.getName());
        if (typeVerify != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            //没保存成功
            redirectAttributes.addFlashAttribute("fail", "新增失败！");
        } else {
            redirectAttributes.addFlashAttribute("success", "操作成功！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/{id}")
    public String edit(@Valid Type type, BindingResult result,@PathVariable Long id,RedirectAttributes redirectAttributes){
        //重复数据校验
        Type typeVerify = typeService.getTypeByName(type.getName());
        if (typeVerify != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null) {
            //没保存成功
            redirectAttributes.addFlashAttribute("fail", "更新失败！");
        } else {
            redirectAttributes.addFlashAttribute("success", "操作成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("success", "删除成功！");
        return "redirect:/admin/types";
    }
}
