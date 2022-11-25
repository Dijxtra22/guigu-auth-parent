package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.vo.SysLoginLogQueryVo;
import com.atguigu.system.mapper.LoginLogMapper;
import com.atguigu.system.service.LoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void recordLoginLog(final String username, final Integer status, final String ipaddr, final String message) {
        SysLoginLog log = new SysLoginLog();
        log.setUsername(username);
        log.setStatus(status);
        log.setIpaddr(ipaddr);
        log.setMsg(message);
        loginLogMapper.insert(log);
    }

    @Override
    public IPage<SysLoginLog> selectPage(final Page<SysLoginLog> pageParam, final SysLoginLogQueryVo sysLoginLogQueryVo) {
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.like("username", username);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }

        Page<SysLoginLog> sysLoginLogPage = loginLogMapper.selectPage(pageParam, wrapper);
        return sysLoginLogPage;
    }

    @Override
    public SysLoginLog getById(final Long id) {
        return loginLogMapper.selectById(id);
    }
}
