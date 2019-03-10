$(document).ready(function () {
    $.ajax({
        url: ajaxRoot+GLOBAL_AJAX_URL.get_information,
        type: "POST",
        async: false,
        beforeSend: setHeader,
        error : function(xhr){
            if (xhr.status == 401) {
                top.location.href="login.html"
            }
        }
    });

// //判断是否登录
    // function isLogin() {
    //     $.ajax({
    //         url: ajaxRoot+GLOBAL_AJAX_URL.get_information,
    //         type: "POST",
    //         async: false,
    //         beforeSend: setHeader(),
    //         success: function (res) {
    //             if(res.data != null){
    //                 $("#loginButton").replaceWith(' <li id="loginButton" class="active lien"><a href="about.html"><i class="glyphicon glyphicon-user sr-icons"></i> 我的</a></li>')
    //             }
    //             console.log(res);
    //         }
    //     })
    // }
    //
    //
    // //调用函数
    // isLogin();

})
