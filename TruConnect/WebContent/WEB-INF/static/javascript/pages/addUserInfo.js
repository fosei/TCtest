/**
 * Sets username to match email
 */
$(function() {
	$("#userInfo").submit(function() {
		$("#user\\.username").val($("#user\\.email").val());
	});
});