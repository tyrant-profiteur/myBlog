package com.ywknight.blog.controller.admin;

import com.ywknight.blog.po.Type;
import com.ywknight.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/types")
public class TypesController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types-list";
    }

    @GetMapping("/edit")
    public String addType(){
        return "admin/types-input";
    }

    @PostMapping
    public String submit(Type type){
        Type t = typeService.saveType(type);
        if (t == null){
            //没保存成功
        }
        return "redirect:/admin/types";
    }
}
