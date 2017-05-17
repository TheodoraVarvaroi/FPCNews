$(function () {
  $('.flipbook').turn({
    // Width

    width: 1000,

    // Height

    height: 600,

    // Elevation

    elevation: 50,

    // Enable gradients

    gradients: true,

    // Auto center this flipbook

    autoCenter: true

  });


  $('#user-register-submit').on('submit', function (e, t) {
    var jThis = $(this);
    var formData = $(jThis).serializeObject();
    console.log(formData);

    //TODO insert ajaxurl
    $.ajax({
      url: '',
      method: 'POST',
      data: formData,
      dataType: 'JSON',
      success: function(data) {

       console.log(data);
      }
    }).fail(function(xhr) {
      console.log(xhr);
    });

});


//transform form data into an object
$.fn.serializeObject = function () {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function () {
    if (o[this.name] !== undefined) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};

