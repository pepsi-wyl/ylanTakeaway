package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ylan.ylantakeaway.common.BaseContext;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.AddressBook;
import com.ylan.ylantakeaway.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿管理控制器
 *
 * @author by ylan
 * @date 2022-12-24 11:23
 */

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询指定用户的全部地址
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        // 获取用户ID
        Long userID = BaseContext.getCurrentId();
        log.info("查询用户收货地址{}", userID);

        // 条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userID != null, AddressBook::getUserId, userID).orderByDesc(AddressBook::getUpdateTime);
        // 查询
        List<AddressBook> list = addressBookService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 添加地址信息
     *
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> addAddressBook(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("添加地址信息{}", addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 获取地址信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getAddressBook(@PathVariable Long id) {
        log.info("获取地址信息{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return (addressBook != null) ? R.success(addressBook) : R.error("没有找到该对象");
    }

    /**
     * 修改地址信息
     *
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> updateAddressBook(@RequestBody AddressBook addressBook) {
        log.info("修改地址信息{}", addressBook);
        boolean flag = addressBookService.updateById(addressBook);
        return flag == true ? R.success("修改成功") : R.error("修改失败");
    }

    /**
     * 删除地址信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteAddressBook(@RequestParam("ids") Long ids) {
        log.info("删除地址信息{}", ids);
        boolean flag = addressBookService.removeById(ids);
        return flag == true ? R.success("删除成功") : R.error("删除失败");
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> defaultAddressBook(@RequestBody AddressBook addressBook) {
        log.info("设置位默认地址", addressBook.getId());
        return addressBookService.defaultAddressBook(addressBook);
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        log.info("查询默认地址");
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId()).eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        return (addressBook != null) ? R.success(addressBook) : R.error("没有找到该对象");
    }

}