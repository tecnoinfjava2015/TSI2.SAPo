(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('NotificationController', NotificationController);
    NotificationController.$inject = ['$scope','$rootScope','NotificationResource'];
    /* @ngInject */
    function NotificationController($scope,$rootScope,NotificationResource) {
    	var vm = this;
    	NotificationResource.query({
            tenantId: $rootScope.virtualStorageId,
        }).$promise.then(function(result) {
            vm.notifications = result;
        });

    }
})();