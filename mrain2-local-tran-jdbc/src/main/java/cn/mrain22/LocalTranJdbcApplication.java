package cn.mrain22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/7/20 14:07
 * @Version 1.0
 * @Describe:
 */
public class LocalTranJdbcApplication {
    private static final Logger LOG = LoggerFactory.getLogger(LocalTranJdbcApplication.class);

    public static void main(String[] args) throws SQLException {
        String plusAmountSQL = "UPDATE t_user SET amount = amount + 100 WHERE username = ?";
        String minusAmountSQL = "UPDATE t_user SET amount = amount - 100 WHERE username = ?";

        Connection dbConnection = getDBConnection();
        LOG.debug("Begin");
        // 关闭事务自动提交
        dbConnection.setAutoCommit(false);

        PreparedStatement plusAmountPS = dbConnection.prepareStatement(plusAmountSQL);
        plusAmountPS.setString(1, "SuperMan");
        plusAmountPS.executeUpdate();


        PreparedStatement minusAmountPS = dbConnection.prepareStatement(minusAmountSQL);
        minusAmountPS.setString(1, "BatMan");
        minusAmountPS.executeUpdate();

        dbConnection.commit();
        LOG.debug("Done!");

        plusAmountPS.close();
        minusAmountPS.close();
        dbConnection.close();
    }


    /**
     * 获取SQL连接
     * @return Connection
     * @throws SQLException
     */
    public static Connection getDBConnection() throws SQLException {
        final String DB_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_CONNECTION = "jdbc:mysql://127.0.0.1:3306/mtry";
        final String DB_USER = "root";
        final String DB_PASSWORD = "123456";
        try {
            // 注册驱动
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }
        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

    /**
     * 模拟错误
     * @throws SQLException
     */
    private static void simulateError() throws SQLException {
        throw new SQLException("Simulate some error!");
    }
}
