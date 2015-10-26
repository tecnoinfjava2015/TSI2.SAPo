(function() {
    'use strict';
    angular
        .module('sapo', [
            'ngMaterial',
            'ngRoute', 
            'ngResource'
        ])
        .run(function($rootScope) {
    		$rootScope.theme = "test1";
    		$rootScope.sidenav = 'right'; //sidenav izquierda o derecha
    		$rootScope.sidenavAux = '';
		});
})();
