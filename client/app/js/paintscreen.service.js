
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
            },
            append: function(arr) {
                for ( var i =0; i<arr.length;i++) {
                            state.objects.push(arr[i]); 
                    }
                    if (state.objects.length >1200) {
                        state.objects.slice(0,200);
                    }
                    
            }
        };
    }]);

