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
              var figure  =jQuery("<figure>");
              var obj = objects[index];
              figure.attr('style', 'left:'+obj.x +"%;top:"+obj.y+"%;");
              element.append(figure);
          }
      });
  }
  return {
      link : link
  };
}]);

paintscreen.factory('paintService', ['$http', function ($http) {
        var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
        var eventSocket = new WS("ws://" + window.location.hostname + ":6696/events/ws")
        var observers = [];
        var state = {
           objects: [] 
        };
        eventSocket.onmessage = function (event) {
         
            for (var i in observers) {
                observers[i](JSON.parse(event.data));
            }
        }
        
        function getEvents() {
            $http.get("/services/paint").success(function(data) {
                     state.objects = data.events;
                 });
        }
        
        getEvents();
        
        return {
            
            getState : function() {
                return state;
            },
            postEvent: function (ev) {
                $http.post("/services/paint", ev);
            },
            registerObserver: function(obs) {
                observers.push(obs);
            }
        };
    }]);

paintscreen.controller('paintCtrl', ['$scope', 'paintService', function ($scope, paintService) {
        $scope.draw = false;
        $scope.state =paintService.getState();

        function observer(ev) {
            $scope.$apply(function() { $scope.state.objects.push(ev); } );
        }
        paintService.registerObserver( observer);
       

        $scope.mouseMoved = function (event) {
            if ($scope.draw) {
           
                var x = (event.clientX - event.currentTarget.offsetLeft) / event.currentTarget.offsetWidth;
                var y = (event.clientY - event.currentTarget.offsetTop) / event.currentTarget.offsetHeight;
                var ev = createEvent(x, y);

                $scope.drawObject(ev);
                paintService.postEvent(ev);
            }
        };
        var createEvent = function (x, y) {
            return {x: x * 100, y: y * 100, radius: 4};
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