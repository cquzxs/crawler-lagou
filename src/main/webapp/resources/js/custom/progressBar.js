function createBar(percentage) {
    if (percentage <= 100) {
        var widthTemp = (percentage).toFixed(0) + '%';
        $('#progressBar').css('width', widthTemp);
        $('#progressBar').text(widthTemp);
    }
}


