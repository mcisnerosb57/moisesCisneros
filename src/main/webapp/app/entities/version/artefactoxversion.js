(function() {
    'use strict';
    angular
        .module('artefactsCheckApp')
        .factory('Artefacts', Artefacts);

    Artefacts.$inject = ['$resource'];

    function Artefacts ($resource) {
        var resourceUrl =  '/api/artefactos/version/:id';

        return $resource(resourceUrl, {}, {
             'get': {
                method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
