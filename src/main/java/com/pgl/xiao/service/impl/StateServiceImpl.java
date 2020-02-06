package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.StateMapper;
import com.pgl.xiao.domain.State;
import com.pgl.xiao.service.StateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("stateService")
public class StateServiceImpl implements StateService {

    @Resource
    private StateMapper mapper;

    @Override
    public State findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(State type) {
        return false;
    }

    @Override
    public boolean update(State type) {
        return false;
    }


    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public PageInfo<State> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        return null;
    }


    @Override
    public List<State> findAll() {
        return mapper.selectByExample(null);
    }
}
