angular.module("sapo")
.factory('ProductResource', function($resource){
	return $resource("http://localhost:8080/SAPo-FO/rest/test/product/create");
})
.factory('CategoryResource', function($resource){
	return $resource("http://localhost:8080/SAPo-FO/rest/test/category/all");
});  