package com.ylan.ylantakeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.AddressBook;

/**
 * @author by ylan
 * @date 2022-12-24 11:21
 */

public interface AddressBookService extends IService<AddressBook> {

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    R<AddressBook> defaultAddressBook(AddressBook addressBook);

}
