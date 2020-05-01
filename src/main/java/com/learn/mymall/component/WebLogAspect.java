package com.learn.mymall.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.learn.mymall.entity.WebLogEntity;
import io.swagger.annotations.ApiOperation;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: likj
 * @create: 2020-05-01 16:57
 * @description: 统一日志处理切面
 * @program: myMall
 */
@Aspect
@Order(1)
@Component
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.learn.mymall.controller.*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning() throws Throwable{

    }
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=requestAttributes.getRequest();
        //记录请求信息
        WebLogEntity webLogEntity = new WebLogEntity();
        //返回结果
        Object proceed = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature= (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            //操作描述
            webLogEntity.setDescription(apiOperation.value());
        }
        long endTime = System.currentTimeMillis();
        String URLstring = request.getRequestURI().toString();
        //根路径
        webLogEntity.setBasePath(StrUtil.removeSuffix(URLstring, URLUtil.url(URLstring).getPath()));
        //IP地址
        webLogEntity.setIp(request.getRemoteUser());
        //请求类型
        webLogEntity.setMethod(request.getMethod());
        //请求参数
        webLogEntity.setParameter(getParameter(method,joinPoint.getArgs()));
        //请求返回的结果
        webLogEntity.setResult(proceed);
        //消耗时间
        webLogEntity.setSpendTime((int) (endTime - startTime));
        //操作时间
        webLogEntity.setStartTime(startTime);
        //URI
        webLogEntity.setUri(request.getRequestURI());
        //URL
        webLogEntity.setUrl(request.getRequestURL().toString());
        LOGGER.info("{}", JSONUtil.parse(webLogEntity));
        return proceed;

    }
    /**
     * 根据方法和传入的参数获取请求参数
     * */
    private Object getParameter(Method method,Object[] args){
        List<Object> argsList=new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i=0;i<parameters.length;i++){
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody!=null)
                argsList.add(args[i]);
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam!=null){
                Map<String,Object> map=new HashMap<>();
                String key= parameters[i].getName();
                if (StringUtils.isEmpty(requestParam.value()))
                    key=requestParam.value();
                map.put(key,args[i]);
                argsList.add(map);
            }
        }
        if (argsList.size()==0){
            return null;
        }else if (argsList.size()==1){
            return argsList.get(0);
        }else {
            return argsList;
        }
    }


}
