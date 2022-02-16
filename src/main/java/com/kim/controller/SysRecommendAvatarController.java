package com.kim.controller;


import cn.hutool.json.JSONObject;
import com.kim.common.lang.Result;
import com.kim.entity.SysRecommendAvatar;
import com.kim.service.SysRecommendAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-12-25
 */
@RestController
@RequestMapping("/systemAvatar")
public class SysRecommendAvatarController {
    @Autowired
    SysRecommendAvatarService sysRecommendAvatarService;

    @Value("${upload.pathSystemAvatar}")
    private String systemAvatarBaseFolderPath;

    @PostMapping("/add")
    public Result addSystemAvatar(MultipartFile file, HttpServletRequest request){

        SysRecommendAvatar sysRecommendAvatar = new SysRecommendAvatar();

        File baseFolder = new File(systemAvatarBaseFolderPath);
        if (!baseFolder.exists()){
            baseFolder.mkdirs();
        }

        StringBuffer url = new StringBuffer();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(systemAvatarBaseFolderPath);

        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + file.getOriginalFilename();

        try {
            File dest = new File(baseFolder, imgName);
            FileCopyUtils.copy(file.getBytes(),dest);
            url.append("/").append(imgName);

            JSONObject object = new JSONObject();
            object.put("url",url);

            System.out.println("最终的url为："+url.toString());

            sysRecommendAvatar.setAvatarAddress(url.toString());

            boolean res = sysRecommendAvatarService.save(sysRecommendAvatar);

            return Result.succ(res);

        } catch (Exception e) {
            return Result.fail("文件上传错误");
        }

    }

    @GetMapping("/get")
    public Result getSystemAvatar(){
        List<SysRecommendAvatar> allSystemAvatars = sysRecommendAvatarService.list(null);
        return Result.succ(allSystemAvatars);
    }

    @GetMapping("/del/{id}")        //删除选定系统头像
    public Result delSystemAvatar(@PathVariable("id") Long id){
        boolean res = sysRecommendAvatarService.removeById(id);
        return Result.succ(res);
    }
}
