(function() {
    'use strict';
    angular
        .module('sapo', [
            'ngMaterial',
            'ngRoute', 
            'ngResource',
            'infinite-scroll',
            'naif.base64',
            'ngCookies',
            'ui.bootstrap',
            'ngMessages',
            'ngMdIcons',
            'ngMap'
        ])
        .run(function($rootScope) {
            $rootScope.virtualStorageView = "templates/virtualStorage.view.html";
            $rootScope.virtualStorageFollowingView = "templates/virtualStorageFollowing.view.html";
            $rootScope.productsView = "templates/products.navigate.html";
            $rootScope.productStockView = "templates/productStock.view.html";
            $rootScope.productPriceView = "templates/productPrice.view.html";
		});
})();
