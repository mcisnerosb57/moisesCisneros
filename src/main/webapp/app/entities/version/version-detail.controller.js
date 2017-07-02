(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('VersionDetailController', VersionDetailController);

    VersionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Artefacts','$http','$q','$state'];

    function VersionDetailController($scope, $rootScope, $stateParams, previousState, entity, Artefacts, $http, $q, $state) {







        var vm = this;
        console.log(entity);
        vm.artefactos = entity;
        $scope.artefactos = entity;
        console.log(entity);
        vm.previousState = previousState.name;



                      $scope.comprobarVarios= function comprobarVarios(id){
    var config =   id.id;

        $scope.addone = function(num){
            var q = $q.defer()
        $scope.step++;
        if (angular.isNumber(num)){
            console.log(id);

        $http.get('http://127.0.0.1:9797/api/versions/comprobar/'+id.id).success(function(data){
            q.resolve(data);
          
        });
        
        }
        else{

            q.reject('NaN')
        }
        return q.promise
    }

    $scope.step=0;
    $scope.myvalue =0;
    $scope.promise = $scope.addone($scope.myvalue);
    $scope.promise
    .then(function(v){$scope.myvalue = 5;
        
    
     $state.reload();
alert("se ha completado la comprobacion de la version con identificador" +id.id);},
        function(err){$scope.myvalue = err}

        )}


                $scope.comprobarUno = function comprobarUno(id){
    var config =   id.id;

        $scope.addone = function(num){
            var q = $q.defer()
        $scope.step++;
        if (angular.isNumber(num)){
            console.log(id);

        $http.get('http://127.0.0.1:9797/api/artefactos/comprobar/'+id.id).success(function(data){
            q.resolve(data);
          
        });
        
        }
        else{

            q.reject('NaN')
        }
        return q.promise
    }

    $scope.step=0;
    $scope.myvalue =0;
    $scope.promise = $scope.addone($scope.myvalue);
    $scope.promise
    .then(function(v){$scope.myvalue = 5;
        
        vm.artefactos=v;
     $state.reload();
alert("se ha completado la comprobacion del artefacto " +id.id);},
        function(err){$scope.myvalue = err}

        )}

    //////
        
    }
})();
