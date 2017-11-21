package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatkillException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;
    @Test
    public void testGetSeckillList() throws Exception {
       List<Seckill> seckills =  seckillService.getSeckillList();
       logger.info("list={}",seckills);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000l;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    /**
     * 测试代码完整逻辑，注意可重复执行
     * @throws Exception
     */
    @Test
    public void testSeckillLogic() throws Exception{
        long id = 1001l;
       Exposer exposer =  seckillService.excportSeckillUrl(id);
       if(exposer.isExposed()){
           logger.info("exposr={}",exposer);
           long phone = 12346678901020l;
           String md5=exposer.getMd5();
           try{
               SeckillExecution exception =  seckillService.executeSeckill(id,phone,md5);
               logger.info("exeption={}",exception);
           }catch (RepeatkillException e){
               logger.info(e.getMessage());
           }catch (SeckillException e){
               logger.info(e.getMessage());
           }
       }else{
           logger.warn("exposer={}",exposer);
       }
    }

    @Test
    public void testExcportSeckillUrl() throws Exception {
        long id = 1000l;
        Exposer exposer = seckillService.excportSeckillUrl(id);
        logger.info("exposr={}",exposer);

    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000l;
        long phone = 1234667890l;
        String md5="3dea0206b6c1164034725cdc81536170";
        try{
            SeckillExecution exception =  seckillService.executeSeckill(id,phone,md5);
            logger.info("exeption={}",exception);
        }catch (RepeatkillException e){
            logger.info(e.getMessage());
        }catch (SeckillException e){
            logger.info(e.getMessage());
        }

    }

}