package com.ckh.blog.controller.admin;

import com.ckh.blog.pojo.Type;
import com.ckh.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //添加分类页面
    @GetMapping("/type/input")
    public String input() {
        return "admin/type/input";
    }

    //分类列表页面
    @GetMapping("/type/List")
    public String typeList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, 5,orderBy);
        List<Type> typeList = typeService.getTypeList();
        PageInfo<Type> pageInfo = new PageInfo<>(typeList);
        System.out.println(pageInfo.toString());
        model.addAttribute("page", pageInfo);
        return "admin/type/List";
    }

    //添加分类
    @PostMapping("/type/add")
    public String typeAdd(@Valid Type type, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t!=null) {
            attributes.addFlashAttribute("msg","不能添加相同的分类");
            return "redirect:/admin/type/input";
        }
        int result = typeService.saveType(type);
        if (result>0) {
            attributes.addFlashAttribute("msg", "新增成功");
        }else {
            attributes.addFlashAttribute("msg","新增失败");
        }
        return "redirect:/admin/type/List";
    }

    //修改分类页面
    @GetMapping("/type/{id}/input")
    public String typeEditInput(@PathVariable("id") Long id, Model model) {
        Type type = typeService.getTypeById(id);
        model.addAttribute("type", type);
        return "admin/type/update-input";
    }

    //修改分类
    @PostMapping("/type/update")
    public String updateType(Type type, RedirectAttributes attributes,Model model) {
        Type t = typeService.getTypeByName(type.getName());
        if (t!=null) {
            model.addAttribute("msg", "分类名称没有修改,请重新输入");
            return "admin/type/update-input";
        }
        int result = typeService.updateType(type);
        if (result>0) {
            attributes.addFlashAttribute("msg", "修改成功");
        }else {
            attributes.addFlashAttribute("msg", "修改失败");
        }
        return "redirect:/admin/type/List";
    }

    //删除分类
    @GetMapping("/type/{id}/delete")
    public String deleteType(@PathVariable("id") Long id, RedirectAttributes attributes) {
        int result = typeService.deleteType(id);
        if (result>0) {
            attributes.addFlashAttribute("msg", "删除成功");
        }else {
            attributes.addFlashAttribute("msg", "删除失败");
        }
        return "redirect:/admin/type/List";
    }
}
