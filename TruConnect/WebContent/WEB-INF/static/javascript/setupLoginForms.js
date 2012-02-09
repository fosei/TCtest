/**
 * Adds text-effects to username and password field
 */
$(function() {
	$("#j_username").enableCaption();
	$("#j_password_holder").enableCaption().focus(function() {
		$(this).hide();
		$("#j_password").show().focus();
	});
	$("#j_password").blur(function() {
		if ($("#j_password").val() == "") {
			$("#j_password").hide();
			$("#j_password_holder").show().showCaption();
		}
	});
});