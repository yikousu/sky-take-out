package com.sky.task;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 自定义定时任务类
 */
//@Component
@Slf4j
public class MyTask {

    /**
     * 定时任务 每隔5秒触发一次
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Scheduled(cron = "0/5 * * * * ?")
    public void executeTask(){
        log.info("定时任务开始执行：{}",new Date());
    }


}
