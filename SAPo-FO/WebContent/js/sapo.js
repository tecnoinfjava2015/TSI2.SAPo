(function() {
    'use strict';
    angular
        .module('sapo', [
            'ngMaterial',
            'ngRoute', 
            'ngResource'
        ])
        .run(function($rootScope) {
    		$rootScope.theme = "test2";
    		$rootScope.sidenav = 'right'; //sidenav izquierda o derecha
            $rootScope.rightNav = true;
            $rootScope.leftNav = true;
    		$rootScope.sidenavAux = '';
            $rootScope.bgcolor = 'background-color:grey;';
            $rootScope.toolbarTemplate = 'templates/index.toolbar.html';
		});
})();
