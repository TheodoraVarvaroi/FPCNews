(function(){
  'use strict';

  var app = angular.module('app');

  app.controller("ArticlesController", ['$scope', '$timeout', '$compile', 'ArticlesService', articlesController]);
  function articlesController($scope, $timeout, $compile, ArticlesService) {
    $scope.list_of_articles = [];
    $scope.list_of_other_articles = [];
    $scope.currentDate = new Date();
    $scope.selectedLanguage = 'en';
    $scope.tags_list = [];
    $scope.showFirstPage = true;
    // localStorage.setItem('token', '113eed51-731f-45d8-a1b1-9905016cc7d0');
    getArticleTags();

    $scope.getArticlesByTags = function(){
      var pref_tags = getSelectedTags();
      console.log(pref_tags);
      ArticlesService.getFavoriteArticles(pref_tags, $scope.selectedLanguage)
        .then(function(response){
          $scope.list_of_articles = response;
          reconstructNewsPaper();
          compilePages(function(){
            getSixRandomArticles(function(){
              compileRestOfArticles(function(){
                var last_page = angular.element('<div>'+
                  '<p class="article-title" style="margin-top: 350px; cursor: pointer;" ng-click="goToFirstStep()">Go to page 1 if you want to read about other subjects</p>'+
                  '</div>');
                addNextPage(last_page);
                $("#flipbook-first").turn('next');
              });
            });
          });
          }, function(err){
          console.log(err);
        });
    };

    function getSixRandomArticles(callback){
      var rest_of_tags = _.chain($scope.tags_list).filter(['checked', false]).map('label').value();
      ArticlesService.getFavoriteArticles(rest_of_tags, $scope.selectedLanguage)
        .then(function(response){
            $scope.list_of_other_articles = response;
            callback();
        }, function(err){
            console.log(err)
        });
    }

    $scope.goToFirstStep = function(){
      var i = $('#flipbook-first').turn('pages');
      while(i > 1){
        console.log(i);
        i--;
        $('#flipbook-first').turn('previous');
      }
    };

    function reconstructNewsPaper(){
      if(($('#flipbook-first').turn('pages') > 1 )){
        var i = $('#flipbook-first').turn('pages');
        while(i > 1){
          console.log(i);
          $('#flipbook-first').turn('removePage', i);
          i--;
        }
      }
    }

    function compileRestOfArticles(callback) {
      var new_page_template = angular.element('<div></div>');
      _.each($scope.list_of_other_articles, function (article, index) {
        var article_date = new Date(article.source.date);
        var new_article_template = angular.element(
          '<div style="margin-bottom: 20px; max-height: none;">' +
            '<p class="article-title">'+article.title+'</p>' +
            '<div style="text-align: justify;">'+article.summary+'</div>' +
            '<div class="metadata-article-container">' +
            '<p>'+article.source.author+'</p>' +
            '<span> -- </span>' +
            '<p>'+ (article_date.getMonth() + 1) + "/" + article_date.getDate() + "/" + article_date.getFullYear()+'</p>' +
            '</div>' +
          '</div>'
        );
        new_page_template.append(new_article_template);
        if(index === 1 || index === 3){
          addNextPage(new_page_template);
          new_page_template = angular.element('<div></div>');
        } else if(index === 5){
          addNextPage(new_page_template);
          return false;
        }
      });
      callback();
    }

    function compilePages(callback) {
      _.each($scope.list_of_articles, function (article, index) {
        var article_date = new Date(article.source.date);
        if(article.contentLength <= 1580){
          var new_page_template = angular.element(
            '<div>' +
            '<p class="article-title">'+article.title+'</p>' +
            (index % 2 == 0 ? '<img ng-src="'+article.imageUrl+'">' : '')+
            '<div style="text-align: justify;">'+article.content+'</div>' +
            (index % 2 != 0 ? '<img ng-src="'+article.imageUrl+'">' : '')+
            '<div class="metadata-article-container">' +
            '<p>'+article.source.author+'</p>' +
            '<span> -- </span>' +
            '<p>'+ (article_date.getMonth() + 1) + "/" + article_date.getDate() + "/" + article_date.getFullYear()+'</p>' +
            '</div>' +
            '</div>'
          );
          addNextPage(new_page_template);

        } else {
          var new_page_template = angular.element(
            '<div>' +
            '<p class="article-title">' + article.title + '</p>' +
            (index % 2 == 0 ? '<img ng-src="' + article.imageUrl + '">' : '') +
            '<div style="text-align: justify;">' + article.content.substring(0, 1580) + '</div>' +
            (index % 2 != 0 ? '<img ng-src="' + article.imageUrl + '">' : '') +
            '<div class="metadata-article-container">' +
            '<p>' + article.source.author + '</p>' +
            '<p>' + (article_date.getMonth() + 1) + "/" + article_date.getDate() + "/" + article_date.getFullYear() + '</p>' +
            '</div>' +
            '</div>'
          );
          addNextPage(new_page_template);
          var full_length = article.contentLength;
          var current_length = 0;
          var i = 0;
          while(full_length > 0){
            if(i === 0) {
              var content_length_to_fit_page = 1580;
            } else{
              var content_length_to_fit_page = 3600;
            }
            current_length += content_length_to_fit_page;

            var article_next_page =
              article.content.substring(current_length, (current_length + 3600 > article.contentLength ? article.contentLength : current_length + 3600));
            var next_page_template = angular.element(
              '<div>' +
              '<div class="article_next_page">'+
              article_next_page +
              '</div>' +
              '</div>');
            if(article_next_page !== ""){
              addNextPage(next_page_template);
            }
            full_length -= content_length_to_fit_page;
            i++;
          }
        }
    });
      callback();
    }

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

    function addNextPage(template){
      var linkFunction = $compile(template);
      var result = linkFunction($scope);
      $('#flipbook-first').turn('addPage', result, ($('#flipbook-first').turn('pages') + 1));
    }

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
        height: 950,
        gradients: true,
        autoCenter: true
      });

    }, 200);

    $scope.toggleBrightness = true;
  }
})();
