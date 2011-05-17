DROP TABLE media_clip_temp;
DROP TABLE media_clip;

-- This is stagging database
CREATE TABLE media_clip_temp ( 
        youtube_url VARCHAR (600),
        startposition VARCHAR (10),
        endposition VARCHAR (10),
        artistname VARCHAR (100),
        songname VARCHAR (100),
        done INT);

-- This database is used at run time
CREATE TABLE media_clip ( 
         url VARCHAR (30), 
         artistname VARCHAR (100),
         songname VARCHAR(100));
