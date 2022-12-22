package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-17 10:27
 */

@Mapper
@Transactional
public interface EmployeeMapper extends BaseMapper<Employee> {

}