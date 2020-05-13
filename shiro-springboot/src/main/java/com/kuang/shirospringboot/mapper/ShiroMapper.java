package com.kuang.shirospringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.shirospringboot.pojo.Shiro;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiroMapper extends BaseMapper<Shiro> {

}
