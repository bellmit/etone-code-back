// JavaScript Document
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = 'js/ext2/resources/images/default/s.gif';

	var strartup = function() {
		mapWin = window.open('main.jsp', '_blank', 'left=0,' + 'top=0,'
				+ 'width=screen.availWidth,' + 'height=screen.availHeight,'
				+ 'menubar=yes,' + 'location=no,' + 'scrollbars=no,'
				+ 'toolbar=no,' + 'status=yes,' + 'resizable=yes,'
				+ 'copyhistory=no');

		if (mapWin == null) {
			Ext.MessageBox.alert("系统使用提示",
					"注意: 要正常使用本系统,请关闭或删除您的浏览中拦截弹出窗口的功能或插件!");
			return;
		}

		var larg = 0;
		var altez = 0;
		if (document.layers) {
			larg = screen.availWidth - 0;
			altez = screen.availHeight - 0;
		} else {
			larg = screen.availWidth + 8;
			altez = screen.availHeight + 8;
		}
		mapWin.resizeTo(larg, altez);
		mapWin.moveTo(-4, -4);
		shutdown();
	}

	var shutdown = function() {
		window.opener = null;
		window.close();
		return;
	}

	var loginForm = new Ext.form.BasicForm('login-form');
	// 帐号输入框效果及验证设置
	loginForm.add(new Ext.form.TextField({
		allowBlank : false, // 设置不允许为空
		blankText : '用户名不能为空',
		applyTo : 'userid'
	}));
	// 密码输入框效果及验证设置
	loginForm.add(new Ext.form.TextField({
		allowBlank : false, // 设置不允许为空
		blankText : '用户密码不能为空',
		applyTo : 'userpwd'
	}));

	Ext.get('login-form').un("submit", loginForm.onSubmit, loginForm);

	/**
	 * 登录
	 */
	var loginHandle = function() {
		if (loginForm.isValid()) {
			loginForm.timeout = 120;
			loginForm.submit({
				waitMsg : '正在进行系统登录 ... ',
				success : function(form, action) {
					strartup();
				},
				failure : function(form, action) {
					var msg = '服务器连接失败';
					if (action.result != undefined)
						msg = action.result.message;
					Ext.MessageBox.alert("登录失败", msg);
				}
			})
		} else {
			alert('test');
		}
	};

	var loginAction = new Ext.Action({
		applyTo : 'login-action',
		text : '登　录',
		width : 195,
		handler : loginHandle
	});

	var cancelAction = new Ext.Action({
		applyTo : 'logout-action',
		text : '关　闭',
		width : 195,
		handler : function() {
			Ext.MessageBox.confirm("请确认", "是否关闭及退出系统", function(btn) {
				if (btn == "yes") {
					top.opener = null;
					top.close();
				}
			});
		}
	});

	var loginButton = new Ext.Button(loginAction);
	var cancelButtion = new Ext.Button(cancelAction);

	Ext.KeyNav
	loginBody = Ext.getBody();
	loginBody.on('keypress', function(e) {
		if (e.getKey() == e.ENTER) {
			loginButton.focus();
			loginForm.fireEvent('sumbit');
		}
	});

	Ext.QuickTips.init();
});