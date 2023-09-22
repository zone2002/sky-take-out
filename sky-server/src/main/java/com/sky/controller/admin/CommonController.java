package com.sky.controller.admin;

import com.aliyuncs.exceptions.ClientException;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    private final AliOssUtil aliOssUtil;

    public CommonController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }


    /**
     * 文件上传
     * @param file 要上传的文件
     * @return 上传的url
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) throws IOException, ClientException {
        log.info("文件上传:{}", file);

        //原始文件名
        String originalFileName = file.getOriginalFilename();

        //原始文件名
        if(originalFileName == null){
            log.info("没找到文件");
            return Result.error(MessageConstant.NOT_FIND_FILE);
        }

        //截取原始文件名的后缀
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        //构造新文件名称
        String objectName = UUID.randomUUID() + extension;
        //文件的请求路径
        String filePath = aliOssUtil.upload(file.getBytes(), objectName);

        return Result.success(filePath);
    }
}
