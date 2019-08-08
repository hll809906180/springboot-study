package cn.leadeon.mybits.mapper;

import cn.leadeon.mybits.entity.City;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CityMapper {

    List<City> getMastCityList();

    List<City> getSlaveCityList();


    void insertCitySlave01(City city);

    void insertCitySlave02(City city);

}
