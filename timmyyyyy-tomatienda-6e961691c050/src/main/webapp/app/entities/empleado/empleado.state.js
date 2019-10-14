(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('empleado', {
            parent: 'entity',
            url: '/empleado?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tomatiendaApp.empleado.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/empleado/empleados.html',
                    controller: 'EmpleadoController',
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empleado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('empleado-detail', {
            parent: 'empleado',
            url: '/empleado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tomatiendaApp.empleado.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/empleado/empleado-detail.html',
                    controller: 'EmpleadoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empleado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Empleado', function($stateParams, Empleado) {
                    return Empleado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'empleado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('empleado-detail.edit', {
            parent: 'empleado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empleado/empleado-dialog.html',
                    controller: 'EmpleadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Empleado', function(Empleado) {
                            return Empleado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('empleado.new', {
            parent: 'empleado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empleado/empleado-dialog.html',
                    controller: 'EmpleadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido: null,
                                dni: null,
                                fechanac: null,
                                ingreso: null,
                                sueldo: null,
                                cargo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('empleado', null, { reload: 'empleado' });
                }, function() {
                    $state.go('empleado');
                });
            }]
        })
        .state('empleado.edit', {
            parent: 'empleado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empleado/empleado-dialog.html',
                    controller: 'EmpleadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Empleado', function(Empleado) {
                            return Empleado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('empleado', null, { reload: 'empleado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('empleado.delete', {
            parent: 'empleado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empleado/empleado-delete-dialog.html',
                    controller: 'EmpleadoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Empleado', function(Empleado) {
                            return Empleado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('empleado', null, { reload: 'empleado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
