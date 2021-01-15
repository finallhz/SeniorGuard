#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Dec 14 18:48:33 2020

@author: lizhi
"""
from math import sqrt
import numpy
import os
import sys

def dataProc(filename, endTime, weight):
    data = []
    sumEnergy = 0
    if(os.path.exists(filename)):
        with open (filename, 'r') as fr:
            lines = fr.readlines()
            startTime = int(lines[0].replace('\r','').replace('\n','').strip().split(' ')[8])
            for line in lines:
                line = line.replace('\r','').replace('\n','').strip().split(' ')
                if int(line[8])<=endTime:
                    dataline = [float(x) for x in line[0:3]]
                    dataline.append(int(line[8]))
                    dataline.append(int(line[7]))
                    if int(line[8]) >= startTime+30:
                        #print("if执行次数")
                        data = numpy.array(data)
                        ee = EE(data, weight)
                        sumEnergy = sumEnergy + ee
                        data = []
                        startTime = startTime + 30
                    data.append(dataline)
                else:
                    #print("else执行次数")
                    if data:
                        data = numpy.array(data)
                        ee = EE(data, weight)
                        sumEnergy = sumEnergy + ee
                    break
            #print("开始时间", startTime)
    #print(len(data))
    return sumEnergy


#sz [[ax,ay,az,t,hr]]心率
def EE(sz,w):
    l = len(sz)
    vm = 0
    f = 1 #计数
    x0 = sz[0][0]
    y0 = sz[0][1]
    z0 = sz[0][2]
    a2 = sqrt(x0*x0+y0*y0+z0*z0)
    vm = 0
    HR0 = sz[0][4]
    for i in range(1,l):
        HR0 = HR0 + sz[i][4]
        x = sz[i][0]
        y = sz[i][1]
        z = sz[i][2]
        a = sqrt(x*x+y*y+z*z)   #总加速度
        t1 = sz[i-1][3]
        t2 = sz[i][3]
        if(t2 == t1):
            a2 = a2 + a
            f = f + 1
        else:
            a_avg = a2/f
            vm = vm + a_avg
            f = 1
            a2 = a
    HR = HR0/l
    ee = 0.00014*vm + 0.15*w + 0.004*HR + 1.071
    #print(ee)
    return ee

if __name__ == '__main__':
    energy = dataProc(sys.argv[1],int(sys.argv[2]),int(sys.argv[3]))
    print(energy)

#energy = dataProc("E:\\赛尔\\data flush\\new\\2020-12-15.csv", 1608022800, 70)
#print(energy)
    
