import moment from "moment"
//import * as moment from './moment.js';

function currentTime() {
    // const date = new Date()
    // const hours = date.getHours()
    // const minutes = date.getMinutes()
    // const seconds = date.getSeconds()
    // return `${hours}:${minutes}:${seconds}`
    return moment().format("H:m:s")
}

const div = document.createElement("div")
document.body.appendChild(div)
setInterval(() => (div.innerText = currentTime()), 1000)
