/* 
 * global:angular
 */
var paintscreen = angular.module("paintscreen", []);


var canvasArr = [];
function getFreeCanvas(){
    var maxCanvasSize = 5;
    if ( canvasArr.length < maxCanvasSize) {
           var canvas = jQuery("<canvas width='600' height='400'>");
           canvasArr.push(canvas);
           canvas.addClass("animated");
           return canvas;
    } else {
        var canvas  = canvasArr[0];
        canvasArr.shift();
        canvas.removeClass("animated");
        canvas.addClass("animated");
        canvasArr.push(canvas);
        var c = canvas.get(0);
        var context=canvas.get(0).getContext("2d");
        context.clearRect ( 0 , 0 , c.width, c.height );
        return canvas;
    }
     
    
}

function drawWave(data){
           
           var canvas = getFreeCanvas();
           var canvasWidth= 600;
           var canvasHeight= 400;
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
            ctx.moveTo(0,canvasHeight/2);

            var dx  = canvasWidth/data.length;
            var dy = 200;
            for ( var i = 0 ; i< data.length; i++ ) {
                  ctx.lineTo( dx*i, canvasHeight/2+(data[i]*dy) );
              
               
            }
             ctx.stroke();
             /*$timeout( function( ) {
                    $scope.record();
             }, 100);*/
             return canvas;
       }
