(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageResource', VirtualStorageResource);
    VirtualStorageResource.$inject = ['$resource'];
    /* @ngInject */
    function VirtualStorageResource($resource) {
        return $resource('/SAPo-FO/api/virtualstorages');
    }
})(); 