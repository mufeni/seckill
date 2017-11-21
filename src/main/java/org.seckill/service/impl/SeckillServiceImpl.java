package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilleDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatkillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

//@Component @Service @Dao @Conroller
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired//@Resource
    private SeckillDao seckillDao ;
    @Autowired
    private SuccessKilleDao successKilleDao;
    //md5盐值
    private final String slat="asdad&*&*&&asda,.asjdkas.sahdha89&*&^*sad";

    @Override
    public List<Seckill> getSeckillList() {

        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long sId) {

        return seckillDao.queryById(sId);
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    public Exposer excportSeckillUrl(long sId) {
        Seckill seckill = seckillDao.queryById(sId);
        if(seckill==null){
            return new Exposer(false,sId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();//当前时间
        if(nowTime.getTime()<startTime.getTime() ||nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,sId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5 = getMD5(sId);
        return new Exposer(true,md5,sId);
    }
    private String getMD5(long sId){
        String base = sId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long sId, long userPhone, String md5) throws SeckillException, RepeatkillException, SeckillCloseException {
        if(md5==null||!md5.equals(getMD5(sId))){
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(sId, nowTime);
            if (updateCount <= 0) {
                //没有更新到记录,秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilleDao.insertSuccessKilled(sId, userPhone);
                //唯一验证：sId,userPhone
                if (insertCount <= 0) {
                    throw new RepeatkillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilleDao.queryByIdWithSeckill(sId, userPhone);
                    return new SeckillExecution(sId,SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatkillException e2){
            throw e2;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常 转化为运行期异常
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
