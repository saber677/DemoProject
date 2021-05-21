import DAO.Customers;
import DAO.Users;
import conndata.JDBCUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {

//        String sql1 = "select id,name from customers where id=?";
//        List<Customers> customers = JDBCUtil.query(Customers.class, sql1, 1);
//        customers.forEach(System.out::println);

//        String sql2 = "select userId as id,userName as name,userPwd as password,userAge as age FROM tb_user WHERE userId=?";
//        List<Users> admin = JDBCUtil.query(Users.class, sql2, 1);
//        admin.forEach(System.out::println);
        String sql = "select userID as id,userName as name from tb_user as Users where userName=?";
        String name = "adin";
        //获取查询结果
        List<Users> result = JDBCUtil.query(Users.class, sql, name);
        result.forEach(System.out::println);
        if (result.isEmpty()){
            System.out.println("a");
        }



    }
}
