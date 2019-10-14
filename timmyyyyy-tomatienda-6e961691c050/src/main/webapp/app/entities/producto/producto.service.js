(function() {
    'use strict';
    angular
        .module('tomatiendaApp')
        .factory('Producto', Producto);

    Producto.$inject = ['$resource', 'DateUtils'];

    function Producto ($resource, DateUtils) {
        var resourceUrl =  'api/productos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.caducidad = DateUtils.convertDateTimeFromServer(data.caducidad);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
