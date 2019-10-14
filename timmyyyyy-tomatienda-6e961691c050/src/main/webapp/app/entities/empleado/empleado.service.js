(function() {
    'use strict';
    angular
        .module('tomatiendaApp')
        .factory('Empleado', Empleado);

    Empleado.$inject = ['$resource', 'DateUtils'];

    function Empleado ($resource, DateUtils) {
        var resourceUrl =  'api/empleados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechanac = DateUtils.convertDateTimeFromServer(data.fechanac);
                        data.ingreso = DateUtils.convertDateTimeFromServer(data.ingreso);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
