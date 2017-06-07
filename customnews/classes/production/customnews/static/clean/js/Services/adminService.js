(function() {
  'use strict';

  var app = angular.module('app');

  app.service("AdminService", ['$http', '$q', adminService]);
  function adminService($http, $q) {
    var service = {};

    service.deleteThisArticle = function(article_id) {
      var deferred = $q.defer();
      $http({
        method: "DELETE",
        url: "http://localhost:8181/articles/" + article_id,
        headers: {
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*",
          "Token" : localStorage.getItem('token')
        }
      }).then(function (response) {
        deferred.resolve(response.data);
      }, function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    service.editThisArticle = function (edited_article){
      var deferred = $q.defer();
      $http({
        method: "PUT",
        url: "http://localhost:8181/articles/" + edited_article.id,
        headers: {
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*",
          "Token" : localStorage.getItem('token')
        }
      }).then(function (response) {
        deferred.resolve(response.data);
      }, function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };
    return service;
  }
})();
