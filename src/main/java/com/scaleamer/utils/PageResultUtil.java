package com.scaleamer.utils;

import com.scaleamer.dto.PageResult;
import com.github.pagehelper.Page;

public class PageResultUtil {
    public static <E>PageResult<E> getPageResult(Page<E> page){
        PageResult<E> pageResult= new PageResult<>();
        pageResult.setList(page.getResult());
        pageResult.setTotal(page.getTotal());
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPages(page.getPages());
        pageResult.setPageSize(page.getPageSize());

        return pageResult;
    }
}
