package cn.tycoon.sprlog4j2.controller;

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
public class GoodManagerController {

   private static Logger logger = LoggerFactory.getLogger(GoodManagerController.class);

   @RequestMapping("/info")
   public Map<String,Object> getGoodInfo(Good good){
      long startTime = System.currentTimeMillis();
      logger.info("请求参数:"+good);
      Map<String,Object> map = new HashMap<String,Object>();
      try {
         map.put("name",good.name);
      }catch (Exception e){
         logger.error("获取商品信息异常!"+e.getMessage(),e);
      }
      logger.info("接口消费时间:"+(System.currentTimeMillis()-startTime));
      return  map;
   }

   class Good{
      private String name;

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }
   }

}
