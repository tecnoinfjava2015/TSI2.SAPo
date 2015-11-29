(function() {
	'use strict';
	angular.module('sapo')
			.factory('ShoppingListResource', ShoppingListResource);
	ShoppingListResource.$inject = [ '$resource' ];
	/* @ngInject */
	function ShoppingListResource($resource) {
		return $resource('/SAPo-FO/api/shoppingList/list', {
			VSId : '@VSId'
		}, {
			'get' : {
				isArray : true,
				method : 'GET'
			}
		});
	}
})();

(function() {
	'use strict';
	angular.module('sapo').factory('ShoppingListInsertResource',
			ShoppingListInsertResource);
	ShoppingListInsertResource.$inject = [ '$resource' ];
	/* @ngInject */
	function ShoppingListInsertResource($resource) {
		return $resource('/SAPo-FO/api/shoppingList',{
			'save' : {
				method : 'POST',
				tenantId : '@tenantId',
				virtualStorageId: '@virtualStorageId',
				productBarcode: '@productBarcode',
				productname: '@productname',
				quantity: '@quantity'
			}
		});
	}
})();

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ShoppingListProductsResource', ShoppingListProductsResource);
    ShoppingListProductsResource.$inject = ['$resource'];
    /* @ngInject */
    function ShoppingListProductsResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/products/:barcode',{tenantId:'@tenantId', barcode:'@barcode'});
    }
})(); 

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ShoppingListDeleteResource', ShoppingListDeleteResource);
    ShoppingListDeleteResource.$inject = ['$resource'];
    /* @ngInject */
    function ShoppingListDeleteResource($resource) {
        return $resource('/SAPo-FO/api/shoppingList/listItem',{VSId:'@VSId', barcode:'@barcode'});
    }
})(); 

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('ShoppingListEditResource', ShoppingListEditResource);
    ShoppingListEditResource.$inject = ['$resource'];
    /* @ngInject */
    function ShoppingListEditResource($resource) {
        return $resource('/SAPo-FO/api/shoppingList',{
			'update' : {
				method : 'PUT',
				tenantId : '@tenantId',
				virtualStorageId: '@virtualStorageId',
				productBarcode: '@productBarcode',
				productname: '@productname',
				quantity: '@quantity'
			}
		});
	}
})(); 

