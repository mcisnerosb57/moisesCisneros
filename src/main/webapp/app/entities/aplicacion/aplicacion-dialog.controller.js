(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('AplicacionDialogController', AplicacionDialogController);

    AplicacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Aplicacion'];

    function AplicacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Aplicacion) {
        var vm = this;

        vm.aplicacion = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aplicacion.id !== null) {
                Aplicacion.update(vm.aplicacion, onSaveSuccess, onSaveError);
            } else {
                Aplicacion.save(vm.aplicacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artefactsCheckApp:aplicacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
