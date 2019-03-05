//将JWT存储在Cookie
function setTokenToCookie(value) {
    $.cookie('maitu_loginToken', value, { expires: 1 });
    // var Days = 1; //此 cookie 将被保存 30 天
    // var exp = new Date();
    // exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    // document.cookie = "my_token =" + escape(value) + ";expires=" + exp.toGMTString();
}

//将JWT 放到Authorization
function setHeader(xhr) {
    var value = $.cookie('maitu_loginToken')
    xhr.setRequestHeader('Authorization',value);
}

//获取Token
function getCookie() {
    return $.cookie('maitu_loginToken');
}