'use strict';

describe('Controller Tests', function() {

    describe('Artefacto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockArtefacto, MockVersion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockArtefacto = jasmine.createSpy('MockArtefacto');
            MockVersion = jasmine.createSpy('MockVersion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Artefacto': MockArtefacto,
                'Version': MockVersion
            };
            createController = function() {
                $injector.get('$controller')("ArtefactoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'artefactsCheckApp:artefactoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
