package dao;

import entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Chris Yang
 * created 2022-07-24 20:18
 **/


// 配置Spring和Junit整合，Junit启动时加载Spring IOC容器

@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class SeckillDaoTest {

    // 注入Dao实现类依赖
    // 方便下列测试方法中进行统一的调用，而不用新生成对象
    @Resource
    private SeckillDao seckillDao;


    /*
     * 21:41:23.004 [main] DEBUG org.mybatis.spring.transaction.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@5a5338df [wrapping: com.mysql.cj.jdbc.ConnectionImpl@418c5a9c]] will not be managed by Spring
     * 21:41:23.008 [main] DEBUG dao.SeckillDao.reduceNumber - ==>  Preparing: update seckill set number = number - 1 where seckill_id = ? and start_time <= ? and end_time >= ? and number > 0;
     * 21:41:23.040 [main] DEBUG dao.SeckillDao.reduceNumber - ==> Parameters: 1000(Long), 2022-07-24 21:41:22.616(Timestamp), 2022-07-24 21:41:22.616(Timestamp)
     * 21:41:23.072 [main] DEBUG dao.SeckillDao.reduceNumber - <==    Updates: 0
     */
    @Test
    public void testReduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L,killTime);
        System.out.println(updateCount);
    }

    /*
     * 注意各类配置中类的路径书写
     */
    // 结果
    // 100
    // Seckill{seckillid=1000, name='1800元秒杀Switch', number=100, startTime=Fri May 20 00:00:00 CST 2022, endTime=Mon May 23 00:00:00 CST 2022, createTime=Fri Jul 22 22:01:25 CST 2022}
    @Test
    public void testQueryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getNumber());
        System.out.println(seckill);
    }


    /*
     * org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [arg1, arg0, param1, param2]
     * java 没有保存形参的记录：queryAll（int offset, int limit) --> queryAll(arg0,arg1)
     * 修改接口规定形参
     */

    // 结果
    // Seckill{seckillid=1000, name='1800元秒杀Switch', number=100, startTime=Fri May 20 00:00:00 CST 2022, endTime=Mon May 23 00:00:00 CST 2022, createTime=Fri Jul 22 22:01:25 CST 2022}
    // Seckill{seckillid=1001, name='3000元秒杀iPad3', number=100, startTime=Fri May 20 00:00:00 CST 2022, endTime=Mon May 23 00:00:00 CST 2022, createTime=Fri Jul 22 22:01:25 CST 2022}
    // Seckill{seckillid=1002, name='2000元秒杀Xbox', number=100, startTime=Fri May 20 00:00:00 CST 2022, endTime=Mon May 23 00:00:00 CST 2022, createTime=Fri Jul 22 22:01:25 CST 2022}
    // Seckill{seckillid=1003, name='2200元秒杀PS4', number=100, startTime=Fri May 20 00:00:00 CST 2022, endTime=Mon May 23 00:00:00 CST 2022, createTime=Fri Jul 22 22:01:25 CST 2022}
    @Test
    public void testQueryAll() {
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for(Seckill seckill : seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void test(){
        String s = "112434";
        System.out.println('a'+s.charAt(1));
        LinkedList res = new LinkedList();
        LinkedList<LinkedList<Character>> list = new LinkedList<>();
        list.add(res);
    }
}