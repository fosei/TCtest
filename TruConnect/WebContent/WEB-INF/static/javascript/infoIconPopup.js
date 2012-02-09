//$(function() {
//	$("img.info").mousemove(function(e) {
//		$(this).next().show();
//		$(this).next().css({
//			top : ($(this).position().top + 10) + "px",
//			left : ($(this).position().left - 335) + "px"
//		});
//	});
//	$("img.info").mouseout(function() {
//		$(this).next().hide();
//	});
//});
$(function() {
	$("img.info").staticMousePopup(-355, 10);
});