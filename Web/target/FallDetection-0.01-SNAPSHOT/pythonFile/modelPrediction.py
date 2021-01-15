# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import sklearn
import numpy as np
import joblib
import random
import sys
import numpy

def distribution(dataset):
    feaValue = numpy.zeros(9)
    flag = dataset[0].strip().split()[10]
    #对当前数据集进行特征提取
    for i in range(1,len(dataset)):
#        print(i)
        preLine=dataset[i-1].strip().split()
        curLine=dataset[i].strip().split()
        #缺失值数据删除
        if(len(curLine)!=11 or len(preLine)!=11):
            continue

        #去除时间戳和标志位
        del preLine[9:11]
        del curLine[9:11]
        #属性值转化为float
        preLine = [ round(float(x),18) for x in preLine ]  
        curLine = [ round(float(x),18) for x in curLine ]
        
        #print(preLine,curLine)
        #对每个摔倒数据集中向量  相减平方
        feaValue += (numpy.array(curLine) - numpy.array(preLine)) ** 2
        
    feaValue = ((feaValue/len(dataset)) ** 0.5)
    
    #训练数据集暂时需要获取标志位，因此在特征向量中添加‘标志位’项
    feaValue = feaValue.tolist()
    feaValue.append(flag)
    return feaValue

def dataExtra(file1, file2):
    #with open('D:\\赛尔\\数据集\\chooseData\\feaAllDateShuf.csv', 'r') as f1:
    with open(file1, 'r') as f1:
        lines = f1.readlines()
        selectList=random.sample(range(0, len(lines)), 10)
        #with open('D:\\赛尔\\数据集\\chooseData\\dataRandomExtra.csv', 'w') as f2:
        with open(file2, 'w') as f2:
            for i in selectList:
                f2.write(lines[i])

def createDataset(feaValue):
    dataGroup = []
    label = []
    #判断数据集中有没有无穷大的
    if (np.isnan(list( map(float,feaValue[0:8]))).any()):
        return
    #前9个是方向、加速度、角速度
    dataGroup.append(list( map(float,feaValue[0:8])))
    #第10个是标志位
    label.append(feaValue[9])
    return dataGroup,label

def fallPrediction(feaValue):
    dataGroup,label = createDataset(feaValue)
    clf = joblib.load('E:\\赛尔\\数据集\\模型\\feaDataModel.pkl')
    pred_y = clf.predict(dataGroup)
    accuracy = sklearn.metrics.accuracy_score(label,pred_y)
    return label,pred_y,accuracy


if __name__ == '__main__':
    #dataExtra(sys.argv[1], sys.argv[2])
    #print('数据提取成功！！！')
    label,pred_y,accuracy = fallPrediction(sys.argv[1])
    #label,pred_y,accuracy = fallPrediction('E:\\赛尔\\数据集\\chooseData\\dataRandomExtra.csv')
    print(label)
    print(list(pred_y))
    print(accuracy)
    #print('标签集' + ','.join(label) +'\n' + '预测集' + ','.join(pred_y) +'\n' + '准确率' + str(accuracy))
    
#dataExtra('E:\\赛尔\\数据集\\chooseData\\feaAllDateShuf.csv', 'E:\\赛尔\\数据集\\chooseData\\dataRandomExtra.csv')