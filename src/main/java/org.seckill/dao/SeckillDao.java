package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     * 减库存
     * @param
     * @param killTime
     * @return 影响行数
     */
    int reduceNumber(@Param("sid") long sid,@Param("time") Date time);

    /**
     * 找商品
     * @param
     * @return
     */
    Seckill queryById(long sid);

    /**
     * 所有的商品
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offet") int offet,@Param("limit") int limit);



}
