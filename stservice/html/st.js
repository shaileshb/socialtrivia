$(document).ready(function() {
  // Handler for .ready() called.

	var uid;
	
    $("createquestion").click(function() {

        alert("inside the question");
           
        var question = {"text" : "My question",
        				"uid" : uid,
        				"options": "this is my option", 
                        };
            
     
        $.ajax({
           url: "http://192.168.0.101:8080/rest/question",
           async:true,
           type: "PUT",
           crossDomain: true,
           dataType: "jsonp",
           data: question,
          success: function(){
            alert("inside success");
          },
          failure: function() {
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
           alert("inside success");
            alert(data.uid);
            uid=data.uid;
            
          },
          failure: function() {
            alert("inside failure");
          }
        });
           
        
    });
});
