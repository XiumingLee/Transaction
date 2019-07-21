package cn.mrain22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/7/20 14:07
 * @Version 1.0
 * @Describe:
 */
public class LocalTranJdbcApplication2 {
    private static final Logger LOG = LoggerFactory.getLogger(LocalTranJdbcApplication2.class);

    public static void main(String[] args) throws SQLException {

        Connection dbConnection = LocalTranJdbcApplication.getDBConnection();
        LOG.debug("Begin session2");
        // 关闭事务自动提交
        dbConnection.setAutoCommit(false);

        // 查询
        String sql = "SELECT * FROM t_user FOR UPDATE";
        PreparedStatement queryPS = dbConnection.prepareStatement(sql);
        ResultSet rs = queryPS.executeQuery();
        Long superManAmount = 0L;
        while (rs.next()) {
            String name = rs.getString(2);
            Long amount = rs.getLong(3);
            LOG.info("{} has amount:{}", name, amount);
            if (name.equals("SuperMan")) {
                superManAmount = amount;
            }
        }

        // 更新数据
        String plusAmountSQL = "UPDATE t_user SET amount = ? WHERE username = ?";
        PreparedStatement updatePS = dbConnection.prepareStatement(plusAmountSQL);
        updatePS.setLong(1, superManAmount + 100);
        updatePS.setString(2, "SuperMan");
        updatePS.executeUpdate();

        dbConnection.commit();
        LOG.debug("Done session2!");

        queryPS.close();
        updatePS.close();
        dbConnection.close();
    }

}
