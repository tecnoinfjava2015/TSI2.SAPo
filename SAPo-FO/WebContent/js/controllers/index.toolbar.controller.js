(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ToolbarController', ToolbarController);
    ToolbarController.$inject = ['$mdSidenav', '$scope', '$location', '$window', 'VirtualStorageEditResource', '$cookies'];
    /* @ngInject */
    function ToolbarController($mdSidenav, $scope, $location, $window, VirtualStorageEditResource, $cookies) {
        $scope.title = 'ToolbarController';
        $scope.redirectToNotifications = redirectToNotifications;
        $scope.vsSelected = false;
        var vsId = $cookies.get("sapoCurrentVirtualStorage");
        actualizar(vsId);
        $scope.$on("changeTheme", function(event, t) {
            actualizar(t.vsId);
        });

        function redirectToNotifications() {
            /*var landingUrl = "http://" + $window.location.host + "/SAPo-FO/";
            console.log(landingUrl);
            $window.location.href = landingUrl;*/
            $window.location.href = "http://" + $window.location.host + '/SAPo-FO/#' + '/virtualStorage/' + $cookies.get('sapoCurrentVirtualStorageName') + '/notificationlist';
        }


        $scope.toggleSidenavLeft = toggleSidenavLeft;

        function toggleSidenavLeft() {
            $mdSidenav('left').toggle();
        }

        function actualizar(id) {
        	console.log(id);
            if (typeof id !== 'undefined' && id !== null && id !== '') {
            	$scope.vsSelected = true;
                VirtualStorageEditResource.get({
                    id: id
                }).$promise.then(function(result) {
                        $scope.vs = result;
                        console.log($scope.vs);
                        if (typeof $scope.vs !== 'undefined' && $scope.vs !== null && $scope.vs !== '') {
                            if (typeof $scope.vs.logo === 'undefined' || $scope.vs.logo === null || $scope.vs.logo === '') {
                                $scope.vs.logo = "images/littleFrog.gif";
                            }
                        }
                    },
                    function() {
                        console
                            .log("ERROR: Ocurrio un error al obtener el almacén virtual.")
                    });

            }
        } 
    }
})();
