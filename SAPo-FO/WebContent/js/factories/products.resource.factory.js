(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ProductsResource', ProductsResource);
    ProductsResource.$inject = ['$resource'];
    /* @ngInject */
    function ProductsResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/products/:barcode',{tenantId:'@tenantId', barcode:'@barcode'}, {
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
        .factory('ProductStockResource', ProductStockResource);
    ProductStockResource.$inject = ['$resource'];
    /* @ngInject */
    function ProductStockResource($resource) {
        return $resource('/SAPo-FO/api/movement/whereStockChangeByProduct/:VSId/:barcode',{VSId:'@VSId', barcode:'@barcode'}, {
            'query': {
                method: 'POST',
                isArray: true,
            }
        });
    }
})(); 

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ProductPriceResource', ProductPriceResource);
    ProductPriceResource.$inject = ['$resource'];
    /* @ngInject */
    function ProductPriceResource($resource) {
        return $resource('/SAPo-FO/api/movement/wherePriceChange/:VSId/:barcode',{VSId:'@VSId', barcode:'@barcode'}, {
            'query': {
                method: 'POST',
                isArray: true,
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