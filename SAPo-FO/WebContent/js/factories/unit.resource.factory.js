(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('UnitResource', UnitResource);
    UnitResource.$inject = ['$resource'];
    /* @ngInject */
    function UnitResource($resource) {
        return $resource('/SAPo-FO/api/Unit/:tenantId',{tenantId:'@tenantId'}, {'query': { method: 'GET' }});
    }
})(); 