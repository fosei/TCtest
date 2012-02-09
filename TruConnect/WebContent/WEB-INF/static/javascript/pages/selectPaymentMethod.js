$(function() {
	$("a.paymentSelect").click(function(e) {
		e.preventDefault();
		var paymentId = $(this).attr("name");
		$("#paymentid").val(paymentId);
		$("#choosePaymentMethodSelectSubmit").click();
	});
});