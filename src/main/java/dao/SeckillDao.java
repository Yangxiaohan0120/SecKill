package dao;

import entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Chris Yang
 * created 2022-07-22 22:10
 **/
public interface SeckillDao {

    /**
     * @description 减库存
     * @author yxh
     * @date  2022-07-22 22:11:42
     * @para * @param: seckillId
     * @param: killTime
     * @return 如果影响行数>1，表示更新的行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * @description 查询系统
     * @author yxh
     * @date 2022-07-22 22:12:44
     * @para * @param: seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * @description 根据偏移量，查询秒杀商品列表
     * @author yxh
     * @date 2022-07-22 22:13:28
     * @para * @param: offset
     * @param: limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * @description 使用存储过程进行秒杀
     * @author yxh
     * @date 2022-08-01 22:19:35
     * @para * @param: paramMap
     * @return 
     */

    void killByProcedure(Map<String,Object> paramMap);
}
