#!/usr/bin/python

from weightdictionary import weightdictionary
import sys
import random

# returns an integer
def calculate_distance(str1, str2):
    tokens1 = str1.split()
    tokens2 = str2.split()
    diff = 0
    for x in tokens1:
        if not x in tokens2:
            diff += weightdictionary[x]
    for x in tokens2:
        if not x in tokens1:
            diff += weightdictionary[x]
    return diff

def calculate_centroids(nclusters, data, cids):
    wordcounts=[{} for i in range(0, nclusters)]
    for i in range(0, len(data)):
        tokens=data[i].split() # This should only be done once. data never changes.
        for token in tokens:
            if not token in wordcounts[cids[i]]:
                wordcounts[cids[i]][token] = 1
            else:
                wordcounts[cids[i]][token] += 1

    # now find the most frequent token in each wordcount[i] and call it the centroid.
    newcentroids=[]
    for i in range(0, nclusters):
        # wordcounts[i] is a dictionary. keys are words and values are counts.
        # find the max for each wordcount[i]
        maxcount=0
        maxword=''
        for word, count in wordcounts[i].iteritems():
            if count > maxcount:
                maxcount = count
                maxword = word
        # add word to the new centroid list
        newcentroids.append(maxword)
    return newcentroids

def cluster(data, nclusters):
    cids=[0 for i in range(0,len(data))]
    """ Initial cluster centroid is first few elements """
    centroids=data[0:nclusters]

    done=False
    while not done:
        done=True
        print "centroids: " + str(centroids)
        for i in range(0, len(data)):
            mindistance=sys.maxint
            closest=-1
            for j in range(0, nclusters):
                distance = calculate_distance(data[i], centroids[j])
                if  distance < mindistance:
                    mindistance = distance
                    closest=j
            if cids[i] != closest:
                cids[i]=closest
                done=False
        if not done:
            centroids=calculate_centroids(nclusters, data, cids)
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
data=["pacific ocean",
      "pacific",
      "the pacific ocean",
      "pacific ocean",
      "atlantic",
      "atlantic ocean",
      "ocean",
      "sea",
      "indian ocean",
      "pacific ocean",
      "indian",
      "atlantic",
      ]

random.shuffle(data)
print "data: " + str(data)

cids=cluster(data, nclusters)
dump_clusters(nclusters, data, cids)

