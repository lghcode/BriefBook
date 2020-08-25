package com.lghcode.briefbook.task;

import com.lghcode.briefbook.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author lgh
 * @Date 2020/8/25 21:23
 */
@Component
@Slf4j
public class RecycleArticleTask {

    @Autowired
    private ArticleService articleService;

    /**
     * 每天对回收站的文章的剩余天数减少1天，如果剩余天数为零，则彻底删除该文章
     * 每天凌晨0点1分执行
     */
    @Scheduled(cron = "0 1 1 * * ?")
    public void task(){
        log.info("定时任务开启...");
        articleService.runRecycleArticleTask();
        log.info("定时任务完成");
    }
}
