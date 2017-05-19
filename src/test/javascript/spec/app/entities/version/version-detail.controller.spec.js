'use strict';

describe('Controller Tests', function() {

    describe('Version Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVersion, MockAplicacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVersion = jasmine.createSpy('MockVersion');
            MockAplicacion = jasmine.createSpy('MockAplicacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Version': MockVersion,
                'Aplicacion': MockAplicacion
            };
            createController = function() {
                $injector.get('$controller')("VersionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'artefactsCheckApp:versionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
