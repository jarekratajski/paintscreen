describe('paintscreen service', function () {
    var paintService = null;
    var controller=null;
    var scope = null;
    beforeEach(function () {
        module("paintscreen");
        paintService = {
           getState : function() { } ,
           registerObserver : function() { } 
        };
    });
    
        describe('paintCtrl', function () {
        beforeEach(inject(function ($rootScope, $controller) {
            scope = $rootScope.$new();
            var timeout = null; 
            
            controller = $controller('paintCtrl', {
                '$scope': scope,
                '$timeout':timeout,
                'paintService':paintService
                
            });
        }));
        

        it('sets draw to true', function () {
            expect(scope.draw).toBe(false);
         
            scope.$digest();
            scope.mouseDown();
              expect(scope.draw).toBe(true);
        }); 
    });

}
);

