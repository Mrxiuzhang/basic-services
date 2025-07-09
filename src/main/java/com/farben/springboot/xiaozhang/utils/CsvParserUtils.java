package com.farben.springboot.xiaozhang.utils;

import com.farben.springboot.xiaozhang.dto.CSVDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvParserUtils {

    public static List<CSVDTO> parseCsv(InputStream inputStream) {
        List<CSVDTO> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader() // 使用首行作为表头
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            for (CSVRecord record : csvParser) {
                CSVDTO user = new CSVDTO();
                user.setName(record.get("name"));  // 表头字段名
                user.setEmail(record.get("email"));
                user.setAge(Integer.parseInt(record.get("age")));
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("解析 CSV 文件失败: " + e.getMessage());
        }

        return users;
    }
}