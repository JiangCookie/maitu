// 服务器请求地址
// var ajaxRoot = 'http://127.0.0.1:8083';
var ajaxRoot = 'http://localhost:8083';


// 后台ajax请求地址
var GLOBAL_AJAX_URL = {

    //------登录注册------
    userlogin: '/user/login',
    userRegist: '/user/regist',
    validatePhone: '/user/validatePhone', //验证手机号

    //------用户------
    get_information: '/user/get_information',  //获取用户信息

    //------商品分类------
    add_category: '/manage/category/add_category',   //添加分类
    categoryList: '/manage/category/get_category',     //一级分类


    //------管理员登录------
    adminLogin: '/manage/user/login'

}