(function(){
  'use strict';

  var app = angular.module('app');

  app.service("ArticlesService", ['$http', '$q', articlesService]);
  function articlesService($http, $q){
    var service = {};

    service.getAllArticles = function(page_number){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/api/articles/page/" + (page_number || 1),
        headers: {
            'Access-Control-Allow-Origin' : '*'
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

