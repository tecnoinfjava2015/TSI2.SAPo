(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('TestController', TestController);
    TestController.$inject = ['$scope'];
    /* @ngInject */
    function TestController($scope) {
        var vm = this;
        vm.title = 'SAPO Your Cloud Storage';
        vm.changeTheme = changeTheme;
        $scope.$emit('menuOption',"NULL"); 
        
        function changeTheme(t) {
            alert(t);
            $scope.$emit('changeTheme', t);
        }
    }
})();
