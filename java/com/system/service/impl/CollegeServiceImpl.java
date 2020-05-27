package com.system.service.impl;

import com.system.mapper.CollegeMapper;
import com.system.mapper.CollegeMapperCustom;
import com.system.po.College;
import com.system.po.CollegeCustom;
import com.system.po.CollegeExample;
import com.system.po.PagingVO;
import com.system.service.CollegeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    // 2020.05.26新增内容
    @Autowired
    private CollegeMapperCustom collegeMapperCustom;

    public List<College> finAll() throws Exception {
        CollegeExample collegeExample = new CollegeExample();
        CollegeExample.Criteria criteria = collegeExample.createCriteria();

        criteria.andCollegeidIsNotNull();


        return collegeMapper.selectByExample(collegeExample);
    }


    // 2020.05.26新增内容

    @Override
    public int getCountCollege() {
        //自定义查询对象
        CollegeExample courseExample = new CollegeExample();
        //通过criteria构造查询条件
        CollegeExample.Criteria criteria = courseExample.createCriteria();
        criteria.andCollegenameIsNotNull();

        return collegeMapper.countByExample(courseExample);
    }

    @Override
    public List<CollegeCustom> findByPaging(Integer toPageNo) {
        PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(toPageNo);

        List<CollegeCustom> list = collegeMapperCustom.findByPaging(pagingVO);
        return list;
    }


    // 2020.05.27新增内容

    // 添加院系
    @Override
    public Boolean save(CollegeCustom collegeCustoms) throws Exception {
        College college = collegeMapper.selectByPrimaryKey(collegeCustoms.getCollegeid());
        System.out.println(collegeCustoms.getCollegeid());
        if (college==null) {
            collegeMapper.insert(collegeCustoms);
            return true;
        }
        return false;
    }

    @Override
    public College findById(Integer id) throws Exception {

        College college = collegeMapper.selectByPrimaryKey(id);
        CollegeCustom collegeCustom = null;
        if (college != null) {
            collegeCustom = new CollegeCustom();
            BeanUtils.copyProperties(college, collegeCustom);
        }
        return collegeCustom;
    }

    /*
    public StudentCustom findById(Integer id) throws Exception {

        Student student  = studentMapper.selectByPrimaryKey(id);
        StudentCustom studentCustom = null;
        if (student != null) {
            studentCustom = new StudentCustom();
            //类拷贝
            BeanUtils.copyProperties(student, studentCustom);
        }

        return studentCustom;
    }
     */

    @Override
    public void updateById(Integer id, College college) {
        collegeMapper.updateByPrimaryKey(college);
    }

    @Override
    public void removeById(Integer id) {
        collegeMapper.deleteByPrimaryKey(id);
    }

    /*
    public void updataById(Integer id, StudentCustom studentCustom) throws Exception {
        studentMapper.updateByPrimaryKey(studentCustom);
    }
     */

    /*
    @Override
    public boolean reg(College record) {
        College college = collegeMapper.selectByPrimaryKey(record.getCollegeid());
        try {
            collegeMapper.insert(record);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /*
    public Boolean save(StudentCustom studentCustoms) throws Exception {
        Student stu = studentMapper.selectByPrimaryKey(studentCustoms.getUserid());
        if (stu == null) {
            studentMapper.insert(studentCustoms);
            return true;
        }

        return false;
    }
     */
}
