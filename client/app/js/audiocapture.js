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

}


var mediaStream;
var rec;
function record() {
  // ask for permission and start recording
  navigator.getUserMedia({audio: true}, function(localMediaStream){
    mediaStream = localMediaStream;

    // create a stream source to pass to Recorder.js
    var mediaStreamSource = context.createMediaStreamSource(localMediaStream);

    // create new instance of Recorder.js using the mediaStreamSource
    rec = new Recorder(mediaStreamSource, {
      // pass the path to recorderWorker.js file here
      workerPath: 'bower_components/recorderjs/recorderWorker.js'
    });

    // start recording
    rec.record();
  }, function(err){
    console.log('Browser not supported');
  });
}

function stop() {
   rec.getBuffer( function(data) {
      console.log( data[0].length);
      var len = Math.floor(data[0].length/100);
      var arr=[];
      for (var i = 0; i< data[0].length; i+=len) {
        arr.push((data[0])[i]);
      }
      console.log(arr);
  });
    // stop the media stream
  mediaStream.stop();
  
  
  // stop Recorder.js
  rec.stop();

  
  
}