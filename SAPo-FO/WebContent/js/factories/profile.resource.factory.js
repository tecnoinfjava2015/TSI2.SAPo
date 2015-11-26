(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('UserProfileResource', UserProfileResource);
    UserProfileResource.$inject = ['$resource'];
    /* @ngInject */
    function UserProfileResource($resource) {
        return $resource('/SAPo-FO/api/usuario/:id');
    }
})(); 

(function() {
    'use strict';
    angular
        .module('sapo')
        .factory('UserProfileResourceSubmit', UserProfileResourceSubmit);
    UserProfileResourceSubmit.$inject = ['$resource'];
    /* @ngInject */
    function UserProfileResourceSubmit($resource) {
        return $resource('/SAPo-FO/api/usuario/emailUpdate', {
            'save': {
                method: 'POST',
                nick: '@nick',
                mail: '@mail'
            }     
        });
    }
})();

