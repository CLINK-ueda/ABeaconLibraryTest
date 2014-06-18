
package com.example.abeaconlibrarytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import jp.gaprot.abeaconlibrary.data.BeaconRegion;
import jp.gaprot.abeaconlibrary.manager.BeaconManager;
import jp.gaprot.abeaconlibrary.manager.BeaconManager.BeaconException;
import jp.gaprot.abeaconlibrary.manager.BeaconManager.BeaconResultCallback;
import jp.gaprot.abeaconlibrary.manager.BeaconResult;
import jp.gaprot.abeaconlibrary.service.BeaconService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements BeaconResultCallback{
    //監視対象となるビーコンの値
  //  private static final String TARGET_UUID = "F99C233A-E1B5-4AE1-9F5C-1A1DFB936FFA";
    private static final String TARGET_UUID = "00000000-EA00-1001-B000-001C4D25E26A";
    private static final String TARGET_ID = "testRegion";

    private TextView mResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //マネージャにコールバックと、監視対象ビーコンリストを設定
        BeaconManager beaconManager = BeaconManager.getInstance();
        beaconManager.setCallback(this);

        BeaconRegion region = new BeaconRegion(TARGET_UUID,TARGET_ID);
        List<BeaconRegion> regionList = new ArrayList<BeaconRegion>();
        regionList.add(region);
        beaconManager.setRegionList(regionList);

        mResultView = (TextView)findViewById(R.id.textView);
        mResultView.setText("ready\n");

        setStartButton();

        setStopButton();
    }
    private void setStartButton() {
        Button startButton = (Button)findViewById(R.id.button);
        startButton.setText("start");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(MainActivity.this, BeaconService.class);
                mResultView.append("start\n");
                //監視スタート
                startService(serviceIntent);
            }
        });
    }

    private void setStopButton() {
        Button stopButton = (Button)findViewById(R.id.button2);
        stopButton.setText("stop");
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(MainActivity.this, BeaconService.class);
                mResultView.append("stop\n");
                //監視終了
                stopService(serviceIntent);
            }
        });
    }

    @Override
    public void onBeaconMonitoringFailed(BeaconException error) {
        mResultView.append("\nError occur : " +error+"\n");
    }
    @Override
    /*
    public void onEnterRegion(BeaconResult result) {
        mResultView.append("\ndiscover iBeacon \n uuid = "+result.getUUID()+"\n");
        
    }
    */
    public void onEnterRegion(BeaconResult result) {
        mResultView.append("\ndiscover iBeacon \n uuid = "+result.getUUID()+"\n");
    	// 郵便局のHPを開く
    	Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://map.japanpost.jp/pc/syousai.php?id=300143258000"));
    	startActivity(intent);
    }
    public void onExitRegion(BeaconResult result) {
        mResultView.append("\nlost iBeacon \n uuid = " + result.getUUID()+"\n" );  
//    	startActivity(this);
    }

}
