$(document).ready(function() {
	$("#loading").ajaxStart(function() {
		$(this).show();
		$("#curtain").css("z-index", 202);
	});
	$("#loading").ajaxStop(function() {
		$(this).hide();
		$("#curtain").css("z-index", 200);
	});
});

function ajaxThis(target, url) {
	$(target).load(url);
}

function ajaxThisPost(form, target) {
	$.post($(form).attr("action"), $(form).serialize(), function(data) {
		$(target).html(data);
	});
}

function loadCenter(url) {
	var targetObj = $("#centerPopupContent");
	ajaxThis(targetObj, url);
}

function submitCenter(button, url) {
	var formObj = $(button).parents('form:first');
	var targetObj = $("#centerPopupContent");
	ajaxThisPost(formObj, targetObj);
}
