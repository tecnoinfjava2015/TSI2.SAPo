(function() {
    'use strict';
    angular
        .module('sapo', [
            'ngMaterial',
            'ngRoute', 
            'ngResource',
            'infinite-scroll'
        ])
        .run(function($rootScope) {
    		$rootScope.theme = "test1";
            $rootScope.rightNav = true;
            $rootScope.leftNav = true;
    		$rootScope.sidenavAux = '';
            $rootScope.bgcolor = 'background-color:grey;';
            $rootScope.toolbarTemplate = 'templates/index.toolbar.html';
            $rootScope.navTopLeft = "templates/index.sidenav.menu.html";
            $rootScope.navBottomLeft = "templates/index.sidenav.categories.html";
            $rootScope.navTopRight  = "templates/index.sidenav.tweets.html";
            $rootScope.navBottomRight = "";
		});
})();
