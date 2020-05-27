<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--开启el表达式--%>
<%@ page  isELIgnored="false"%>

<html>
<head>
    <title>错误提示</title>
</head>
<body>
    ${message}<br>
    <input type="button" name="Submit" value="返回" onclick="javascript:window.history.back();"/>
</body>
</html>
