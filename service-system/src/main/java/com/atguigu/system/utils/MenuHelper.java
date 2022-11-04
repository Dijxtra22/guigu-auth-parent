package com.atguigu.system.utils;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    public static List<SysMenu> buildTree(final List<SysMenu> sysMenus) {
        List<SysMenu> trees = new ArrayList<>();
        for(SysMenu sysMenu : sysMenus){
            if("0".equals(sysMenu.getParentId())){
                trees.add(findChildren(sysMenu, sysMenus));
            }
        }
        return trees;
    }

    /**
     * 从根节点进行递归查询，查询子节点
     */
    private static SysMenu findChildren(final SysMenu root, final List<SysMenu> sysMenus) {
        for(SysMenu sysMenu : sysMenus){
            if(root.getId().equals(sysMenu.getParentId())){
                if(root.getChildren() == null){
                    root.setChildren(new ArrayList<>());
                }
                root.getChildren().add(findChildren(sysMenu, sysMenus));
            }
        }
        return root;
    }
}
