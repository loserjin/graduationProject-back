package com.canteen.controller;


import com.canteen.common.lang.Result;
import com.canteen.entity.Component;
import com.canteen.service.ComponentService;
import com.canteen.service.DepartmentService;
import com.canteen.service.DepartmentfloorService;
import com.canteen.service.MenuService;
import com.canteen.util.TencentCOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片上传Controller
 */
@Controller
public class ImageUploadController {

    @Value("${tencent.path}")
    private String IMAGE_PATH;
    @Autowired
    ComponentService componentService;
    @Autowired
    MenuService menuService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentfloorService departmentfloorService;


    /**
     * 上传图片
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile multipartFile,
                         Model model) throws Exception {
        //获取文件的名称
        String fileName = multipartFile.getOriginalFilename();

        //判断有无后缀
        if (fileName.lastIndexOf(".") < 0)
            return Result.fail(500, "上传图片格式不正确", null);

        //获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));

        //如果不是图片
        if (!prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".jpeg") && !prefix.equalsIgnoreCase(".svg") && !prefix.equalsIgnoreCase(".gif") && !prefix.equalsIgnoreCase(".png")) {
            return Result.fail(500, "上传图片格式不正确", null);
        }

        //使用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile("imagesFile-" + System.currentTimeMillis(), prefix);
        //将Multifile转换成File
        multipartFile.transferTo(excelFile);

        //调用腾讯云工具上传文件
        String imageName = TencentCOS.uploadfile(excelFile, "image");

        //程序结束时，删除临时文件
        deleteFile(excelFile);

        //存入图片名称，用于网页显示
        model.addAttribute("imageName", imageName);

        String imageUrl="https://image-1304681438.cos.ap-guangzhou.myqcloud.com/"+imageName;
//        //更新数据库
//        //配料图片
//        if (type.intValue()==0){
//
//        }
//        //菜单图片
//        if (type.intValue()==1){
//
//        }
//        //饭堂图片
//        if (type.intValue()==2){
//
//        }
//        //楼层图片
//        if (type.intValue()==3){
//
//        }
//        userInfoService.updateUserAvatar(imageName, username);

        //返回成功信息
        return Result.fail(200, "图片上传成功", imageUrl);
    }

    /**
     * 删除临时文件
     *
     * @param files 临时文件，可变参数
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
