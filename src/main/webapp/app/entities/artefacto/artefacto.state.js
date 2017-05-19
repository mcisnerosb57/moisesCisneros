(function() {
    'use strict';

    angular
        .module('artefactsCheckApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('artefacto', {
            parent: 'entity',
            url: '/artefacto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Artefactos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/artefacto/artefactos.html',
                    controller: 'ArtefactoController',
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
        .state('artefacto-detail', {
            parent: 'entity',
            url: '/artefacto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Artefacto'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/artefacto/artefacto-detail.html',
                    controller: 'ArtefactoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Artefacto', function($stateParams, Artefacto) {
                    return Artefacto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'artefacto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('artefacto-detail.edit', {
            parent: 'artefacto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/artefacto/artefacto-dialog.html',
                    controller: 'ArtefactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Artefacto', function(Artefacto) {
                            return Artefacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('artefacto.new', {
            parent: 'artefacto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/artefacto/artefacto-dialog.html',
                    controller: 'ArtefactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                versiona: null,
                                repositorio: null,
                                comprobado: null,
                                existe: null,
                                grupo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('artefacto', null, { reload: 'artefacto' });
                }, function() {
                    $state.go('artefacto');
                });
            }]
        })
        .state('artefacto.edit', {
            parent: 'artefacto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/artefacto/artefacto-dialog.html',
                    controller: 'ArtefactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Artefacto', function(Artefacto) {
                            return Artefacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('artefacto', null, { reload: 'artefacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('artefacto.delete', {
            parent: 'artefacto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/artefacto/artefacto-delete-dialog.html',
                    controller: 'ArtefactoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Artefacto', function(Artefacto) {
                            return Artefacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('artefacto', null, { reload: 'artefacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
