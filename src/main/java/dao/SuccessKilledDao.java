package dao;

import entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * @author Chris Yang
 * created 2022-07-22 22:14
 **/
public interface SuccessKilledDao {

    /**
     * @description 插入购买明细，过滤重复
     * @author yxh
     * @date 2022-07-22 22:14:50
     * @para * @param: seckillId
     * @param: userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * @description 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @author yxh
     * @date 2022-07-22 22:15:59
     * @para * @param: seckillId
     * @return 
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
