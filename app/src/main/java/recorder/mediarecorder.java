package recorder;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mycloud.R;

import java.io.IOException;

public class mediarecorder extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.recorder_main);  
	    ImageView btn_record=(ImageView) findViewById(R.id.btn_record);
	    ImageView btn_stop=(ImageView) findViewById(R.id.btn_stop);
	    ImageView btn_list=(ImageView) findViewById(R.id.btn_list);
	    
	    final MediaRecorder recorder=new MediaRecorder();
	    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(Environment.getExternalStorageDirectory()+"/myr.amr");
	    try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    btn_record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
			    recorder.start();
			    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
			}
		});
	    btn_stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				recorder.stop();
				Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
			}
		});
	}
	}

