package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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

}
