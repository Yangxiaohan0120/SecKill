-- 秒杀执行存储过程 --
-- ;
DELIMITER $$
-- console ; 转换为 $$

-- 定义存储过程
-- 参数：in 输入参数； out 输出参数
-- row_count(): 返回上一条修改类型sql（delete，insert，update）的影响行数
-- row_count：0 未修改数据 >0 修改的行数 <0 sql错误/未执行修改sql
CREATE PROCEDURE `seckill`.`execute_seckill`
    (IN v_seckill_id BIGINT, IN v_phone BIGINT,
    IN v_kill_time TIMESTAMP, OUT r_result INT)
    BEGIN
        DECLARE insert_count INT DEFAULT 0;
        START TRANSACTION;
        INSERT IGNORE INTO success_killed
            (seckill_id,user_phone,create_time)
        values( v_seckill_id,v_phone,v_kill_time);
        select row_count() into insert_count;
        IF(insert_count = 0 )THEN
            ROLLBACK;
            SET r_result = -1;
        ELSEIF(insert_count < 0)THEN
            ROLLBACK;
            SET R_RESULT = -2;
        ELSE
            UPDATE seckill
            SET number = number -1
            WHERE seckill_id = v_seckill_id
                AND end_time > v_kill_time
                AND start_time < v_kill_time
                AND number > 0;
            SELECT row_count() INTO insert_count;
            IF(insert_count = 0) THEN
                ROLLBACK;
                SET r_result = 0;
            ELSEIF(insert_count < 0)THEN
                ROLLBACK;
                SET r_result = -2;
            ELSE
                COMMIT;
                SET r_result = 1;
            END IF;
        END IF;
    END
$$

-- 储存过程定义结束
-- 将定界符重新改为;
DELIMITER ;

-- 定义一个用户变量r_result
SET @r_result = -3;
-- 执行储存过程
CALL execute_seckill(1000, 13502178891, now(), @r_result);
-- 获取结果
SELECT @r_result;

-- 存储过程
-- 1, 存储过程优化：事务行级锁持有的时间
-- 2, 不过度依赖存储过程
-- 3, 简单逻辑可以应用存储过程
-- 4, QPS：一个秒杀单6000/qps