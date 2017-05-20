(function() {
    'use strict';
    angular
        .module('artefactsCheckApp')
        .factory('Artefacto', Artefacto);

    Artefacto.$inject = ['$resource'];

    function Artefacto ($resource) {
        var resourceUrl =  'api/artefactos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
