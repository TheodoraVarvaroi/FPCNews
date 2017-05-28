(function(){
  'use strict';

  var app = angular.module('app');

  app.service("ArticlesService", ['$http', '$q', articlesService]);
  function articlesService($http, $q){
    var service = {};

    service.getAllTags = function(){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/articles/tags",
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

    service.getPreferedArticles = function(tags, lang){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/articles/" + _.join(tags, '&') + "/" + lang,
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

