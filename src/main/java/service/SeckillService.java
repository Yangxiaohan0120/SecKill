package service;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在 "使用者" 角度设计接口
 * 1. 方法定义粒度
 * 2. 参数直接简练
 * 3. 返回类型友好
 *
 * @author Chris Yang
 * created 2022-07-28 20:56
 **/
public interface SeckillService {
    /**
     * @description 查询所有秒杀记录
     * @author yxh
     * @date 2022-07-28 20:59:25
     * @para * @param:
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * @description 查询单个秒杀记录
     * @author yxh
     * @date 2022-07-28 20:59:41
     * @para * @param: seckillId
     * @return
     */
    Seckill getById(long seckillId);
    
    /**
     * @description 秒杀开启时，输出秒杀接口的地址，否则输出系统时间和秒杀时间
     * @author yxh
     * @date 2022-07-28 21:02:11
     * @para * @param: seckillId
     * @return Exposer
     */
    
    Exposer exportSeckillUrl(long seckillId);

    /**
     * @description 执行秒杀操作，验证md5值
     * @author yxh
     * @date 2022-07-28 21:04:54
     * @para * @param: seckillId
     * @param: userPhone
     * @param: md5
     * @return SeckillExecution
     */

    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException;

}
