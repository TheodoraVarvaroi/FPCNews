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


  $('.user-form').on('submit', function (event) {

    //default action specified in the html will be ignored
    event.preventDefault();
    var jThis = $(this),
      urlMehod, urlAjax,
      formAction = jThis.data('action'),
     formData = $(jThis).serializeObject();


    switch (formAction) {
      case 'register': {
        urlMehod = 'PUT';
        urlAjax = '';
      }
      break;
      case 'login': {
        urlMehod = 'POST';
        urlAjax = '';
      }
      break;
      case 'article-preferences': {
        urlMehod = 'POST';
        urlAjax = '/teste/preferinte.php';
      }
        break;
      default: return;
    }

    //TODO insert ajaxurl
    $.ajax({
      url: urlAjax,
      method: urlMehod,
      data: formData,
      dataType: 'JSON',
      success: function (data) {

        //TODO ajax success implementation
        console.log(data);
      }
    }).fail(function (xhr) {
      //TODO ajax fail implementation
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
});


