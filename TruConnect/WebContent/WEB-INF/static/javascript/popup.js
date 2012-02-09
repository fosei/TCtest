$(function() {
	// Adjust height of overlay to fill screen when page loads
	$("#curtain").css("height", $(document).height());
	// When the link that triggers the message is clicked fade in overlay/msgbox
	$(".openPopup").click(function() {
		$("#curtain").fadeIn(100);
		$("#centerPopup").fadeIn(1000);
		return false;
	});
	// When the message box is closed, fade out
	$(".closePopup").click(function() {
		$("#curtain").fadeOut();
		$("#centerPopup").fadeOut();
		$("#centerPopupContent").html("");
		return false;
	});
});

// Adjust height of overlay to fill screen when browser gets resized
$(window).bind("resize", function() {
	$("#curtain").css("height", $(window).height());
	$(".search_results_box").css("height", $(window).height() - 200);
});

function openPopup() {
	$("#curtain").fadeIn(100);
	$("#centerPopup").fadeIn(1000);
	return false;
}

function closePopup() {
	$("#curtain").fadeOut();
	$("#centerPopup").fadeOut();
	$("#centerPopupContent").html("");
	return false;
}

$.fn.center = function() {
	this.css("position", "absolute");
	this.css("top", (($(window).height() - this.outerHeight()) / 2)
			+ $(window).scrollTop() + "px");
	this.css("left", (($(window).width() - this.outerWidth()) / 2)
			+ $(window).scrollLeft() + "px");
	return this;
};
