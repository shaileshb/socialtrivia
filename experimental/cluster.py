#!/usr/bin/python

import random

def cluster(data, nclusters):
    cids=[0 for i in range(0,len(data))]
    guess=range(0,nclusters)
    #guess=data[0:nclusters]
    done=0
    while not done:
        done=1
        for i in range(0, len(data)):
            mindist=2**31-1
            closest=-1
            for j in range(0, nclusters):
                if abs(data[i]-guess[j]) < mindist:
                    mindist = abs(data[i]-guess[j])
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
            guess=newguess
    print "cluster centers: " + str(guess)
    return cids


nclusters=4
data=[]

for i in range(0,10):
    for j in range(0, nclusters):
        data.append(1000 * j + random.randint(0,50))

cids=cluster(data, nclusters)

#print data
#print
for i in range(0,nclusters):
    tlist=[]
    for j in range(0, len(data)):
        if i == cids[j]:
            tlist.append(data[j])
    tlist.sort()
    print tlist


