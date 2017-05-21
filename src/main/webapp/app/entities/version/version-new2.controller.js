(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('Versionnew2controller', Versionnew2controller);

    Versionnew2controller.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Version', 'Aplicacion','$state','$http'];

    function Versionnew2controller ($timeout, $scope, $stateParams, $uibModalInstance, entity, Version, Aplicacion, $state, $http) {
        var vm = this;

        vm.version = entity;
        vm.aplicacions = Aplicacion.query();
        vm.save5 = save5;
        $scope.state = $state.current
        $scope.params = $stateParams; 
        console.log($stateParams.id);
        console.log($state);




           function save5(){
    
    var id = $stateParams.id;

        
        if ((!(id==null)) && (!(id==undefined))){
            console.log(id);
    
        $http.get('http://127.0.0.1:9797/api/versions/new/'+id+'/'+vm.version.versionapp).success(function(data){
          
        });
        alert("se ha creado la nueva version");

        $state.reload();
         }else{
alert("No puede crear una copia de una version sin artefactos. Si desea Crear una version vacia puede hacerlo desde version");

        }
     
     //$state.reload();

    }

    }




  




        

     



})();
