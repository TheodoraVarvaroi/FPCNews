(function() {
  'use strict';

  var app = angular.module('app');

  app.service("AdminService", ['$http', '$q', adminService]);
  function adminService($http, $q) {
    var service = {};

    service.getAllArticles = function () {
      var deferred = $q.defer();
      $http({
        method: "GET",
        url: "http://localhost:8181/articles/",
        headers: {
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*"
        }
      }).then(function (response) {
        deferred.resolve(response.data);
      }, function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    service.deleteThisArticle = function (article_id) {
      var deferred = $q.defer();
      $http({
        method: "DELETE",
        url: "http://localhost:8181/articles/" + article_id,
        headers: {
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*"
        }
      }).then(function (response) {
        deferred.resolve(response.data);
      }, function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    service.editThisArticle = function (article_id) {
      var deferred = $q.defer();
      $http({
        method: "DELETE",
        url: "http://localhost:8181/articles/" + article_id,
        headers: {
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*"
        }
      }).then(function (response) {
        deferred.resolve(response.data);
      }, function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };
  }
})();
