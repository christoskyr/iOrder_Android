/*package mycompany.iorder.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import mycompany.iorder.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import mycompany.iorder.Config;
import mycompany.iorder.Utils;

public class iOrderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

	/*
	@Override
	public void onPause() {
	    super.onPause();
	    ((PreyApp)this.getApplication()).mLastPause = System.currentTimeMillis();
	}

	@Override
	public void onResume() {
	    super.onResume();
	    PreyApp app = ((PreyApp)this.getApplication());
	    if (System.currentTimeMillis() - app.mLastPause > PreyConfig.PASSWORD_PROMPT_DELAY) {
	    	Intent intent = new Intent(PreyActivity.this, CheckPasswordActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
	    }
	}


    protected Config getConfig(){
        return Config.getConfig(iOrderActivity.this);
    }

    protected String getDeviceType() {
        return Utils.getDeviceType(this);
    }

}*/