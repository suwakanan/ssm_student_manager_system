package com.system.controller;

import com.system.exception.CustomException;
import com.system.po.*;
import com.system.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource(name = "studentServiceImpl")
    private StudentService studentService;

    @Resource(name = "teacherServiceImpl")
    private TeacherService teacherService;

    @Resource(name = "courseServiceImpl")
    private CourseService courseService;

    @Resource(name = "collegeServiceImpl")
    private CollegeService collegeService;

    @Resource(name = "userloginServiceImpl")
    private UserloginService userloginService;


    // 学生相关
    // 学生信息显示
    @RequestMapping("/showStudent")
    public String showStudent(Model model, Integer page) throws Exception {

        List<StudentCustom> list = null;
        // 页码对象
        PagingVO pagingVO = new PagingVO();
        // 设置总页数
        pagingVO.setTotalCount(studentService.getCountStudent());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = studentService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = studentService.findByPaging(page);
        }

        model.addAttribute("studentList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showStudent";

    }

    //  添加学生信息页面显示
    @RequestMapping(value = "/addStudent", method = {RequestMethod.GET})
    public String addStudentUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);

        return "admin/addStudent";
    }

     // 添加学生信息操作
    @RequestMapping(value = "/addStudent", method = {RequestMethod.POST})
    public String addStudent(StudentCustom studentCustom, Model model) throws Exception {

        Boolean result = studentService.save(studentCustom);

        if (!result) {
            model.addAttribute("message", "学号重复");
            return "error"; // 这里的返回是返回到error.jsp
        }
        // 添加成功后，也添加到登录表
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(studentCustom.getUserid().toString());

        // 这里可能有问题 2020.05.26
        // 其实是默认密码 123
        userlogin.setPassword("123");
        userlogin.setRole(2);
        userloginService.save(userlogin);

        // 重定向
        return "redirect:/admin/showStudent";
    }

    // 修改学生信息页面显示
    @RequestMapping(value = "/editStudent", method = {RequestMethod.GET})
    public String editStudentUI(Integer id, Model model) throws Exception {
        if (id == null) {
            // 加入没有带学生id就进来的话就返回学生显示页面
            return "redirect:/admin/showStudent";
        }
        StudentCustom studentCustom = studentService.findById(id);
        if (studentCustom == null) {
            throw new CustomException("未找到该名学生");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("student", studentCustom);


        return "admin/editStudent";
    }

    // 修改学生信息处理
    @RequestMapping(value = "/editStudent", method = {RequestMethod.POST})
    public String editStudent(StudentCustom studentCustom) throws Exception {

        studentService.updataById(studentCustom.getUserid(), studentCustom);

        // 重定向
        return "redirect:/admin/showStudent";
    }

    // 删除学生
    @RequestMapping(value = "/removeStudent", method = {RequestMethod.GET} )
    private String removeStudent(Integer id) throws Exception {
        if (id == null) {
            // 如果没带id参数的话返回去
            return "admin/showStudent";
        }
        studentService.removeById(id);
        userloginService.removeByName(id.toString());

        return "redirect:/admin/showStudent";
    }

    // 搜索学生
    @RequestMapping(value = "selectStudent", method = {RequestMethod.POST})
    private String selectStudent(String findByName, Model model) throws Exception {

        List<StudentCustom> list = studentService.findByName(findByName);

        model.addAttribute("studentList", list);
        return "admin/showStudent";
    }

    // 教师相关
    // 教师页面显示
    @RequestMapping("/showTeacher")
    public String showTeacher(Model model, Integer page) throws Exception {

        List<TeacherCustom> list = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(teacherService.getCountTeacher());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = teacherService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = teacherService.findByPaging(page);
        }

        model.addAttribute("teacherList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showTeacher";

    }

    // 添加教师信息
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.GET})
    public String addTeacherUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);

        return "admin/addTeacher";
    }

    // 添加教师信息处理
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.POST})
    public String addTeacher(TeacherCustom teacherCustom, Model model) throws Exception {

        Boolean result = teacherService.save(teacherCustom);

        if (!result) {
            model.addAttribute("message", "工号重复");
            return "error"; // 这里的返回是返回到error.jsp
        }

        // 添加成功后，也添加到登录表
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(teacherCustom.getUserid().toString());

        // 这里可能也有问题 2020.05.26
        // 其实是默认密码 123
        userlogin.setPassword("123");
        userlogin.setRole(1);
        userloginService.save(userlogin);

        // 重定向
        return "redirect:/admin/showTeacher";
    }

    // 修改教师信息页面显示
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.GET})
    public String editTeacherUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showTeacher";
        }
        TeacherCustom teacherCustom = teacherService.findById(id);
        if (teacherCustom == null) {
            throw new CustomException("未找到该名学生");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("teacher", teacherCustom);


        return "admin/editTeacher";
    }

    // 修改教师信息页面处理
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.POST})
    public String editTeacher(TeacherCustom teacherCustom) throws Exception {

        teacherService.updateById(teacherCustom.getUserid(), teacherCustom);

        // 重定向
        return "redirect:/admin/showTeacher";
    }

    // 删除教师
    @RequestMapping("/removeTeacher")
    public String removeTeacher(Integer id) throws Exception {
        if (id == null) {
            //加入没有带教师id就进来的话就返回教师显示页面
            return "admin/showTeacher";
        }
        teacherService.removeById(id);
        userloginService.removeByName(id.toString());

        return "redirect:/admin/showTeacher";
    }

    // 搜索教师
    @RequestMapping(value = "selectTeacher", method = {RequestMethod.POST})
    private String selectTeacher(String findByName, Model model) throws Exception {

        List<TeacherCustom> list = teacherService.findByName(findByName);

        model.addAttribute("teacherList", list);
        return "admin/showTeacher";
    }

    // 课程相关
    // 课程信息显示
    @RequestMapping("/showCourse")
    public String showCourse(Model model, Integer page) throws Exception {

        List<CourseCustom> list = null;

        // 2020.05.26新增内容
        List<TeacherCustom> teacherCustomList = teacherService.findAll();

        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(courseService.getCountCouse());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = courseService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = courseService.findByPaging(page);
        }

        model.addAttribute("courseList", list);
        model.addAttribute("pagingVO", pagingVO);


        // 2020.05.26新增内容，用来把添加课程时候的教师ID显示成教师姓名
        model.addAttribute("teacherList", teacherCustomList);

        return "admin/showCourse";

    }

    // 添加课程
    @RequestMapping(value = "/addCourse", method = {RequestMethod.GET})
    public String addCourseUI(Model model) throws Exception {

        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();

        model.addAttribute("collegeList", collegeList);
        model.addAttribute("teacherList", list);

        return "admin/addCourse";
    }

    // 添加课程信息处理
    @RequestMapping(value = "/addCourse", method = {RequestMethod.POST})
    public String addCourse(CourseCustom courseCustom, Model model) throws Exception {

        Boolean result = courseService.save(courseCustom);

        if (!result) {
            model.addAttribute("message", "课程号重复");
            return "error"; // 这里的返回是返回到error.jsp
        }


        // 重定向
        return "redirect:/admin/showCourse";
    }

    // 修改教师信息页面显示
    @RequestMapping(value = "/editCourse", method = {RequestMethod.GET})
    public String editCourseUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showCourse";
        }
        CourseCustom courseCustom = courseService.findById(id);
        if (courseCustom == null) {
            throw new CustomException("未找到该课程");
        }
        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();

        model.addAttribute("teacherList", list);
        model.addAttribute("collegeList", collegeList);
        model.addAttribute("course", courseCustom);


        return "admin/editCourse";
    }

    // 修改教师信息页面处理
    @RequestMapping(value = "/editCourse", method = {RequestMethod.POST})
    public String editCourse(CourseCustom courseCustom) throws Exception {

        courseService.upadteById(courseCustom.getCourseid(), courseCustom);

        // 重定向
        return "redirect:/admin/showCourse";
    }

    // 删除课程信息
    @RequestMapping("/removeCourse")
    public String removeCourse(Integer id) throws Exception {
        if (id == null) {
            // 如果没有id值则重定向
            return "admin/showCourse";
        }
        courseService.removeById(id);

        return "redirect:/admin/showCourse";
    }

    // 搜索课程
    @RequestMapping(value = "selectCourse", method = {RequestMethod.POST})
    private String selectCourse(String findByName, Model model) throws Exception {

        List<CourseCustom> list = courseService.findByName(findByName);

        model.addAttribute("courseList", list);
        return "admin/showCourse";
    }


    // 密码重置操作

    // 普通账号密码重置
    @RequestMapping("/userPasswordRest")
    public String userPasswordRestUI() throws Exception {
        return "admin/userPasswordRest";
    }

    // 普通账号密码重置处理
    @RequestMapping(value = "/userPasswordRest", method = {RequestMethod.POST})
    public String userPasswordRest(Userlogin userlogin) throws Exception {

        Userlogin u = userloginService.findByName(userlogin.getUsername());

        if (u != null) {
            if (u.getRole() == 0) {
                throw new CustomException("该账户为管理员账户，没法修改");
            }
            u.setPassword(userlogin.getPassword());
            userloginService.updateByName(userlogin.getUsername(), u);
        } else {
            throw new CustomException("没找到该用户");
        }

        return "admin/userPasswordRest";
    }

    // 当前登录密码重置
    @RequestMapping("/passwordRest")
    public String passwordRestUI() throws Exception {
        return "admin/passwordRest";
    }


    /**
     * 以下是2020.05.26新增内容
     */
    // 院系信息显示
    @RequestMapping("/showCollege")
    public String showCollege(Model model, Integer page) throws Exception {

        List<CollegeCustom> list = null;
        // 页码对象
        PagingVO pagingVO = new PagingVO();
        // 设置总页数
        pagingVO.setTotalCount(collegeService.getCountCollege());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = collegeService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = collegeService.findByPaging(page);
        }

        model.addAttribute("collegeList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showCollege";

    }


    /**
     * 以下内容是2020.05.27新增内容
     */

     //  添加院系信息页面显示
    @RequestMapping(value = "/addCollege", method = {RequestMethod.GET})
    public String addCollegeUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();
        model.addAttribute("collegeList", list);

        return "admin/addCollege";
    }

     // 添加院系信息操作
    @RequestMapping(value = "/addCollege", method = {RequestMethod.POST})
    public String addCollege(CollegeCustom collegeCustom, Model model) throws Exception {

        //Boolean result = studentService.save(studentCustom);
        Boolean result = collegeService.save(collegeCustom);

        if (!result) {
            model.addAttribute("message", "院系编号已经存在啦");
            return "error"; // 这里的返回是返回到error.jsp
        }

        // 重定向
        return "redirect:/admin/showCollege";
    }
    // 修改院系操作
    @RequestMapping(value = "/editCollege", method = {RequestMethod.GET})
    public String editCollegeUI(Integer id, Model model) throws Exception {
        if (id == null) {
            // 如果没有带id进来就返回
            return "redicert:/admin/showCollege";
        }
        College college = collegeService.findById(id);
        if (college == null) {
            // 如果没有找到，这里暂时跳转回院系信息列表
            return "redicert:/admin/showCollege";
        }

        model.addAttribute("college", college);
        return "admin/editCollege";
    }

    // 修改院系信息处理
    @RequestMapping(value = "/editCollege", method = {RequestMethod.POST})
    public String editCollege(College college) throws Exception {

        collegeService.updateById(college.getCollegeid(), college);

        // 重定向到院系信息页
        return "redirect:/admin/showCollege";
    }

    /// 删除院系信息处理
    @RequestMapping("/removeCollege")
    public String removeCollege(Integer id) {
        if (id == null) {
            return "admin/showCollege";
        }
        collegeService.removeById(id);
        return "redirect:/admin/showCollege";
    }





}
