package com.atguigu.system.mapper;

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
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据userid查询菜单权限数据
     * @param userId
     * @return
     */
    List<SysMenu> findMenuListByUserId(@Param("userId") String userId);
}
