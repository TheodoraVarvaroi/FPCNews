(function(){
  'use strict';

  var app = angular.module('app');

  app.service("RegisterService", ['$http', '$q', registerService]);
  function registerService($http, $q){
    var service = {};

    service.register = function(user_data){
      var deferred = $q.defer();
      $http({
        method : "POST",
        url : "http://localhost:8181/admin",
        data: user_data,
        headers: {
          'Access-Control-Allow-Origin' : '*',
          'Token': localStorage.getItem('token')
        }
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
