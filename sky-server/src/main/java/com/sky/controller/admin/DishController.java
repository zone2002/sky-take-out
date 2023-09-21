package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品管理
 */

@Slf4j
@RestController
@Api(tags = "菜品管理")
@RequestMapping("/admin/dish")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
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
     * @return 成功
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用菜品")
    public Result<Void> startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用菜品:{},{}",status,id);
        dishService.startOrStop(status, id);
        return Result.success();
    }
}
