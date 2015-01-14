
paintscreen.controller('paintCtrl', ['$scope', '$timeout','paintService', function ($scope, $timeout, paintService) {
        $scope.draw = false;
        $scope.state =paintService.getState();

        function observer(arr) {
            $scope.$apply(function() {
                paintService.append( arr);
                    
                } );
        }
        paintService.registerObserver( observer);
        prepareRecording();
       $scope.record = function () {
           
            record();
            $timeout( function() {
                stop(playWave);
            }, 50);
       };

       function playWave(data)  {
            var max = Math.max.apply(null, data);
            if  ( max > 0.15) {
                var ev = {"jsonClass":"WaveEvent","wave": data };
                paintService.postEvent(ev);
            } 
              $timeout( function( ) {
                    $scope.record();
             }, 100);
            
       }
       

       
       
       $scope.coloe = 1;
       
       $scope.colorChanged  = function() {
           console.log( "changed"+ $scope.color);
           paintService.postEvent(createSetColorEvent($scope.color));
       };


        $scope.mouseMoved = function (event) {
            if ($scope.draw) {
           
                var x = (event.clientX - event.currentTarget.offsetLeft) / event.currentTarget.offsetWidth;
                var y = (event.clientY - event.currentTarget.offsetTop) / event.currentTarget.offsetHeight;
                var ev = createPutPixelEvent(x, y);

                //$scope.drawObject(ev);
                paintService.postEvent(ev);
            }
        };
        var createPutPixelEvent = function (x, y) {
            return {"jsonClass":"PutPixelEvent",x: x * 100, y: y * 100, radius: 4.0};
        };
        
         var createSetColorEvent = function (c) {
            return {"jsonClass":"SetColorEvent","c":c}
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


