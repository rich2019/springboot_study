package com.kuang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 陈东东
 * @date 2020/11/26 16:45
 * @description 一句話描述用途
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {

    private String name;
    private int age;

}
