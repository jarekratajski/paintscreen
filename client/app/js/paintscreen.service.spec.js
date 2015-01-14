describe('grunt-karma', function () {
    var paintService = null;
    var postedEvents = [];
    var httpBackend;
    beforeEach(function () {
         postedEvents = [];

        module('paintscreen');
        inject(function ($httpBackend) {
            httpBackend = $httpBackend;
            $httpBackend.when("POST", "/services/paint").respond(function (method, url, data, headers) {
                postedEvents.push(JSON.parse(data));
                return [200, {}, {}];
            });
              $httpBackend.when("GET", "/services/paint").respond(function (method, url, data, headers) {
                
                return [200, {}, {}];
            });
        });
        inject(function ($injector) {
            paintService = $injector.get('paintService');
        });
    });

    describe('paintService', function () {
        it('should be injected', function () {
            expect(paintService).not.toBe(null);
        });

        it('should post event', function () {
            paintService.postEvent({"test": "test7"});
            httpBackend.flush();
            expect(postedEvents[0].test).toBe("test7");
        });
    });



});

