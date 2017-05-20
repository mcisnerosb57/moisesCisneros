(function() {
    'use strict';
    angular
        .module('artefactsCheckApp')
        .factory('Version2', Version2);

    Version2.$inject = ['$resource'];

    function Version2 ($resource) {
        var resourceUrl =  '/api/versions/aplicacion/:id';

        return $resource(resourceUrl, {}, {
             'get': {
                method: 'GET', isArray: true},

            'update': { method:'PUT' }
        });
    }
})();
