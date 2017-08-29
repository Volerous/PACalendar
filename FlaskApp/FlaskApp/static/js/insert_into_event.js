$(document).ready(function () {
  $('a#done').bind('click', function () {
    $.getJSON('/_insert_event', {
      title: $('input[name="title]').val(),
      begin_date: $('input[name="begin_date"]').val(),
      begin_time: $('input[name="begin_time"]').val(),
      end_date: $('input[name="end_date"]').val(),
      end_time: $('input[name="end_time"]').val(),
      description: $('textarea[name="description"]').val(),
      contact: $('input[name="contact"]').val(),
      color: $('input[name="color"]').val(),
      tags: $('input[name="tags"]').material_chip('data')
    }, function (data) {
      $("result").text(data.title);
    });
    return false;
  });
  $('a#test').bind('click', function () {
    var data = $("#tags_id").material_chip('data');
    console.log(data);
    $("test-res").text(data[0].tag);

  });
});