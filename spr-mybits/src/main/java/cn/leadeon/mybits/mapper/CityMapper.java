package cn.leadeon.mybits.mapper;

import cn.leadeon.mybits.comm.dbconfig.TargetDataSource;
import cn.leadeon.mybits.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface CityMapper {

    List<City> getMastCityList();

    List<City> getSlaveCityList();


    void insertCitySlave01(City city);

    void insertCitySlave02(City city);

}
