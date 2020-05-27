package com.system.service;

import com.system.po.College;
import com.system.po.CollegeCustom;

import java.util.List;


public interface CollegeService {

    List<College> finAll() throws Exception;

    // 2020.05.26新增内容
    int getCountCollege();
    List<CollegeCustom> findByPaging(Integer toPageNo);

    // 2020.05.27新增内容
    // 保存院系信息
    Boolean save(CollegeCustom collegeCustoms) throws Exception;
    //boolean reg(College college);
    // 添加院系信息
    void updateById(Integer id, College college);
    // 修改院系信息
    College findById(Integer id) throws Exception;
    // 删除院系信息
    void removeById(Integer id);

}
