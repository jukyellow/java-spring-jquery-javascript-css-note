//import moment from "moment" ->이렇게 사용하면 오류가 발생

function currentTime() {
    //const date = new Date()
    //const hours = date.getHours()
    //const minutes = date.getMinutes()
    //const seconds = date.getSeconds()
    //return `${hours}:${minutes}:${seconds}`
	return moment().format("H:m:s");
}
