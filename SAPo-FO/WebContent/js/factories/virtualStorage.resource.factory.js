(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageResource', VirtualStorageResource);
    VirtualStorageResource.$inject = ['$resource'];
    /* @ngInject */
    function VirtualStorageResource($resource) {
        return $resource('/SAPo-FO/api/VirtualStorage');
    }
})(); 

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageViewResource', VirtualStorageViewResource);
    VirtualStorageViewResource.$inject = ['$resource'];
    /* @ngInject */
    function VirtualStorageViewResource($resource) {
        return $resource('/SAPo-FO/api/VirtualStorage/owner/:oId', {oid:'@oid'});
    }
})(); 
