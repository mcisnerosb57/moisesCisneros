(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('AplicacionDetailController', AplicacionDetailController);

    AplicacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aplicacion', 'Version2'];

    function AplicacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Aplicacion, Version2) {
        var vm = this;
        console.log(entity);
        vm.version = entity;
        vm.previousState = previousState.name;

    }
})();
