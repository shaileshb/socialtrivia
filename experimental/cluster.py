#!/usr/bin/python

import random

def cluster(data, nclusters):
    cids=[0 for i in range(0,len(data))]

    centroids=data[0:nclusters]

    done=0
    while not done:
        done=1
        for i in range(0, len(data)):
            mindist=sys.maxint
            closest=-1
            for j in range(0, nclusters):
                if abs(data[i]-centroids[j]) < mindist:
                    mindist = abs(data[i]-centroids[j])
                    closest=j
            if cids[i] != closest:
                cids[i]=closest
                done=0

        print "####################################################"
        print "centroids: " + str(centroids)
        dump_clusters(nclusters, data, cids)

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


def dump_clusters(nclusters, data, cids):
    for i in range(0,nclusters):
        tlist=[]
        for j in range(0, len(data)):
            if i == cids[j]:
                tlist.append(data[j])
        tlist.sort()
        print tlist

nclusters=4
data=[]

for j in range(0, nclusters):
    for i in range(0,10):
        data.append(100 * j + random.randint(0,20))

print "data: " + str(data)

nclusters=5
cids=cluster(data, nclusters)
dump_clusters(nclusters, data, cids)

