(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('NotificationResource', NotificationResource);
    NotificationResource.$inject = ['$resource'];
    /* @ngInject */
    function NotificationResource($resource) {
        return $resource('/SAPo-FO/api/:tenantId/notification',{tenantId:'@tenantId'});
    }
})();


