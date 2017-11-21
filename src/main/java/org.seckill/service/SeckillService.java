package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatkillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型
 */
public interface SeckillService {

    /**
     * 查杀所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param sId
     * @return
     */
    Seckill getById(long sId);

    /**
     * 秒杀开启是输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param sId
     */
    Exposer excportSeckillUrl(long sId);

    /**
     * 执行秒杀操作
     * @param sId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long sId, long userPhone, String md5) throws SeckillException,RepeatkillException,SeckillCloseException;


}
