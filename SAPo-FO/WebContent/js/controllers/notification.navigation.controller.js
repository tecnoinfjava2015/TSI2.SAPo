(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('NotificationNavigationController', NotificationNavigationController);
    ProductsNavigationController.$inject = ['NotificationResource', '$scope', '$http'];
    /* @ngInject */
    function NotificationNavigationController(NotificationResource, $scope, $http) {
    	var vm = this;
    	
    	var url = 'http://localhost:8080/SAPo-FO/api/'
    	
    	var res = $location.path().split("/");
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	var count = virtualStorages.owned.length;
    	var i = 0;
    	for (i = 0; i < count; i++) {
    		if (virtualStorages.owned[i].name == res[2]) {
    			$scope.virtualStorageName = virtualStorages.owned[i].name;
    			$scope.tenantId = virtualStorages.owned[i].id;
    		}
    	}
    	$scope.list = true;
    	
    	$scope.getNotifications = function getNotifications() {
            $http.get(url + $scope.tenantId + '/notification')
            .success(function (notificationList){
            	$scope.notification_list = notificationList;
            })
        }    	
    }
});