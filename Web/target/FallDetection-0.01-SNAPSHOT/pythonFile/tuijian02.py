#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Nov 26 16:04:41 2020

@author: lizhi
"""


import os
import csv
import sys

def openfile(filename):
    with open(filename, "rt", encoding="utf-8") as vsvfile:
        reader = csv.reader(vsvfile)
        global rows
        rows = [row for row in reader]  # 一个文件中的所有数据，二维数组
        # print(rows)
    
    
def statFoods(args):
    prop = []
    row = []
    l = len(args)
    for i in  range(0,l):
        n = args[i] + 2
        for j in range(3,11):     # 8个属性
            if(rows[n][j] == '1'):    # n菜 j属性
                row.append(j)
        prop.append(row)
        row = []
    return(prop)
      
# dataSet = statFoods([1,15,26,27,108,125,134])        # 输入菜品编号



# 创建大小为1的所有数据集的集合，即将所有的单项放在一个list中
def createC1(dataSet):
    C1 = []
    for data in dataSet:
        for i in data:
            if [i] not in C1:
                C1.append([i])
    return list(map(frozenset,C1))

# C1 = createC1(dataSet)


def degreeSupport(dataSet,C1,minSupport=0.2):
    degreeSupportList = {}  
    dataList = [] 
    itemCount = {}   
    for data in dataSet:
        for item in C1:
            if item.issubset(data): 
                if not item in itemCount:
                    itemCount[item] = 0
                itemCount[item] += 1
    n = float(len(dataSet))
    for key in itemCount.keys():
        support = itemCount[key]/n   
        if support >= minSupport:
            dataList.append(key)
        degreeSupportList[key] = support
    return dataList,degreeSupportList

# L1,degreeSupportList = degreeSupport(dataSet,C1,0.2)


def merge(L,k):
    n = len(L)
    dataMergeList = []
    for i in range(n-1):
        for j in range(i+1,n):
            L1 = list(L[i])[:k]
            L2 = list(L[j])[:k]
            L1.sort()
            L2.sort()
            if L1 == L2:
                dataMergeList.append(L[i] | L[j])
    return dataMergeList

#
def apriori(dataSet,minSupport=0.2):
    dataSet = list(map(set,dataSet))  
    C1 = createC1(dataSet)
    L1, degreeSupportList = degreeSupport(dataSet, C1, minSupport)
    L = [L1]
    k = 0
    while len(L[k]) > 1:  
        Lm = merge(L[k],k)
        Lk,support = degreeSupport(dataSet,Lm,minSupport)
        L.append(Lk)
        degreeSupportList.update(support)
        k += 1
    return L

# L = apriori(dataSet,0.2)

# 推荐菜品
def recommendFoods(l):
    len1 = len(l);
    arr1 = l[len1-1]
    len2 = len(arr1)
    arr2 = []   #推荐属性集
    reFoodsName = []
    reFoodsNum = []
    for i in range(0,len2):
        arr2.append(list(arr1[i]))    
    # 对每道菜循环
    for j in range(2,len(rows)):
        # print(r[j][1])
        # 对属性集循环 
        for k in range(0,len2):
            x = arr2[k]
            # print(x)
            num = 0
            for h in range(0,len(x)):    
                s = arr2[k][h]   # s 是属性编号
                if rows[j][s] == '1' :
                    num += 1
            if num== len(x):    # 找到了符合要求的菜
               reFoodsName.append(rows[j][1]) 
               reFoodsNum.append(rows[j][0])
               break
    #print('推荐结果:')
    #print(reFoodsName)
    #print(reFoodsNum)
    return reFoodsNum
    
# recommendFoods(L)

def Recommend(f):
    dataSet = statFoods(f)
    C1 = createC1(dataSet)
    L1,degreeSupportList = degreeSupport(dataSet,C1,0.2)
    L = apriori(dataSet,0.2)
    result = recommendFoods(L)
    # 无推荐
    if (len(result) == 0):
        for i in range(0,len(f)):
            result.append((f[i]+3)%160)
    print(result)
    
    
    
#Recommend([1,15,26,27,108,125,134])
if __name__ == '__main__':
    L = sys.argv[1].split(",")
    L = list(map(int,L))
    openfile(sys.argv[2])
    Recommend(L)


