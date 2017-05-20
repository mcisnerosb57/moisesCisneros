(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('ArtefactoDetailController', ArtefactoDetailController);

    ArtefactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Artefacto', 'Version'];

    function ArtefactoDetailController($scope, $rootScope, $stateParams, previousState, entity, Artefacto, Version) {
        var vm = this;

        vm.artefacto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artefactsCheckApp:artefactoUpdate', function(event, result) {
            vm.artefacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
