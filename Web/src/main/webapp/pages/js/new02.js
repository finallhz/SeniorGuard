var linestatus = document.getElementById('line-status');


var sz  = ["摔倒","摔倒","行走","摔倒","行走","摔倒","摔倒","行走","摔倒"];
var i = 0;
linestatus.firstChild.nodeValue = sz[0];
// var num02 = 0;
// var max02 = 9;
var timeout02 = null;

function aaa() {
    if(i == 9){
        clearInterval(timeout02);
    }
    linestatus.firstChild.nodeValue = sz[i];
    i++;

    // var rannum = Math.floor(Math.random()*10);
    //
    // if(rannum%2 == 0){
    //     linestatus.firstChild.nodeValue = "行走";
    // }else{
    //     linestatus.firstChild.nodeValue = "摔倒";
    // }
    // num02++;
}

timeout02 = setInterval(aaa,10000);
