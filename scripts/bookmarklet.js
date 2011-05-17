javascript:(function(){    
 w = window.open("", "mywindow1", "status=1,width=350,height=150");
 w.document.write('<form action="http://localhost/populate_stagging.php" method="post"> <input type="hidden" name="youtube_url" value=' + document.location +'> start position : <input type="text" name="startposition"/></br> Artist name: <input type="text" name="artistname"/> </br> Song name: <input type="text" name="songname"/> <input type="submit" name="submit" value="Submit" /></form>');
	
})();

