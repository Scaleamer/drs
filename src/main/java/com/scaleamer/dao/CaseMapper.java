package com.scaleamer.dao;

import com.scaleamer.domain.Case;
import com.scaleamer.domain.statistics.StatisticsPerPlaceDay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface CaseMapper {
    //增加案例
    public int addCase(Case the_case);

    //删除案例
    public int deleteCase(@Param("case_id") int case_id);

    //修改案例
    public int updateCase(@Param("case") Case the_case);

    //单案例查询
    public Case findCase(int case_id);

    //多案例查询--按查询条件查询
    public List<Case> findMultiCase(Map<String, String> map);

    //全案例查询
    public List<Case> findAllCase();

    List<StatisticsPerPlaceDay> getStatistics();
}
