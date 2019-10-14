(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('conline', {
            parent: 'entity',
            url: '/conline?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tomatiendaApp.conline.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conline/conlines.html',
                    controller: 'ConlineController',
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
                    $translatePartialLoader.addPart('conline');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('conline-detail', {
            parent: 'conline',
            url: '/conline/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tomatiendaApp.conline.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conline/conline-detail.html',
                    controller: 'ConlineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conline');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Conline', function($stateParams, Conline) {
                    return Conline.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'conline',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('conline-detail.edit', {
            parent: 'conline-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conline/conline-dialog.html',
                    controller: 'ConlineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conline', function(Conline) {
                            return Conline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conline.new', {
            parent: 'conline',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conline/conline-dialog.html',
                    controller: 'ConlineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                importe: null,
                                fecha: null,
                                descuento: null,
                                codigo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('conline', null, { reload: 'conline' });
                }, function() {
                    $state.go('conline');
                });
            }]
        })
        .state('conline.edit', {
            parent: 'conline',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conline/conline-dialog.html',
                    controller: 'ConlineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conline', function(Conline) {
                            return Conline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conline', null, { reload: 'conline' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conline.delete', {
            parent: 'conline',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conline/conline-delete-dialog.html',
                    controller: 'ConlineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Conline', function(Conline) {
                            return Conline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conline', null, { reload: 'conline' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
