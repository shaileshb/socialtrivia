<?php

$filename = "foo2.html";
$handle = fopen($filename, "r");
$file = fread($handle, filesize($filename));

preg_match('/flashvars="(.*)"/', $file, $matches);
$flashvars=$matches[1];

$nv_pairs=split(';', $flashvars);

foreach ($nv_pairs as $nv_pair) {
  $nv=split('=', $nv_pair);
  // $nv[0] is name e.g. fmt_url_map
  // $nv[1] is value
  
   
  if (strcmp($nv[0], 'fmt_url_map') == 0) {
   	echo 'Value is ' ;
   	echo "\n";
   	$fmt_url_map =  urldecode($nv[1]);
   	
   	
   	$fmt_url_maps = split(',', $fmt_url_map);
   	$result = array();
   	
   	foreach ($fmt_url_maps as $fmt_r_map) {
   	    //echo $fmt_r_map;
   		echo "\n";    
   		
   		// Now split the fmt_url_map into 
   		
	   	$resolution = explode('|', $fmt_r_map);
	   	//print_r( $resolution);
	   	array_push($result, $resolution[1]);
	   		   			
   	}  
	echo "\n";     
  }
}



print_r($result);

//$wgetresult = system('wget -O mv.flv' . '"' . $result[0] . '"');

//echo $wgetresult;

?>

