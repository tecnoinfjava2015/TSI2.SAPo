(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ProductsResource', ProductsResource);
    ProductsResource.$inject = ['$resource'];
    /* @ngInject */
    function ProductsResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/products/:productId',{tenantId:'@tenantId', productId:'@productId'});
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