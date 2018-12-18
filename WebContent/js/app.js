/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo) {
		loginInfo = loginInfo || {};
		localStorage.setItem('$users', JSON.stringify(loginInfo));
	};
	
	/**
	 * 帖子评论页缓存，防止图片上传时数据丢失
	 **/
	owner.commentCache = function(commentInfo) {
		commentInfo = commentInfo || {};
		localStorage.setItem('$comment', JSON.stringify(commentInfo));
	};
	
	/**
	 * 帖子评论页缓存，防止图片上传时数据丢失
	 **/
	owner.getCommentCache = function() {
		var comment = localStorage.getItem('$comment') || "{}";
		return JSON.parse(comment);
	};
	
	/**
	 * 帖子页缓存，防止图片上传时数据丢失
	 **/
	owner.topicCache = function(topicInfo) {
		topicInfo = topicInfo || {};
		localStorage.setItem('$topic', JSON.stringify(topicInfo));
	};
	
	/**
	 * 帖子页缓存，防止图片上传时数据丢失
	 **/
	owner.getTopicCache = function() {
		var topic = localStorage.getItem('$topic') || "{}";
		return JSON.parse(topic);
	};
	
	/**
	 * 帖吧页缓存，防止图片上传时数据丢失
	 **/
	owner.tiebaCache = function(tiebaInfo) {
		tiebaInfo = tiebaInfo || {};
		localStorage.setItem('$tieba', JSON.stringify(tiebaInfo));
	};
	
	/**
	 * 帖吧页缓存，防止图片上传时数据丢失
	 **/
	owner.getTiebaCache = function() {
		var tieba = localStorage.getItem('$tieba') || "{}";
		return JSON.parse(tieba);
	};
	
	/**
	 * 获取用户信息
	 **/
	owner.getUser = function() {
		var user = localStorage.getItem('$users') || "{}";
		return JSON.parse(user);
	}

	/**
	 * 新用户注册
	 **/
	owner.regCheck = function(regInfo, callback) {
		callback = callback || $.noop;
		regInfo = regInfo || {};
		regInfo.account = regInfo.account || '';
		regInfo.password = regInfo.password || '';
		if (regInfo.account.length < 5) {
			return callback('用户名最短需要 5 个字符');
		}
		if (regInfo.password.length < 6) {
			return callback('密码最短需要 6 个字符');
		}
		if (!checkEmail(regInfo.email)) {
			return callback('邮箱地址不合法');
		}
		
		return callback();
	};
	
	/**
	 * 用户信息变更
	 **/
	owner.userCheck = function(userInfo, callback) {
		callback = callback || $.noop;
		userInfo = userInfo || {};
		userInfo.user_pet_name = userInfo.user_pet_name || '';
		userInfo.user_email = userInfo.user_email || '';
		
		if (userInfo.user_pet_name.length < 2) {
			return callback('昵称最短需要 2 个字符');
		}
		if (!checkEmail(userInfo.user_email)) {
			return callback('邮箱地址不合法');
		}
		
		return callback();
	};

	var checkEmail = function(email) {
		email = email || '';
		return (email.length > 3 && email.indexOf('@') > -1);
	};

	/**
	 * 找回密码
	 **/
	owner.forgetPassword = function(forgetInfo, callback) {
		callback = callback || $.noop;
		if (!checkEmail(forgetInfo.email)) {
			return callback('邮箱地址不合法');
		}
		return callback(null, '邮件正在发送，如果没有收到，请去邮箱垃圾邮件那里找找。');
	};
	
	/**
	 * 验证创建贴吧信息
	 **/
	owner.tiebaCheck = function(tiebaInfo, callback) {
		callback = callback || $.noop;
		
		if (tiebaInfo.name.length < 1) {
			return callback('贴吧名最短需要 1 个字符');
		}
		if (tiebaInfo.description.length < 6) {
			return callback('贴吧描述介绍最短需要 6 个字符');
		}
		if (tiebaInfo.cover.length < 5) {
			return callback('图片地址为空');
		}
		return callback(null, '正在创建贴吧！');
	};
	

	/**
	 * 验证创建贴子信息
	 **/
	owner.topicCheck = function(tiebaInfo, callback) {
		callback = callback || $.noop;
		
		if (tiebaInfo.topic_title.length < 3) {
			return callback('帖子名最短需要 3 个字符');
		}
		if (tiebaInfo.topic_content.length < 6) {
			return callback('帖子内容最短需要 6 个字符');
		}
		
		return callback(null, '正在发送帖子，请稍候！');
	};
	
	/**
	 * 验证回复信息
	 **/
	owner.commentCheck = function(commentInfo, callback) {
		callback = callback || $.noop;
		
		if (commentInfo.comment_content.length < 3) {
			return callback('回复最少需要 3 个字符');
		}
		
		return callback(null, '正在发送评论内容 ，请稍候！');
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	}

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
			var settingsText = localStorage.getItem('$settings') || "{}";
			return JSON.parse(settingsText);
		}
		/**
		 * 获取本地是否安装客户端
		 **/
	owner.isInstalled = function(id) {
		if (id === 'qihoo' && mui.os.plus) {
			return true;
		}
		if (mui.os.android) {
			var main = plus.android.runtimeMainActivity();
			var packageManager = main.getPackageManager();
			var PackageManager = plus.android.importClass(packageManager)
			var packageName = {
				"qq": "com.tencent.mobileqq",
				"weixin": "com.tencent.mm",
				"sinaweibo": "com.sina.weibo"
			}
			try {
				return packageManager.getPackageInfo(packageName[id], PackageManager.GET_ACTIVITIES);
			} catch (e) {}
		} else {
			switch (id) {
				case "qq":
					var TencentOAuth = plus.ios.import("TencentOAuth");
					return TencentOAuth.iphoneQQInstalled();
				case "weixin":
					var WXApi = plus.ios.import("WXApi");
					return WXApi.isWXAppInstalled()
				case "sinaweibo":
					var SinaAPI = plus.ios.import("WeiboSDK");
					return SinaAPI.isWeiboAppInstalled()
				default:
					break;
			}
		}
	}
}(mui, window.app = {}));