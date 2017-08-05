package com.baili.dao;

import com.baili.entity.QuartzJob;
import com.baili.entity.QuartzJobCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QuartzJobMapper {
    long countByExample(QuartzJobCriteria example);

    int deleteByExample(QuartzJobCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(QuartzJob record);

    int insertSelective(QuartzJob record);

    List<QuartzJob> selectByExampleWithRowbounds(QuartzJobCriteria example, RowBounds rowBounds);

    List<QuartzJob> selectByExample(QuartzJobCriteria example);

    QuartzJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QuartzJob record, @Param("example") QuartzJobCriteria example);

    int updateByExample(@Param("record") QuartzJob record, @Param("example") QuartzJobCriteria example);

    int updateByPrimaryKeySelective(QuartzJob record);

    int updateByPrimaryKey(QuartzJob record);
}