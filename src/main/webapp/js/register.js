;
! function() {
	var layer = layui.layer,
		form = layui.form;

	// 当勾选不同意协议按钮后禁止注册
	form.on("checkbox(agreen-checkbox)", function(data) {
		if(!data.elem.checked) {
			$("#reg").addClass("layui-btn-disabled");
			$("#reg").prop("disabled", "disabled");
		} else {
			$("#reg").removeClass("layui-btn-disabled");
			$("#reg").removeAttr("disabled");
		}
	});





	var phone_status;

	// 验证手机号唯一
	function vailPhone() {
		$.ajax({
			url: ajaxRoot+GLOBAL_AJAX_URL.validatePhone,
			type: "post",
			async: false,
			data: {
				"phone": $("#phone").val(),
			},
			success: function(result) {
				phone_status = result.status;
			}
		});
	}

	// 手机号输入框改变时验证
	$("#phone").on("change", function() {
		vailPhone();
		if(phone_status == 1) {
			layer.msg("该手机号已被注册", {
				icon: 5
			});
		}
	});


	// 自定义验证规则
	form.verify({
		regexit: function() {
			vailPhone();
			if(phone_status == 1) {
				return "该手机号已被注册";
			}
		},
		pwd: function(value) {
			if(value.length < 8) {
				return "密码长度不能少于8位";
			} else if(!/^(\w){8,20}$/.test(value)) {
				return "密码只能包含字母、数字或下划线";
			}
		},
		rePwd: function(value) {
			if(value != $("#password").val()) {
				return "两次输入的密码不一致";
			}
		},
		code: function(value) {
			if(value.toUpperCase() != vailCode) {
				refCode();
				return "图品验证码错误";
			}
		},
		msgcode: function(value) {
			if(value.trim().length != 6) {
				return "短信验证码错误";
			} else if(msg_send_count == 0) {
				layer.msg("请点击获取验证码");
			} else {
				$.ajax({
					url: "user/vailSMSCode.do",
					type: "post",
					async: false,
					data: {
						"code": value,
						"type": "info"
					},
					success: function(result) {
						if(result.status == 0) {
							return result.msg;
						}
					}
				});
			}
		}
	});

	//监听提交
	form.on("submit(register)", function(data) {
		if(phone_status == 1) {
			layer.msg("该手机号已被注册", {
				icon: 5
			});
		} else {
			$.ajax({
				url: ajaxRoot+GLOBAL_AJAX_URL.userRegist,
				type: "post",
				async: false,
				data: {
					"username": data.field.username,
					"phone": data.field.phone,
					"password": data.field.password,
					"question": data.field.question,
					"answer": data.field.answer
				},
				success: function(result) {
					if(result.status == 0) {
						layer.msg("注册成功");
						setTimeout("location='login.html'", 2000);
					} else {
						$("form")[0].reset();
						layer.msg(result.msg, {
							icon: 5
						});
					}
				}
			});
		}

		return false;
	});
}();