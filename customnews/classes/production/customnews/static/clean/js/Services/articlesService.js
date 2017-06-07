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
        headers:{
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*"
        }
      }).then(function(response) {
        deferred.resolve(response.data);
      }, function(error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    service.getAllArticles = function(page_number){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/articles/page/" + page_number + '/en',
        headers:{
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*"
        }
      }).then(function(response) {
        deferred.resolve(response.data);
      }, function(error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    service.getFavoriteArticles = function(tags, lang){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/articles/tags/" + _.join(tags, '&') + '/page/1/' + lang,
        headers:{
          'Content-type': 'application/json; charset=utf-8',
          "Access-Control-Allow-Origin": "*"
        }
      }).then(function(response) {
          deferred.resolve(response.data);
        }, function(error) {
          deferred.reject(error);
        });
        return deferred.promise;
    };

    service.getAllLanguages = function(){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/articles/languages"
      }).then(function(response) {
        deferred.resolve(response.data);
      }, function(error) {
        deferred.reject(error);
      });
      return deferred.promise;
    };

    service.getAdminData = function(){
      var deferred = $q.defer();
      $http({
        method : "GET",
        url : "http://localhost:8181/admin"
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

