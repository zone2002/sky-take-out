package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 分页查询
     * @param dishPageQueryDTO 分页查询DTO
     * @return 分页查询结果
     */
    PageResult pageQurey(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 启用禁用菜品
     * @param status 要更改的状态
     * @param id id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 新增菜品和对应的口味
     *
     * @param dishDTO 菜品DTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品批量删除
     *
     * @param ids id列表
     */
    void deleteBatch(List<Long> ids);


    /**
     * 根据id查询菜品和对应的口味数据
     * @param id id
     * @return 查询到的菜品VO
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO 菜品DTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 条件查询菜品和口味
     * @param dish 菜品
     * @return 菜品VO
     */
    List<DishVO> listWithFlavor(Dish dish);
}
