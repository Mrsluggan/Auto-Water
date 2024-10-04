
setInterval(function () {
    window.location.reload();
    console.log("fuck");

}, 180000);
if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
    console.log('Nattläge aktiverat');
} else {
    console.log('Ljust läge aktiverat');
}