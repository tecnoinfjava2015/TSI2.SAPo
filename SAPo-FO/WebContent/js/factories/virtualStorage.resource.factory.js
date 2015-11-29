(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageResource', VirtualStorageResource);
    VirtualStorageResource.$inject = ['$resource', '$cookies'];
    /* @ngInject */
    function VirtualStorageResource($resource, $cookies) {
        var userId = $cookies.getObject("sapoUser").id;
        return $resource('/SAPo-FO/api/VirtualStorage/' + userId, {});
    }
})();

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageEditResource', VirtualStorageEditResource);
    VirtualStorageEditResource.$inject = ['$resource'];
    /* @ngInject */
    function VirtualStorageEditResource($resource) {
        return $resource('/SAPo-FO/api/VirtualStorage/:id', {id:'@vsId'}, {
            'update': {
                method: 'PUT' 
            }
        });

    }
})();

 
(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageViewResource', VirtualStorageViewResource);
    VirtualStorageViewResource.$inject = ['$resource', '$cookies'];
    /* @ngInject */
    function VirtualStorageViewResource($resource, $cookies) {
        var userId = $cookies.getObject("sapoUser").id;
        return $resource('/SAPo-FO/api/VirtualStorage/myVSs/' + userId, {}, {
            query: {
                method: 'GET',
                isArray: false
            }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('VirtualStorageShareResource', VirtualStorageShareResource);
    VirtualStorageShareResource.$inject = ['$resource', '$cookies'];
    /* @ngInject */
    function VirtualStorageShareResource($resource, $cookies) {
        //var userId = $cookies.getObject("sapoUser").id;
        return $resource('/SAPo-FO/api/VirtualStorage/:VSId/share/:nick', {VSId:'@VSId', nick:'@nick'}, {
            'save': {
                method: 'POST'
            }
        });
    }
})();
