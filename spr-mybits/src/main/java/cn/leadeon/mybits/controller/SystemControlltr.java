package cn.leadeon.mybits.controller;

import cn.leadeon.mybits.entity.City;
import cn.leadeon.mybits.entity.ResBody;
import cn.leadeon.mybits.service.CityManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-16 17:00
 **/
@RequestMapping("/text")
@RestController
public class SystemControlltr {

    @Autowired
    private CityManger cityManger;

    @RequestMapping("/getCityInfo")
    public List<City> getCityInfo(String dbName){
        List<City> list = new ArrayList<City>();
        if(dbName.equals("m")){
            list = cityManger.getMastCityList();
        }else{
            list = cityManger.getSlaveCityList();
        }
        return list;
    }
    @RequestMapping("/insertCify")
    public ResBody insterCift(){
        ResBody resBody = new ResBody();
        try {
            City city = new City();
            cityManger.insertCity(city);
            resBody.setResCode("200");
            resBody.setResMessage("新增成功");
        }catch (Exception e){
            e.printStackTrace();
            resBody.setResCode("201");
            resBody.setResMessage("失败");
        }

        return resBody;
    }

}
