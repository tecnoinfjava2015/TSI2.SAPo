(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('GenericProductResource', GenericProductResource);
    GenericProductResource.$inject = ['$resource'];
    /* @ngInject */
    function GenericProductResource($resource) {

        	return $resource('/SAPo-FO/api/genericProduct/:barcode',{ barcode:'@barcode'});

    }
})();   