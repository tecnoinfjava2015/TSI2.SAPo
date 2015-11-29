(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ToolbarSearchController', ToolbarSearchController);
    ToolbarSearchController.$inject = ['$scope','$cookies','CategoriesResource'];
    /* @ngInject */
    function ToolbarSearchController($scope,$cookies,CategoriesResource) {
        var vm = this;
        vm.title = 'ToolbarSearchController';
        

    }
})();