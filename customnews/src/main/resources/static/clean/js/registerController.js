(function() {
  'use strict';

  var app = angular.module('app');

  app.controller("RegisterController", ['$scope', 'RegisterService', registerController]);
  function registerController($scope, RegisterService) {
    $scope.newUser = {
      username: '',
      password: '',
      conf_password: '',
      email: ''
    };
    $scope.registerFailed = false;

    $scope.submitRegistration = function(){
      console.log($scope.newUser);
      if(!_.isEmpty($scope.newUser.username) && !_.isEmpty($scope.newUser.password) && $scope.newUser.password === $scope.newUser.conf_password &&
        !_.isEmpty($scope.newUser.email)) {
        RegisterService.register($scope.newUser)
          .then(function(response){
            console.log(response);
            $scope.registerFailed = response.OK !== 1;
          }, function(error){
            console.log(error);
            $scope.registerFailed = true;
          });
      }
    };
  }
})();
