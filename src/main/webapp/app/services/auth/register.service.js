(function () {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
