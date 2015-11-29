(function() {
	'use strict';
	angular.module('sapo').controller('NotificationNavigationController',
			NotificationNavigationController);
	NotificationNavigationController.$inject = [ 'NotificationResource',
			'$scope', '$location', '$cookies','$rootScope','$mdDialog' ];
	/* @ngInject */
	function NotificationNavigationController(NotificationResource, $scope,
			$location, $cookies,$rootScope,$mdDialog) {


		var res = $location.path().split("/");
		$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
		var virtualStorages = $cookies.getObject("sapoVirtualStorages");
		$scope.virtualStorageName = res[2];
		$rootScope.$broadcast("menuOption","NOTIFICATION_NAVIGATION");
		
		$scope.createNotification = createNotification;
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

			function createNotification(ev) {
				$mdDialog.show({
					controller : 'NotificationCreateController',
					templateUrl : 'templates/notification.create.html',

					parent : angular.element(document.body),
					targetEvent : ev,
					clickOutsideToClose : true
				}).then(
					function(answer) {
						$scope.status = 'You said the information was "'
								+ answer + '".';
					}, function() {
						$scope.status = 'You cancelled the dialog.';
				});
			
			}			
			
	}
})();