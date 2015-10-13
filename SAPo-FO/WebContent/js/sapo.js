/**
 * SAPo Module
 *
 * Modulo principal
 */
angular.module('sapo', ['ngMaterial', 'ngRoute', 'ngResource'])
    .config(function($routeProvider) {
        $routeProvider
            .when('/:tenant', {
                controller: 'HomeController',
                templateUrl: 'templates/home.html'
            })
    })
    .config(function($routeProvider) {
        $routeProvider
            .when('/:tenant/test', {
                controller: 'test',
                templateUrl: 'templates/test.html'
            })
    })
    .config(function($routeProvider) {
        $routeProvider
            .when('/:tenant/product/create', {
                controller: 'NewProductController',
                templateUrl: 'templates/productCreate.html'
            })
    })    
    .config(function($mdIconProvider) {
        $mdIconProvider.iconSet("avatar", 'icons/avatar-icons.svg', 128);
    })
    .config(function($mdThemingProvider) {
    	$mdThemingProvider.theme("default").primaryPalette("blue").accentPalette("pink");
    });