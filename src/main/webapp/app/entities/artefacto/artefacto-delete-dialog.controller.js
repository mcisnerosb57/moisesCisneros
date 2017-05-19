(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('ArtefactoDeleteController',ArtefactoDeleteController);

    ArtefactoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Artefacto'];

    function ArtefactoDeleteController($uibModalInstance, entity, Artefacto) {
        var vm = this;

        vm.artefacto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Artefacto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
