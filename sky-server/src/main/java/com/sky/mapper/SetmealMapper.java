package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id 分类id
     * @return 套餐的数量
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 动态条件查询套餐
     * @param setmeal 条件对象
     * @return 符合条件的结果
     */
    List<Setmeal> list(Setmeal setmeal);
	
	/**
     * 根据套餐id查询菜品选项
     * @param setmealId 套餐id
     * @return 套餐内菜品
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);


    /**
     * 分页查询
     * @param setmealPageQueryDTO 套餐DTO
     * @return 分页查询结果
     */
    Page<Dish> findByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 插入套餐
     * @param setmeal 要插入的套餐
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
}
