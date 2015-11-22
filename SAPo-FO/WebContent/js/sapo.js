(function() {
    'use strict';
    angular
        .module('sapo', [
            'ngMaterial',
            'ngRoute', 
            'ngResource',
            'infinite-scroll',
            'naif.base64',
            'ngCookies'
        ])
        .run(function($rootScope) {
            $rootScope.virtualStorageView = "templates/virtualStorage.view.html";
            $rootScope.virtualStorageFollowingView = "templates/virtualStorageFollowing.view.html";
            $rootScope.ReportsView = "templates/report.view.html";
            $rootScope.productsView = "templates/products.navigate.html";
		});
})();
