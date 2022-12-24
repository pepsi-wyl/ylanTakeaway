package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-24 11:19
 */

@Mapper
@Transactional
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
