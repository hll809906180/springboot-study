package cn.leadeon.mybits.service;

import cn.leadeon.mybits.comm.dbconfig.TargetDataSource;
import cn.leadeon.mybits.entity.City;
import cn.leadeon.mybits.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-16 16:57
 **/
@Service
public class CityManger {
    @Autowired
    private CityMapper cityMapper;

    public List<City> getMastCityList(){
        return  cityMapper.getMastCityList();
    }

    public List<City> getSlaveCityList(){
        return cityMapper.getSlaveCityList();
    }
    @TargetDataSource("slave1")
    public void insertCity(City sss)throws Exception{
        City city = new City();
        city.setCityCode("0004");
        city.setCityName("西安");
        cityMapper.insertCitySlave01(city);
    }
}
