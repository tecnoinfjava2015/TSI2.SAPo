(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('UserProfileResource', UserProfileResource);
    UserProfileResource.$inject = ['$resource'];
    /* @ngInject */
    function UserProfileResource($resource) {
        return $resource('/SAPo-FO/api/usuario/:id');
    }
})(); 

