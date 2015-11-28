(function() {
    'use strict';
    angular
        .module('sapo')
        .config(config);
    config.$inject = ['$routeProvider'];
    /* @ngInject */
    function config($routeProvider) {
        $routeProvider
	        .when('/virtualStorage/:tenantName', {
	        	controller: 'VirtualStorageHomeController',
                templateUrl: 'templates/virtualstorage.home.html',
                controllerAs: 'vm'
	        })
            .when('/test', {
                controller: 'TestController',
                templateUrl: 'templates/test.html',
                controllerAs: 'vm'
            })
            .when('/:tenantName/edit', {
                controller: 'VirtualStorageController',
                templateUrl: 'templates/virtualStorage.edit.html',
                controllerAs: 'vm'
            })
            .when('/', {
                controller: 'ProductsNavigationController',
                templateUrl: 'templates/products.navigate.html',
                controllerAs: 'vm'
            })
            .when('/virtualStorage/:tenantName/category', {
                controller: 'CreateCategoryController',
                templateUrl: 'templates/category.create.html',
                controllerAs: 'vm'
            })
            .when('/virtualStorage/:tenantName/Product/:productName', {
                controller: 'ViewProductController',
                templateUrl: 'templates/product.view.html',
                controllerAs: 'vm'
            })
            .when('/userProfile', {
                controller: 'UserProfileController',
                templateUrl: 'templates/profile.view.html',
                controllerAs: 'vm'
            })
            .when('/virtualStorage/:tenantName/dashboard', {
                controller: 'DashboardController',
                templateUrl: 'templates/dashboard.view.html',
                controllerAs: 'vm'
            })
            .when('/dashboard', {
                controller: 'DashboardController',
                templateUrl: 'templates/dashboard.view.html',
                controllerAs: 'vm'
            })
            .when('/notification', {
                controller: 'NotificationCreateController',
                templateUrl: 'templates/notification.create.html',
                controllerAs: 'vm'
            })
            .when('/notificationlist', {
                controller: 'NotificationNavigationController',
                templateUrl: 'templates/notification.navigation.html',
                controllerAs: 'vm'
            })
            .when('/virtualStorage/:tenantName/reports', {
            	controller: 'ReportsController',
            	templateUrl: 'templates/report.view.html',
            	controllerAs: 'vm'
            })
            .otherwise({
                redirectTo: '/error',
                templateUrl: 'templates/error.view.html'
            });
    };
})();
