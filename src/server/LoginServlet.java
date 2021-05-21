package server;

import DAO.Users;
import conndata.JDBCUtil;

import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //设置文本内容
        resp.setContentType("text/html;charset=UTF-8");
        //获取用户输入的账号密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username);
        System.out.println(password);

        //获取session,得到sessionID
        HttpSession session = req.getSession();
        String id = session.getId();

        //设置cookie
        Cookie cookie = new Cookie("username", id);
        resp.addCookie(cookie);

        //查询语句和接口
        String sql = "select userID as id,userName as name,userPwd as password,userAge as age from tb_user as Users where userName=?";

        //获取查询结果
        List<Users> result = JDBCUtil.query(Users.class, sql, username);

        //账号和密码都输入了
        if (username.trim() != "" && password.trim() != "") {

            //账号不存在
            if (result.isEmpty()) {
                System.out.println("账号不存在，请先注册");
                resp.sendRedirect("http://localhost:8080/dp/login.jsp");
                session.setAttribute("msg","账号不存在，请先注册");

            }
            //账号存在
            else {
                //获取user对象的密码属性，就是在数据找到的密码
                String resultPsw = result.get(0).getPassword();

                //用户输入的密码和数据库的密码一样
                if (password.equals(resultPsw)) {

                    //设置session
                    session.setAttribute("user", username);
                    resp.sendRedirect("http://localhost:8080/dp/Home.jsp");
                }
                //密码不一致，密码错误
                else {
                    System.out.println("密码错误");
                    resp.sendRedirect("http://localhost:8080/dp/login.jsp");
                    session.setAttribute("msg","密码错误");
                }
            }
        }
        //判断账号没输入还是密码没输入
        else {
            if (username.trim() == "") {
                resp.getWriter().write("请先输入账号");
            } else {
                resp.getWriter().write("请先输入密码");
            }
        }
    }

//    //暂时没用的方法
//    protected String ifEmpty(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        System.out.println(username);
//
//        //查询语句和接口
//        String sql = "select userID as id,userName as name,userPwd as password,userAge as age from tb_user as Users where userName=?";
//
//        //获取数据库查询结果
//        List<Users> result = JDBCUtil.query(Users.class, sql, username);
//        String resultName = result.get(0).getName();
//        String resultPsw = result.get(0).getPassword();
//        String a = "请输入账号";
//        //判断用户信息
//        if (username.trim() == "") {
//
////            return a;
//            response.getWriter().print("请输入账号");
//        }
////        else if (password.trim() == "") {
////            response.getWriter().write("请输入密码");
////        } else if (username != resultName) {
////            response.getWriter().write("账号不对");
////        } else if (password != resultPsw) {
////            response.getWriter().write("密码不对");
////        }
//        return a;
//    }

}



