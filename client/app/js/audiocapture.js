
var mediaStream;
var rec;
function prepareRecording() {
    var navigator = window.navigator;
    navigator.getUserMedia = (
            navigator.getUserMedia ||
            navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia ||
            navigator.msGetUserMedia
            );
    var Context = window.AudioContext || window.webkitAudioContext;
    window.context = new Context();
     // ask for permission and start recording
  navigator.getUserMedia({audio: true}, function(localMediaStream){
    mediaStream = localMediaStream;

    // create a stream source to pass to Recorder.js
    var mediaStreamSource = context.createMediaStreamSource(localMediaStream);

    // create new instance of Recorder.js using the mediaStreamSource
    rec = new Recorder(mediaStreamSource, {
      // pass the path to recorderWorker.js file here
      workerPath: 'bower_components/recorderjs/recorderWorker.js',
      bufferLen : 256
    });

    
  }, function(err){
    console.log('Browser not supported');
  });

}


function record() {
 
    rec.record();
 
}

function stop(callback) {
    var max = 100;
    var skip =5;
   rec.getBuffer( function(data) {
      
      var len = Math.floor(data[0].length);
      console.log(len);
      var arr=[];
      for (var i = 0; i< data[0].length && arr.length < max; i+=skip) {
        arr.push((data[0])[i]);
      }
      callback(arr);
        rec.clear();
  });
    // stop the media stream
  // mediaStream.stop();
  
  
  // stop Recorder.js
  rec.stop();

  
  
}