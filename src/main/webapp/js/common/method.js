//将JWT存储在Cookie
function setTokenToCookie(value) {
    $.cookie('maitu_loginToken', value, { expires: 1 });
}

//将JWT 放到Authorization  用于返回服务器添加header
var setHeader = function (xhr) {
    var value = $.cookie('maitu_loginToken')
    xhr.setRequestHeader('Authorization',value);
}

//获取Token
function getCookie() {
    return $.cookie('maitu_loginToken');
}