(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ReportsResource', ReportsResource);
    ReportsResource.$inject = ['$resource'];
    /* @ngInject */
    function ReportsResource($resource) {
        return $resource('/SAPo-FO/api/movement/QuantityBetweenDates/:tenantId', {tenantId : '@tenantId'}, {
            'get': {
                method: 'POST',
                tenantId: '@tenantId'
            }     
        });
    }
})();