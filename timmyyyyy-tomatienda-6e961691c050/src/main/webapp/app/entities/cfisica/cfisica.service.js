(function() {
    'use strict';
    angular
        .module('tomatiendaApp')
        .factory('Cfisica', Cfisica);

    Cfisica.$inject = ['$resource', 'DateUtils'];

    function Cfisica ($resource, DateUtils) {
        var resourceUrl =  'api/cfisicas/:id';

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
