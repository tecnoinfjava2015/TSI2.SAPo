(function() {
    'use strict';
    angular
        .module('sapo')
        .config(config);
    config.$inject = ['$routeProvider'];
    /* @ngInject */
    function config($routeProvider) {
        $routeProvider
            .when('/test', {
                controller: 'TestController',
                templateUrl: 'templates/test.html',
                controllerAs: 'vm'
            })
            .when('/', {
                controller: 'ProductsNavigationController',
                templateUrl: 'templates/products.navigate.html',
                controllerAs: 'vm'
            })
            .when('/vs', {
                controller: 'VirtualStorageController',
                templateUrl: 'templates/virtualStorage.create.html',
                controllerAs: 'vm'
            })
            .when('/createProduct', {
                controller: 'CreateProductController',
                templateUrl: 'templates/product.create.html',
                controllerAs: 'vm'
            })
            .when('/userProfile', {
                controller: 'UserProfileController',
                templateUrl: 'templates/profile.view.html',
                controllerAs: 'vm'
            })
    };
})();
