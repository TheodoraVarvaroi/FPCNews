(function() {
  'use strict';

  var app = angular.module('app');

  app.controller("AdminController", ['$scope', 'ngDialog', 'AdminService', 'ArticlesService', adminController]);
  function adminController($scope, ngDialog, AdminService, ArticlesService){
    $scope.table_index = 1;
    $scope.list_of_articles = [];
    localStorage.setItem('token', '113eed51-731f-45d8-a1b1-9905016cc7d0');

    $scope.getPaginatedArticles = function(){
      ArticlesService.getAllArticles($scope.table_index)
        .then(function(response){
          console.log(response);
          _.each(response, function(article){
            $scope.list_of_articles.push(article);
          });
        }, function(error){
          console.log(error);
        });
      $scope.table_index++;
    };
    $scope.getPaginatedArticles();

    $scope.openArticleContentForEdit = function(article){
      $scope.editedArticle = article;
      $scope.editThisArticle = $scope.editArticle;
      ngDialog.open({
          template: 'views/editArticleContent.html',
          className: 'css/adminStyle.css',
          scope: $scope
      });
    };

    $scope.deleteArticle = function(article_id){
      AdminService.deleteThisArticle(article_id)
        .then(function(response){
          console.log(response);
        }, function(error){
          console.log(error);
        });
    };

    $scope.editArticle = function(edited_article){
      console.log(edited_article);
      AdminService.editThisArticle(edited_article)
        .then(function(response){
          console.log(response);
        }, function(error){
          console.log(error);
        });
    };

    $scope.openLoginPopup = function(){
      ngDialog.open({
        template: 'views/loginView.html',
        className: 'css/loginStyle.css'
      });
    };

    $scope.openRegisterPopup = function(){
      ngDialog.open({
        template: 'views/registerView.html',
        className: 'css/registerStyle.css'
      });
    };
  }
})();
