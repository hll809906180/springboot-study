package cn.tycoon.sprbase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-11 14:41
 **/
@RestController
@RequestMapping("/good")
public class GoodManagerController{

   private static Logger logger = LoggerFactory.getLogger(GoodManagerController.class);

   @RequestMapping("/info")
   public Map<String,Object> getGoodInfo(String cellNum){
      long startTime = System.currentTimeMillis();
      System.out.println(startTime);
      logger.info("请求参数:"+cellNum);
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("1",1);
      logger.info("接口消费时间:"+(System.currentTimeMillis()-startTime));
      return  map;
   }

}
