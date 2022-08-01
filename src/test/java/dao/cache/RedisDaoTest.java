package dao.cache;

import dao.SeckillDao;
import entity.Seckill;
import junit.runner.BaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Chris Yang
 * created 2022-08-01 20:58
 **/


@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class RedisDaoTest {
    private long id = 1000;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testGetSeckill() {
        // 从缓存中获取
        Seckill seckill = redisDao.getSeckill(id);

        if(seckill == null){
            // 没获取到则从服务器中获取
            seckill = seckillDao.queryById(id);
            if(seckill != null){
                // 从服务器中读取到之后再放入缓存中
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                // 从缓存中获取并打印
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }

    @Test
    public void testPutSeckill() {
    }
}