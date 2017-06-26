package hook.android;

import java.lang.reflect.Method;
import java.util.Arrays;

import android.app.PendingIntent;
import android.util.Log;
import tools.statckTrace;

import com.saurik.substrate.MS;

//hook ��������
public class smsManager implements MS.ClassLoadHook
{

	private static smsManager SmsManager_Instance;
	public smsManager( )
	{
		super( );
	}

	public static void hook(){
		MS.hookClassLoad("android.telephony.SmsManager", getInstance());
	}
	
	public static smsManager getInstance( )
	{
		if ( SmsManager_Instance == null )
		{
			SmsManager_Instance = new smsManager( );
		}
		return SmsManager_Instance;
	}

	@Override
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public void classLoaded( Class< ? > SmsManager )
	{
		Method sendTextMessage = null;
		try
		{
			sendTextMessage = SmsManager.getMethod( "sendTextMessage", new Class[] { String.class, String.class, String.class, PendingIntent.class, PendingIntent.class } );
		}
		catch ( Exception e )
		{
			sendTextMessage =null;
			e.printStackTrace();
		}
		if(sendTextMessage!=null){
			final MS.MethodPointer sendTextMessageold = new MS.MethodPointer( );
			MS.hookMethod( SmsManager, sendTextMessage, new MS.MethodHook( )
			{
				@Override
				public Object invoked( Object arg0, Object... arg1 ) throws Throwable
				{
					Log.i( "hook_smsT_destination", (String) arg1[ 0 ] );
					Log.i( "hook_smsT_text", (String) arg1[ 2 ] );
					statckTrace.LogStatckTrace("hook_smsT_StatckTrace");
					return sendTextMessageold.invoke( arg0, arg1 );
				}
			}, sendTextMessageold );
		}
		Method sendDataMessage = null;
		try
		{
			sendDataMessage = SmsManager.getMethod( "sendDataMessage", new Class[] { String.class, String.class, short.class,byte[].class, PendingIntent.class, PendingIntent.class } );

		}
		catch ( Exception e )
		{
			sendDataMessage = null;
			e.printStackTrace();
		}
		
		if(sendDataMessage!=null){
			final MS.MethodPointer sendDataMessageold = new MS.MethodPointer( );
			MS.hookMethod( SmsManager, sendDataMessage, new MS.MethodHook( )
			{
				@Override
				public Object invoked( Object arg0, Object... arg1 ) throws Throwable
				{
					Log.i( "hook_smsD_destination",(String) arg1[ 0 ] );
					Log.i( "hook_smsD_port", String.valueOf( arg1[ 2 ] ) );
					Log.i( "hook_smsD_array",  Arrays.toString((byte[]) arg1[ 3 ]) );
					statckTrace.LogStatckTrace("hook_smsD_StatckTrace");
					return sendDataMessageold.invoke( arg0, arg1 );
				}
			}, sendDataMessageold );
		}
		
		
		
		
	}




}