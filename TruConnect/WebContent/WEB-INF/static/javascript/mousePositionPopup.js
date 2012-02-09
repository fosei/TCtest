$.fn.mousePopup = function() {
	$(this).mousemove(function(e) {
		$(this).next(".tooltip").show().css({
			top : e.pageY + "px",
			left : e.pageX + "px"
		});
	});
	$(this).mouseout(function() {
		$(this).next(".tooltip").hide();
	});
};

//10, -335
$.fn.staticMousePopup = function(xOffset, yOffset) {
	$(this).mousemove(function(e) {
		$(this).next(".tooltip").show().css({
			top : ($(this).position().top + yOffset) + "px",
			left : ($(this).position().left + xOffset) + "px"
		});
	});
	$(this).mouseout(function() {
		$(this).next(".tooltip").hide();
	});
};