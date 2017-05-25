var mySideNav = $('#mySidenav');
$(function () {
  var fileInput = $('input[name="file"]'),
    filename,
    fileNameSpan = $('.js-fileName');

  fileInput.on('change', function (e) {
    if (!this.value) {
      fileNameSpan.text('Choose a file');
      return;
    }
    filename = this.value.replace(/^.*[\\\/]/, '');
    fileNameSpan.text(filename);
    changeBackground(this);
  });

});

function openNav() {
  mySideNav[0].style.width = "500px";
}

function closeNav() {
  mySideNav[0].style.width = "0";
}


function changeBackground(img) {
  var file = img.files[0];
  var imagefile = file.type;
  var match = ["image/jpeg", "image/png", "image/jpg"];
  if (!((imagefile === match[0]) || (imagefile === match[1]) || (imagefile === match[2]))) {
    alert("Invalid File Extension");
  } else {
    var reader = new FileReader();
    reader.onload = imageIsLoaded;
    reader.readAsDataURL(img.files[0]);
  }
  function imageIsLoaded(e) {
    $('body').css({'background-image': "url(" + e.target.result + ")"});

  }
}
