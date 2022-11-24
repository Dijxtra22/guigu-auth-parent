package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.system.mapper.LoginLogMapper;
import com.atguigu.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
