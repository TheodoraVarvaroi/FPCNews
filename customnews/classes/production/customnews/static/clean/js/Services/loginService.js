(function(){
  'use strict';

  var app = angular.module('app');

  app.service("LoginService", ['$http', '$q', loginService]);
  function loginService($http, $q){
    var service = {};

    service.login = function(user_data){
      var deferred = $q.defer();
      $http({
        method : "POST",
        url : "http://localhost:8181/admin/login",
        data: user_data
        // headers: {
        //   'Content-type': 'application/json; charset=utf-8',
        //   'Access-Control-Allow-Origin' : '*'
        // }
      }).then(function(response) {
        deferred.resolve(response.data);
      }, function(error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    return service;
  }
})();
