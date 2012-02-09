$.fn.enableResultsBox = function() {
	var resultsBox = $(this).next(".search_results_box");
	$(this).keyup(function(e) {
		$(resultsBox).toggleSlideDownFullHeight();
	});
	$(this).blur(function() {
		if ($(this).val() == "") {
			$(resultsBox).toggleSlideUp();
		}
	});
	return $(this);
};

$.fn.loadAjax = function(url, params) {
	var numRand = Math.floor(Math.random() * 101);
	url = url + "?refresh=" + numRand;
	$(this).load(url, params, function() {
		$("#admin_search_email_results_list li.result").mouseover(function() {
			$(this).css("background", "#71C0F5");
		}).mouseout(function() {
			$(this).css("background", "");
		}).click(function() {
			var userId = $(this).children("span.id").html();
			var email = $(this).children("span.value").html();
			$("#admin_search_id").val(userId);
			$("#admin_search_email").val(email);
			$("#adminControlButton").click();
		});
		currentAjaxRequest = null;
	});
	return $(this);
};

function loadUsers(email) {
	alert('making ajax request');
	$.getJSON("admin/search/email/ajax", {
		email : email
	}, function(searchResponse) {
		alert("0");
		if (searchResponse.success) {
			alert("1");
			var asdf = "asdf " + searchResponse.users;
			$("#admin_search_results").html(asdf);
			alert("3");
		} else {
			alert("2");
		}
	});
}

$(function() {
	$("#admin_search_email").enableCaption().enableResultsBox();
});

var ajaxBufferTime;
var currentAjaxRequest;
var searchEmailUrl = "/TruConnect/admin/search/email";
var params;

$(function() {
	$("#admin_search_email").keyup(function(e) {
		ajaxBufferTime = 2;
		if (currentAjaxRequest == null) {
			currentAjaxRequest = "pending";
			makeBufferedAjaxCall(searchEmailUrl);
		}
	});
});

function makeBufferedAjaxCall(url) {
	if (currentAjaxRequest == null) {
		params = "email=" + $("#admin_search_email").val();
		//loadUsers($("#admin_search_email").val());
		$("#admin_search_results").loadAjax(url, params);
		currentAjaxRequest = "complete";
	} else {
		$("#admin_search_results")
				.html(
						"<div style='width:100%; text-align:center; margin-top:50px;'>Searching...<br/><img src='/TruConnect/static/images/util/ajax_working_bar.gif' /></div>");

		setTimeout(function() {
			makeBufferedAjaxCall_recurse(url);
		}, 1000);
	}
}

function makeBufferedAjaxCall_recurse(url) {
	ajaxBufferTime = ajaxBufferTime - 1;
	if (ajaxBufferTime == 0) {
		currentAjaxRequest = null;
	}
	makeBufferedAjaxCall(url);
}
