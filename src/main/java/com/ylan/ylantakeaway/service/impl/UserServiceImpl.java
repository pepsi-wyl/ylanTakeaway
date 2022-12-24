package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.entity.User;
import com.ylan.ylantakeaway.mapper.UserMapper;
import com.ylan.ylantakeaway.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author by ylan
 * @date 2022-12-23 21:06
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
