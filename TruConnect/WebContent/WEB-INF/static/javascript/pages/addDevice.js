function setDeviceLabel_bak(name) {
	var labelValue = $("#label").val();
	if (labelValue.length == 0) {
		$("#label").val(name);
	}
}