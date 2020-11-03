package com.ckh.blog.controller.admin;

import com.ckh.blog.pojo.Tag;
import com.ckh.blog.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    //添加标签页面
    @GetMapping("/tag/input")
    public String input() {
        return "admin/tag/input";
    }

    //标签列表页面
    @GetMapping("/tag/List")
    public String tagList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, 5,orderBy);
        List<Tag> tagList = tagService.getTagList();
        PageInfo<Tag> pageInfo = new PageInfo<>(tagList);
        System.out.println(pageInfo.toString());
        model.addAttribute("page", pageInfo);
        return "admin/tag/List";
    }

    //添加标签
    @PostMapping("/tag/add")
    public String tagAdd(@Valid Tag tag, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t!=null) {
            attributes.addFlashAttribute("msg","不能添加相同的标签");
            return "redirect:/admin/tag/input";
        }
        int result = tagService.saveTag(tag);
        if (result>0) {
            attributes.addFlashAttribute("msg", "新增成功");
        }else {
            attributes.addFlashAttribute("msg","新增失败");
        }
        return "redirect:/admin/tag/List";
    }

    //修改标签页面
    @GetMapping("/tag/{id}/input")
    public String tagEditInput(@PathVariable("id") Long id, Model model) {
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag", tag);
        return "admin/tag/update-input";
    }

    //修改标签
    @PostMapping("/tag/update")
    public String updateTag(Tag tag, RedirectAttributes attributes,Model model) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t!=null) {
            model.addAttribute("msg", "标签名称没有修改,请重新输入");
            return "admin/tag/update-input";
        }
        int result = tagService.updateTag(tag);
        if (result>0) {
            attributes.addFlashAttribute("msg", "修改成功");
        }else {
            attributes.addFlashAttribute("msg", "修改失败");
        }
        return "redirect:/admin/tag/List";
    }

    //删除标签
    @GetMapping("/tag/{id}/delete")
    public String deleteTag(@PathVariable("id") Long id, RedirectAttributes attributes) {
        int result = tagService.deleteTag(id);
        if (result>0) {
            attributes.addFlashAttribute("msg", "删除成功");
        }else {
            attributes.addFlashAttribute("msg", "删除失败");
        }
        return "redirect:/admin/tag/List";
    }
}
