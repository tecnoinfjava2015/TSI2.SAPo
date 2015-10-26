(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('IndexController', IndexController);
    IndexController.$inject = ['$rootScope'];
    /* @ngInject */
    function IndexController($rootScope ) {
        var vm = this;
        vm.title = 'SAPO Your Cloud Storage';
        vm.theme = $rootScope.theme;
        vm.sidenavMain = $rootScope.sidenav;
        if (vm.sidenavMain=='left') {
            vm.sidenavAux =='right';
        }else{
            vm.sidenavAux =='left';
        };
        vm.sidenavAuxVisible = $rootScope.sidenavAux;
      
    }
})();