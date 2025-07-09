package com.farben.springboot.xiaozhang.dto;

import com.farben.springboot.xiaozhang.annotation.ValidDateRange;
import lombok.Data;

import java.time.LocalDate;
// 在DTO中使用
@Data
@ValidDateRange(startDate = "startDate", endDate = "endDate")
public class EventDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
