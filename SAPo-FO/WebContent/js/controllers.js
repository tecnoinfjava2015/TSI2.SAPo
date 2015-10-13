angular.module('sapo')
    .controller('IndexController', ['$scope', '$mdSidenav',
        function($scope, $mdSidenav) {
            $scope.toggleSidenav = function(menuId) {
                $mdSidenav(menuId).toggle();
            };
        }  
    ])
    .controller('test', function($scope, $routeParams, CategoryResource) {
        $scope.test = CategoryResource.get({tenant: $routeParams.tenant});
    })
    .controller('NewProductController', function($scope, $routeParams, ProductResource) {
        $scope.product = {
            uniqueName: '',
            barCode: '',
            description: '',
            nameValueSpec: null,
            categoryName: 'test',
            descName: '',
            valueQuantity: '',
            tenant: $routeParams.tenant,
            valueType: '',
            images: null,
            Quantity: '94043'
        };
        $scope.saveProduct = function() {
            ProductResource.save($scope.product);
        }
    })
    .controller('HomeController', function($scope,$routeParams) {
        $scope.tenant = $routeParams.tenant;
    })
    .controller('MenuController',function($scope,$routeParams){
        var av = $routeParams.tenant;
        $scope.tenant = av;

        var items = [{link:"#/" + av,desc:"HOME"}];
        var item = {};
        item.link = "#/" + av + "/test";
        item.desc = "TEST";
        items.push(item);
        item = {};
        item.link = "#/"  + av + "/product/create";
        item.desc = "Crear Producto";
        items.push(item);
        $scope.items = items;

    });