(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('VersionDialogController', VersionDialogController);

    VersionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Version', 'Aplicacion'];

    function VersionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Version, Aplicacion) {
        var vm = this;

        vm.version = entity;
        vm.clear = clear;
        vm.save = save;
        vm.aplicacions = Aplicacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.version.id !== null) {
                Version.update(vm.version, onSaveSuccess, onSaveError);
            } else {
                Version.save(vm.version, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artefactsCheckApp:versionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
