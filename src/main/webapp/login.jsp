<%--
  Created by IntelliJ IDEA.
  User: luqiqi
  Date: 2021/4/12
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
<div style="text-align: center">
    <form id="loginForm" method="post">
        账号：<input type="text" name="username" id="username"><br>
        密码：<input type="password" name="password" id="password"><br>
        <span id="msg"
              style="text-align: center;
              color: #cd5c5c;
              font-size: 12px">
            ${sessionScope.msg}
        </span><br>
        <button type="button" class="loginBtn1">登录</button>
        <button type="button" class="loginBtn2">注册</button>
    </form>
</div>


<script type="text/javascript" src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
    //一点击就获取账号和密码的信息
    $(".loginBtn1").click(function (){
        document.forms[0].action="http://localhost:8080/dp/loginServlet"

        var username=$("#username").val();
        var password=$("#password").val();

        if (isEmpty(username)){
            $("#msg").html("请先输入账号")
            return;
        }
        if (isEmpty(password)){
            $("#msg").html("请先输入密码")
            return;
        }
        $("#loginForm").submit();
    });

    //注册信息按钮
    $(".loginBtn2").click(function (){
        document.forms[0].action="http://localhost:8080/dp/register.jsp";
        $("#loginForm").submit();
    })

    //判断用户名和密码是否为空，是就是没有输入
    function isEmpty(str){
        if (str==null||str.trim()==""){
            return true;
        }else {
            return false;
        }
    }


// //ajax请求
// $(".loginBtn").click(function (){
//     $.ajax({
//         url:"http://localhost:8080/dp/loginServlet",
//         data:"action=ifEmpty&username="+username,
//         type:"POST",
//         dataType:Text,
//         success:function (data){
//             console.log(data);
//         }
//     })
// })
</script>
</body>
</html>
