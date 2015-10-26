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
    };
})();
