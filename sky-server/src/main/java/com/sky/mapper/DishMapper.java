package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
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
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);


    /**
     * 插入菜品数据
     *
     * @param dish 要插入的菜品
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 根据id查找菜品
     * @param id 要查找的id
     * @return 查询到的菜品
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据id删除菜品数据
     *
     * @param id 要删除的id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);
}
