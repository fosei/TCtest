function setCurrentPage(navId) {
	$("#" + navId).children(":first").css("color", "black");
}

function setCurrentStep(stepId) {
	$("#" + stepId).addClass("current");
}