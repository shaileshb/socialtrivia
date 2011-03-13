#!/usr/bin/python

import random

def build_canopies(data, t1, t2):
    deleted=[False for i in range(0,len(data))]
    canopies=[]
    while True:
        canopyroot=-1
        for i in range(0, len(deleted)):
            if not deleted[i]:
                canopyroot=i
                break
        if canopyroot == -1:
            break
        canopy=[]
        for i in range(0, len(data)):
            dist=abs(data[i]-data[canopyroot])
            if dist < t2:
                canopy.append(data[i])
            if dist < t1:
                deleted[i]=True
        canopies.append(canopy)
    return canopies

def cluster(data, nclusters):
    cids=[0 for i in range(0,len(data))]

    centroids=data[0:nclusters]

    done=0
    while not done:
        done=1
        for i in range(0, len(data)):
            mindist=2**31-1
            closest=-1
            for j in range(0, nclusters):
                if abs(data[i]-centroids[j]) < mindist:
                    mindist = abs(data[i]-centroids[j])
                    closest=j
            if cids[i] != closest:
                cids[i]=closest
                done=0

        if not done:
            newguess=[0 for i in range(0, nclusters)]
            newcounts=[0 for i in range(0, nclusters)]
            for i in range(0, len(data)):
                newguess[cids[i]] = newguess[cids[i]] + data[i]
                newcounts[cids[i]] = newcounts[cids[i]] + 1
            for j in range(0, nclusters):
                if newcounts[j] !=0:
                    newguess[j] = 1.0*newguess[j]/newcounts[j]
            centroids=newguess
    print "cluster centers: " + str(centroids)
    return cids


nclusters=10
data=[]

for j in range(0, nclusters):
    for i in range(0,10):
        data.append(100 * j + random.randint(0,20))

canopies=build_canopies(data, 100, 200)
for i in range(0, len(canopies)):
    canopies[i].sort()
    print canopies[i]

#cids=cluster(data, nclusters)

#print data
#print
# for i in range(0,nclusters):
#     tlist=[]
#     for j in range(0, len(data)):
#         if i == cids[j]:
#             tlist.append(data[j])
#     tlist.sort()
#     print tlist
# 
