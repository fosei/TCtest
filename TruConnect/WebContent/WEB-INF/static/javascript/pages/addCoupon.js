var couponAjaxBufferTime = 1;
var currentCouponAjaxRequest = null;

$(function() {
	$("#couponCode").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			$("#next_1").click();
			return false;
		} else {
			return true;
		}
	});
});

// $(function() {
// $("#couponCode").keyup(function(e) {
// couponAjaxBufferTime = 1;
// if (currentCouponAjaxRequest == null) {
// currentCouponAjaxRequest = "pending";
// makeBufferedCouponAjaxCall();
// }
// });
// });
//
// function makeBufferedCouponAjaxCall() {
// var couponCode = $("#couponCode").val();
// if (currentCouponAjaxRequest == null) {
// validateCoupon(couponCode);
// } else {
// makeDelayedCouponAjaxCall();
// }
// }
//
// function makeDelayedCouponAjaxCall() {
// setTimeout(function() {
// makeBufferedCouponAjaxCall_recurse();
// }, 500);
// }
//
// function makeBufferedCouponAjaxCall_recurse() {
// couponAjaxBufferTime = couponAjaxBufferTime - 1;
// if (couponAjaxBufferTime == 0) {
// currentCouponAjaxRequest = null;
// }
// makeBufferedCouponAjaxCall();
// }
//
// function validateCoupon(couponCode) {
// $.getJSON("coupons/validate", {
// couponCode : couponCode
// }, function(couponResponse) {
// if (couponResponse.valid) {
// fieldValidated("couponCode", {
// valid : true,
// message : couponResponse.description
// });
// } else {
// fieldValidated("couponCode", {
// valid : false,
// message : couponCode + " is not a valid coupon"
// });
// }
// });
// currentCouponAjaxRequest = null;
// }
//
// function fieldValidated(field, result) {
// var obj = $("#" + field);
// var alertErrorRow = $("div.alert.error").parent("div.row");
// if (result.valid) {
// $(alertErrorRow).fadeOut("slow");
// $(obj).removeClass("validationFailed");
// $(obj).css("color", "green");
// $(obj).css("background", "#eee");
// $("#couponMessage").html(result.message);
// } else {
// $(obj).addClass("validationFailed");
// $("#couponMessage").html(result.message);
// }
// }

$(function() {
	$("div.slider a.continue").click(function(e) {
		e.preventDefault();
		var slider = $(this).parent().parent();
		var nextSlider = $(this).parent().parent().next();
		$(slider).hide();
		$(nextSlider).show();
		// $(slider).animate({
		// marginLeft : '-1000px'
		// }, 250, function() {
		// $(slider).hide();
		// $(nextSlider).show();
		// $(nextSlider).animate({
		// marginLeft : '0px'
		// }, 250);
		// });
	});
});

$(function() {
	$("div.slider a.back").click(function(e) {
		e.preventDefault();
		var slider = $(this).parent().parent();
		var prevSlider = $(this).parent().parent().prev();
		$(slider).hide();
		$(prevSlider).show();
		// $(slider).animate({
		// marginLeft : '1000px'
		// }, 250, function() {
		// $(slider).hide();
		// $(prevSlider).show();
		// $(prevSlider).animate({
		// marginLeft : '0px'
		// }, 250);
		// });
	});
});

$(function() {
	var deviceButtons = $("div.deviceList input:radio");
	var numDevices = $(deviceButtons).length;
	$(deviceButtons).click(function(e) {
		$(deviceButtons).selectRadioFromList($(this));
	});
	if (numDevices == 1) {
		$(deviceButtons).get(0).click();
	}
});