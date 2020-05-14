package com.kuang.shirospringboot.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shiro {
    @TableId(type = IdType.INPUT)  //对应数据库中的自增  默认为ID_WORKER, 全局唯一,雪花算法
    private Long id;
    private String username;
    private String password;
    private String perms;

}
