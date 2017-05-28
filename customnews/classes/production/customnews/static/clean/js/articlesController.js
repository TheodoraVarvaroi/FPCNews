(function(){
  'use strict';

  var app = angular.module('app');

  app.controller("ArticlesController", ['$scope', '$timeout', 'ngDialog', '$compile', 'ArticlesService', articlesController]);
  function articlesController($scope, $timeout, ngDialog, $compile, ArticlesService) {
    $scope.list_of_articles = [];
    $scope.cloned_list_of_all_articles = [];
    $scope.nr_of_article_lists = 0;
    $scope.current_global_id = -1;
    $(function () {
      $scope.getArticles(5);
    });

    $scope.getArticles = function(index_of_clicked_article) {
      $scope.nr_of_article_lists++;
      if(!(index_of_clicked_article == $scope.nr_of_article_lists * 5 || (index_of_clicked_article - 1) == $scope.nr_of_article_lists * 5
        || (index_of_clicked_article + 1) || $scope.nr_of_article_lists * 5)){
        console.log('not this index');
        return;
      }
      ArticlesService.getAllArticles($scope.nr_of_article_lists)
        .then(function (response) {
          console.log('success');
          if (_.isEmpty($scope.list_of_articles)) {
            mapIdForArticles(response);
            $scope.list_of_articles = response;
            $scope.cloned_list_of_all_articles = _.cloneDeep($scope.list_of_articles);
          } else {
            mapIdForArticles(response);
            _.each(response, function (new_article) {
              addNewPagesToNewsPaper(new_article);
              // $scope.cloned_list_of_all_articles.push(new_article);
            });
            $scope.number_of_total_pages = $(".flipbook").turn("pages");
            $scope.cloned_list_of_all_articles = _.uniq($scope.cloned_list_of_all_articles);
          }
        }, function (error) {
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
        className: 'css/registerStyle.css',
        closeByDocument: true
      });
    };

    function mapIdForArticles(array) {
      _.each(array, function (item) {
        item.id = ++$scope.current_global_id;
      });
    }

    function addNewPagesToNewsPaper(data) {
      var article_date = new Date(data.source.date);
      var new_page_template = angular.element(
        '<div ng-click="getArticles(data.id)">' +
        '<p>' + data.title + '</p>' +
        '<img ng-if="data.id % 2 == 0" src="' + data.imageUrl + '">' +
        '<div>' +
        data.content +
        '</div>' +
        '<img ng-if="data.id % 2 != 0" src="' + data.imageUrl + '">' +
        '<a style="cursor:pointer;" target="_blank" href="' + data.source.site + '">Click to read the entire article...</a>' +
        '<div class="metadata-article-container">' +
        '<p>' + data.source.author + '</p>' +
        '<p>' + (article_date.getMonth() + 1) + '/' + article_date.getDate() + '/' + article_date.getYear() + '</p>' +
        '</div>' +
        '</div>'
      );

      var linkFunction = $compile(new_page_template);
      var result = linkFunction($scope);
      $('.flipbook').turn('addPage', result, ($scope.number_of_total_pages + 1));
    }

    $timeout(function () {
      $('.flipbook').turn({
        width: window.outerWidth * (80 / 100),
        height: window.outerHeight,
        gradients: true,
        autoCenter: true
      });
      $scope.number_of_total_pages = $(".flipbook").turn("pages");

    }, 200);

    $scope.toggleBrightness = true;
  }
})();
