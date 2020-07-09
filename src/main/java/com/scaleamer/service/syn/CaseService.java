package com.scaleamer.service.syn;

import com.scaleamer.domain.Case;
import com.scaleamer.domain.User;

import java.util.List;
import java.util.Map;

public interface CaseService {

    //增加案例
    public void addCase(Case the_case);

    //删除案例
    public void deleteCase(int case_id);

    //修改案例
    public void updateCase(Case the_case);

    //单案例查询
    public Case findCase(int case_id);

    //多案例查询--按查询条件查询
    public List<Case> findMultiCase(Map<String, String> map);

    //全案例查询
    public List<Case> findAllCase();
}
