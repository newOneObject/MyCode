package Mapper;

import com.example1.Domain.Messgae;

public interface MessgaeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Messgae record);

    int insertSelective(Messgae record);

    Messgae selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Messgae record);

    int updateByPrimaryKeyWithBLOBs(Messgae record);

    int updateByPrimaryKey(Messgae record);
}