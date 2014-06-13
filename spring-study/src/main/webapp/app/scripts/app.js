'use strict';

var contextPath = '/spring-study';

angular.module('springStudyApp', [
//  'ngCookies',
  'ngResource',
//  'ngSanitize',
  'ngRoute'

])
  .config(function ($routeProvider, $locationProvider) {
	  $routeProvider
      .when('/rst/attaches/new', {
          templateUrl: contextPath+'/app/views/partials/attach/attach_edit.html',
          controller: 'AttachEditCtrl'
        })
      .when('/rst/attaches/:filekey', {
          templateUrl: contextPath+'/app/views/partials/attach/attach_edit.html',
          controller: 'AttachEditCtrl'
        })
      .when('/rst/attaches', {
        templateUrl: contextPath+'/app/views/partials/attach/attach_list.html',
        controller: 'AttachListCtrl'
      })
      /*
      .otherwise({
        redirectTo: '/'
      })*/;
      
    //$locationProvider.html5Mode(true);
  });