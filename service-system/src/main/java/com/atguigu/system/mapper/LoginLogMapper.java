package com.atguigu.system.mapper;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-11-04
 */
@Repository
@Mapper
public interface LoginLogMapper extends BaseMapper<SysLoginLog> {

}
