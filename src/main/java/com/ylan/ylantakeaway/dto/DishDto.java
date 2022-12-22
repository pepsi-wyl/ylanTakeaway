package com.ylan.ylantakeaway.dto;

import com.ylan.ylantakeaway.entity.Dish;
import com.ylan.ylantakeaway.entity.DishFlavor;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ylan
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
