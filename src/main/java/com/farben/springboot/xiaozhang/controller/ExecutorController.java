package com.farben.springboot.xiaozhang.controller;

import com.farben.springboot.xiaozhang.service.AsyncService;
import com.sun.javafx.binding.StringFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController("/executor")
@Api(value = "线程池API", description = "线程池控制器")
@Slf4j
public class ExecutorController {

    private final Map<String, Future<?>> activeExports = new ConcurrentHashMap<>();
    private final ExecutorService executorService;
    // 推荐使用构造器注入（防止循环依赖）
    @Autowired
    public ExecutorController(@Qualifier("taskExecutor") ExecutorService executorService) {
        this.executorService = executorService;
    }

    @PostMapping("/export")
    @ApiOperation(value = "测试导入excel", notes = "启动线程池")
    public ResponseEntity<String> startExport(@RequestParam String reportId) {
        Future<?> future = executorService.submit(() -> {
            try {
                // 模拟长时间导出操作
                for (int i = 0; i < 100; i++) {
                    // 检查线程中断状态
                    if (Thread.currentThread().isInterrupted()) {
                        activeExports.remove(reportId);
                        return;
                    }
                    // 导出进度处理
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                activeExports.remove(reportId);
                Thread.currentThread().interrupt();
            }
        });

        activeExports.put(reportId, future);
        return ResponseEntity.ok("导出任务已启动");
    }

    @PostMapping("/cancel-export")
    @ApiOperation(value = "测试取消excel", notes = "终止线程池")
    public ResponseEntity<String> cancelExport(@RequestParam String reportId) {
        Future<?> future = activeExports.get(reportId);
        //幂等设计：确保取消操作可以安全多次调用
        if (future != null && !future.isDone()) {
            // 发送中断信号并尝试取消任务
            boolean canceled = future.cancel(true);
            activeExports.remove(reportId);
            return canceled ?
                    ResponseEntity.ok("导出已取消") :
                    ResponseEntity.badRequest().body("取消失败（可能已完成）");
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/processPayment")
    @ApiOperation(value = "服务超时控制", notes = "调用外部服务时设置超时，超时后取消任务")
    public String processPayment(@RequestParam long timeOut) {
        Future<String> future = executorService.submit(() -> {
            // 模拟第三方支付调用
            Thread.sleep(5000);//5s
            log.info("第三方支付调用成功");
            return "第三方支付调用成功";
        });

        try {
            return future.get(timeOut, TimeUnit.MILLISECONDS);//2s超时
        } catch (TimeoutException e) {
            // 超时后取消任务
            future.cancel(true);
            log.info("支付超过规定时间");
            return "支付超过规定时间";
        } catch (Exception e) {
            log.error("中断第三方支付发生异常", e);
            return "中断第三方支付发生异常!";
        }
    }


    @PostMapping("/processBatch")
    @ApiOperation(value = "批量任务中的单任务失败处理", notes = "执行多个独立任务时，其中一个任务失败则取消其他任务。")
    public void processBatch() {

        List<Future<Boolean>> futures = new ArrayList<>();

        log.info("核心线程数:{}",Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            futures.add(executorService.submit(() -> processItem(finalI) ));
        }

        try {
            log.info("futures的长度:{}",futures.size());
            for (int i = 0; i < futures.size(); i++) {
                log.info("开始循环第：{}个任务",i);
                if (!futures.get(i).get()) {
                    log.info("从list里面获取到了:{}个任务",i);
                    // 任务失败，取消剩余任务
                    cancelRemainingTasks(futures, i );
                    break;
                }
            }
        } catch (Exception e) {
            log.error("异常原因:{}",e);
            cancelAllTasks();
        }
    }

    private void cancelRemainingTasks(List<Future<Boolean>> futures, int startIndex) {
        for (int i = startIndex; i < futures.size(); i++) {
            log.info(String.format("任务%s正在被取消",i));
            futures.get(i).cancel(true);
        }
    }

    /**
     * 取消所有任务
     */
    public void cancelAllTasks() {
        System.out.println("正在取消所有任务...");
        int cancelledCount = 0;

        for (Map.Entry<String, Future<?>> entry : activeExports.entrySet()) {
            String taskId = entry.getKey();
            Future<?> future = entry.getValue();

            if (!future.isDone()) {
                // 尝试取消任务，true表示中断正在执行的任务
                boolean success = future.cancel(true);
                if (success) {
                    cancelledCount++;
                    System.out.println("已取消任务: " + taskId);
                }
            }
        }

        activeExports.clear();
        System.out.printf("共取消 %d 个任务%n", cancelledCount);
    }

    /**
     * 取消指定任务
     * @param taskId 任务ID
     */
    public boolean cancelTask(String taskId) {
        Future<?> future = activeExports.get(taskId);
        if (future != null && !future.isDone()) {
            boolean cancelled = future.cancel(true);
            if (cancelled) {
                activeExports.remove(taskId);
                System.out.println("任务 " + taskId + " 已取消");
                return true;
            }
        }
        return false;
    }

    public boolean processItem(int id) {
        // 记录开始时间
        final long startTime = System.currentTimeMillis();
        System.out.printf("[%s] 开始处理任务: %s%n",
                Thread.currentThread().getName(), id);

        try {
            // 1. 模拟业务处理 - 实际应用中替换为真实业务逻辑 5s
            Thread.sleep(5000);

            // 2. 模拟随机失败 - 实际应用中移除这部分
            if (Math.random() < 0.2) { // 20%失败率
                throw new RuntimeException("处理失败: " + id);
            }

            // 3. 处理成功逻辑
            System.out.printf("[%s] 任务 %s 处理成功, 耗时: %dms%n",
                    Thread.currentThread().getName(),
                    id,
                    System.currentTimeMillis() - startTime);
            return true;

        } catch (InterruptedException e) {
            // 处理中断请求
            System.out.printf("[%s] 任务 %s 被中断%n",
                    Thread.currentThread().getName(), id);
            Thread.currentThread().interrupt(); // 重置中断状态
            return false;

        } catch (Exception e) {
            // 处理其他异常
            System.err.printf("[%s] 任务 %s 处理失败: %s%n",
                    Thread.currentThread().getName(),
                    id, e.getMessage());
            return false;
        }

    }
}
