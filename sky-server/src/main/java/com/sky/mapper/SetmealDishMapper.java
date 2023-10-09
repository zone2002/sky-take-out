package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    /**
     * 批量插入套餐菜品对应表
     * @param setmealDishes 批量插入套餐菜品对应表
     */
    @AutoFill(value = OperationType.INSERT)
    void insertBatch(List<SetmealDish> setmealDishes);
}
