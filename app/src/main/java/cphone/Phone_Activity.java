package cphone;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mycloud.R;


public class Phone_Activity extends Activity implements OnClickListener{
	public static final int ITEM0=Menu.FIRST;
	public static final int ITEM1=Menu.FIRST+1;
	Button btn1,btn2;
	private ImageView ic_phonecontact_open,ic_contactrencent_open,number_delete,ic_phonekeyboard_close,ic_phonekeyboard_open,ic_phone_call,number_00,number_01,number_02,number_03,number_04,number_05,number_06,number_07,number_08,number_09,number_010,number_011;
	private LinearLayout ll_keyboard_open,keyboard_04;
	private EditText phone_number;
	public String edtnumber,str_length;
	public TelephonyManager Tel;
	public int U=0;		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.phone_mian);

		/*menu*/
		ic_phonekeyboard_close=(ImageView) findViewById(R.id.ic_phonekeyboard_open);
		ic_phonekeyboard_close.setOnClickListener(this);
		ic_phonekeyboard_close=(ImageView) findViewById(R.id.ic_phonekeyboard_close);
		ic_phonekeyboard_close.setOnClickListener(this);
		ic_phone_call=(ImageView) findViewById(R.id.ic_phone_call);
		ic_phone_call.setOnClickListener(this);
		ic_contactrencent_open=(ImageView) findViewById(R.id.ic_contactrencent_open);
		ic_contactrencent_open.setOnClickListener(this);
		ic_phonecontact_open=(ImageView) findViewById(R.id.ic_phonecontact_open);
		ic_phonecontact_open.setOnClickListener(this);
		ll_keyboard_open=(LinearLayout) findViewById(R.id.ll_keyboard_open);
		ll_keyboard_open.setOnClickListener(this);
		keyboard_04=(LinearLayout) findViewById(R.id.keyboard_04);
		keyboard_04.setOnClickListener(this);
		
		number_delete=(ImageView) findViewById(R.id.number_delete);
		number_delete.setOnClickListener(this);
		number_00=(ImageView) findViewById(R.id.number_00);
		number_00.setOnClickListener(this);
		number_01=(ImageView) findViewById(R.id.number_01);
		number_01.setOnClickListener(this);
		number_02=(ImageView) findViewById(R.id.number_02);
		number_02.setOnClickListener(this);
		number_03=(ImageView) findViewById(R.id.number_03);
		number_03.setOnClickListener(this);
		number_04=(ImageView) findViewById(R.id.number_04);
		number_04.setOnClickListener(this);
		number_05=(ImageView) findViewById(R.id.number_05);
		number_05.setOnClickListener(this);
		number_06=(ImageView) findViewById(R.id.number_06);
		number_06.setOnClickListener(this);
		number_07=(ImageView) findViewById(R.id.number_07);
		number_07.setOnClickListener(this);
		number_08=(ImageView) findViewById(R.id.number_08);
		number_08.setOnClickListener(this);
		number_09=(ImageView) findViewById(R.id.number_09);
		number_09.setOnClickListener(this);
		number_010=(ImageView) findViewById(R.id.number_010);
		number_010.setOnClickListener(this);
		number_011=(ImageView) findViewById(R.id.number_011);
		number_011.setOnClickListener(this);
		
		phone_number=(EditText) findViewById(R.id.phone_number);
		edtnumber=phone_number.getText().toString();
		phone_number.setInputType(InputType.TYPE_NULL);
		phone_number.setText(edtnumber.toCharArray(), 0, phone_number.length());
		Tel=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
		}
	public int state(){
		int state;
		state=Tel.getCallState();
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			U=0;
			Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			U=1;
			Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			U=2;
			Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
		    break;

		default:
			break;
		}
		return U;
	}
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,ITEM0,0,"ť1");
		menu.add(0,ITEM1,0,"ť2");
		menu.add(0,ITEM1,0,"ť3");
		menu.add(0,ITEM1,0,"ť4");
		menu.findItem(ITEM1);
		return true;
		
	}
	public boolean onCreateOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case ITEM0:
			actionClickMenuItem1();
			break;
		case ITEM1:
			actionClickMenuItem2();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void actionClickMenuItem2() {
		// TODO Auto-generated method stub
		setTitle("btn1 kejian");
		btn1.setVisibility(View.VISIBLE);
		btn2.setVisibility(View.INVISIBLE);
		
	}

	private void actionClickMenuItem1() {
		// TODO Auto-generated method stub
		setTitle("btn2 kejian");
		btn2.setVisibility(View.VISIBLE);
		btn1.setVisibility(View.INVISIBLE);}
		
	
	/*menu*/
		
	
	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		
		case R.id.ic_phonecontact_open:
			Intent intent2=new Intent();
			intent2.setAction(Intent.ACTION_VIEW);
			intent2.setData(Contacts.People.CONTENT_URI);
			startActivity(intent2);

			break;
		case R.id.ic_contactrencent_open:
			Intent intent1=new Intent();
			intent1.setAction(Intent.ACTION_CALL_BUTTON);
			startActivity(intent1);


			break;
		case R.id.ic_phonekeyboard_open:
			ll_keyboard_open.setVisibility(View.VISIBLE);
			keyboard_04.setVisibility(View.GONE);
			break;
		case R.id.ic_phonekeyboard_close:
			ll_keyboard_open.setVisibility(View.GONE);
			keyboard_04.setVisibility(View.VISIBLE);
			
			break;
		case R.id.ic_phone_call:
			state();
			String phonenumber=phone_number.getText().toString();
			    Intent intent=new Intent();
				intent.setAction("android.intent.action.CALL");
			    intent.addCategory("android.intent.category.DEFAULT");
			    intent.setData(Uri.parse("tel:"+phonenumber));
			    startActivity(intent);
			break;
		case R.id.number_01:
			
			edtnumber=edtnumber+"1";
			phone_number.setText(edtnumber);
			break;
		case R.id.number_02:
			edtnumber=edtnumber+"2";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_03:
			edtnumber=edtnumber+"3";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_04:
			edtnumber=edtnumber+"4";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_05:
			edtnumber=edtnumber+"5";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_06:
			edtnumber=edtnumber+"6";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_07:
			edtnumber=edtnumber+"7";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_08:
			edtnumber=edtnumber+"8";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_09:
			edtnumber=edtnumber+"9";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_010:
			edtnumber=edtnumber+"*";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_011:
			edtnumber=edtnumber+"#";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_00:
			edtnumber=edtnumber+"0";
			phone_number.setText(edtnumber);			
			break;
		case R.id.number_delete:
			if (phone_number.getText().toString().length()>0) {
				edtnumber=edtnumber.substring(0,edtnumber.length()-1);
				phone_number.setText(edtnumber);
			}
			
			break;

		default:
			break;
		}
		
	
		
	}
	
}
