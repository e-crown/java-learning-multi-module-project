package com.kv.redis.mybatis.mapper;

import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kv.redis.mybatis.entity.IdentityGoldEntity;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 */
public interface IdentityGoldDAO extends BaseMapper<IdentityGoldEntity> {
    /**
     * 批量插入（mysql）
     * 
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<IdentityGoldEntity> entityList);
}
