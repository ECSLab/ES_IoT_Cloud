package com.wust.iot.service;

import com.wust.iot.model.DataType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataTypeService {

    /**
     * 查找一个数据类型
     * @param id
     * @return
     */
    DataType findOneDataType(Integer id);

    /**
     * 找出全部的数据类型
     * @return
     */
    List<DataType> findDataTypeList();

    /**
     * 插入新的数据类型
     * @param dataType
     * @return
     */
    int insertOneDataType(DataType dataType);

    /**
     * 修改一个新的数据类型
     * @param dataType
     * @return
     */
    int updateOneDataType(DataType dataType);

    /**
     * 删除一个数据类型
     * @param id
     * @return
     */
    int deleteOneDataType(Integer id);
}
