(function() {
    'use strict';
    angular
        .module('artefactsCheckApp')
        .factory('Aplicacion', Aplicacion);

    Aplicacion.$inject = ['$resource'];

    function Aplicacion ($resource) {
        var resourceUrl =  'api/aplicacions/:id';

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
