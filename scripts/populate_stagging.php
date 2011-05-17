
<?php
$con = mysql_connect("localhost","root","ma");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

// some code
mysql_select_db("mysql", $con);

$sql_insert = "INSERT INTO media_clip_temp (youtube_url, startposition,artistname, songname, done) VALUES (" . "'" . mysql_real_escape_string($_POST['youtube_url']) ."','" . mysql_real_escape_string($_POST['startposition']) . "','" .  mysql_real_escape_string($_POST['artistname']) . "','" . mysql_real_escape_string($_POST['songname']) . "', 0)";
echo $sql_insert;

$result = mysql_query($sql_insert);
if (!$result) {
    die('Invalid query: ' . mysql_error());
}

mysql_close();
?> 

sucess!
