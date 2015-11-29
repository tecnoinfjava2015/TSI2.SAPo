(function() {
	'use strict';
	angular.module('sapo').controller('NotificationNavigationController',
			NotificationNavigationController);
	NotificationNavigationController.$inject = [ 'NotificationResource',
			'$scope', '$location', '$cookies' ];
	/* @ngInject */
	function NotificationNavigationController(NotificationResource, $scope,
			$location, $cookies) {
		console.log("hola1");

		var res = $location.path().split("/");
		$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
		var virtualStorages = $cookies.getObject("sapoVirtualStorages");
		$scope.virtualStorageName = res[2];
		$rootScope.$broadcast("menuOption","NOTIFICATION_NAVIGATION")
//		NotificationResource.query({
//			tenantId : $scope.virtualStorageId
//		}).$promise.then(function(result) {
//			$scope.notifications = result;
//			//console.log($scope.notifications);
//		});
//		$scope.getNotifications = function(){
			NotificationResource.query({
				tenantId : $scope.virtualStorageId
			}).$promise.then(function(result){
//				var notificationAux = $scope.notifications.concat(result);
				$scope.notifications = result;
				console.log(result);
				console.log(result.active);
			},function(){
				console.log("error");
			});
//		}
	}
})();