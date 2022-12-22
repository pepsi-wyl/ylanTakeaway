package com.ylan.ylantakeaway.utils;

import com.ylan.ylantakeaway.entity.Employee;
import org.springframework.util.DigestUtils;

/**
 * @author by pepsi-wyl
 * @date 2022-12-17 18:05
 */

public class EmployeeUtils {

    /**
     * 将身份证后六位设置初始密码，并进行md5加密
     *
     * @param employee
     */
    public static void setMd5Password(Employee employee) {
        // 得到后六位
        String subIdNumber = StringUtils.substring(employee.getIdNumber(), 12);
        // MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(subIdNumber.getBytes());
        // 设置
        employee.setPassword(md5Password);
    }
}
