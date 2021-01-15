package com.snnu.Service;

import com.snnu.Dao.FallStatisticsDao;
import com.snnu.POJO.FallData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public class FallStatisticsService {
    @Autowired
    private FallStatisticsDao fallStatisticsDao;

    //添加摔倒记录
    public int addFallData(FallData fallData){
        return fallStatisticsDao.addFallData(fallData);
    }
    //查询当前用户所有摔倒记录
    public List<FallData> getAllFallData(int uid){
        return fallStatisticsDao.getAllFallData(uid);
    }
    //修改误报记录，标记位flag置为0
    public int updateFallData(FallData fallData){
        return fallStatisticsDao.updateFallData(fallData);
    }

    //统计数据
    public List<HashMap> getFallStatistic(int uid){
        return fallStatisticsDao.getFallStatistic(uid);
    }

    //误报统计
    public List<HashMap> getSucErr(int uid){
        return fallStatisticsDao.getSucErr(uid);
    }
}
