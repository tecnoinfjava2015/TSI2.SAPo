(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ToolbarService', ToolbarService);
    ToolbarService.$inject = [];
    /* @ngInject */
    function ToolbarService() {
    	return {
        	data: {}
        };
       
    }
})();