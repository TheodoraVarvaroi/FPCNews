(function(){
  'use strict';

  var app = angular.module('app');

  app.controller("ArticlesController", ['$scope', '$timeout', 'ngDialog', '$compile', 'ArticlesService', articlesController]);
  function articlesController($scope, $timeout, ngDialog, $compile, ArticlesService) {
    $scope.list_of_articles = [];
    $scope.list_of_languages = [];
    $scope.list_of_tags = [];
    $(function () {
      getArticleTags();
      getLanguages();
    });

    $scope.getArticlesByTags = function(){
      ArticlesService.getFavoriteArticles()
        .then(function(response){
          console.log(response);
          $scope.list_of_articles = response;
        }, function(err){
          console.log(err);
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

    function getArticleTags(){
      ArticlesService.getAllTags()
        .then(function(response){
         _.each(response, function (tag) {
           $scope.list_of_tags.push({name: tag, checked: false});
         });
        }, function(err){
          console.log(err);
        });
    }

    function getLanguages(){
      ArticlesService.getAllLanguages()
        .then(function(response){
          _.each(response, function (lang) {
            $scope.list_of_languages.push({name: lang, checked: false});
          });
        }, function(err){
          console.log(err);
        });
    }

    // function mapIdForArticles(array) {
    //   _.each(array, function (item) {
    //     item.id = ++$scope.current_global_id;
    //   });
    // }

    // function addNewPagesToNewsPaper(data) {
    //   var article_date = new Date(data.source.date);
    //   var new_page_template = angular.element(
    //     '<div ng-click="getArticles(data.id)">' +
    //     '<p>' + data.title + '</p>' +
    //     '<img ng-if="data.id % 2 == 0" src="' + data.imageUrl + '">' +
    //     '<div>' +
    //     data.content +
    //     '</div>' +
    //     '<img ng-if="data.id % 2 != 0" src="' + data.imageUrl + '">' +
    //     '<a style="cursor:pointer;" target="_blank" href="' + data.source.site + '">Click to read the entire article...</a>' +
    //     '<div class="metadata-article-container">' +
    //     '<p>' + data.source.author + '</p>' +
    //     '<p>' + (article_date.getMonth() + 1) + '/' + article_date.getDate() + '/' + article_date.getYear() + '</p>' +
    //     '</div>' +
    //     '</div>'
    //   );
    //
    //   var linkFunction = $compile(new_page_template);
    //   var result = linkFunction($scope);
    //   $('.flipbook').turn('addPage', result, ($scope.number_of_total_pages + 1));
    // }

    $timeout(function () {
      $('.flipbook').turn({
        width: window.outerWidth,
        height: window.outerHeight,
        gradients: true,
        autoCenter: true,
        pages: 8
      });

    }, 200);

    $scope.toggleBrightness = true;
  }
})();
