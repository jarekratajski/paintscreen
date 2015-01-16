//main controller  of paintScreen
paintscreen.controller('actionsCtrl', ['$scope', '$timeout', 'paintService', function ($scope, $timeout, paintService) {
         $scope.color= "#ffffff";

        $scope.colorChanged = function () {
            paintService.postEvent(createSetColorEvent($scope.color));
        };
        //lets configure recording
        prepareRecording();
        $scope.record = function () {

            record();
            $timeout(function () {
                //stop recording
                stop(playWave);
            }, 50);
        };
        
        function playWave(data) {
            var max = Math.max.apply(null, data);
            if (max > 0.05) {//0.20 is ok
                var ev = {"jsonClass": "WaveEvent", "wave": data};
                //post recording event
                paintService.postEvent(ev);
            }
            //after that record again
            $timeout(function ( ) {
                $scope.record();
            }, 100);
        }
    }]);



