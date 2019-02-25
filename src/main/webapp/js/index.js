$(function(){
    //分类一级查询
    $.ajax({
        url: ajaxRoot+GLOBAL_AJAX_URL.categoryList,
        type: "GET",
        success: function (res) {
            //todo
        }
    })

    //判断是否登录
    function isLogin() {
        $.ajax({
            url: ajaxRoot+GLOBAL_AJAX_URL.get_information,
            type: "POST",
            async: false,
            success: function (res) {
                if(res.data != null){
                    $("#loginButton").replaceWith(' <li id="loginButton" class="active lien"><a href="about.html"><i class="glyphicon glyphicon-user sr-icons"></i> 我的</a></li>')
                }
                console.log(res);
            }
        })
    }


    //调用函数
    isLogin();
});