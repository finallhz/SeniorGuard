# -*- coding: utf-8 -*-
"""
Created on Thu Aug  6 15:26:00 2020

@author: Administrator
"""
import sys
import json
import time
import modelPrediction
from modelPrediction import fallPrediction
from modelPrediction import distribution

def csvToJson(fileName):
    ls = []
    #json文件中的key
    list0 = ['acc', 'gyr', 'timestamp']
    #list1 = ['0.0000011111', '0.0000022222', '0.0000033333']
    #预测结果
    pred_y = 0
    with open(fileName, 'r') as fr:
        #读取数据文件
        lines = fr.readlines()
        cutpoint = 0
        for i in range(0, len(lines)):
            #如果遇到标记，意味当前运动数据结束
            if(lines[i] == 'This line is over\n'):
                #截取此次运动的数据集，该数据集还是原始格式，需要处理
                sportdata = lines[cutpoint:i]
                #特征向量
                feaValue = distribution(sportdata)
                #print(feaValue)
                #执行摔倒检测 返回值为 标记值，预测值，准确度
                label,pred_y,accuracy = fallPrediction(feaValue)
                #print("pred_y:",pred_y,"label",label)
                #print(i)
                #print(pred_y[0])
                #封装成json数据返回
                for j in range(0, len(ls)):
                    ls[j]= dict(zip(list0, ls[j]))
                ls.append({'predResult' : pred_y[0]})
                jsonset = json.dumps(ls)
                print(jsonset)
                ls = []
                #跳过当前提示行
                cutpoint = i+1
                continue
            curLine = lines[i].strip().split()
            acc = (float(curLine[3])**2+float(curLine[4])**2+float(curLine[5])**2)**0.5
            gyr = (float(curLine[6])**2+float(curLine[7])**2+float(curLine[8])**2)**0.5
            curLine[9] = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(int(curLine[9])))
            s = [str(acc), str(gyr), curLine[9]]
            #print(s)
            ls.append(s)

        #for i in range(0, len(ls)):
        #    ls[i]= dict(zip(list0, ls[i]))
            #print(ls[i])
            # zip()是一个内置函数，将两个长度相同的列表组合成一个关系对
        #jsonset = json.dumps(ls)
        
        #print(jsonset)
        #print(len(ls))

 
if __name__ == '__main__':
    csvToJson(sys.argv[1])
           
#csvToJson('E:\赛尔\数据集\chooseData\webData1.csv', 1100)