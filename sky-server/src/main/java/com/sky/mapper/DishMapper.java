package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId 分类id
     * @return 查询结果
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 分页查询
     * @param dishPageQueryDTO 分页查询DTO
     * @return 查询到的结果
     */
    Page<Dish> findByPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 更新
     * @param dish 更新的数据对象
     */
    void update(Dish dish);
}
