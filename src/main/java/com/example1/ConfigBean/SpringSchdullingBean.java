package com.example1.ConfigBean;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：
 * 作者: TWL
 * 创建日期: 2017/5/18
 */

@Component
@Configurable
@EnableScheduling
public class SpringSchdullingBean {

    public void work(){
        //这儿插入具体的调度任务..
        /*System.out.println ("AAAA任务执行，时间:" + dateFormat ().format (new Date ()));*/
    }

    /*@Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("任务执行开始，时间: " + dateFormat ().format (new Date()));
    }*/

    //每1分钟执行一次
    @Scheduled(cron = "0 1 *  * * * ")
    public void reportCurrentByCron(){
        System.out.println ("cron任务执行开始，时间:" + dateFormat ().format (new Date ()));
    }
    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }
}

