(function(){
  'use strict';
  angular.module('app', ['ngDialog']).config(['ngDialogProvider', function(ngDialogProvider) {
    ngDialogProvider.setDefaults({
      plain: false,
      showClose: false,
      closeByDocument: true,
      closeByEscape: true
    });
  }])
  .filter('capitalize', function() {
    return function(input, all) {
      var reg = (all) ? /([^\W_]+[^\s-]*) */g : /([^\W_]+[^\s-]*)/;
      return (!!input) ? input.replace(reg, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();}) : '';
    }
  });
})();
