package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.User;
import com.ylan.ylantakeaway.service.UserService;
import com.ylan.ylantakeaway.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author by ylan
 * @date 2022-12-23 21:10
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user) {
        // 获取手机号码
        String phoneNumber = user.getPhone();
        if (phoneNumber != null) {
            // 随机生成4位验证码
            String code = SMSUtils.generateValidateCode(4).toString();
            log.info("发送验证码={}", code);

            // 发送验证码
            //  SMSUtils.sendMessage(phone, code);

            // 设置到Session域当中
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute(phoneNumber, code);

            // 返回前端
            return R.success("短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * 用户登陆
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取参数
        String phoneNumber = user.get("phone");
        String code = user.get("code");
        String sessionCode = (String) request.getSession().getAttribute(phoneNumber);
        log.info("用户登陆 phoneNumber={},code={},sessionCode={}", phoneNumber, code, sessionCode);

        // 验证码比对
        if (sessionCode != null && sessionCode.equals(code)) {
            // 查询是否为新用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phoneNumber);
            User u = userService.getOne(queryWrapper);
            // 新用户
            if (u == null) {
                u = new User();
                u.setPhone(phoneNumber);
                u.setStatus(1);
                userService.save(u);
            }

            // 保存对象到Session域中
            request.getSession().setAttribute("user", u.getId());

            return R.success(u);
        }
        return R.error("登陆失败");
    }

}
