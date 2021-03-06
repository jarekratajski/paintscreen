paintscreen.directive('semiCanvas', ['$parse', function($parse) {
   var creators = {
    'PixelView': function( obj) {
                    var figure  =jQuery("<figure>");
                    figure.attr('style', 'left:'+obj.x +"%;top:"+obj.y+"%;background-color:"+obj.c+";");
                    return figure;
    },
    'WaveView' : function(obj) {
        return drawWave(obj.wave);
    }
   };
   
   
        
        
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
              element.append(creators[obj.jsonClass](obj));
             /* if (obj.jsonClass === 'PixelView') {
                    var figure  =jQuery("<figure>");
                    figure.attr('style', 'left:'+obj.x +"%;top:"+obj.y+"%;background-color:"+obj.c+";");
                    element.append(figure);
                } else if (obj.jsonClass === 'WaveView'){
                    element.append( );
                }*/
          }
      });
  }
  return {
      link : link
  };
}]);
