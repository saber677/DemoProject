package server;

import DAO.Users;
import conndata.JDBCUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.LogRecord;

public class loginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //获取用户输入的账号和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //
        //查询语句和接口
        String sql = "select userID as id,userName as name,userPwd as password,userAge as age from tb_user as Users where userName=?";

        //获取查询结果
        List<Users> result = JDBCUtil.query(Users.class, sql, username);
        String resultName = result.get(0).getName();
        String resultPsw = result.get(0).getPassword();

        //判断用户信息
        if (username.trim() == "") {
            resp.getWriter().write("请输入账号");
        } else if (password.trim() == "") {
            resp.getWriter().write("请输入密码");
        } else if (username.equals(resultName)) {
            resp.getWriter().write("账号不对");
        } else if (password.equals(resultPsw)) {
            resp.getWriter().write("密码不对");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
