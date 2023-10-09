package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 菜品管理
 */

@Slf4j
@RestController
@Api(tags = "菜品管理")
@RequestMapping("/admin/dish")
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


    /**
     * 根据id查询菜品
     *
     * @param id id
     * @return 菜品VO
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);//后绪步骤实现
        return Result.success(dishVO);
    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO 分页查询DTO
     * @return 查询到的数据
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQurey(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 启用禁用菜品
     * @param status 要更改的状态
     * @param id id
     * @return 成功返回
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用菜品")
    public Result<Void> startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用菜品:{},{}",status,id);
        dishService.startOrStop(status, id);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 新增菜品
     *
     * @param dishDTO 饭菜DTO
     * @return 成功返回
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result<Void> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        String key = "dish_" + dishDTO.getId();
        cleanCache(key);

        return Result.success();
    }
//    public Result<Void> save(@RequestBody DishDTO dishDTO) {
//        log.info("新增菜品：{}", dishDTO);
//        dishService.saveWithFlavor(dishDTO);
//        return Result.success();
//    }

    /**
     * 菜品批量删除
     *
     * @param ids 菜品id列表
     * @return 成功返回
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result<Void> delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}", ids);
        dishService.deleteBatch(ids);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }


//    public Result<Void> delete(@RequestParam List<Long> ids) {
//        log.info("菜品批量删除：{}", ids);
//        dishService.deleteBatch(ids);//后绪步骤实现
//        return Result.success();
//    }

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品DTO
     * @return 成功
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result<Void> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 清理缓存数据
     * @param pattern 通配符
     */
    //TODO: 后期优化缓存清理方式
    private void cleanCache(String pattern){
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }
}
