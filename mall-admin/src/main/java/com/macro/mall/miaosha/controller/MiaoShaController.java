package com.macro.mall.miaosha.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dao.PmsSkuStockDao;
import com.macro.mall.mapper.PmsSkuStockMapper;
import com.macro.mall.model.PmsSkuStock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MiaoShaController {

    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @GetMapping("/miaosha/test1/{skuId}")
    public CommonResult<String> test1(@PathVariable("skuId") Long skuId) {
        PmsSkuStock pmsSkuStock = pmsSkuStockMapper.selectByPrimaryKey(skuId);
        Integer stock = pmsSkuStock.getStock();
        if (stock < 1) {
            return CommonResult.success("秒杀失败！");
        }
        System.out.println("查询库存" + stock);
        pmsSkuStock.setStock(stock - 1);
        pmsSkuStockMapper.updateByPrimaryKey(pmsSkuStock);
        return CommonResult.success("秒杀成功！");
    }


    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/yure/{skuId}")
    public CommonResult<String> yure(@PathVariable("skuId") Long skuId) {
        RSemaphore lock = redissonClient.getSemaphore("lock");
        PmsSkuStock pmsSkuStock = pmsSkuStockMapper.selectByPrimaryKey(skuId);
        boolean b = lock.trySetPermits(pmsSkuStock.getStock());
        return b ? CommonResult.success("预热成功") : CommonResult.failed("预热失败");
    }

    @GetMapping("/miaosha/test2/{skuId}")
    public CommonResult<String> test2(@PathVariable("skuId") Long skuId) throws InterruptedException {
        RSemaphore lock = redissonClient.getSemaphore("lock");
        boolean b = lock.tryAcquire(1);
        if (b) {
            System.out.println("减一个信号成功");
            System.out.println("发送给mq去处理把");
            return CommonResult.success("秒杀成功");
        } else {
            return CommonResult.failed("秒杀失败");
        }
    }
}
