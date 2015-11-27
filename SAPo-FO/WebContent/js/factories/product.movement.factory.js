(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ProductMovementResource', ProductMovementResource);
    ProductMovementResource.$inject = ['$resource'];
    /* @ngInject */
    function ProductMovementResource($resource) {
        return $resource('/SAPo-FO/api/movement');
    }
})();
 