(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ProductsResource', ProductsResource);
    ProductsResource.$inject = ['$resource'];
    /* @ngInject */
    function ProductsResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/products/:barcode',{tenantId:'@barcode', barcode:'@barcode'}, {
            'get': {
                method: 'GET',
                tenantId: '@tenantId',
                barcode : '@barcode'
            }
        });
    }
})(); 

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('CreateProductsResource', CreateProductsResource);
    CreateProductsResource.$inject = ['$resource'];
    /* @ngInject */
    function CreateProductsResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/products',{tenantId:'@tenantId'});
    }
})(); 