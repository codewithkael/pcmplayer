import nove from 'pcmplayer';


in your function call the method like this :


{
....

//method
nove.playAudio(rate : Integer , byteArray : String)

//convert byte array to string
// you convert array to string using JSON.stringify
//like this -> let values = JSON.stringify(arr);
//             nove.playAudio(44100, values);



....
}
