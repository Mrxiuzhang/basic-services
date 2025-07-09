package com.farben.springboot.xiaozhang.controller;


import com.farben.springboot.xiaozhang.dto.CSVDTO;
import com.farben.springboot.xiaozhang.utils.CsvParserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "示例API", description = "这是一个示例API的描述")
public class ExcelController {

    @GetMapping("/hello")
    @ApiOperation(value = "获取问候语", notes = "返回一个简单的问候语")
    public String hello() {
        return "Hello, World!";
    }


    @PostMapping("/parseCsvFile")
    @ApiOperation(value = "解析CSV", notes = "返回解析后的数据")
    public List<CSVDTO> parseCsvFile(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            return CsvParserUtils.parseCsv(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + e.getMessage());
        }
    }
}