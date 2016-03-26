$(document).ready(function() {

	$('ul.nav.nav-sidebar li').on('click', function() {

		// remove active class from old link

		$('li').removeClass('active');

		// add active class to current link

		$(this).addClass('active');

	});

});