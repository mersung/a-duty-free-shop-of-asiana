$(document).ready(function(){
	$.ajax({
		url:"/team0/exchange",
		method:"GET",
		dataType:"text",
		header:{"Content-Type":"application/json"},
	}).done(function (fragment) {
		alert("성공");
    }).fail(function() {
  		alert("실패");
 	}).always(function() {
  		alert("완료");
	});
	});