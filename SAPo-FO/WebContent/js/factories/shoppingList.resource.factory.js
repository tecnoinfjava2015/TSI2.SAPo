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
        return $resource('/SAPo-FO/api/:tenantId/products',{tenantId:'@tenantId'});
    }
})(); 
// (function() {
// 'use strict';
// angular
// .module('sapo')
// .factory('ReportsResourceMovList', ReportsResourceMovList);
// ReportsResourceMovList.$inject = ['$resource'];
// /* @ngInject */
// function ReportsResourceMovList($resource) {
// return $resource('/SAPo-FO/api/movement/ListBetweenDates/:tenantId',
// {tenantId : '@tenantId'}, {
// 'query': {
// method: 'POST',
// isArray: true,
// fromDate: '@fromDate',
// toDate: '@toDate'
// }
// });
// }
// })();
//
// (function() {
// 'use strict';
// angular
// .module('sapo')
// .factory('ReportsResourceVSWorth', ReportsResourceVSWorth);
// ReportsResourceVSWorth.$inject = ['$resource'];
// /* @ngInject */
// function ReportsResourceVSWorth($resource) {
// return $resource('/SAPo-FO/api/VirtualStorage/worth/:tenantId', {tenantId :
// '@tenantId'}, {
// 'get': {
// method: 'GET',
// tenantId: '@tenantId'
// }
// });
// }
// })();
