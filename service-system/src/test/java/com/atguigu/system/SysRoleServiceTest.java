package com.atguigu.system;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void findAll(){
        System.out.println(sysRoleService.list());
    }

    @Test
    public void testInsert(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员test");
        sysRole.setRoleCode("role test");
        sysRole.setDescription("角色管理员test");

        boolean result = sysRoleService.save(sysRole);
        System.out.println(result); //成功还是失败
        System.out.println(sysRole.getId());
    }

    @Test
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId(String.valueOf(1586564425792233473L));
        sysRole.setRoleName("角色管理员test1111");

        boolean result = sysRoleService.updateById(sysRole);
        System.out.println(result);
    }

    @Test
    public void testDeleteById(){
        boolean result = sysRoleService.removeById(1586564425792233473L);
        System.out.println(result);
    }

    @Test
    public void testQueryWrapper() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code", "SYSTEM");
        List<SysRole> users = sysRoleService.list(queryWrapper);
        System.out.println(users);
    }
}
