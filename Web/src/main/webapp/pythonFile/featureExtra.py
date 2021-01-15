# -*- coding: utf-8 -*-
"""
Created on Fri Jul  3 08:28:37 2020

@author: LHZ
"""

import numpy
import matplotlib.pyplot as plt
import os
import random

def distribution(dataset):
    feaValue = numpy.zeros(9)
      
    for i in range(1,len(dataset)):
#        print(i)
        preLine=dataset[i-1].strip().split()
        curLine=dataset[i].strip().split()
        if(len(curLine)!=11 or len(preLine)!=11):
            continue

        preLine = [ round(float(x),18) for x in preLine ]  
        curLine = [ round(float(x),18) for x in curLine ]
        
        feaValue += (numpy.array(curLine) - numpy.array(preLine)) ** 2
        
    feaValue = ((feaValue/len(dataset)) ** 0.5)
    
    return feaValue
                
                
def shuffleDataSet():
    with open ( 'D:\\赛尔\\数据集\\chooseData\\feaAllData.csv' ) as f1:
        fileList1=f1.readlines()
#        print(len(fileList1))
#            for line in fileList1:
#                print(line)
        random.shuffle(fileList1)
        for line in fileList1:
            print(line)
        with open ( 'D:\\赛尔\\数据集\\chooseData\\feaAllDateShuf.csv','a' ) as f2:
            for line in fileList1:
                f2.write(line)
                
                
shuffleDataSet()
#distribution('D:\\赛尔\\数据集\\Tests')
#allPath('D:\\赛尔\\数据集\\Tests')