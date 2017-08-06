$(document).ready(function () {
    $('#calendar').fullCalendar({
        // put your options and callbacks here
    })

});
$(function () {
    $('a#done').bind('click', function () {
        $.getJSON('/background_process', {
            first_name: $('input[name="first_name"]').val(),
            last_name: $('input[name="last_name"]').val()
        }, function (data) {
            $("#result").text(data.first_name+ " " +data.last_name);
        });
        return false;
    });
});
