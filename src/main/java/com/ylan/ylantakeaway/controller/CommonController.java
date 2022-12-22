package com.ylan.ylantakeaway.controller;

import com.ylan.ylantakeaway.common.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 通用控制器
 *
 * @author by ylan
 * @date 2022-12-20 18:44
 */

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * 传入配置文件中文件下载路径
     */
    @Value("${ylanTakeaway.path}")
    private String basePath;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @SneakyThrows
    @PostMapping("/upload")
    // file为临时文件，本次请求结束临时文件会消失，需要转存到指定位置
    public R<String> upload(@RequestPart("file") MultipartFile file) {
        log.info("文件上传:{}", file.toString());

        // 准备上传的目录
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdir();

        // 获取原始文件名称
        String fileName = file.getOriginalFilename();
        // 根据UUID生成新的文件名称
        String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        // 文件转存
        file.transferTo(new File(basePath + File.separator + newFileName));

        // 返回新文件名称
        return R.success(newFileName);
    }

    /**
     * 下载文件
     *
     * @param name
     * @param response
     */
    @SneakyThrows
    @GetMapping("/download")
    public void download(@RequestParam("name") String name, HttpServletResponse response) {
        log.info("文件下载:{}", name);

        // 设置响应方式
        response.setContentType("image/jpeg");

        // 输入流和输出流
        InputStream input = new FileInputStream(new File(basePath, name));
        ServletOutputStream output = response.getOutputStream();

        // 创建字节数组
        byte[] bytes = new byte[input.available()];

        // 循环写出
        int len = 0;
        while ((len = input.read(bytes)) != -1) {
            output.write(bytes, 0, len);
            output.flush();
        }

        // 关闭资源
        output.close();
        input.close();
    }

}