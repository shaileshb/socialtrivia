<?php

function getFMTURls() {   

		$filename = "foo.html";
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
	return $result;
}

$row = -1;

$song_prefix = 'yearlyhit';
$output_file = $song_prefix. '.json';

$fp = fopen($output_file, 'w');
fwrite($fp, '{"mediaclip": ['. "\n");


if (($handle = fopen("ShaileshMusicList.csv", "r")) !== FALSE) {
    while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
          if ($row == -1)
          {     
                $row = $row +1;
          		continue;
          }
          $row = $row +1;
        list($youtubeurl, $start, $end, $artist, $yearpublished, $songname )  = $data;
        echo $youtubeurl;
        echo $songname;
        
		system('wget -O foo.html '. $youtubeurl , $retval);
        if ($retval != False) {
        	echo 'Failed to get ' . $youtubeurl;
        	continue;
        }
        $fmt_urls = getFMTURls();
        echo 'FMT URLS';
        echo $fmt_urls[0];
        echo 'End of FMT URL';
        
        foreach ($fmt_urls as $fmt) {
        	system('wget --timeout 10 --tries=2  -O foo.flv ' . '"' . $fmt . '"', $ret);
        	if ($ret == 0)
        		break;
        }
        if($ret != 0)
        	continue;
        $totaltime = ($end - $start) * 100;
         $start = $start *100;
        $mp3filename = $song_prefix . '_' . (string)$row . '.mp3';
		     
		     
		system('rm foo.flac foo-normalized.flac ');   
        system('ffmpeg -i foo.flv  -ss '. $start .' -t ' . '30' . ' foo.flac');
        system('sox --norm foo.flac  foo-normalized.flac');
        system('ffmpeg -i foo-normalized.flac -acodec libmp3lame ' . $mp3filename);

        if ($row > 1 )
            fwrite($fp , ',');
            
        fwrite($fp , '{"location": "http://www.earbuzilla.com/mediaclip/'. $mp3filename .'",');
        fwrite($fp, "\n");
        
        fwrite($fp, '"band": "' . $artist . '",');
        fwrite($fp, "\n");
        fwrite($fp, '"song": "' . $songname . '"}');
        
        
        
     }
     
     fwrite($fp, ']}');
    fclose($handle);
    fclose($fp);
}
?>


<?php


#print_r($result);

#ffmpeg -i prudence -acodec libmp3lame -ss 25 -t 10 p1.mp3


?>

