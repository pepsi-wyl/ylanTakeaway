package com.ylan.ylantakeaway.dto;

import com.ylan.ylantakeaway.entity.Setmeal;
import com.ylan.ylantakeaway.entity.SetmealDish;
import lombok.*;

import java.util.List;

/**
 * @author ylan
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}