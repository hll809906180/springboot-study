package cn.leadeon.sprjsp.mapper;

import cn.leadeon.sprjsp.entity.TraficLuck;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface TraficluckMapper {
    List<TraficLuck> find(Map<String,Object> params);

    Integer total(Map<String,Object> params);
}
