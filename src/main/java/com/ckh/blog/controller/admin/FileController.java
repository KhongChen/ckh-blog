package com.ckh.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FileController {

    @PostMapping("/admin/img/upload")
    @ResponseBody
    //接收图片的参数名需要为"editormd-image-file"
    public JSONObject upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        String uploadFileName = file.getOriginalFilename(); //文件名

        String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".")); //后缀名
        String filePath = "/root/img/"; //上传后路径
        String fileName = System.currentTimeMillis()+suffix; //上传后新文件名
        //目标目录
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //保存
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("文件名"+uploadFileName);
        System.out.println("文件后缀:"+suffix);
        System.out.println("上传后文件名:"+fileName);
        JSONObject res = new JSONObject();
        res.put("url", "http://123.57.84.157:8080/upload/"+fileName);
        res.put("success", 1);
        res.put("message", "上传成功");
        return res;
    }
}
