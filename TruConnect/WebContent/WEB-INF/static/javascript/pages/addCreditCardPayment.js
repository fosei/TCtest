//$(function() {
//	$("#coupon\\.couponCode").blur(function() {
//		if ($("#coupon\\.couponCode").val()) {
//			validateCoupon($("#coupon\\.couponCode").val());
//		}
//	});
//});
//
//function validateCoupon(couponCode) {
//	$.getJSON("coupons/validate", {
//		couponCode : couponCode
//	}, function(couponResponse) {
//		if (couponResponse.valid) {
//			fieldValidated("coupon\\.couponCode", {
//				valid : true,
//				message : couponResponse.description
//			});
//		} else {
//			fieldValidated("coupon\\.couponCode", {
//				valid : false,
//				message : couponCode + " is not a valid coupon"
//			});
//		}
//
//	});
//}
//
//function fieldValidated(field, result) {
//	var obj = $("#" + field);
//	if (result.valid) {
//		$(obj).removeClass("validationFailed");
//		$(obj).css("color", "green");
//		$(obj).css("background", "#eee");
//		// $(obj).attr("disabled", true);
//		$("#couponMessage").html(result.message);
//	} else {
//		$(obj).addClass("validationFailed");
//		$("#couponMessage").html(result.message);
//	}
//}