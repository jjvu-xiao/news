package com.pgl.xiao.dao;

import com.pgl.xiao.domain.Me;
import com.pgl.xiao.domain.MeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MeMapper {
    long countByExample(MeExample example);

    int deleteByExample(MeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Me record);

    int insertSelective(Me record);

    List<Me> selectByExample(MeExample example);

    Me selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Me record, @Param("example") MeExample example);

    int updateByExample(@Param("record") Me record, @Param("example") MeExample example);

    int updateByPrimaryKeySelective(Me record);

    int updateByPrimaryKey(Me record);
}