(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfisica', {
            parent: 'entity',
            url: '/cfisica?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tomatiendaApp.cfisica.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfisica/cfisicas.html',
                    controller: 'CfisicaController',
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
                    $translatePartialLoader.addPart('cfisica');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfisica-detail', {
            parent: 'cfisica',
            url: '/cfisica/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tomatiendaApp.cfisica.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfisica/cfisica-detail.html',
                    controller: 'CfisicaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfisica');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfisica', function($stateParams, Cfisica) {
                    return Cfisica.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cfisica',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cfisica-detail.edit', {
            parent: 'cfisica-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfisica/cfisica-dialog.html',
                    controller: 'CfisicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfisica', function(Cfisica) {
                            return Cfisica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfisica.new', {
            parent: 'cfisica',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfisica/cfisica-dialog.html',
                    controller: 'CfisicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                importe: null,
                                fecha: null,
                                codigo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cfisica', null, { reload: 'cfisica' });
                }, function() {
                    $state.go('cfisica');
                });
            }]
        })
        .state('cfisica.edit', {
            parent: 'cfisica',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfisica/cfisica-dialog.html',
                    controller: 'CfisicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfisica', function(Cfisica) {
                            return Cfisica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfisica', null, { reload: 'cfisica' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfisica.delete', {
            parent: 'cfisica',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfisica/cfisica-delete-dialog.html',
                    controller: 'CfisicaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfisica', function(Cfisica) {
                            return Cfisica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfisica', null, { reload: 'cfisica' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
