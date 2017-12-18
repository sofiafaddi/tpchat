$(document).ready(function () {
    var checked = $("input[name='entre']").is(':checked');

    var nbreoption = $('.salon .optionnumber').length;
    $('.salon').hide();

    if (nbreoption <= 0) {
        $(".clickbt").hide();
    } else {
        $(".clickbt").css("display", "inline-block");
        $(".clickbt").click(function () {
            $('.salon').show();

            return false;
        });

        $(".salon").change(function () {
            var valeursalon = $('.salon option:selected').text();
            $("#basicaddon2input").val(valeursalon);
        });

    }

    $(".rg input[type=checkbox]").change(function () {
        if ($("input[name='entre']").prop('checked')) {
            checked = true;
            $("input[name='entre']").val("true");
        } else {
            checked = false;
            $("input[name='entre']").val("false");
        }
    });

    $(".formmessage textarea").keypress(function (e) {
        var keycode;
        if (window.event)
            keycode = window.event.keyCode;
        else if (e)
            keycode = e.which;
        else return true;

        if (keycode == 13 && checked == 1) {
            $(".formmessage").submit();
        }
    });

	$(window).on('load',function()
	{
		var contents = $('#scroll_body').height();
		$(".bgelement").scrollTop(contents);

	});

    $(window).on('load',function()
    {
        var contents = $('#scroll_body').height();
        $(".bgelement").scrollTop(700);

    });

	$('.formmessage .btn').click(function () {
	    console.log("ddd");

        $("#scroll_body").scrollTop(700);

    });
});