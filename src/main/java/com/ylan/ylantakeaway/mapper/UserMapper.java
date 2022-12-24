package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-23 21:03
 */

@Mapper
@Transactional
public interface UserMapper extends BaseMapper<User> {

}
