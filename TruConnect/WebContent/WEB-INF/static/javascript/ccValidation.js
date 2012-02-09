/**
 * LUHN validation of credit card number.
 */
function mod10(cc_card_no) {
	var ar = new Array(cc_card_no.length);
	var i = 0, sum = 0;
	if (cc_card_no.length != 15 && cc_card_no.length != 16) {
		return false;
	}
	for (i = 0; i < cc_card_no.length; ++i) {
		ar[i] = parseInt(cc_card_no.charAt(i));
	}
	for (i = ar.length - 2; i >= 0; i -= 2) { // Start form the right.
		ar[i] *= 2; // every second digit from the right (check digit)
		if (ar[i] > 9)
			ar[i] -= 9; // will be doubled, and summed with the skipped digits.
	} // if the double digit is > 9, ADD those individual digits together
	for (i = 0; i < ar.length; ++i) {
		sum += ar[i]; // if the sum is divisible by 10 mod10 succeeds
	}
	return (((sum % 10) == 0) ? true : false);
}

function isCreditCardNo(cc_no) {
	if (cc_no.length == 15 || cc_no.length == 16) {
		return true;
	} else {
		return false;
	}
}

function isAmexFormat(cc_no, cc_type) {
	if (cc_no.length == 15 && (cc_type == 0 || cc_type == 3)) {
		return true;
	} else {
		return false;
	}
}

function isOtherFormat(cc_no, cc_type) {
	if (cc_no.length == 16 && (cc_type != 3)) {
		return true;
	} else {
		return false;
	}
}

function setExpirationDate(date) {
	if (date != null && date.length == 4) {
		var month = date.substring(0, 2);
		var year = date.substring(2);
		$("#monthSelect").val(month);
		$("#yearSelect").val(year);
	}
}
