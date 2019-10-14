(function() {
    'use strict';
    angular
        .module('tomatiendaApp')
        .factory('Conline', Conline);

    Conline.$inject = ['$resource', 'DateUtils'];

    function Conline ($resource, DateUtils) {
        var resourceUrl =  'api/conlines/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
