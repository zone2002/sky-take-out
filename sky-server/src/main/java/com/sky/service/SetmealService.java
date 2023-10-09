package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import java.util.List;

public interface SetmealService {

    /**
     * 条件查询
     * @param setmeal 套餐对象
     * @return 套餐中包含的菜品
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id 套餐id
     * @return 套餐中包含的菜品VO
     */
    List<DishItemVO> getDishItemById(Long id);

    /**
     * 分页查询
     * @param setmealPageQueryDTO 套餐DTO
     * @return 分页查询结果
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 保存套餐
     * @param setmealDTO 要保存的套餐DTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 批量删除套餐
     * @param ids 套餐id列表
     */
    void deleteBatch(List<Long> ids);

    /**
     * 更新套餐
     * @param setmealDTO 要更新的套餐DTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 套餐起售停售
     * @param status 下一次更改后的状态
     * @param id 套餐id
     */
    void startOrStop(Integer status, Long id);
}
