package com.atguigu.system;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void findAll(){
        System.out.println(sysRoleMapper.selectList(null));
    }

    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色姓名");
        sysRole.setRoleCode("testManager");
        sysRole.setDescription("测试角色姓名");
        final int rows = sysRoleMapper.insert(sysRole);
        System.out.println("-----》 成功插入条数:" + rows);
        System.out.println("-----》 id自动回填:" + sysRole.getId());
    }

    @Test
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId(1586544478076751874L);
        sysRole.setRoleName("测试角色11111");

        int result = sysRoleMapper.updateById(sysRole);
        System.out.println(result);
    }

    @Test
    public void testDeleteById(){
        int result = sysRoleMapper.deleteById(1586544478076751874L);
        System.out.println(result);
    }

    @Test
    public void testDeleteBatchIds() {
        int result = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(result);
    }

    @Test
    public void testQueryWrapper() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("description", "管理员");
        List<SysRole> users = sysRoleMapper.selectList(queryWrapper);
        System.out.println(users);
    }
}
