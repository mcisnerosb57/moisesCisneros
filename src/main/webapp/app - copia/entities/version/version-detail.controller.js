(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('VersionDetailController', VersionDetailController);

    VersionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Version', 'Aplicacion'];

    function VersionDetailController($scope, $rootScope, $stateParams, previousState, entity, Version, Aplicacion) {
        var vm = this;

        vm.version = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artefactsCheckApp:versionUpdate', function(event, result) {
            vm.version = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
