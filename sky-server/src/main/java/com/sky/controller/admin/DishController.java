package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return Result.success();
    }

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
        dishService.deleteBatch(ids);//后绪步骤实现
        return Result.success();
    }

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
        return Result.success();
    }
}
