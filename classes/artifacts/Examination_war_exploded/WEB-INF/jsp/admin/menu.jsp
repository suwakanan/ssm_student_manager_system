<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="col-md-2">
    <ul class="nav nav-pills nav-stacked" id="nav">



        <li><a href="/admin/showCourse">课程管理<span class="badge pull-right"></span></a></li>
        <li><a href="/admin/showStudent">学生管理<span class="badge pull-right"></span></a></li>
        <li><a href="/admin/showTeacher">教师管理<span class="badge pull-right"></span></a></li>
        <%-- 2020.05.26新增内容 --%>
        <li><a href="/admin/showCollege">院系管理<span class="badge pull-right"></span></a></li>
        <li><a href="/admin/userPasswordRest">密码重置</a></li>
        <li><a href="/admin/passwordRest">密码修改</a></li>

        <li><a href="/logout">退出系统</a></li>
        <%--<li class="disabled"><a href="##">Responsive</a></li>--%>
    </ul>
</div>