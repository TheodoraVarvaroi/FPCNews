<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>


  <form name="upload" method="post" action="#" enctype="multipart/form-data" accept-charset="utf-8">
    <div class="form-group">
      <input type="file" accept="image/*" name="file" id="file" class="input-file">
      <label for="file"  class="btn btn-tertiary js-labelFile">
        <i class="icon fa fa-check"></i>
        <span class="js-fileName">Choose a file for the backgrond</span>
      </label>
    </div>

    <label class="btn btn-tertiary color-input-label ">
      <input name="color-button-background" type="color">
      <span>Choose a background color for buttons</span>
    </label>

    <label class="btn btn-tertiary color-input-label">
      <input name="color-button-text" type="color">
      <span>Choose a color for buttons</span>
    </label>

    <!--additional fields-->
    <div class="paper-button_container">
      <!--the defauld disabled btn and the actual one shown only if the three fields are valid-->
      <button type="button" class="btn btn-default util submit-button">Submit!</button>
    </div>
  </form>
</div>
