package cn.leadeon.rediscluster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: redis
 * @description:
 * @author: Mr.he
 * @create: 2019-03-06 10:13
 **/
@RestController
@RequestMapping(value="/redis")
@Api(value = "缓存测试接口", description = "缓存测试接口")
public class RedisController {
    private static final Log LOGGER = LogFactory.getLog(RedisController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "获取value", notes = "获取value")
    @RequestMapping(value="/get", method = RequestMethod.POST)
    public String get(@RequestParam  String key){
        String rdsVal = null;
        try {
             rdsVal = (String)redisTemplate.opsForValue().get(key);
            LOGGER.info("获取key:key001 的值为"+rdsVal);
            return  rdsVal;
        }catch (Exception e){
            LOGGER.error("获取缓存数据失败",e);
            return  rdsVal;
        }
    }

    @ApiOperation(value = "新增key-value", notes = "新增key-value")
    @RequestMapping(value="/set", method = RequestMethod.POST)
    public String set(String key,String value){
        try {
            if(key!=null&&!("").equals(key)){
                redisTemplate.opsForValue().set(key,value);
            }else{
                LOGGER.info("数据校验异常--》key不能为空");
                return "false";
            }
            LOGGER.info("新增缓存数据成功");
            return "suceess";
        }catch (Exception e){
            LOGGER.info("新增缓存数据失!");
            return "false";
        }
    }
}
