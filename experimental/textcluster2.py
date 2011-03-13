#!/usr/bin/python

from weightdictionary import weight
import sys

def calculate_distance(str1, str2):
    diff=0
    has_common_words=False
    tstr1=str1[:]
    tstr2=str2[:]
    tstr1.sort()
    tstr2.sort()
    i = j = 0
    while i < len(tstr1) and j < len(tstr2):
        if tstr1[i] == tstr2[j]:
            i += 1
            j += 1
            has_common_words=True
        elif tstr1[i] < tstr2[j]:
            diff += weight(tstr1[i])
            i += 1
        elif tstr1[i] > tstr2[j]:
            diff += weight(tstr2[j])
            j += 1
    while i < len(tstr1):
        diff += weight(tstr1[i])
        i += 1
    while j < len(tstr2):
        diff += weight(tstr2[j])
        j += 1
    if has_common_words:
        return diff
    return sys.maxint

def calculate_centroids(nclusters, data, cids):
    # step 1: for each cluster compile a set of all words across all data points.
    wordmaps=[{} for i in range(0, nclusters)]
    for i in range(0, len(data)):
        for token in data[i]:
            if not token in wordmaps[cids[i]]:
                wordmaps[cids[i]][token] = True
    wordsets=[]
    for wordmap in wordmaps:
        wordsets.append(wordmap.keys())

    # step 2: for each cluster, find the data point that is closest to the
    # cluster wordmap computed above.
    mindistance=[sys.maxint for i in range(0, nclusters)]
    newcentroids=[[] for i in range(0, nclusters)]
    for i in range(0, len(data)):
        distance=calculate_distance(data[i], wordsets[cids[i]])
        if distance < mindistance[cids[i]]:
            mindistance[cids[i]] = distance
            newcentroids[cids[i]] = data[i]
    return newcentroids

def tokenize_data(data):
    newdata=[]
    for i in range(0, len(data)):
        newdata.append(data[i].split())
    return newdata

def cluster(data, nclusters):
    origdata=data
    data=tokenize_data(data)
    cids=[0 for i in range(0,len(data))]

    # Initial cluster centroid is first few elements
    centroids=data[0:nclusters]

    done=False
    while not done:
        spare_cluster=False
        done=True
        #print "\n\n###############################################"
        #print "centroids: " + str(centroids)
        for i in range(0, len(data)):
            mindistance=sys.maxint
            closest=-1
            for j in range(0, nclusters):
                distance = calculate_distance(data[i], centroids[j])
                if  distance <= mindistance:
                    mindistance = distance
                    closest=j
            if mindistance == sys.maxint:
                cids[i] = nclusters
                spare_cluster=True
            elif cids[i] != closest:
                cids[i]=closest
                done=False
        if spare_cluster:
            nclusters += 1
        #dump_clusters(nclusters, centroids, origdata, cids)
        if not done:
            centroids=calculate_centroids(nclusters, data, cids)
    return nclusters, centroids, cids

def dump_clusters(nclusters, centroids, data, cids):
    for i in range(0,nclusters):
        print "## Cluster " + str(i) + " : " + (str(centroids[i]) if i < len(centroids) else '*')
        tlist=[]
        for j in range(0, len(data)):
            if i == cids[j]:
                tlist.append(data[j])
        tlist.sort()
        for entry in tlist:
            print "\t## " + entry

