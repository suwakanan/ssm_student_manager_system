package com.system.mapper;

import com.system.po.CourseCustom;
import com.system.po.PagingVO;

import java.util.List;


public interface CourseMapperCustom {

    // 分页查询课程信息
    List<CourseCustom> findByPaging(PagingVO pagingVO) throws Exception;

}
