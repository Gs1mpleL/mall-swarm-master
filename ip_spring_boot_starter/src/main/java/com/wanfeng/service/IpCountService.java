package com.wanfeng.service;

import com.wanfeng.properties.IpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IpCountService {
    private Map<String, Integer> ipCountMap = new HashMap<>();

    /**
     * 这个对象的注入工作由导入依赖的模块进行注入
     */
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IpProperties ipProperties;


    public void count() {
        String ip = request.getRemoteAddr();
        System.out.println("--------------------------" + ip + "--------------------------------");
        ipCountMap.put(ip, ipCountMap.getOrDefault(ip, 0) + 1);
    }

    @Scheduled(cron = "0/#{IpProperties.cycle} * * * * ?")
    public void show() {
        if (ipProperties.getModel().equals(IpProperties.LogModel.DETAIL.getValue())) {
            System.out.println("IP访问监控");
            System.out.println("+-----ipAddress-----+---Num---+");
            for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                String format = String.format("|%18s  |%5d  |", key, value);
                System.out.println(format);
            }
            System.out.println("+-------------------+---------+");
        } else if ((ipProperties.getModel().equals(IpProperties.LogModel.SIMPLE.getValue()))) {
            System.out.println("IP访问监控");
            System.out.println("+-----ipAddress-----+");
            Set<String> strings = ipCountMap.keySet();
            for (String string : strings) {
                String format = String.format("|%18s  |", string);
                System.out.println(format);
            }
            System.out.println("+-------------------+");
        }


        if (ipProperties.getCycleReset()) {
            ipCountMap.clear();
        }
    }




}
