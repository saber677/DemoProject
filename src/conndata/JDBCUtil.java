package conndata;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtil {

    public static Connection getConnection() throws Exception {
        //创建输入流，导入配置文件
        InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("users.properties");

        //创建配置文件对象，加载配置文件进入JVM
        Properties properties = new Properties();
        properties.load(is);

        //获取连接池，通过配置文件实现 druied创建连接
        Connection conn = DruidDataSourceFactory.createDataSource(properties).getConnection();

        return conn;
    }

    //只关闭连接资源
    public static void closeResource(Connection connection, PreparedStatement ps) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //关闭连接资源和结果集的资源
    public static void closeResource(Connection connection, PreparedStatement ps, ResultSet rs) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {

            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    //增删改操作
    public static void update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //创建连接
            conn = JDBCUtil.getConnection();
            //创建预编译对象
            ps = conn.prepareStatement(sql);

            //遍历把占位符填充，然后执行操作execute()
            for (int i = 1; i <= args.length; i++) {
                ps.setObject(i, args[i - 1]);
            }
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(null, ps);
        }
    }

    public static <T> List<T> query(Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //创建连接，预编译
            Connection conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);

            //通过遍历填充占位符，并执行executeQuery()
            for (int i = 1; i <= args.length; i++) {
                ps.setObject(i, args[i - 1]);
            }
            //执行查询操作，返回结果集
            rs = ps.executeQuery();

            //通过结果集 获取元数据
            ResultSetMetaData metaData = ps.getMetaData();

            //通过元数据 获取结果集的列数
            int columnCount = metaData.getColumnCount();

            //创建集合存储对象
            ArrayList<T> list = new ArrayList<>();

            //遍历每一条数据
            while (rs.next()) {
                T t = clazz.newInstance();

                //遍历结果集
                for (int i = 1; i <= columnCount; i++) {
                    //获取结果集的每一个值
                    Object columnValue = rs.getObject(i);
                    //通过元数据获取字段别名
                    String columnLabel = metaData.getColumnLabel(i);
                    //通过别名获取对象属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置权限，准备给对象的属性赋值
                    field.setAccessible(true);
                    //通过结果集的每一个值 给对象的属性赋值
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtil.closeResource(null, ps, rs);
        }
        return null;
    }
}
