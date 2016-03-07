package com.vv.plugin.launch;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class LaunchActivityPlugin extends CordovaPlugin {

	private static final String ACTION = "activity";
	private static final int CALL_ACTIVITY = 1;
	private CallbackContext callbackContext;
	
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		
		this.callbackContext = callbackContext;
		if (ACTION.equals(action)) {
			String packageName = args.getString(0);
			if (null == packageName || 0 == packageName.length()) {
				callbackContext.error(String.format("启动activity方法包名错误：%s", packageName));
			}
			
			String activityName = args.getString(1);
			if (null == activityName || 0 == activityName.length()) {
				callbackContext.error(String.format("启动activity方法activity名错误：%s", activityName));
			}
			ComponentName componentName = new ComponentName(packageName, packageName + "." + activityName);
			
			Intent intent = new Intent();
			String arg = args.getString(2);
			if (null != arg && 0 != arg.length()) {
				Bundle bundle = new Bundle();
				bundle.putString("id", arg);
				intent.putExtras(bundle);
			}
			intent.setComponent(componentName);
			this.cordova.startActivityForResult(this, intent, CALL_ACTIVITY);
			
		} else {
			callbackContext.error(String.format("启动activity方法调用错误，本次调用方法为：%s，正确方法为：activity()", action));
		}
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (Activity.RESULT_OK == requestCode) {
			
		} else if (requestCode == Activity.RESULT_CANCELED) {
			try {
				 if (CALL_ACTIVITY != resultCode) {
					 this.callbackContext.success("fail");
				}
			} catch (Exception e) {
				this.callbackContext.error(e.getMessage());
			}
		} else {
			
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
	
}
