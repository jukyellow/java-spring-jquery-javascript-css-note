import moment from "moment"
import * as helloJS from './hello';

function currentTime() {
    return moment().format("H:m:s");
    // const date = new Date()
    // const hours = date.getHours()
    // const minutes = date.getMinutes()
    // const seconds = date.getSeconds()
    // return `${hours}:${minutes}:${seconds}`
}

const div = document.createElement("div")
document.body.appendChild(div)
setInterval(() => (div.innerText = currentTime()), 1000);


function alertCurrtime(){
    alert(currentTime());
}

var indexJS = {
    alertCurrtime : alertCurrtime,
    currentTime : currentTime,
};
window.indexJS = indexJS;
window.helloJS = helloJS;

//export { alertCurrtime };
