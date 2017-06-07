(function() {
  'use strict';

  var app = angular.module('app');

  app.controller("AdminController", ['$scope', 'ngDialog', 'AdminService', 'ArticlesService', adminController]);
  function adminController($scope, ngDialog, AdminService, ArticlesService){
    $scope.table_index = 1;
    $scope.list_of_articles = [];
    // localStorage.setItem('token', '113eed51-731f-45d8-a1b1-9905016cc7d0');
    $scope.showLogoutButton =  localStorage.getItem('token') !== null ? true : false;

    $scope.logout = function(){
      localStorage.clearAll();
    };

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
      article.source.data = new Date(article.source.data);
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
          $scope.list_of_articles = _.reject($scope.list_of_articles, ['id', article_id]);
        }, function(error){
          console.log(error);
        });
    };

    $scope.editArticle = function(edited_article){
      console.log(edited_article);
      AdminService.editThisArticle(edited_article)
        .then(function(response){
          console.log(response);
          ngDialog.closeThisDialog();
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
