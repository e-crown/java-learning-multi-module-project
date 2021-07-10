package com.kv.redis.mybatis;

import java.util.List;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

public class EasySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(final Class<?> mapperClass) {

        final List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 添加InsertBatchSomeColumn方法
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}