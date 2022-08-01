package service;

import com.mysql.cj.log.Log;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Chris Yang
 * created 2022-07-29 10:20
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception{
        List<Seckill> list =  seckillService.getSeckillList();
        logger.info("List={}",list);
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(1001L);
        logger.info("seckill={}",seckill);
        System.out.println(seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("Exposer={}",exposer);
    }

    //Exposer=Exposer{exposed=true,
    // md5='ebac8118edd99508faee5dd1d6741588',
    // seckillId=1000,
    // now=0, start=0, end=0}
    @Test
    public void executeSeckill() {
        long seckillId = 1000L;
        long userPhone = 14352632754L;
        String md5 = "ebac8118edd99508faee5dd1d6741588";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            logger.info("seckillExecution={}", seckillExecution);
        }catch (RepeatKillException e){
            logger.info(e.getMessage());
        }catch (SeckillCloseException e1){
            logger.info(e1.getMessage());
        }
    }

    //10:54:54.462 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
    //10:54:54.468 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.474 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@7642df8f [wrapping: com.mysql.cj.jdbc.ConnectionImpl@3e30646a]] will be managed by Spring
    //10:54:54.479 [main] DEBUG dao.SeckillDao.reduceNumber - ==>  Preparing: update seckill set number = number - 1 where seckill_id = ? and start_time <= ? and end_time >= ? and number > 0;
    //10:54:54.511 [main] DEBUG dao.SeckillDao.reduceNumber - ==> Parameters: 1000(Long), 2022-07-29 10:54:54.455(Timestamp), 2022-07-29 10:54:54.455(Timestamp)
    //10:54:54.526 [main] DEBUG dao.SeckillDao.reduceNumber - <==    Updates: 1
    //10:54:54.527 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.527 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a] from current transaction
    //10:54:54.527 [main] DEBUG d.S.insertSuccessKilled - ==>  Preparing: insert ignore into success_killed(seckill_id,user_phone,state,create_time) values (?,?,0,NOW())
    //10:54:54.528 [main] DEBUG d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 14352632354(Long)
    //10:54:54.529 [main] DEBUG d.S.insertSuccessKilled - <==    Updates: 1
    //10:54:54.529 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.529 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a] from current transaction
    //10:54:54.530 [main] DEBUG d.S.queryByIdWithSeckill - ==>  Preparing: select sk.seckill_id, sk.user_phone, sk.create_time, sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id=? and sk.user_phone=?
    //10:54:54.531 [main] DEBUG d.S.queryByIdWithSeckill - ==> Parameters: 1000(Long), 14352632354(Long)
    //10:54:54.556 [main] DEBUG d.S.queryByIdWithSeckill - <==      Total: 1
    //10:54:54.562 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.562 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.563 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.563 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22ffa91a]
    //10:54:54.563 [main] DEBUG o.s.j.d.DataSourceTransactionManager - Initiating transaction commit
    //10:54:54.563 [main] DEBUG o.s.j.d.DataSourceTransactionManager - Committing JDBC transaction on Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@7642df8f [wrapping: com.mysql.cj.jdbc.ConnectionImpl@3e30646a]]
    //10:54:54.567 [main] DEBUG o.s.j.d.DataSourceTransactionManager - Releasing JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@7642df8f [wrapping: com.mysql.cj.jdbc.ConnectionImpl@3e30646a]] after transaction
    //10:54:54.568 [main] INFO  service.SeckillServiceTest - seckillExecution=dto.SeckillExecution@4233e892
    //10:54:54.569 [main] DEBUG o.s.t.c.c.DefaultCacheAwareContextLoaderDelegate - Retrieved ApplicationContext [720167805] from cache with key [[MergedContextConfiguration@6279cee3 testClass = SeckillServiceTest, locations = '{classpath:spring/spring-dao.xml, classpath:spring/spring-service.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
    //10:54:54.570 [main] DEBUG o.springframework.test.context.cache - Spring test ApplicationContext cache statistics: [DefaultContextCache@79517588 size = 1, maxSize = 32, parentContextCount = 0, hitCount = 4, missCount = 1]
    //10:54:54.571 [main] DEBUG o.s.t.c.c.DefaultCacheAwareContextLoaderDelegate - Retrieved ApplicationContext [720167805] from cache with key [[MergedContextConfiguration@6279cee3 testClass = SeckillServiceTest, locations = '{classpath:spring/spring-dao.xml, classpath:spring/spring-service.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
    //10:54:54.571 [main] DEBUG o.springframework.test.context.cache - Spring test ApplicationContext cache statistics: [DefaultContextCache@79517588 size = 1, maxSize = 32, parentContextCount = 0, hitCount = 5, missCount = 1]
    //10:54:54.572 [main] DEBUG o.s.t.c.s.AbstractDirtiesContextTestExecutionListener - After test method: context [DefaultTestContext@c8e4bb0 testClass = SeckillServiceTest, testInstance = service.SeckillServiceTest@4c40b76e, testMethod = executeSeckill@SeckillServiceTest, testException = [null], mergedContextConfiguration = [MergedContextConfiguration@6279cee3 testClass = SeckillServiceTest, locations = '{classpath:spring/spring-dao.xml, classpath:spring/spring-service.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]], attributes = map['org.springframework.test.context.event.ApplicationEventsTestExecutionListener.recordApplicationEvents' -> false]], class annotated with @DirtiesContext [false] with mode [null], method annotated with @DirtiesContext [false] with mode [null].
    //10:54:54.573 [main] DEBUG o.s.t.c.c.DefaultCacheAwareContextLoaderDelegate - Retrieved ApplicationContext [720167805] from cache with key [[MergedContextConfiguration@6279cee3 testClass = SeckillServiceTest, locations = '{classpath:spring/spring-dao.xml, classpath:spring/spring-service.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
    //10:54:54.573 [main] DEBUG o.springframework.test.context.cache - Spring test ApplicationContext cache statistics: [DefaultContextCache@79517588 size = 1, maxSize = 32, parentContextCount = 0, hitCount = 6, missCount = 1]
    //10:54:54.573 [main] DEBUG o.s.t.c.s.AbstractDirtiesContextTestExecutionListener - After test class: context [DefaultTestContext@c8e4bb0 testClass = SeckillServiceTest, testInstance = [null], testMethod = [null], testException = [null], mergedContextConfiguration = [MergedContextConfiguration@6279cee3 testClass = SeckillServiceTest, locations = '{classpath:spring/spring-dao.xml, classpath:spring/spring-service.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]], attributes = map['org.springframework.test.context.event.ApplicationEventsTestExecutionListener.recordApplicationEvents' -> false]], class annotated with @DirtiesContext [false] with mode [null].
    //10:54:54.574 [SpringContextShutdownHook] DEBUG o.s.c.s.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@2aece37d, started on Fri Jul 29 10:54:53 CST 2022


    // 将Exposer和Execution结合进行测试
    @Test
    public void testSeckillLogic() throws Exception{
        long seckillId = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long userPhone = 14322632754L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("seckillExecution={}", seckillExecution);
            }catch (RepeatKillException e){
                logger.info(e.getMessage());
            }catch (SeckillCloseException e1){
                logger.info(e1.getMessage());
            }
        }else {
            logger.info("exposer={}",exposer);
        }
    }

    @Test
    public void testExecuteSeckillProcedure(){
        long seckillId = 1000L;
        long phone = 14240938512L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }


    }

}