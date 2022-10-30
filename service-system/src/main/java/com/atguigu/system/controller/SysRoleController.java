package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("findAll")
    public Result findAll(){
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    @ApiOperation(value = "根据id删除角色")
    @DeleteMapping("remove/{id}")
    public Result removeRole(@PathVariable Long id){
        boolean isSuccess = sysRoleService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail();
    }



}
