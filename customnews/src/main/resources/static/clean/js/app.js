(function(){
  'use strict';
  angular.module('app', ['ngDialog']).config(['ngDialogProvider', function(ngDialogProvider) {
    ngDialogProvider.setDefaults({
      plain: false,
      showClose: false,
      closeByDocument: true,
      closeByEscape: true
    });

  }]);
})();
