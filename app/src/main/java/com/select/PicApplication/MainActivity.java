package com.select.PicApplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.select.untils.CallBackListener;
import com.select.untils.FileHelper;
import com.select.untils.LightSensorManager;
import com.select.untils.Untils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CallBackListener, SeekBar.OnSeekBarChangeListener {
    TextView sele_pic, bnt1, bnt2, bnt3, bnt5;
    TextView values1, values2, values3;
    TextView values_name1,values_name2,values_name3,values_name4;
    ImageView img;
    SeekBar seekBar;
    LightSensorManager li;
    int draw[];
    String num[];
    String fiele_name="save_info.txt";
    int posi=-1;
    String eva;
    int sinfo;
    String startValue,endValues,eValues="0";
    boolean isTrue=true;
    TextView tvs[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initveiw();
        initlistener();
        setDefaultValues();
    }

    private void setDefaultValues() {
        int v = Untils.getScreenBrightness(this);
        sinfo=v;
        values2.setText(v + "");
        Random random = new Random();// 定义随机类
        int result = random.nextInt(6);// 返回[0,10)集合中的整数，注意不包括10
        img.setBackgroundDrawable(Untils.getBitmap(this, draw[result]));
        posi=result;
        values3.setText(num[result] + "");
        if(result==1||result==2||result==5){
            setTextColors("#ffffff");
            setSeekBars(1);
        }else{
            setTextColors("#232323");
            setSeekBars(0);
        }
        seekBar.setMax(255);
        seekBar.setProgress(v);
        startValue=endValues=v+"";
        requestPermission();
    }

    private void initlistener() {
        sele_pic.setOnClickListener(this);
        bnt1.setOnClickListener(this);
        bnt2.setOnClickListener(this);
        bnt3.setOnClickListener(this);
        //   bnt4.setOnClickListener(this);
        bnt5.setOnClickListener(this);
     
        draw = new int[]{R.mipmap.white, R.mipmap.blake, R.mipmap.blue, R.mipmap.green, R.mipmap.mi, R.mipmap.zi};
        num = new String[]{"001", "002", "003", "004", "005", "006"};
        tvs=new TextView[]{sele_pic,bnt1,bnt2,bnt3,bnt5,values1, values2, values3,values_name1,values_name2,values_name3,values_name4}; 
    }
   
    private void initveiw() {
       
        sele_pic = (TextView) findViewById(R.id.sele_pic);
        bnt1 = (TextView) findViewById(R.id.bnt1);
        bnt2 = (TextView) findViewById(R.id.bnt2);
        bnt3 = (TextView) findViewById(R.id.bnt3);

        bnt5 = (TextView) findViewById(R.id.bnt5);
        values1 = (TextView) findViewById(R.id.values1);
        values2 = (TextView) findViewById(R.id.values2);
        values3 = (TextView) findViewById(R.id.values3);
        img = (ImageView) findViewById(R.id.img);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        values_name1= (TextView) findViewById(R.id.values_name1);
        values_name2= (TextView) findViewById(R.id.values_name2);
        values_name3= (TextView) findViewById(R.id.values_name3);
        values_name4= (TextView) findViewById(R.id.values_name4);
        li = LightSensorManager.getInstance(this, this);
    }

 
    
    private void setTextColors(String co){
      for(int i=0;i<tvs.length;i++){
          tvs[i].setTextColor(Color.parseColor(co));
        }
    }

    private void setSeekBars(int co){
       if(co==1){
           seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.mn_player_progress_diy1));
           seekBar.setThumb(getResources().getDrawable(R.drawable.mn_player_thumb1));
       }else{
           seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.mn_player_progress_diy));
           seekBar.setThumb(getResources().getDrawable(R.drawable.mn_player_thumb));
       }
    }
    
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sele_pic) {
            startActivityForResult(new Intent(MainActivity.this, SeleActivity.class), 100);
        } else if (v.getId() == R.id.bnt1) {
            eva="好评";
           // saveInfo();
            Toast.makeText(MainActivity.this, "你点了好评", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.bnt2) {
            eva="一般";
           // saveInfo();
            Toast.makeText(MainActivity.this, "你点了一般", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.bnt3) {
            eva="差评";
           // saveInfo();
            Toast.makeText(MainActivity.this, "你点了差评", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.bnt5) {
            Toast.makeText(MainActivity.this, "你点了保存", Toast.LENGTH_SHORT).show();
            saveInfo();
            startValue=values2.getText().toString()+"";
        }

    }

    private void requestPermission() {
        //申请android.permission.WRITE_SETTINGS权限的方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果当前平台版本大于23平台
            if (!Settings.System.canWrite(this)) {
                //如果没有修改系统的权限这请求修改系统的权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 0);
            } else {
                //有了权限，你要做什么呢？具体的动作
                processShow();
            }
        }


        //动态申请权限
        // 先判断是否有权限。
        if (AndPermission.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 有权限，直接do anything.
            processShow();
        } else {
            // 申请权限。
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .send();
        }
    }

    private void processShow() {
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            int v = data.getIntExtra("position", -1);
            if (v != -1) {
                img.setBackgroundDrawable(Untils.getBitmap(this, draw[v]));
                values3.setText(num[v] + "");
                posi=v;
              //  saveInfo();
                seekBar.setProgress(sinfo);
                if(v==1||v==2||v==5){
                    setTextColors("#ffffff");
                    setSeekBars(1);
                }else{
                    setTextColors("#232323");
                    setSeekBars(0);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void back(float value) {
        values1.setText(value + "");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Untils.setScreenBrightness(MainActivity.this, progress);
        int v = progress;
        values2.setText(v + "");
        endValues=v+"";
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            //权限申请成功回调
            if (requestCode == 100) {
                processShow();

            } else if (requestCode == 101) {
                Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            //权限申请失败的回调
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                //第一种默认的提示语
                AndPermission.defaultSettingDialog(MainActivity.this, requestCode).show();
            }
        }
    };

    private void saveInfo(){
        FileHelper helper=new FileHelper(this);
        eValues=values1.getText().toString();
        if(TextUtils.isEmpty(eValues)){
            eValues="0";
        }
        helper.writeSDFile(fiele_name,num[posi]+"",startValue,endValues,eValues,eva);
    }
}
