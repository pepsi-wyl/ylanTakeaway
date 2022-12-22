package com.ylan.ylantakeaway.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.Employee;

/**
 * @author by ylan
 * @date 2022-12-17 10:27
 */

public interface EmployeeService extends IService<Employee> {

    /**
     * 员工登陆
     *
     * @param employee
     * @return
     */
    R<Employee> login(Employee employee);

    /**
     * 员工退出
     *
     * @return
     */
    R<String> logout();

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    R<String> addEmployee(Employee employee);

    /**
     * 分页查询员工
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    R<Page<Employee>> pageList(int page, int pageSize, String name);

    /**
     * 禁用员工账号
     * 编辑员工信息
     *
     * @param employee
     * @return
     */
    R<String> updateEmploy(Employee employee);

}