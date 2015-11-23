(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ReportsResource', ReportsResource);
    ReportsResource.$inject = ['$resource'];
    /* @ngInject */
    function ReportsResource($resource) {
        return $resource('/SAPo-FO/api/QuantityBetweenDates/:tenantId', {tenantId : '@tenantId'}, {
            'get': {
                method: 'GET',
                tenantId: '@tenantId'
            }     
        });
    }
})();