(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .controller('ArtefactoController', ArtefactoController);

    ArtefactoController.$inject = ['$scope', '$state', 'Artefacto', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants','$http', '$q'];

    function ArtefactoController ($scope, $state, Artefacto, ParseLinks, AlertService, pagingParams, paginationConstants,$http, $q) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();



       


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



        function loadAll () {
            Artefacto.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.artefactos = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
