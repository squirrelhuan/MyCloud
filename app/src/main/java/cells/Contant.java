package cells;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mycloud.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
			DATA="";
			break;
		case TelephonyManager.DATA_CONNECTING:
			DATA="";
			break;
		case TelephonyManager.DATA_DISCONNECTED:
			DATA="";
			break;
		case TelephonyManager.DATA_SUSPENDED:
			DATA="";
			break;

		default:DATA="֪";
			break;
		}
		switch (dataactivity_state) {
		case TelephonyManager.DATA_ACTIVITY_IN:
			dataactivity="";
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
		simoperatorname=Tel.getSimOperatorName();//������
		simserialnumber=Tel.getSimSerialNumber();//��ȡsim�����к�
		subscriberid=Tel.getSubscriberId();//��ȡ�û�ΨһID
		mailalphatag=Tel.getVoiceMailAlphaTag();//��ȡ��������ʶ����
		icc=Tel.hasIccCard();
		network_roaming=Tel.isNetworkRoaming();
		voiceMailnumber=Tel.getVoiceMailNumber()+"�����ʼ���";
		
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			phone="����״̬��";
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			phone="���ڽ����绰��";
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			phone="�������壡";
		    break;
		default:
			break;}
		switch (networktype) {
		case TelephonyManager.NETWORK_TYPE_CDMA:
			worktype="��ǰ����ΪCDMA";
			break;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			worktype="��ǰ����ΪEDGE";
			break;
		case TelephonyManager.NETWORK_TYPE_GPRS:
			worktype="��ǰ����ΪGPRS";
			break;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			worktype="��ǰ����ΪUMTS";
			break;
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			worktype="��ǰ����ΪHSUPA";
			break;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			worktype="��ǰ����ΪHSDPA";
			break;
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			worktype="��ǰ����ΪEHRPD";
			break;
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			worktype="��ǰ����Ϊ1xRTT";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			worktype="��ǰ����ΪEVDO_0";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			worktype="��ǰ����ΪEVDO_A";
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			worktype="��ǰ����ΪEVDO_B";
			break;
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			worktype="��ǰ����ΪHSPAP";
			break;
		case TelephonyManager.NETWORK_TYPE_LTE:
			worktype="��ǰ����ΪLTE";
			break;
		case TelephonyManager.NETWORK_TYPE_IDEN:
			worktype="��ǰ����ΪIDEN";
			break;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			worktype="��ǰ����ΪUNKNOWN";
			break;
		default:
			break;
		}
		switch (phtype) {
		case TelephonyManager.PHONE_TYPE_CDMA:
			phonetype="��ǰ�ֻ�ΪCDMA��ʽ";
			break;
		case TelephonyManager.PHONE_TYPE_GSM:
			phonetype="��ǰ�ֻ�ΪGSM��ʽ";
			break;
		case TelephonyManager.PHONE_TYPE_NONE:
			phonetype="��ǰ�ֻ�ΪNONE��ʽ";
			break;
		case TelephonyManager.PHONE_TYPE_SIP:
			phonetype="��ǰ�ֻ�ΪSIP��ʽ";
			break;

		default:
			break;
		}
		switch (simstate) {
		case TelephonyManager.SIM_STATE_ABSENT:
			sim_state="û�忨";
			break;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			sim_state="����״̬����Ҫ�����PIN�����";
			break;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			sim_state="����״̬����Ҫ�û���PIN�����";
			break;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			sim_state="����״̬����Ҫ�û���PUK�����";
			break;
		case TelephonyManager.SIM_STATE_READY:
			sim_state="׼������״̬";
			break;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			sim_state="δ֪״̬";
			break;

		default:
			break;
		}
		if(icc){
			icc_state="ICC������";
		}else{
			icc_state="ICC��������";
		}
		if(network_roaming){
			networkroaming="����״̬";
		}else{
			networkroaming="������״̬";
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
		mobile_info=Build.MODEL;//��ȡ�ֻ��ͺ�
		SDK_info=Build.VERSION.SDK;//��ȡSDK�汾��
		SDK_info2=Build.VERSION.SDK_INT;
		android_info=Build.VERSION.RELEASE;//��ȡϵͳ�汾
		BOOTLOADER=Build.BOOTLOADER;
		BRAND=Build.BRAND;
		CPU_ABI=Build.CPU_ABI;//ָ�
		CPU_ABI2=Build.CPU_ABI2;//ָ�
		DEVICE=Build.DEVICE;
		DISPLAY=Build.DISPLAY;//��ʾ
		FINGERPRINT=Build.FINGERPRINT;//Ψһ��ʾ��
		HARDWARE=Build.HARDWARE;//Ӳ��CPU
		HOST=Build.HOST;
		ID=Build.ID;
		MANUFACTURER=Build.MANUFACTURER;
		PRODUCT=Build.PRODUCT;
		RADIO=Build.RADIO;//���ߵ�汾��
		SERIAL=Build.SERIAL;//Ӳ�����к�
		BOARD=Build.BOARD;//�ײ������
		TAGS=Build.TAGS;
		TIME=Build.TIME;
		TYPE=Build.TYPE;//��������
		USER=Build.USER;//�����û���
		CODENAME=Build.VERSION.CODENAME;//��������
		INCREMENTAL=Build.VERSION.INCREMENTAL;//��������
		
	
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
	data="����Ŀ¼��"+f.getPath().toString();
	f=Environment.getDownloadCacheDirectory();
	download="����Ŀ¼��"+f.getPath().toString();
	f=Environment.getExternalStorageDirectory();
	storage="����Ŀ¼��"+f.getPath().toString();
	f=Environment.getRootDirectory();
	root="root��"+f.getPath().toString();
	f=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
	DIRECTORY_DCIM="��Ƭ��"+f.getPath().toString();
	state=Environment.getExternalStorageState();
	if(Environment.MEDIA_BAD_REMOVAL.equals(state)){
		sdcardstate="��ý��ֹͣǰ��SDcard�ѱ�ж�أ�";
	}else if(Environment.MEDIA_CHECKING.equals(state)){
		sdcardstate="���̼����...";
	}else if(Environment.MEDIA_MOUNTED.equals(state)){
		sdcardstate="����������д��";
	}else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
		sdcardstate="ֻ���Զ�ȡ��";
	}else if(Environment.MEDIA_NOFS.equals(state)){
		sdcardstate="�հף����߲�֧�ֵ��ļ�ϵͳ��";
	}else if(Environment.MEDIA_REMOVED.equals(state)){
		sdcardstate="SD�������ڣ�";
	}else if(Environment.MEDIA_SHARED.equals(state)){
		sdcardstate="SD��û��װ������ͨ��usb������Ϊ�洢����";
	}else if(Environment.MEDIA_UNKNOWN.equals(state)){
		sdcardstate="SD��δ֪��";
	}else if(Environment.MEDIA_UNMOUNTABLE.equals(state)){
		sdcardstate="ý����ڣ����ļ�ϵͳ�𻵲��ܰ�װ��";
	}else if(Environment.MEDIA_UNMOUNTED.equals(state)){
		sdcardstate="ý����ڵ�û�а�װ��";
	}
	state_emul=Environment.isExternalStorageEmulated();
	if(state_emul){
		emulated_state="ģ���ⲿ�洢�豸��";
	}else{
		emulated_state="��ģ���ⲿ�洢�豸��";
	}
	state_remove=Environment.isExternalStorageRemovable();
	if(state_remove){
		remove_state="�ⲿ�洢�豸�����Ƴ���";
	}else{
		remove_state="�ⲿ�洢�豸�������Ƴ���";
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
