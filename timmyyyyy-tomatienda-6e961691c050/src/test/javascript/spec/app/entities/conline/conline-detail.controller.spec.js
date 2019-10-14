'use strict';

describe('Controller Tests', function() {

    describe('Conline Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConline, MockCliente, MockProducto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConline = jasmine.createSpy('MockConline');
            MockCliente = jasmine.createSpy('MockCliente');
            MockProducto = jasmine.createSpy('MockProducto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Conline': MockConline,
                'Cliente': MockCliente,
                'Producto': MockProducto
            };
            createController = function() {
                $injector.get('$controller')("ConlineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tomatiendaApp:conlineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
