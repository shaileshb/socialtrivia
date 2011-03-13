#!/usr/bin/python

import random
from textcluster2 import cluster, dump_clusters

nclusters=3
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
      "southern ocean",
      "lake",
      "lake baikal",
      "crater lake"
      ]

random.shuffle(data)
print "data: " + str(data)

nclusters, centroids, cids=cluster(data, nclusters)
print
dump_clusters(nclusters, centroids, data, cids)

