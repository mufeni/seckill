package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilleDaoTest {

    @Resource
   private SuccessKilleDao successKilleDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id =1001l;
        long phone = 132465798l;
        int row = successKilleDao.insertSuccessKilled(id,phone);
        System.out.println("插入了"+row+"行");
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id = 1001l;
        long phone = 132465798l;
        SuccessKilled successKilled = successKilleDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled.toString());
        System.out.println(successKilled.getSeckill().getName());
    }

}