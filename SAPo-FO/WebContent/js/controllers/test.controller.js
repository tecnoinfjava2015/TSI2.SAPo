(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('TestController', TestController);
    TestController.$inject = ['$rootScope'];
    /* @ngInject */
    function TestController($rootScope) {
        var vm = this;
        vm.title = 'SAPO Your Cloud Storage';
        vm.theme = $rootScope.theme;
        $rootScope.theme = vm.theme;
        vm.changeTheme = changeTheme;
        vm.crossSidenav = crossSidenav;
        vm.hideSidenavAux = hideSidenavAux;
        vm.showSidenavAux = showSidenavAux;

        function changeTheme(t){
            $rootScope.theme = t;
        }

        function crossSidenav() {
            if ($rootScope.sidenav=="left") {
                $rootScope.sidenav="right"
            } else{
                $rootScope.sidenav="left"
            };
        }
        function hideSidenavAux(){
            $rootScope.sidenavAux = ''
        }
        
        function showSidenavAux(){
            if ($rootScope.sidenav=="left") {
                $rootScope.sidenavAux="right"
            } else{
                $rootScope.sidenavAux="left"
            };
        }

    }
})();