package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilleDao {
    /**
     * 插入购买明细，可过滤重复
     * @param sid
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("sid") long sid,@Param("userPhone") long userPhone );

    /**
     * 根据id查询Successkilled并携带秒杀产品对象实体
     * @param sid
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("sid") long sid,@Param("userPhone") long userPhone );

}
