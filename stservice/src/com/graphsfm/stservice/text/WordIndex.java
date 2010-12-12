package com.graphsfm.stservice.text;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordIndex {
  private static Logger log = Logger.getLogger(Dictionary.class.getName());
  private HashMap<String, ArrayList<FilePositions>> idx = new HashMap<String, ArrayList<FilePositions>>();
  private Dictionary dict = Dictionary.getInstance();

  class FilePositions {
    long fileid;
    ArrayList<Long> poslist;
  }

  public boolean addWord(String str, long fileid, long pos) {
    /*
     * Filter out certain strings. e.g. words that are either very rare (e.g.
     * spelling mistakes) or extremely common "stop words".
     * http://en.wikipedia.org/wiki/Stop_words
     * 
     * Our definition of stop words is completely arbitrary. It has more to do
     * with what kind of computation we can do on a single appengine node.
     * 
     * Ideally we would not ignore any words, but that will require sharding the
     * word index across a number of serving machines.
     */
    if (str.length() < 3)
      return false;
    int freq = dict.getFrequency(str);
    if (freq == 0 || freq > 90000)
      return false;

    ArrayList<FilePositions> fplist = idx.get(str);
    if (fplist == null) {
      fplist = new ArrayList<FilePositions>();
      idx.put(str, fplist);
    }

    if (fplist.size() == 0 || fplist.get(fplist.size() - 1).fileid != fileid) {
      FilePositions fp = new FilePositions();
      fp.fileid = fileid;
      fp.poslist = new ArrayList<Long>();
      fplist.add(fp);
    }
    fplist.get(fplist.size() - 1).poslist.add(pos);
    return true;
  }

  private void writeMap() {
    for (Map.Entry<String, ArrayList<FilePositions>> e : idx.entrySet()) {
      System.out.print(e.getKey());
      for (FilePositions fp : e.getValue()) {
        System.out.print(" " + fp.fileid);
        for (Long pos : fp.poslist)
          System.out.print(" " + pos);
        System.out.print(" . ");
      }
      System.out.println();
    }
  }

  public void initFromText(String fname) {
    long count = 0;
    long last = 0;
    long values = 0;

    try {
      DataInputStream is = new DataInputStream(new FileInputStream(fname));
      while (true) {
        long fileid;
        try {
          fileid = is.readLong();
        } catch (EOFException e) {
          break;
        }
        long len = is.readLong();
        byte[] buf = new byte[(int) len];
        is.read(buf);

        String str = new String(buf);
        String[] tokens = str.split("[^a-zA-Z]+");

        int pos = 0;
        for (String t : tokens) {
          t = new String(t.toLowerCase());
          boolean added = addWord(t, fileid, pos++);
          if (added) {
            values++;
          }
        }

        count += len;
        if (count > last + 100 * 1024 * 1024) {
          System.err
              .printf(
                  "processed = %d Mb, # words = %d, # positions = %d, lastfileid = %d\n",
                  count / 1024 / 1024, idx.size(), values, fileid);
          last = count;
        }
      }
    } catch (IOException e) {
      log.log(Level.WARNING, "Error: " + fname, e);
    } finally {
    }
  }

  public static void main(String args[]) {
    WordIndex wi = new WordIndex();
    wi.initFromText(args[0]);
    wi.writeMap();
  }
}
