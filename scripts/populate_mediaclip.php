
<?php
$con = mysql_connect("localhost","root","ma");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

// some code
mysql_select_db("mysql", $con);

$sql_select = "SELECT * FROM media_clip_temp";

$result = mysql_query($sql_select);

while($row = mysql_fetch_array($result))
  {
  echo 'youtube_url:   ' . $row['youtube_url'];

  echo 'start:  ' . $row['startposition'];
  $str = 'curl ' . '\''.$row['flvurl'] .'\'';
   
  shell_exec($str);
  
  }


mysql_close();
?> 
