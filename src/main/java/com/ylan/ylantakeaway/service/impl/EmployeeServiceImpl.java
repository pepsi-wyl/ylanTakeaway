package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.Employee;
import com.ylan.ylantakeaway.mapper.EmployeeMapper;
import com.ylan.ylantakeaway.service.EmployeeService;
import com.ylan.ylantakeaway.utils.EmployeeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author by ylan
 * @date 2022-12-17 10:32
 */

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    /**
     * 员工登陆服务层
     *
     * @return R
     */
    @Override
    public R<Employee> login(Employee employee) {

        /**
         * 根据员工名进行查询用户
         */
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee selectEmployee = this.getOne(queryWrapper);
        log.info(selectEmployee.toString());

        /**
         * 判断查询员工是否为空
         */
        if (Objects.isNull(selectEmployee)) {
            return R.error("登陆失败!");
        }

        /**
         * 比较查询员工的密码
         */
        String md5Password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if (!Objects.equals(selectEmployee.getPassword(), md5Password)) {
            return R.error("登陆失败!");
        }

        /**
         * 比较查询员工的状态
         */
        if (selectEmployee.getStatus() == 0) {
            return R.error("账号被禁用!");
        }

        /**
         * 登陆成功放入session中
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("employee", selectEmployee.getId());

        // 返回员工对象
        return R.success(selectEmployee);
    }

    /**
     * 员工退出登陆服务层
     *
     * @return R
     */
    @Override
    public R<String> logout() {

        /**
         * 退出登陆移除session数据
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute("employee");

        // 返回成功信息
        return R.success("退出成功");
    }

    /**
     * 添加员工服务层
     *
     * @param employee
     * @return
     */
    @Override
    public R<String> addEmployee(Employee employee) {
        /**
         * 密码加密
         */
        EmployeeUtils.setMd5Password(employee);

        /**
         * 保存数据
         */
        this.save(employee);
        return R.success("添加员工成功");
    }

    /**
     * 分页查询员工服务层
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Page<Employee>> pageList(int page, int pageSize, String name) {
        // 分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);

        // 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        // 添加过滤条件 name不为空的时候进行like查询
        queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        this.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 禁用员工账号
     * 编辑员工信息
     *
     * @param employee
     * @return
     */
    @Override
    public R<String> updateEmploy(Employee employee) {
        this.updateById(employee);
        return R.success("员工修改成功");
    }
}