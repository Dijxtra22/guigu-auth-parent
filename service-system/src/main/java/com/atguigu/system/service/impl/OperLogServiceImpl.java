package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysOperLog;
import com.atguigu.model.vo.SysOperLogQueryVo;
import com.atguigu.system.mapper.OperLogMapper;
import com.atguigu.system.service.OperLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public void saveSysLog(final SysOperLog sysOperLog) {
        operLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SysOperLog> selectPage(final Page<SysOperLog> pageParam, final SysOperLogQueryVo sysOperLogQueryVo) {
        String operName = sysOperLogQueryVo.getOperName();
        String title = sysOperLogQueryVo.getTitle();
        String createTimeBegin = sysOperLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysOperLogQueryVo.getCreateTimeEnd();
        QueryWrapper<SysOperLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(operName)) {
            wrapper.like("oper_name", operName);
        }
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }

        Page<SysOperLog> sysOperLogPage = operLogMapper.selectPage(pageParam, wrapper);
        return sysOperLogPage;
    }

    @Override
    public SysOperLog getById(final Long id) {
        return operLogMapper.selectById(id);
    }
}
