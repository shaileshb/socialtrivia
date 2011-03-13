#!/usr/bin/python

import sys
import json
import io
from textcluster2 import cluster, dump_clusters

# def fetch_tweets():
#     tweets=[]
#     uhandle=urllib2.urlopen('http://api.twitter.com/1/statuses/public_timeline.json')
#     jstr=uhandle.read()
#     jarray=json.loads(jstr)
#     for jobj in jarray:
#         tweets.append(jobj['text'])
#     return tweets

def read_tweets(fnames):
    tweets=[]
    for fname in fnames:
        with io.open(fname, 'r') as f:
            jstr=f.read()
            jarray=json.loads(jstr)
            for jobj in jarray:
                tweets.append(jobj['text'])
    return tweets

tweets=read_tweets(sys.argv[1:])
nclusters,centroids,cids=cluster(tweets, 8)
print
dump_clusters(nclusters, centroids, tweets, cids)

