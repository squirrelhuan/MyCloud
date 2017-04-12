package cells;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.mycloud.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Sampler.Value;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Contant extends Activity{
	private int fromId;
	private String[] name;
	private String info="";;
	private ListView list;
	public TelephonyManager Tel;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.cell_contant);
	list=(ListView) findViewById(R.id.contant);
	Intent intent =getIntent();
	Bundle bundle = intent.getExtras();
	final String re_name = bundle.getString("re_name");
	Tel=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	
	ArrayList<HashMap<String,Object>> item1=new ArrayList<HashMap<String,Object>>();
	
	if(re_name.equals("gps")){
		
		int datastate,dataactivity_state;
		String DATA,dataactivity;
		datastate=Tel.getDataState();
		dataactivity_state=Tel.getDataActivity();
		switch (datastate) {
		case TelephonyManager.DATA_CONNECTED:
			DATA="已连接";
			break;
		case TelephonyManager.DATA_CONNECTING:
			DATA="正在连接";
			break;
		case TelephonyManager.DATA_DISCONNECTED:
			DATA="断开连接";
			break;
		case TelephonyManager.DATA_SUSPENDED:
			DATA="暂停连接";
			break;

		default:DATA="连接状态未知";
			break;
		}
		switch (dataactivity_state) {
		case TelephonyManager.DATA_ACTIVITY_IN:
			dataactivity="正在接收数据";
			break;

		default:
			break;
		}
		for(int i=0;i<26;i++){
			HashMap<String,Object>item_contant=new HashMap<String,Object>();
		switch (i) {
		case 0:
			info=DATA;
			break;
		default:
			break;
		}
		   item_contant.put("username",info);
		   item_contant.put("age","11");
		   item_contant.put("img",info );
		   item1.add(item_contant);
	}
	   SimpleAdapter simpleAdapter_1 = new SimpleAdapter(this, item1,
			    R.layout.cell_listview_item_image,
			    new String[]{ "username"},
			    new int[]{ R.id.name});
	   list.setAdapter(simpleAdapter_1);
	}else if(re_name.equals("Telephony")){
		Tel=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		Boolean icc,network_roaming;
		int state,networktype,phtype,simstate;
		String IMEI,phone = null,num,countryiso,networkoperator,worktype = null,phonetype = null,
				simcountryiso,sim_state = null,simoperator,simoperatorname,simserialnumber,subscriberid,
				mailalphatag,icc_state,networkroaming = null,voiceMailnumber;
		state=Tel.getCallState();
		IMEI=Tel.getDeviceSoftwareVersion();
		num=Tel.getLine1Number();
		countryiso=Tel.getNetworkCountryIso();
		networkoperator=Tel.getNetworkOperator();
		networktype=Tel.getNetworkType();
		phtype=Tel.getPhoneType();
		simcountryiso=Tel.getSimCountryIso();
		simstate=Tel.getSimState();
		simoperator=Tel.getSimOperator();
		simoperatorname=Tel.getSimOperatorName();//服务商
		simserialnumber=Tel.getSimSerialNumber();//获取sim卡序列号
		subscriberid=Tel.getSubscriberId();//获取用户唯一ID
		mailalphatag=Tel.getVoiceMailAlphaTag();//获取语音邮箱识别码
		icc=Tel.hasIccCard();
		network_roaming=Tel.isNetworkRoaming();
		voiceMailnumber=Tel.getVoiceMailNumber()+"语音邮件号";
		
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			phone="空闲状态！";
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			phone="正在接听电话！";
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			phone="正在响铃！";
		    break;
		default:
			break;}
		switch (networktype) {
		case TelephonyManager.NETWORK_TYPE_CDMA:
			worktype="当前网络为CDMA";
			break;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			worktype="当前网络为EDGE";
			break;
		case TelephonyManager.NETWORK_TYPE_GPRS:
			worktype="当前网络为GPRS";
			break;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			worktype="当前网络为UMTS";
			break;
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			worktype="当前网络为HSUPA";
			break;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			worktype="当前网络为HSDPA";
			break;
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			worktype="当前网络为EHRPD";
			break;
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			worktype="当前网络为1xRTT";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			worktype="当前网络为EVDO_0";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			worktype="当前网络为EVDO_A";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			worktype="当前网络为EVDO_B";
			break;
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			worktype="当前网络为HSPAP";
			break;
		case TelephonyManager.NETWORK_TYPE_LTE:
			worktype="当前网络为LTE";
			break;
		case TelephonyManager.NETWORK_TYPE_IDEN:
			worktype="当前网络为IDEN";
			break;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			worktype="当前网络为UNKNOWN";
			break;
		default:
			break;
		}
		switch (phtype) {
		case TelephonyManager.PHONE_TYPE_CDMA:
			phonetype="当前手机为CDMA制式";
			break;
		case TelephonyManager.PHONE_TYPE_GSM:
			phonetype="当前手机为GSM制式";
			break;
		case TelephonyManager.PHONE_TYPE_NONE:
			phonetype="当前手机为NONE制式";
			break;
		case TelephonyManager.PHONE_TYPE_SIP:
			phonetype="当前手机为SIP制式";
			break;

		default:
			break;
		}
		switch (simstate) {
		case TelephonyManager.SIM_STATE_ABSENT:
			sim_state="没插卡";
			break;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			sim_state="锁定状态，需要网络的PIN码解锁";
			break;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			sim_state="锁定状态，需要用户的PIN码解锁";
			break;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			sim_state="锁定状态，需要用户的PUK码解锁";
			break;
		case TelephonyManager.SIM_STATE_READY:
			sim_state="准备就绪状态";
			break;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			sim_state="未知状态";
			break;

		default:
			break;
		}
		if(icc){
			icc_state="ICC卡存在";
		}else{
			icc_state="ICC卡不存在";
		}
		if(network_roaming){
			networkroaming="漫游状态";
		}else{
			networkroaming="非漫游状态";
		}
		for(int i=0;i<26;i++){
			HashMap<String,Object>item_contant=new HashMap<String,Object>();
		switch (i) {
		case 0:info=phone;
			break;
		case 1:info=IMEI;
			break;
		case 2:info=num;
			break;
		case 3:info=countryiso;
			break;
		case 4:info=networkoperator;
			break;
		case 5:info=worktype;
			break;
		case 6:info=phonetype;
			break;
		case 7:info=simcountryiso;
			break;
		case 8:info=sim_state;
		    break;
		case 9:info=simoperator;
	        break;
		case 10:info=simoperatorname;
	        break;
		case 11:info=simserialnumber;
	        break;
		case 12:info=subscriberid;
	        break;
		case 13:info=mailalphatag;
		    break;
		case 14:info=icc_state;
			break;
		case 15:info=networkroaming;
		    break;
		case 16:info=voiceMailnumber;
			break;
		case 17:
			break;
		
		default:
			break;
		}
		
		
		item_contant.put("username",info);
		   item_contant.put("age","11");
		   item_contant.put("img",info );
		   item1.add(item_contant);
	}
	   SimpleAdapter simpleAdapter_1 = new SimpleAdapter(this, item1,
			    R.layout.cell_listview_item_image,
			    new String[]{ "username"},
			    new int[]{ R.id.name});
	   list.setAdapter(simpleAdapter_1);
	}else if(re_name.equals("mobile")){
	
		String mobile_info,SDK_info,android_info,BOOTLOADER,BRAND,CPU_ABI,CPU_ABI2,DEVICE,DISPLAY,
		FINGERPRINT,HARDWARE,HOST,ID,MANUFACTURER,PRODUCT,RADIO,SERIAL,BOARD,TAGS,TYPE,USER,CODENAME,INCREMENTAL;
		long TIME;
		int SDK_info2;
		mobile_info=Build.MODEL;//获取手机型号
		SDK_info=Build.VERSION.SDK;//获取SDK版本号
		SDK_info2=Build.VERSION.SDK_INT;
		android_info=Build.VERSION.RELEASE;//获取系统版本
		BOOTLOADER=Build.BOOTLOADER;
		BRAND=Build.BRAND;
		CPU_ABI=Build.CPU_ABI;//指令集
		CPU_ABI2=Build.CPU_ABI2;//指令集
		DEVICE=Build.DEVICE;
		DISPLAY=Build.DISPLAY;//显示
		FINGERPRINT=Build.FINGERPRINT;//唯一标示符
		HARDWARE=Build.HARDWARE;//硬件CPU
		HOST=Build.HOST;
		ID=Build.ID;
		MANUFACTURER=Build.MANUFACTURER;
		PRODUCT=Build.PRODUCT;
		RADIO=Build.RADIO;//无线电版本号
		SERIAL=Build.SERIAL;//硬件序列号
		BOARD=Build.BOARD;//底层版名称
		TAGS=Build.TAGS;
		TIME=Build.TIME;
		TYPE=Build.TYPE;//构建类型
		USER=Build.USER;//构建用户名
		CODENAME=Build.VERSION.CODENAME;//开发代码
		INCREMENTAL=Build.VERSION.INCREMENTAL;//构建增量
		
	
	for(int i=0;i<26;i++){
		HashMap<String,Object>item_contant=new HashMap<String,Object>();
		switch (i) {
		case 0:
			info=mobile_info;
			break;
		case 1:
			info=SDK_info;
			break;
		default:
			info=String.valueOf(SDK_info2);
			break;
		case 3:
			info=android_info;
			break;
		case 4:
			info=BOOTLOADER;
			break;
		case 5:
			info=BRAND;
			break;
		case 6:
			info=CPU_ABI;
			break;
		case 7:info=CPU_ABI2;
			break;
		case 8:info=DEVICE;
			break;
		case 9:info=DISPLAY;
			break;
		case 10:info=FINGERPRINT;
			break;
		case 11:info=HARDWARE;
			break;
		case 12:info=HOST;
			break;
		case 13:info=ID;
			break;
		case 14:info=MANUFACTURER;
			break;
		case 15:info=PRODUCT;
			break;
		case 16:info=RADIO;
			break;
		case 17:info=SERIAL;
			break;
		case 18:info=BOARD;
			break;
		case 19:info=TAGS;
			break;
		case 20:info=String.valueOf(TIME);
			break;
		case 21:info=TYPE;
			break;
		case 22:info=USER;
			break;
		case 23:info=CODENAME;
			break;
		case 24:info=INCREMENTAL;
			break;
		case 25:
			break;
		case 26:
			break;
		case 27:
			break;
			
		}
		
		   item_contant.put("username",info);
		   item_contant.put("age","11");
		   item_contant.put("img",info );
		   item1.add(item_contant);
	}
	   SimpleAdapter simpleAdapter_1 = new SimpleAdapter(this, item1,
			    R.layout.cell_listview_item_image,
			    new String[]{ "username"},
			    new int[]{ R.id.name});
	   list.setAdapter(simpleAdapter_1);	
}
else if(re_name.equals("storage")){
	File f;
	String data,download,storage,root,DIRECTORY_DCIM,state,sdcardstate="",emulated_state="",remove_state;
	boolean state_emul,state_remove;
	f=Environment.getDataDirectory();
	data="数据目录："+f.getPath().toString();
	f=Environment.getDownloadCacheDirectory();
	download="数据目录："+f.getPath().toString();
	f=Environment.getExternalStorageDirectory();
	storage="数据目录："+f.getPath().toString();
	f=Environment.getRootDirectory();
	root="root："+f.getPath().toString();
	f=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
	DIRECTORY_DCIM="照片："+f.getPath().toString();
	state=Environment.getExternalStorageState();
	if(Environment.MEDIA_BAD_REMOVAL.equals(state)){
		sdcardstate="在媒体停止前，SDcard已被卸载！";
	}else if(Environment.MEDIA_CHECKING.equals(state)){
		sdcardstate="磁盘检查中...";
	}else if(Environment.MEDIA_MOUNTED.equals(state)){
		sdcardstate="可以正常读写！";
	}else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
		sdcardstate="只可以读取！";
	}else if(Environment.MEDIA_NOFS.equals(state)){
		sdcardstate="空白，或者不支持的文件系统！";
	}else if(Environment.MEDIA_REMOVED.equals(state)){
		sdcardstate="SD卡不存在！";
	}else if(Environment.MEDIA_SHARED.equals(state)){
		sdcardstate="SD卡没安装，或正通过usb链接作为存储器！";
	}else if(Environment.MEDIA_UNKNOWN.equals(state)){
		sdcardstate="SD卡未知！";
	}else if(Environment.MEDIA_UNMOUNTABLE.equals(state)){
		sdcardstate="媒体存在，但文件系统损坏不能安装！";
	}else if(Environment.MEDIA_UNMOUNTED.equals(state)){
		sdcardstate="媒体存在但没有安装！";
	}
	state_emul=Environment.isExternalStorageEmulated();
	if(state_emul){
		emulated_state="模拟外部存储设备！";
	}else{
		emulated_state="非模拟外部存储设备！";
	}
	state_remove=Environment.isExternalStorageRemovable();
	if(state_remove){
		remove_state="外部存储设备可以移除！";
	}else{
		remove_state="外部存储设备不可以移除！";
	}
	for(int i=0;i<12;i++){
		HashMap<String,Object>item_contant=new HashMap<String,Object>();
		String info="";
		switch (i) {
		case 0:info=data;
			break;
		case 1:info=download;
			break;
		case 2:info=storage;
			break;
		case 3:info=root;
			break;
		case 4:info=DIRECTORY_DCIM;
			break;
		case 5:info=sdcardstate;
			break;
		case 6:info=emulated_state;
			break;
		case 7:info=remove_state;
		    break;

		default:
			break;
		}
		item_contant.put("username",info);
		   item_contant.put("age","11");
		   item_contant.put("img",info );
		   item1.add(item_contant);
		}
	SimpleAdapter simpleAdapter_1 = new SimpleAdapter(this, item1,
		    R.layout.cell_listview_item_image,
		    new String[]{ "username"},
		    new int[]{ R.id.name});
   list.setAdapter(simpleAdapter_1);
}
}}
