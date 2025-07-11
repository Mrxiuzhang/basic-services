package com.farben.springboot.xiaozhang.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RedisDto implements Serializable {


    private String key;

    private String value;

    private String detail;

    public RedisDto(String key,String detail){
        this.detail =detail;
        this.key = key;
    }

}
