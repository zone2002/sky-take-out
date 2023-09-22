package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO 员工DTO
     * @return 登录的员工
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新建用户
     * @param employeeDTO 用户DTO
     */
    void addEmp(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO 查询DTO
     * @return 分页查询结果
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status 当前状态
     * @param id    要操作的id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 编辑员工信息
     * @param employeeDTO 员工DTO
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 根据id查找员工
     * @param id 要查找的员工id
     * @return 找到的员工
     */
    Employee getById(Long id);


}
