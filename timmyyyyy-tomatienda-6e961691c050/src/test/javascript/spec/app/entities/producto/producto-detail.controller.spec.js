'use strict';

describe('Controller Tests', function() {

    describe('Producto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProducto, MockConline, MockCfisica;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProducto = jasmine.createSpy('MockProducto');
            MockConline = jasmine.createSpy('MockConline');
            MockCfisica = jasmine.createSpy('MockCfisica');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Producto': MockProducto,
                'Conline': MockConline,
                'Cfisica': MockCfisica
            };
            createController = function() {
                $injector.get('$controller')("ProductoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tomatiendaApp:productoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
