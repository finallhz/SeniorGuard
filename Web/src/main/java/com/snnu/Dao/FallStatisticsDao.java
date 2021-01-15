package com.snnu.Dao;

import com.snnu.POJO.FallData;

import java.util.HashMap;
import java.util.List;

public interface FallStatisticsDao {
    //添加摔倒记录
    int addFallData(FallData fallData);
    //查询所有摔倒记录
    List<FallData> getAllFallData(int uid);
    //修改误报记录，标记位flag置为0
    int updateFallData(FallData fallData);
    //统计数据
    List<HashMap> getFallStatistic(int uid);
    //误报统计
    List<HashMap> getSucErr(int uid);
}
