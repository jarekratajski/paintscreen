/* 
 * global:angular
 */
var paintscreen = angular.module("paintscreen", []);
paintscreen.directive('semiCanvas', ['$parse', function($parse) {
  function link(scope, element, attrs) {
      console.log(attrs.semiCanvas);
      var getter = $parse(attrs.semiCanvas);
      
     
      scope.$watch(function() { 
            return getter(scope).length;
        }, function( newV, oldV) {
          console.log("changed"+newV);
          var  objects = getter(scope);
          
          for (var index = newV-1; index >= oldV; index--) {
              
              var obj = objects[index];
              if (obj.jsonClass === 'PixelView') {
                    var figure  =jQuery("<figure>");
                    figure.attr('style', 'left:'+obj.x +"%;top:"+obj.y+"%;background-color:"+obj.c+";");
                    element.append(figure);
                } else if (obj.jsonClass === 'WaveView'){
                    element.append( drawWave(obj.wave));
                }
          }
      });
  }
  return {
      link : link
  };
}]);

function drawWave(data){
           var canvas = jQuery("<canvas width='1000' height='1000'>");
           
           canvas.width("100%");
           canvas.height("100%");
         //  jQuery("article").append(canvas);
           
           
           var c = canvas.get(0);
           console.log(c.width+","+c.height);
           
           var ctx=c.getContext("2d");
           ctx.beginPath();
           ctx.lineWidth = 10;
            ctx.moveTo(0,10);
            ctx.lineTo(100,0);
            ctx.stroke();
           
            ctx.beginPath();
            ctx.strokeStyle = '#ff0000';
            ctx.lineWidth = 10;
            ctx.moveTo(0,500);

            var dx  = 1000/data.length;
            var dy = 300;
            for ( var i = 0 ; i< data.length; i++ ) {
                  ctx.lineTo( dx*i, 500+(data[i]*dy) );
              
               
            }
             ctx.stroke();
             /*$timeout( function( ) {
                    $scope.record();
             }, 100);*/
             return canvas;
       }

paintscreen.factory('paintService', ['$http', function ($http) {
        var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
        var eventSocket = new WS("ws://" + window.location.hostname + ":6696/events/ws")
        var observers = [];
        var state = {
           objects: [],
           sessionId: 0
        };
        eventSocket.onmessage = function (event) {
         
            for (var i in observers) {
                observers[i](JSON.parse(event.data));
            }
        }
        
        function getEvents() {
            $http.get("/services/paint").success(function(data) {
                     state.objects = data.objects;
                     state.sessionId = data.sessionId;
                 });
        }
        
        getEvents();
        
        return {
            
            getState : function() {
                return state;
            },
            postEvent: function (ev) {
                ev.session = state.sessionId;
                $http.post("/services/paint", ev);
            },
            registerObserver: function(obs) {
                observers.push(obs);
            }
        };
    }]);

paintscreen.controller('paintCtrl', ['$scope', '$timeout','paintService', function ($scope, $timeout, paintService) {
        $scope.draw = false;
        $scope.state =paintService.getState();

        function observer(arr) {
            $scope.$apply(function() {
                    for ( var i =0; i<arr.length;i++) {
                            $scope.state.objects.push(arr[i]); 
                    }
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