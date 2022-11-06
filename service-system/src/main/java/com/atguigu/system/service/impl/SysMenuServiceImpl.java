package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.utils.MenuHelper;
import com.atguigu.system.utils.RouterHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-04
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        List<SysMenu> resultList = MenuHelper.buildTree(sysMenus);
        return resultList;
    }

    @Override
    public void removeMenuById(final String id) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer cnt = baseMapper.selectCount(wrapper);
        if(cnt > 0){
            throw new GuiguException(201, "请先删除子菜单");
        }
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(final String roleId) {
        // 获取所有菜单 status=1
        QueryWrapper<SysMenu> menuWrapper = new QueryWrapper<>();
        menuWrapper.eq("status", 1);
        List<SysMenu> menuList = baseMapper.selectList(menuWrapper);

        // 根据roleId查询 角色分配过的菜单列表
        QueryWrapper<SysRoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.eq("role_id", roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(roleMenuWrapper);

        // 从菜单列表中获取角色分配所有菜单id
        Set<String> menuIdSet = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());

        // 数据处理 isSelect 如果菜单选中true，否则false
        menuList.forEach(menu -> menu.setSelect(menuIdSet.contains(menu.getId())));

        // 转换成树形结构
        return MenuHelper.buildTree(menuList);
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (String menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                //添加新权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    @Override
    public List<RouterVo> getUserMenuList(final String userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if ("1".equals(userId)) {
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
        } else {
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }
        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        //构建路由
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;
    }

    @Override
    public List<String> getUserButtonList(final String userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if ("1".equals(userId)) {
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
        } else {
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }
        List<String> permissionList = sysMenuList.stream().filter(sysMenu -> sysMenu.getType() == 2).map(SysMenu::getPerms).collect(Collectors.toList());
        return permissionList;
    }
}
