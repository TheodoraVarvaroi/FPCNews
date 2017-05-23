<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>


  <form name="upload" method="post" action="#" enctype="multipart/form-data" accept-charset="utf-8">
    <div class="form-group">
      <input type="file" name="file" id="file" class="input-file">
      <label for="file" class="btn btn-tertiary js-labelFile">
        <i class="icon fa fa-check"></i>
        <span class="js-fileName">Choose a file</span>
      </label>
    </div>
    <!--additional fields-->
    <div class="col-md-12 text-center">
      <!--the defauld disabled btn and the actual one shown only if the three fields are valid-->
      <button type="button" class="btn btn-default util" disabled="disabled" id="fakebtn">Submit! <i
          class="fa fa-minus-circle"></i></button>
    </div>
  </form>
</div>
