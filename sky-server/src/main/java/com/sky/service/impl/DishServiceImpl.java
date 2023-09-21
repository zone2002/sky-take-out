package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;

    public DishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    /**
     * 分页查询
     * @param dishPageQueryDTO 分页查询DTO
     * @return 分页查询结果
     */
    @Override
    public PageResult pageQurey(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> pageResult = dishMapper.findByPage(dishPageQueryDTO);
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }

    /**
     * 启用禁用菜品
     * @param status 要更改的状态
     * @param id id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        dishMapper.update(dish);

    }
}
