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

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ReportsResourceMovList', ReportsResourceMovList);
    ReportsResourceMovList.$inject = ['$resource'];
    /* @ngInject */
    function ReportsResourceMovList($resource) {
        return $resource('/SAPo-FO/api/movement/ListBetweenDates/:tenantId', {tenantId : '@tenantId'}, {
            'query': {
                method: 'POST',
                isArray: true,
                fromDate: '@fromDate',
                toDate: '@toDate'
            }     
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ReportsResourceVSWorth', ReportsResourceVSWorth);
    ReportsResourceVSWorth.$inject = ['$resource'];
    /* @ngInject */
    function ReportsResourceVSWorth($resource) {
        return $resource('/SAPo-FO/api/VirtualStorage/worth/:tenantId', {tenantId : '@tenantId'}, {
            'get': {
                method: 'GET',
                tenantId: '@tenantId'
            }     
        });
    }
})();