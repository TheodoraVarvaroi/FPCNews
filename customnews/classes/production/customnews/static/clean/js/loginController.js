(function() {
  'use strict';

  var app = angular.module('app');

  app.controller("LoginController", ['$scope', 'LoginService', loginController]);
  function loginController($scope, LoginService) {
    $scope.user = {
      username: '',
      password: ''
    };
    $scope.loginFailed = false;

    $scope.loginIntoAccount = function(){
      console.log($scope.user);
      if(!_.isEmpty($scope.user.username) && !_.isEmpty($scope.user.password)) {
        LoginService.login($scope.user)
          .then(function(response){
            console.log(response);
            $scope.loginFailed = response.OK !== 1;
          }, function(error){
            console.log(error);
            $scope.loginFailed = true;
          });
      }
    };
  }
})();
