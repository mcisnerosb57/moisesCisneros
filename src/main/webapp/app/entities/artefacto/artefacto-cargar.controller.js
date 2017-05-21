(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('ArtefactoCargarController', ArtefactoCargarController);

    ArtefactoCargarController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Artefacto', 'Version2', 'Aplicacion','$http'];

    function ArtefactoCargarController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Artefacto, Version2, Aplicacion, $http) {
        var vm = this;

        vm.artefacto ;
        vm.clear = clear;
        vm.save = save;
        vm.save2 = save2;
        vm.versions;
        vm.aplicacions = Aplicacion.query();
        vm.cambiarVersion = function cambiarVersion(id){
            

            $http.get('http://127.0.0.1:9797/api/versions/aplicacion/'+id)
            .success(function(data) {
                vm.versions = data;
            });
            
        }
        
           $http.get('app/entities/artefacto/fichero.json').success(function(data){
            vm.artefacto = data.artefacts; 
            $scope.artefacts = data.artefacts; 
        });
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.artefacto.id !== null) {
                Artefacto.update(vm.artefacto, onSaveSuccess, onSaveError);
            } else {
                Artefacto.save(vm.artefacto, onSaveSuccess, onSaveError);
            }
        }

        




        function onSaveSuccess (result) {
            $scope.$emit('artefactsCheckApp:artefactoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        function save2 () {
             var ar = [];
             var temporal;
             var version = new Object();
             version.id = vm.artefacto.version.id;
             version.versionapp = vm.artefacto.version.versionapp;
            for (var k=0; k<$scope.artefacts.length; k++) {
                temporal = new Object();
                temporal.nombre = $scope.artefacts[k].nombre;
                temporal.versiona = $scope.artefacts[k].version;
                temporal.grupo = $scope.artefacts[k].grupo;
                temporal.id = null;
                temporal.existe = null;
                temporal.comprobado = null;
                temporal.repositorio = $scope.artefacts[k].repositorio;
               console.log(vm.artefacto.version);
                //temporal.versiona = vm.artefacto.version;
                temporal.version= version;
                
                //temporal.versiona.versionapp = vm.artefacto.version.versionapp;
                ar.push(temporal); 
            }
            for (var j=0; j<ar.length; j++){
                console.log(ar[j])
                Artefacto.save(ar[j], onSaveSuccess, onSaveError);
            }
        }





    }
})();
