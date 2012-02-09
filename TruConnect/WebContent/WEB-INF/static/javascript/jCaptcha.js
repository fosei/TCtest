/*
 * Refreshes the jCaptcha with a new image
 */
function reloadJCaptchaImage(imageUrl) {
	var call = Math.floor(Math.random() * 100);
	var springImageUrl = imageUrl + "?call=" + call;
	$("#jCaptchaImage").fadeTo("fast", 0, function() {
		$("#jCaptchaImage").attr("src", springImageUrl);
		$(this).fadeTo("fast", 1);
	});
}