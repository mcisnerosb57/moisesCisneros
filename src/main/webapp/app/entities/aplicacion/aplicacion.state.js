(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aplicacion', {
            parent: 'entity',
            url: '/aplicacion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Aplicacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aplicacion/aplicacions.html',
                    controller: 'AplicacionController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('aplicacion-detail', {
            parent: 'entity',
            url: '/aplicacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Aplicacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aplicacion/aplicacion-detail.html',
                    controller: 'AplicacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Version2', function($stateParams, Version2) {
                    return Version2.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'aplicacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('aplicacion-detail.edit', {
            parent: 'aplicacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion/aplicacion-dialog.html',
                    controller: 'AplicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aplicacion', function(Aplicacion) {
                            return Aplicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aplicacion.new', {
            parent: 'aplicacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion/aplicacion-dialog.html',
                    controller: 'AplicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('aplicacion', null, { reload: 'aplicacion' });
                }, function() {
                    $state.go('aplicacion');
                });
            }]
        })
        .state('aplicacion.edit', {
            parent: 'aplicacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion/aplicacion-dialog.html',
                    controller: 'AplicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aplicacion', function(Aplicacion) {
                            return Aplicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aplicacion', null, { reload: 'aplicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aplicacion.delete', {
            parent: 'aplicacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion/aplicacion-delete-dialog.html',
                    controller: 'AplicacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aplicacion', function(Aplicacion) {
                            return Aplicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aplicacion', null, { reload: 'aplicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
