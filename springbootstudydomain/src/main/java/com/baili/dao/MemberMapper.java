package com.baili.dao;

import com.baili.entity.Member;
import com.baili.entity.MemberCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberMapper {
    long countByExample(MemberCriteria example);

    int deleteByExample(MemberCriteria example);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExampleWithRowbounds(MemberCriteria example, RowBounds rowBounds);

    List<Member> selectByExample(MemberCriteria example);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberCriteria example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberCriteria example);
}