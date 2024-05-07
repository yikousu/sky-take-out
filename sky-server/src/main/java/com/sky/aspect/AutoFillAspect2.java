package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充处理逻辑
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect2 {

    /**
     * 切入点：定义了匹配哪些方法的切点
     */
    @Pointcut("execution(* com.sky.service.*Service.insert*(..)) ")
    public void autoFillPointCut() {
        // 这个方法没有具体的实现，只是一个切入点的声明
    }

    /**
     * 前置通知，在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws Exception {
        // 获取被拦截的方法的参数列表
        Object[] args = joinPoint.getArgs();

        // 获取到实体对象，假设它是参数列表的第一个参数
        Object entity = args[0];

        // 反射获取实体对象的设置创建时间的方法
        Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);

        // 通过反射为对象赋值
        setCreateTime.invoke(entity, LocalDateTime.now());
    }
}

