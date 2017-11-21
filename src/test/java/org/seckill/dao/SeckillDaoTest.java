package org.seckill.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置Spring和junit整合，junit启动时加载SpringIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse("2016-1-1 00:00:00");
        long sid = 1000;
        int row =  seckillDao.reduceNumber(sid,date);
        System.out.println("修改了"+row);
    }

    @Test
    public void testqueryById() throws Exception {
        long id =1000;
        Seckill seckill  = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        int a = 1;
        int b = 10;
        List<Seckill> seckills = seckillDao.queryAll(a,b);
        System.out.println(seckills.size());
       for(Seckill s:seckills){
           System.out.println("打印");
           System.out.println(s.toString());
       }
    }

}