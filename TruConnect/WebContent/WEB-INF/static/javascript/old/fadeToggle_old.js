$(document).ready(function() {
	$(".fadeNext").click(function() {
		$(this).next().fadeToggle();
		return false;
	});
});

jQuery.fn.fadeToggle = function(speed, easing, callback) {
	return this.animate({
		opacity : 'toggle',
		height : 'toggle'
	}, speed, easing, function() {
		return false;
	});
};

function fadeNext(obj) {
	$(obj).next().fadeToggle();
	var arrow = $(obj).html();
	if (arrow == "\u21d1") {
		arrow = "\u21d3";
	} else {
		arrow = "\u21d1";
	}
	$(obj).html(arrow);
	return false;
}

function show(id) {
	var obj = document.getElementById(id);
	if (!$(obj).is(":visible")) {
		$(obj).animate({
			opacity : 1,
			height : 'toggle'
		});
	}
}

function hide(id) {
	var obj = document.getElementById(id);
	if ($(obj).is(":visible")) {
		$(obj).animate({
			opacity : 0,
			height : 'toggle'
		});
	}
}