(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('IndexController', IndexController);
    IndexController.$inject = ['$rootScope'];
    /* @ngInject */
    function IndexController($rootScope ) {
        var index = this;
        index.title = 'SAPO Your Cloud Storage';
        index.theme = $rootScope.theme;
        index.bgcolor = "red";
        // index.sidenavMain = $rootScope.sidenav;
        // if (vm.sidenavMain=='left') {
        //     vm.sidenavAux =='right';
        // }else{
        //     vm.sidenavAux =='left';
        // };
        // vm.sidenavAuxVisible = $rootScope.sidenavAux;
      
    }
})();