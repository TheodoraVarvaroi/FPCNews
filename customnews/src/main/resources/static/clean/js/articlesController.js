(function(){
  'use strict';

  var app = angular.module('app');

  app.controller("ArticlesController", ['$scope', '$timeout', 'ngDialog', '$compile', 'ArticlesService', articlesController]);
  function articlesController($scope, $timeout, ngDialog, $compile, ArticlesService) {
    $scope.list_of_articles = [];
    $scope.currentDate = new Date();
    $scope.selectedLanguage = 'en';
    $scope.tags_list = [];
    getArticleTags();
    // (function(){
    //   ArticlesService.getAdminData().then(function(res){console.log(res);}, function(){});
    // })();


    $scope.getArticlesByTags = function(){
      var pref_tags = getSelectedTags();
      console.log(pref_tags);
      ArticlesService.getFavoriteArticles(pref_tags, $scope.selectedLanguage)
        .then(function(response){
          console.log(response);
          $scope.list_of_articles = response;

          $('#flipbook-content').turn({
            width: window.outerWidth,
            height: window.outerHeight,
            gradients: true,
            autoCenter: true
          });
        }, function(err){
          console.log(err);
        });
    };

    function getSelectedTags(){
      var array = _.filter($scope.tags_list, ['checked', true]);
      var pref_tags = [];
      _.each(array, function(item){
        _.each(item, function(key){
          if(key !== true){
            pref_tags.push(key);
          }
        });
      });
      return pref_tags;
    }

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
         _.each(response, function (tag){
           $scope.tags_list.push({label: tag, checked: false});
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

    $scope.languages_list = [
      {
        checked: false,
        label: 'en',
        name: 'English'
      },
      {
        checked: false,
        label: 'fr',
        name: 'French'
      },
      {
        checked: false,
        label: 'de',
        name: 'Deutsch'
      },
      {
        checked: false,
        label: 'it',
        name: 'Italian'
      },
      {
        checked: false,
        label: 'es',
        name: 'Espaniol'
      }
    ];

    $timeout(function () {
      $('#flipbook-first').turn({
        width: window.outerWidth,
        height: window.outerHeight,
        gradients: true,
        autoCenter: true
      });

    }, 200);

    $scope.toggleBrightness = true;
  }
})();
