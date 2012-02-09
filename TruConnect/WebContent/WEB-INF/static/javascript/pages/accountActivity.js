$(function() {
	var accountButtons = $("#accountList input:radio");
	$(accountButtons).click(function(e) {
		$(accountButtons).selectRadioFromList($(this));
		//$(this).selectRadio(accountButtons);
		var location = '/TruConnect/account/activity/' + $(this).val();
		window.location.href = location;
	});
});