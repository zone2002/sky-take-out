package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    private final RedisTemplate<String, Integer> redisTemplate;

    public ShopController(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 设置店铺状态
     * @param status 要设置的状态 1:营业 0:已打烊
     * @return 统一返回结果
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result<Void> setStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为:{}",status == 1?"营业中":"已打烊");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }

    /**
     * 获取店铺的营业状态
     * @return 查询到的状态
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
        Integer status = redisTemplate.opsForValue().get(KEY);

        if(status == null){
            log.info("管理端查询店铺状态失败!");
            return Result.error(MessageConstant.STATUS_NOT_FIND);
        }

        log.info("管理端获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
