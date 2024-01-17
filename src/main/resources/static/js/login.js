$(document).ready(function() {
	$("#show_hide_password .input-group-append").on('click', function(event) {
		if($('#show_hide_password input').attr("type") == "text"){
			$('#show_hide_password input').attr('type', 'password');
			$('#show_hide_password i').removeClass( "fa-eye-slash" );
	        $('#show_hide_password i').addClass( "fa-eye" );
		} else if($('#show_hide_password input').attr("type") == "password"){
			$('#show_hide_password input').attr('type', 'text');
	        $('#show_hide_password i').addClass( "fa-eye-slash" );
			$('#show_hide_password i').removeClass( "fa-eye" );
		}
	});
});