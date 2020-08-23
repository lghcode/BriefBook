package com.lghcode.briefbook.task;

import com.lghcode.briefbook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author lgh
 * @Date 2020/8/23 21:10
 */
@Component
@Slf4j
public class UserDiamondTask {

    @Autowired
    private UserService userService;

    /**
     * 清算每个用户的简钻余额
     * 每天凌晨1点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        log.info("定时任务开启...");
        userService.settleDiamondTask();
        log.info("定时任务完成");
    }
}
