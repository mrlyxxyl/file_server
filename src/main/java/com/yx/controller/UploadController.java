package com.yx.controller;

import com.yx.constant.GenResult;
import com.yx.util.LogUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Controller
@RequestMapping
public class UploadController {

    @RequestMapping(value = "/mvc/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(required = true) String projectId,
                                      @RequestParam(required = true) String fileName,
                                      @RequestParam(required = true) long fileLen,
                                      @RequestParam(required = true) long startPoint,
                                      @RequestParam(required = true) MultipartFile file) {
        try {
            System.out.println(projectId);
            System.out.println(fileName);
            System.out.println(fileLen);
            System.out.println(startPoint);
            String realPath = "F:/";
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, file.getOriginalFilename()));//使用工具上传
            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }
}
