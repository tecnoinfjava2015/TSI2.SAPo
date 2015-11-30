(function() {
    'use strict';
    angular.module('sapo').controller('ProductMovementCreateController',
        ProductMovementCreateController);
    ProductMovementCreateController.$inject = ['$scope', 'ProductsResource',
        '$location', '$cookies', '$mdDialog', 'ProductMovementDataService','ProductMovementResource'];
    /* @ngInject */
    function ProductMovementCreateController($scope, ProductsResource,
        $location, $cookies, $mdDialog, ProductMovementDataService,ProductMovementResource) {
//    	$scope.$emit('menuOption',null); 
        var vm = this;
        $scope.title = ProductMovementDataService.data.title;
        $scope.movement = {};

        var res = $location.path().split("/");
        $scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
        var virtualStorages = $cookies.getObject("sapoVirtualStorages");
        $scope.virtualStorageName = res[2];

        $scope.loadProducts = loadProducts;
        $scope.reset = reset;
        $scope.cancel = cancel;
        $scope.insert = insert;
        $scope.getProduct = getProduct;
 
        function loadProducts(search) {
            return ProductsResource.query({
                tenantId: $scope.virtualStorageId, 
                limit: 5,
                minSearch: true,
                search: search
            }).$promise;
        }

        function getProduct() {
            $scope.movement.barCode = '';
            $scope.movement.productName = '';
            $scope.movement.finalPrice = '';
            ProductsResource.get({
                tenantId: $scope.virtualStorageId,
                barcode: $scope.product.barCode
            }).$promise.then(function(result) {
                $scope.movement.barCode = result.barCode;
                $scope.movement.productName = result.name;
                $scope.movement.finalPrice = result.purchasePrice;
                $scope.movement.unit = result.unit;
            });
        }
 
        function reset() {
            $scope.movement = {};
            $scope.product = {};
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function insert() {
            $scope.movement.virtualStorageId = $scope.virtualStorageId;
            $scope.movement.stock = $scope.movement.stock * (ProductMovementDataService.data.modifier);
            ProductMovementResource
                .save({},
                    $scope.movement,
                    function() {
                        showAlert('Exito!',
                            'Se ha creado su producto de forma exitosa');

                    },
                    function(r) {
                        console.log(r);
                        showAlert('Error!',
                            'Ocurri&oacute; un error al procesar su petici&oacute;n');
                    });
        }

        function showAlert(title, content) {
            $mdDialog.show($mdDialog.alert().parent(
                    angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true).title(title).content(content)
                .ariaLabel('Alert Dialog Demo').ok('Cerrar'));
        };

    }
})();
