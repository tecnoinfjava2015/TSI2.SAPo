(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ProductMovementDataService', ProductMovementDataService);
    /* @ngInject */
    function ProductMovementDataService() {
        return {
        	data: {}
        };
    }
})();