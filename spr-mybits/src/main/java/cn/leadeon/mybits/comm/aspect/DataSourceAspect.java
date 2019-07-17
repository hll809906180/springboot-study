package cn.leadeon.mybits.comm.aspect;

import cn.leadeon.mybits.comm.dbconfig.DynamicDataSourceHolder;
import cn.leadeon.mybits.comm.dbconfig.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-16 15:44
 **/
@Component
@Aspect
@Order(1)
public class DataSourceAspect {

    private final static Logger log= LoggerFactory.getLogger(DataSourceAspect.class);

    @Value("${slave.hosts}")
    private String slaveHosts;

    //切换放在mapper接口的方法上，所以这里要配置AOP切面的切入点
    @Pointcut("execution( * cn.leadeon.mybits.service.*.*(..))")
    public void dataSourcePointCut() {
    }

    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?>[] clazz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = clazz[0].getMethod(method, parameterTypes);
            //如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
            if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource data = m.getAnnotation(TargetDataSource.class);
                String dataSourceName = data.value();
                //判断指定的数据源类型，如果是slave，则调用LB方法，随机分配slave数据库
                if (dataSourceName.equals("slave")){
                    dataSourceName = slaveLoadBalance();
                }
                DynamicDataSourceHolder.putDataSource(dataSourceName);
                log.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
            } else {
                log.debug("switch datasource fail,use default");
            }
        } catch (Exception e) {
            log.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
        }



      /*  //@within在类上设置
        //@annotation在方法上进行设置
        @Pointcut("@within(com.common.utils.manydatasource.DynamicSwitchDataSource)||@annotation(com.common.utils.manydatasource.DynamicSwitchDataSource)")
        public void pointcut() {}

        @Before("pointcut()")
        public void doBefore(JoinPoint joinPoint)
        {
            Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
            DynamicSwitchDataSource annotationClass = method.getAnnotation(DynamicSwitchDataSource.class);//获取方法上的注解
            if(annotationClass == null){
                annotationClass = joinPoint.getTarget().getClass().getAnnotation(DynamicSwitchDataSource.class);//获取类上面的注解
                if(annotationClass == null) return;
            }
            //获取注解上的数据源的值的信息
            String dataSourceKey = annotationClass.dataSource();
            if(dataSourceKey !=null){
                //给当前的执行SQL的操作设置特殊的数据源的信息
                HandlerDataSource.putDataSource(dataSourceKey);
            }
            log.info("AOP动态切换数据源，className"+joinPoint.getTarget().getClass().getName()+"methodName"+method.getName()+";dataSourceKey:"+dataSourceKey==""?"默认数据源":dataSourceKey);
        }

        @After("pointcut()")
        public void after(JoinPoint point) {
            //清理掉当前设置的数据源，让默认的数据源不受影响
            HandlerDataSource.clear();
        }
*/
    }

    //执行完切面后，将线程共享中的数据源名称清空
    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint){
        DynamicDataSourceHolder.removeDataSource();
    }

    //自己实现的随机指定slave数据源的LB
    private  String slaveLoadBalance() {
        String[] slaves = slaveHosts.split(",");
        //通过随机获取数组中数据库的名称来随机分配要使用的数据库
        int num = new Random().nextInt(slaves.length);
        return slaves[num];
    }
}
