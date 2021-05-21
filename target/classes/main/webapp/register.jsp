<%--
  Created by IntelliJ IDEA.
  User: luqiqi
  Date: 2021/4/17
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
</head>
<body>
<div style="text-align: center">
    <form action="http://localhost:8080/dp/registerServlet" method="post">
        账  号<input type="text" name="username"><br>
        密  码<input type="password" name="password"><br>
        年  龄<input type="text" name="age"><br>

        <button type="submit">注册</button>
    </form>
</div>
</body>
</html>
