package com.ylan.ylantakeaway.controller;

import com.ylan.ylantakeaway.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by pepsi-wyl
 * @date 2022-12-24 17:02
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @GetMapping("/list")
    public R<String> list() {
        return null;
    }

}
