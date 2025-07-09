package com.farben.springboot.xiaozhang.common;

import java.util.List;
/**
 * 通用分页结果封装
 *
 * @param <T> 数据类型
 */
public class PageResult<T>{
    private long total;       // 总记录数
    private long current;      // 当前页码
    private long size;         // 每页大小
    private long pages;        // 总页数
    private List<T> records;  // 当前页数据列表

    // 构造方法
    public PageResult() {
    }

    public PageResult(List<T> records, long total, long current, long size) {
        this.records = records;
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = size == 0 ? 0 : (long) Math.ceil((double) total / size);
    }

    // 快速创建成功分页结果
    public static <T> PageResult<T> success(List<T> records, long total, long current, long size) {
        return new PageResult<>(records, total, current, size);
    }

    // Getters and Setters
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        // 更新总页数
        if (size > 0) {
            this.pages = (long) Math.ceil((double) total / size);
        }
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
        // 更新总页数
        if (size > 0) {
            this.pages = (long) Math.ceil((double) total / size);
        }
    }

    public long getPages() {
        return pages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", current=" + current +
                ", size=" + size +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }
}
