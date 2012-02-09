$(function() {
	var $forms = $("form");
	$.each($forms, function() {
		$(this).setupForm();
	});
});

$.fn.setupForm = function() {
	var formName = $(this).attr("id");
	var formInput = "#" + formName + " input";
	var formButton = "#" + formName + "Button";
	var formResetButton = "#" + formName + "ResetButton";
	var formSubmit = "#" + formName + "Submit";
	var formReset = "#" + formName + "Reset";
	$(formInput).not(".noSubmit").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			$(formButton).click();
			return false;
		} else {
			return true;
		}
	});
	$(formButton).click(function(e) {
		e.preventDefault();
		$(formSubmit).click();
		$("#curtain").fadeIn("fast").center().height($(document).height());
		$("#centerPopup").fadeIn("fast").center();
	});
	$(formResetButton).click(function(e) {
		e.preventDefault();
		$(formReset).click();
		$(formInput).blur();
	});
	// return $(this);
};