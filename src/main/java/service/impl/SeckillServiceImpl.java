package service.impl;

import dao.SeckillDao;
import dao.SuccessKilledDao;
import dao.cache.RedisDao;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import entity.SuccessKilled;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import service.SeckillService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chris Yang
 * created 2022-07-28 21:14
 **/

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 注入Service依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    // 用于混淆md5
    private final String slat = "asfgzdgzsetstd35rf2@$()ew35ads2411531($)@";


    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        /** 一致性建立在超时的基础上进行维护 */
        /** Redis 优化
         * get from cache
         * if null
         *  get db
         *  else
         *      put cache
         *  locgoin
         *
         */
        Seckill seckill =  redisDao.getSeckill(seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false,seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }


        /***************************************************/
//        Seckill seckill = seckillDao.queryById(seckillId);
//        if (seckill == null) {
//            return new Exposer(false, seckillId);
//        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，RPC/HTTP请求/或者剥离到事务方法外
     * 3: 不是所有的方法都需要事务，如只有一条修改操作，只读操作，不需要事务控制。
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        Date nowTime = new Date();

        try {
            // 记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                // 重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                // 秒杀成功
                // 执行秒杀逻辑，减库存 + 记录购买行为
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    throw new SeckillCloseException("seckill cloesd");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译期异常，转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStatEnum.DATE_REWRITE);
        }
        Date killTime = new Date();
        Map<String,Object> map = new HashMap<>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        try {
            seckillDao.killByProcedure(map);
            // 获取result;
            int result = MapUtils.getInteger(map,"result",-2);
            if(result == 1){
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);

            }else {
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);

        }
    }
}
