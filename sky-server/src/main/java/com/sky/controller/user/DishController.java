package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    private final DishService dishService;
    private final RedisTemplate<String, List<DishVO>> redisTemplate;

    public DishController(DishService dishService, RedisTemplate<String, List<DishVO>> redisTemplate) {
        this.dishService = dishService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 该分类下的菜品列表
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        //1. 构造redis的key，规则：dish_(分类id)
        String key = "dish_" + categoryId;
        //2. 如存在，直接返回
        List<DishVO> list =  redisTemplate.opsForValue().get(key);
        if(list != null && !list.isEmpty()){
            return Result.success(list);
        }


        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
        list = dishService.listWithFlavor(dish);

        //4. 如不存在，添加到缓存中
        redisTemplate.opsForValue().set(key, list, 5, TimeUnit.MINUTES);

        return Result.success(list);
    }

//    /**
//     * 根据分类id查询菜品
//     *
//     * @param categoryId 分类id
//     * @return 该分类下的菜品列表
//     */
//    @GetMapping("/list")
//    @ApiOperation("根据分类id查询菜品")
//    public Result<List<DishVO>> list(Long categoryId) {
//        Dish dish = new Dish();
//        dish.setCategoryId(categoryId);
//        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
//
//        List<DishVO> list = dishService.listWithFlavor(dish);
//
//        return Result.success(list);
//    }


}
