(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('CategoriesResource', CategoriesResource);
    CategoriesResource.$inject = ['$resource'];
    /* @ngInject */
    function CategoriesResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/categories/:categoryId', {
            tenantId: '@tenantId',
            categoryId: '@categoryId'
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('CreateCategoryResource', CreateCategoryResource);
    CreateCategoryResource.$inject = ['$resource'];
    /* @ngInject */
    function CreateCategoryResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/categories',{tenantId:'@tenantId'});
    }
})(); 
