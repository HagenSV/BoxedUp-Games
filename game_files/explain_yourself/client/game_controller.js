document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("name").innerText = getCookie("name")

    setInterval(updateGame,500);

});

function updateGame(){
    let contents = document.getElementById("contents");
    let http = new XMLHttpRequest();
    http.open('POST','/game-status');
    http.onreadystatechange = function() {
        if ( http.readyState == 4 && http.status == 200 ) {
            contents.innerHTML = new DOMParser().parseFromString(http.response,"text/html").getElementById("contents").innerHTML;
            let arr = contents.getElementsByTagName('script')
            for (let n = 0; n < arr.length; n++){
                eval?.(arr[n].innerHTML); //run script inside div
            }
        }
    }

    http.send();
}