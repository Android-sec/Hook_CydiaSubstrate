package cydiasubstrate.hooktools;

import android.util.Log;

public class Initialize {
	public static void initialize() {
		Log.v("java_hook", "start!");
		//com.tencent.mm.app.MMApplication 
		//MyApplication.hook("com.tencent.mm.app.MMApplication");
		MyApplication.hook("android.taobao.atlas.startup.AtlasBridgeApplication");

		Log.v("java_hook", "end!");
	}
}
