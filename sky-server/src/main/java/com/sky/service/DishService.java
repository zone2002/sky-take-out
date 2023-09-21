package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

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
}
