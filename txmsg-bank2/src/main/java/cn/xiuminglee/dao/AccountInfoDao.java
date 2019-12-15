package cn.xiuminglee.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @Author Xiuming Lee
 * @Description
 */
@Mapper
@Component
public interface AccountInfoDao {
    /**
     * 修改金额
     * @param accountNo 账号
     * @param amount 金额
     * @return
     */
    @Update("update account_info set account_balance=account_balance+#{amount} where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    /**
     * 判断事务执行
     * @param txNo
     * @return
     */
    @Select("select count(1) from de_duplication where tx_no = #{txNo}")
    int isExistTx(String txNo);

    /**
     * 添加事务
     * @param txNo
     * @return
     */
    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(String txNo);

}
