//TODO generalize this to work with CreditCardPayment and CreditCard class

/**
 * Adds a tool-tip to the cvv info link
 */
$(function() {
	$("#cvvInfo").hover(function() {
		$(this).next("span.toolTip").show();
	}, function() {
		$(this).next("span.toolTip").hide();
	});
});

/**
 * Highlights the image for the type of card when the credit card input is
 * populated with a valid card number
 */
$(function() {
	$("#creditCard\\.creditCardNumber").change(function() {
		highlightCard();
	});
});

$(function() {
	highlightCard();
	setupCreditCardForms();
});

$(function() {
	$("a.addressSelect").click(function(e) {
		e.preventDefault();
		var index = $(this).attr("name");
		$("#creditCard\\.address1").val($("#" + index + "_address1").val());
		$("#creditCard\\.address2").val($("#" + index + "_address2").val());
		$("#creditCard\\.city").val($("#" + index + "_city").val());
		$("#creditCard\\.state").val($("#" + index + "_state").val());
		$("#creditCard\\.zip").val($("#" + index + "_zip").val());
	});
});

$.fn.setupCreditCardForm = function() {
	var formName = $(this).attr("id");
	$("#" + formName).submit(
			function() {
				var expDate = $("#monthSelect").val() + $("#yearSelect").val();
				$("#creditCard\\.expirationDate").val(expDate);
			});
};

function setupCreditCardForms() {
	$(document).find("form").not($("#adminControl")).setupCreditCardForm();
}

/**
 * Highlights the image for the type of credit card entered by calling
 * blurAllExcpet
 * 
 * @returns {Boolean}
 */
function highlightCard() {
	var cardNumber = $("#creditCard\\.creditCardNumber").val();
	if (cardNumber.length > 0) {
		var cardType = cardNumber.substring(0, 1);
		var cvv = document.getElementById("creditCard.verificationcode");
		if (mod10(cardNumber)) {
			if (cardType == 4) {
				cvv.maxLength = 3;
				blurAllExcept($("#ImgVisa"));
			} else if (cardType == "5") {
				cvv.maxLength = 3;
				blurAllExcept($("#ImgMastercard"));
			} else if (cardType == "3") {
				cvv.maxLength = 4;
				blurAllExcept($("#ImgAmex"));
			} else if (cardType == "6") {
				cvv.maxLength = 3;
				blurAllExcept($("#ImgDiscover"));
			} else {
				blurAllExcept("unknown");
			}
		} else {
			$("#creditCardImages").find("img").fadeTo("slow", 1);
		}
	}
	return false;
}

/**
 * Blurs all the credit card images except for the given one by lowering opacity
 * 
 * @param cardImgObj
 */
function blurAllExcept(cardImgObj) {
	$("#creditCardImages").find("img").fadeTo("fast", 0.3);
	if (cardImgObj != "unknown") {
		$(cardImgObj).fadeTo("fast", 1).css("border", "1px solid blue");
	}
}