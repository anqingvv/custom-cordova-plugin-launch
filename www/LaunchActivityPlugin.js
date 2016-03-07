
var exec = require('cordova/exec');
var activity = {};

/** 启动应用的activity
 * @param successCallback 成功的回调函数
 * @param errorCallback 失败的回调函数
 * @param packageName 应用的包名
 * @param activityName activity名
 * @param id 应用id（可为空）
 * */
activity.launch = function(successCallback, errorCallback, packageName, activityName, id) {
	exec(successCallback, errorCallback, "LaunchActivityPlugin", "activity", [packageName, activityName, id]);
};

module.exports = activity;
