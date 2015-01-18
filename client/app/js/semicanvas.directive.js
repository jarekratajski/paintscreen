paintscreen.directive('semiCanvas', ['$parse', function($parse) {
   var creators = {
    'PixelView': function( obj) {
                    var figure  =jQuery("<figure>");
                    figure.attr('style', 'left:'+obj.x +"%;top:"+obj.y+"%");
                    return figure;
    }
   };
   
   function link(scope, element, attrs) {
      console.log(attrs.semiCanvas);
      var getter = $parse(attrs.semiCanvas);
     
      scope.$watch(function() { 
            return getter(scope).length;
        }, function( newV, oldV) {
         
          var  objects = getter(scope);
          
          for (var index = newV-1; index >= oldV; index--) {
              var obj = objects[index];
              element.append(creators[obj.jsonClass](obj));
   
          }
      });
  }
  return {
      link : link
  };
}]);
