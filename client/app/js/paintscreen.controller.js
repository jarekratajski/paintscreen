//main controller  of paintScreen
paintscreen.controller('paintCtrl', ['$scope', '$timeout', 'paintService', function ($scope, $timeout, paintService) {
        $scope.draw = false;
        $scope.state = paintService.getState();

        //This function is called  whenever new object is loaded from server
        //then it calls paintService.
        //The service could store objects directly... but then changes would not be noticed by Angular
        function attachNewObjects(arr) {
            //$apply is called in order to notify angular about changes
            $scope.$apply(function () {
                paintService.append(arr);
            });
        }
        paintService.registerObserver(attachNewObjects);

        
        $scope.mouseMoved = function (event) {
            if ($scope.draw) {
                var x = (event.clientX - event.currentTarget.offsetLeft) / event.currentTarget.offsetWidth;
                var y = (event.clientY - event.currentTarget.offsetTop) / event.currentTarget.offsetHeight;
                var ev = createPutPixelEvent(x, y);
                paintService.postEvent(ev);
            }
        };
        var createPutPixelEvent = function (x, y) {
            return {"jsonClass": "PutPixelEvent", x: x * 100, y: y * 100, radius: 4.0};
        };
        $scope.drawObject = function (obj) {
            $scope.state.objects.push(obj);
        };
        $scope.mouseUp = function () {
            $scope.draw = false;
        };
        $scope.mouseDown = function () {
            $scope.draw = true;
        };
    }]);


