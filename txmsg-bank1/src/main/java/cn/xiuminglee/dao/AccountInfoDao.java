package cn.xiuminglee.dao;

import cn.xiuminglee.entity.AccountInfo;
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
     * 更新账户金额，增加或消费
     * @param accountNo 添加金额为正数，删减金额为负数
     * @param amount 账号
     * @return
     */
    @Update("update account_info set account_balance=account_balance+#{amount} where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    /**
     * 根据账号查询账号信息
     * @param accountNo 账号ID
     * @return AccountInfo
     */
    @Select("select * from account_info where where account_no=#{accountNo}")
    AccountInfo findByIdAccountNo(@Param("accountNo") String accountNo);


    /**
     * 根据事务ID，查询事务是否执行过，保证幂等性。
     * @param txNo 事务id。
     * @return
     */
    @Select("select count(1) from de_duplication where tx_no = #{txNo}")
    int isExistTx(String txNo);

    /**
     * 添加事务
     * @param txNo 事务id
     * @return
     */
    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(String txNo);


}
