package server;

import conndata.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String age = req.getParameter("age");

        String sql = "insert into tb_user(userName,userPwd ,userAge) VALUE(?,?,?)";

        JDBCUtil.update(sql, username, password, age);

        session.setAttribute("msg", "注册成功");

        resp.sendRedirect("http://localhost:8080/dp/login.jsp");

    }
}


