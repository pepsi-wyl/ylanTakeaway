package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.common.BaseContext;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.AddressBook;
import com.ylan.ylantakeaway.mapper.AddressBookMapper;
import com.ylan.ylantakeaway.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-24 11:21
 */

@Slf4j
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @Transactional
    @Override
    public R<AddressBook> defaultAddressBook(AddressBook addressBook) {
        // 全部更新为0
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId()).set(AddressBook::getIsDefault, 0);
        this.update(wrapper);
        // 设置默认地址
        addressBook.setIsDefault(1);
        this.updateById(addressBook);
        return R.success(addressBook);
    }

}
