$(document).ready(function() {
  // Handler for .ready() called.

	var uid;
	
    $("#createquestion").click(function() {

        
        options = [$("#option1").val(),$("#option2").val(), $("#option3").val()];  
        question = $("question").val();
         
        var question = {"text" : question,
        				"uid" : '1300034181-1',
        				"options": options, 
                        };
            
     
        $.ajax({
           url: "http://192.168.0.101:8080/rest/question",
           async:false,
           type: "PUT",
           dataType: "json",
           data: question,
          success: function(){
            alert("inside success");
          },
          failure: function(data ) {
            alert("inside failure");
          },
          error: function() {
            alert("error");
          }
        });   
    });

    $("#login").click(function() {

        $.ajax({
          url: "http://192.168.0.101:8080/rest/user/create",
           async:false,
           type: "PUT",
           dataType: "json",
           
          success: function(data){
           
            alert(data.uid);
            uid=data.uid;
            
          },
          failure: function() {
            alert("inside failure");
          }
        });
           
        
    });
});
