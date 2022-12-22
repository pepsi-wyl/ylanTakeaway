package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.Employee;
import com.ylan.ylantakeaway.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工控制器
 *
 * @author by ylan
 * @date 2022-12-17 10:37
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /**
     * 注入服服务层类
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登陆
     *
     * @param employee @RequestBody 接受前端json数据
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee) {
        log.info("员工登陆" + employee.toString());
        return employeeService.login(employee);
    }

    /**
     * 员工退出
     *
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout() {
        log.info("员工退出");
        return employeeService.logout();
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee) {
        log.info("添加员工" + employee.toString());
        return employeeService.addEmployee(employee);
    }

    /**
     * 分页查询员工
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> pageList(
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "name", required = false) String name) {
        log.info("查询员工page={},pageSize={},name={}", page, pageSize, name);
        return employeeService.pageList(page, pageSize, name);
    }

    /**
     * 禁用员工账号
     * 编辑员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> updateEmploy(@RequestBody Employee employee) {
        log.info("更新员工" + employee.toString());
        return employeeService.updateEmploy(employee);
    }

    /**
     * 根据ID查询员工
     *
     * @param id
     * @return
     */
    @GetMapping({"/{id}"})
    public R<Employee> getEmploy(@PathVariable Long id) {
        log.info("查询员工" + id);
        Employee employee = employeeService.getById(id);
        return employee != null ? R.success(employee) : R.error("没有查询到对于员工");
    }

}