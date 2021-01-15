# -*- coding: utf-8 -*-
"""
Created on Fri Jul  3 09:20:27 2020

@author: LHZ
"""

from sklearn.model_selection  import cross_val_score
import matplotlib.pyplot as plt
from sklearn.neighbors import KNeighborsClassifier
from sklearn import model_selection
import numpy as np
#from sklearn.externals import joblib
import joblib
import sys
import os

def createDataset(filename):
    dataGroup = []
    label = []
    with open (filename, 'r') as f1:
        for line in f1.readlines():
            line=line.strip().split()
            #判断数据集中有没有无穷大的
            if (np.isnan(list( map(float,line[0:6]))).any()):
                continue
            dataGroup.append(list( map(float,line[0:6])))
            label.append(line[6])
            #print(line[0:6])
            #print(line[6])
    return dataGroup,label

def BestK(filename,testPrecent,crossTimes):
    dataGroup,label = createDataset(filename)
    ## 区分训练集和测试集，75%的训练集和25%的测试集
    ##train_data, test_data = model_selection.train_test_split(dataGroup,label,test_size=testPrecent)
    k_range = range(1, 31)
    k_error = []
    minError = 1
    minK = 1
    #循环，取k=1到k=31，查看误差效果
    for k in k_range:
        knn = KNeighborsClassifier(n_neighbors=k)
        #cv：选择每次测试折数  accuracy：评价指标是准确度,可以省略使用默认值
        scores = cross_val_score(knn, dataGroup, label, cv=crossTimes, scoring='accuracy')
        k_error.append(1 - scores.mean())
        if ( (1 - scores.mean()) < minError):
            minError = 1 - scores.mean()
            minK = k
    #print("minK=",minK,"k_error=",k_error,"minError=",minError)
    print(k_error)
    print(minK)
    
    '''
    #画图，x轴为k值，y值为误差值
    plt.plot(k_range, k_error)
    plt.xlabel('Value of K for KNN')
    plt.ylabel('Error')
    plt.show()
    '''
    return dataGroup,label,minK

def ModelTrain(filename,testPrecent, crossTimes, savePath):
    dataGroup,label,minK = BestK(filename,testPrecent,crossTimes)
    train_X,test_X,train_y,test_y = model_selection.train_test_split(dataGroup,label,test_size=testPrecent,random_state=crossTimes)	# 0.25 3这里划分数据以1/3的来划分 训练集训练结果 测试集测试结果
    # 模型训练
    k = minK
    clf = KNeighborsClassifier(n_neighbors=k)#使用KNeighborsClassifier创建分类器
    #对于监督学习，训练数据集包括两部分：输入和结果 通过fit()函数来训练模型
#    clf.fit(train_X,train_y)
    clf.fit(train_X,train_y)
    
    #保存模型
    currPath = savePath
    currPath = currPath + '\\model'
    if os.path.exists(currPath+'\\feaDataModel.pkl'):
        os.remove(currPath+'\\feaDataModel.pkl')
    if not os.path.exists(currPath):
        os.makedirs(currPath)
    joblib.dump(clf, currPath+'\\feaDataModel.pkl')
    
#    pred_y=clf.predict(test_X)
#    print(sklearn.metrics.accuracy_score(test_y,pred_y))
    score = clf.score(test_X,test_y)
    print (format(score))
    
if __name__ == '__main__':
    #数据集路径  训练集比例  交叉验证次数
    ModelTrain(sys.argv[1], float(sys.argv[2]), int(sys.argv[3]), sys.argv[4])