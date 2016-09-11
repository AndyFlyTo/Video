package activity.gcy.com.demo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changvvb.gstreamer3.Gstreamer_test2;
import com.gcy.adapter.MyappliancesAdapter;
import com.gcy.adapter.MyappliancesEnvironmentAdapter;
import com.gcy.beans.IntentKeyString;
import com.gcy.beans.Myappliances;
import com.gcy.beans.TemporaryData;
import com.gcy.thread.HttpGetVideoIPThread;
import com.gcy.thread.HttpService;
import com.gcy.thread.HttpThread;
import com.gcy.util.PreferenceUtil;
import com.gcy.util.UnusualNumerical;
import com.gcy.view.CrossView;
import com.gcy.view.SlidingMenu;
import com.gcy.view.TitleBar;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;



public class MainActivity extends AppCompatActivity {
    //    private TabHost tabHost;
//    private TitleBar titleBar;
//    private SlidingMenu sliding_menu;
//    private Context context;
//    private LinearLayout main_tap_myappliances;
//    private LinearLayout main_tap_environment;
//    private LinearLayout main_tap_chat;
//    private ImageView view1;
//    private ImageView view2;
//    private ImageView view3;
//    private TextView stop_monitoring_text;
//    private Intent serviceIntent;
//    private List<Float> mStandardList;
//    private List<String> mLampList;
//    private String TEMP;
//    private float tempStandard;
//    private float humiStandard;
//    private float waterStandard;
//    private float pm2_5Standard;
//    private float dangStandard;
//
//    private TextView innerTest;
//
//    //tab1
//    private TemporaryData temporaryData;
//    private ListView myApplianceListView;
//    private CrossView myappliances_add;
//    private MyappliancesAdapter adapter;
//    private List<Myappliances> myappliancesesList;
//    private List<Myappliances> myappliancesesListClone;
//
//
//    /********    tab2     ******/
//    private ListView main_tap_environment_listiew;
//    private MyappliancesEnvironmentAdapter environment_adapter;
//    private List<String> mDataList;
    private Handler handler;
    // private HttpService.MDataBinder mBinder;


    private Button openVideoBtn;
    private TextView nameText;
    private ShimmerTextView shimmerTextView;
    private Shimmer shimmer;
    private TemporaryData temporaryData;
    //
//
//    //tab3
    private Button openVideo;
    //    private LinearLayout noDisturb;
//    private TextView noDisturbStatus;
//    private CrossView crossView;
//
//
//    //left menu
//    private RelativeLayout config_frequency;
//    private RelativeLayout applican_standard;
//    private RelativeLayout stop_monitoring;
//    private RelativeLayout about_us;
//
//    private mReceive mr;
    private IntentFilter intentFilter;


//    private ServiceConnection conn = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinder = (HttpService.MDataBinder) service;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer_tv);
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);

        temporaryData = TemporaryData.getInstance();        //保存临时数据


        //TODO 启动服务 dfgh234567
        Intent serviceIntent = new Intent(this, HttpService.class);
        startService(serviceIntent);

        openVideoBtn = (Button) findViewById(R.id.btn_open_video);

        openVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new Thread(new HttpGetVideoIPThread(MainActivity.this, false, temporaryData)).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){        //全屏显示，通知栏背景设置
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
//        context = this;

//        handler = new Handler();     //更新UI

//        temporaryData = TemporaryData.getInstance();        //保存临时数据
//        mDataList = new ArrayList<>();          //保存环境监测数据的list


//环境标准值
//        tempStandard = PreferenceUtil.load(context,"tempStandard",Config.TEMPERATURE_STANDARD);
//        humiStandard = PreferenceUtil.load(context,"humiStandard",Config.HUMIDITY_STANDARD);
//        waterStandard = PreferenceUtil.load(context,"waterStandard",Config.WATER_STANDARD);
//        pm2_5Standard = PreferenceUtil.load(context,"pm2_5Standard",Config.PM2_5_STANDARD);
//        dangStandard = PreferenceUtil.load(context,"dangStandard",Config.DANGEROUS_GAS_STANDARD);

//        mStandardList = new ArrayList<>();          //环境参数标准值
//
//        mStandardList.add(tempStandard);
//        mStandardList.add(humiStandard);
//        mStandardList.add(waterStandard);
//        mStandardList.add(pm2_5Standard);
//        mStandardList.add(dangStandard);
// TODO: 16-9-10 绑定服务
//        Intent bindIntent = new Intent(this,HttpService.class);//绑定服务
//        bindService(bindIntent,conn,BIND_AUTO_CREATE);


//        intentFilter = new IntentFilter();  //绑定接收广播
//        intentFilter.addAction("com.smartHome.demo.GET_DATA");
//        mr = new mReceive();

//        registerReceiver(mr,intentFilter);

//        initViewGroup();
//        mLampList = new ArrayList<String>();
//        serviceIntent = new Intent(this,HttpService.class);
//        startService(serviceIntent);


//设备
//        myappliancesesList = new ArrayList<Myappliances>();
//
//        myappliancesesListClone = new ArrayList<>();
//        for (int i=0;i<Config.getApplicancesList().size();i++) {
//            myappliancesesList.add(Config.getApplicancesList().get(i));
//            myappliancesesListClone.add(Config.getApplicancesList().get(i));

//        }


//        Myappliances mpLamp = new Myappliances();//灯设备
//        mpLamp.setAppliancesName("灯");
//        mpLamp.setApplicansValue("null");
//        mpLamp.setRegulation("是");
//        mpLamp.setStatus("正常");
//        myappliancesesListClone.add(mpLamp);
//
//        Myappliances mpFan = new Myappliances();  //风扇设备
//        mpFan.setAppliancesName("风扇");
//        mpFan.setApplicansValue("null");
//        mpFan.setRegulation("是");
//        mpFan.setStatus("正常");
//        myappliancesesListClone.add(mpFan);
//
//
//
//        adapter = new MyappliancesAdapter(context,R.layout.activity_myappliance_item,myappliancesesListClone);
//        environment_adapter = new MyappliancesEnvironmentAdapter(context,R.layout.activity_myappliance_environment_item,myappliancesesList);
//        myApplianceListView.setAdapter(adapter);
//
//
//
//        tabHost = (TabHost)findViewById(R.id.tabHost);
//        tabHost.setup();
//        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("我的设备",getResources().getDrawable(R.mipmap.ic_launcher)).setContent(R.id.tab1));
//        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("环境卫生",getResources().getDrawable(R.mipmap.ic_launcher)).setContent(R.id.tab2));
//        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("视频对讲",getResources().getDrawable(R.mipmap.ic_launcher)).setContent(R.id.tab3));
//
//
//
//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//
//
//            }
//        });
//
//        //   通过判断radioButton的点击 确定当前为那个tab
//        tabHost.setCurrentTab(1);
//        setCurrentTab(1);
//
//        innerTest.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ApplicansChatActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
//        main_tap_myappliances.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabHost.setCurrentTab(0);
//                setCurrentTab(0);
//
//            }
//        });
//        main_tap_environment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabHost.setCurrentTab(1);
//                setCurrentTab(1);
//
//            }
//        });
//        main_tap_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabHost.setCurrentTab(2);
//                setCurrentTab(2);
//
//            }
//        });


///***********************************tab事件

//        myappliances_add.setVisibility(View.GONE);
//
//
//
//        myApplianceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //水温
//                if(position==2) {
//
//                    if (TEMP == null) {
//                        Toast.makeText(MainActivity.this, "连接服务器失败！请检查网络！", Toast.LENGTH_SHORT).show();
//                        return ;}
//                    if(TEMP.equals("null")){
//                        Toast.makeText(MainActivity.this, "设备离线！", Toast.LENGTH_SHORT).show();
//                        return ;
//                    }
//                    Intent intent = new Intent(MainActivity.this, ApplicansWaterActivity.class);
//                    intent.putExtra(IntentKeyString.ENVIRONMENT_TEMP, TEMP);
//                    startActivity(intent);
//                }
//
//
//                //灯
//                if(position==5){
//                    Intent intent = new Intent(MainActivity.this,ApplicansLampActivity.class);
//                    if(mLampList==null){
//                        Toast.makeText(MainActivity.this, "无服务器连接请检查网络！", Toast.LENGTH_SHORT).show();
//                        return;}
//                    if(mLampList.size()!=4){
//                        intent.putExtra("lamp1","1");
//                        intent.putExtra("lamp2","1");
//                        intent.putExtra("lamp3","1");
//                        intent.putExtra("lamp4","1");
//                        intent.putExtra("online",false);
//                        Toast.makeText(MainActivity.this, "获取数据失败请检查网络！", Toast.LENGTH_SHORT).show();
//                    }else{
//
//                        intent.putExtra("lamp1",mLampList.get(0));
//                        intent.putExtra("lamp2",mLampList.get(1));
//                        intent.putExtra("lamp3",mLampList.get(2));
//                        intent.putExtra("lamp4",mLampList.get(3));
//                        intent.putExtra("online",true);
//                    }
//
//                    startActivity(intent);
//
//
//                }
//
//                //风扇
//                if(position==6){
//                    Intent intent = new Intent(MainActivity.this,ApplicansFanActivity.class);
//
//                    startActivity(intent);
//
//
//
//                }
//
//
//
//            }
//        });

//        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
//            @Override
//            public void onLeftButtonClick(View v) {
//                if(titleBar.getActionView().equals("back"))
//                    sliding_menu.openMenu();
//                else
//                    sliding_menu.closeMenu();
//            }
//
//            @Override
//            public void onRightButtonClick(View v) {
//
//            }
//        });


//  监测频率设置
//        config_frequency.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ConfigActivity.class);
//                intent.putExtra(IntentKeyString.CONFIG_INTENT_KEY,IntentKeyString.CONFIG_INTENT_KEY_CONFIG_FREQUENCY);
//                startActivityForResult(intent,1);
//            }
//        });

//环境参数标准值设置

//        applican_standard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ConfigActivity.class);
//                intent.putExtra(IntentKeyString.CONFIG_INTENT_KEY,IntentKeyString.CONFIG_INTENT_KEY_APPLICANS_STANDARD);
//                intent.putExtra("tempStandard",tempStandard);
//                intent.putExtra("humiStandard",humiStandard);
//                intent.putExtra("waterStandard",waterStandard);
//                intent.putExtra("pm2_5Standard",pm2_5Standard);
//                intent.putExtra("dangStandard",dangStandard);
//                startActivityForResult(intent,2);
//            }
//        });

//停止监测

//        stop_monitoring.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(stop_monitoring_text.getText().toString().equals("停止监测")){
//
//                    mBinder.startMonitoring(false);
//                    stop_monitoring_text.setText("开始监测");}
//                else{
//                    mBinder.startMonitoring(true);
//                    stop_monitoring_text.setText("停止监测");
//                }
//            }
//        });

//关于我们
//        about_us.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ConfigActivity.class);
//                intent.putExtra(IntentKeyString.CONFIG_INTENT_KEY,IntentKeyString.CONFIG_INTENT_KEY_ABOUT_US);
//                startActivity(intent);
//            }
//        });


//        main_tap_environment_listiew.setAdapter(environment_adapter);


//        main_tap_environment_listiew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });


//打开视频！
//        openVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//     new Thread(new HttpThread("VIDEO_START")).start();

//                new Thread(new HttpGetVideoIPThread(MainActivity.this,progressDialog,false,temporaryData)).start();
      /*          progressDialog.setTitle("请稍后...");
                progressDialog.setMessage("正在获取IP中...");
                progressDialog.setCancelable(true);
                progressDialog.show();*/


//            }
//        });

//        //设置消息免打扰
//        noDisturb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(noDisturbStatus.getText().toString().equals("关闭")){
//                    crossView.toggle(500);
//                    mBinder.setReadyToVideo(false);
//                    noDisturbStatus.setText("开启");
//
//                }else{
//                    crossView.toggle(500);
//                    mBinder.setReadyToVideo(true);
//                    noDisturbStatus.setText("关闭");
//
//                }
//
//            }
//        });


//    }

//    private void initViewGroup() {
//
//        titleBar = (TitleBar) findViewById(R.id.title_bar);
//        sliding_menu =(SlidingMenu) findViewById(R.id.sliding_menu);
//        titleBar.initTitleBarInfo("",-1,-1,"","");
//        titleBar.setLeftContainerClickAble(true);
//        main_tap_myappliances = (LinearLayout)findViewById(R.id.main_tap_myappliances);
//        main_tap_environment = (LinearLayout)findViewById(R.id.main_tap_environment);
//        main_tap_chat = (LinearLayout)findViewById(R.id.main_tap_chat);
//        view2 =(ImageView) findViewById(R.id.main_tap_environment_image);
//        view3 =(ImageView) findViewById(R.id.main_tap_chat_image);
//        view1 =(ImageView) findViewById(R.id.main_tap_myappliances_image);
//
//        myappliances_add = (CrossView)findViewById(R.id.layout_myappliances_button_add);
//        myApplianceListView = (ListView)findViewById(R.id.layout_myappliances_lv);
//
//        main_tap_environment_listiew = (ListView) findViewById(R.id.main_tap_environment_listiew);
//
//
//        config_frequency = (RelativeLayout) findViewById(R.id.config_frequency);
//        applican_standard = (RelativeLayout) findViewById(R.id.applican_standard);
//        stop_monitoring = (RelativeLayout) findViewById(R.id.stop_monitoring);
//        about_us = (RelativeLayout) findViewById(R.id.about_us);
//
//        openVideo = (Button)findViewById(R.id.layout_main_open_video);
//        noDisturb = (LinearLayout)findViewById(R.id.layout_main_no_video);
//        noDisturbStatus = (TextView)findViewById(R.id.layout_main_no_video_text) ;
//        crossView = (CrossView) findViewById(R.id.layout_main_no_video_crossview);
//        innerTest = (TextView) findViewById(R.id.inner_test);
//
//        stop_monitoring_text = (TextView) findViewById(R.id.stop_monitoring_text);
//
//    }
//
//    public void setCurrentTab(int id){
//        view2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.widget_bar_explore_nor));
//        view3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.widget_bar_me_nor));
//        view1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.widget_bar_news_nor));
//        switch (id){
//            case 0:
//                view1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.widget_bar_news_over));
//                break;
//            case 1:
//                view2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.widget_bar_explore_over));
//                break;
//            case 2:
//                view3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.widget_bar_me_over));
//                break;
//
//        }
//    }


//    @Override
//    protected void onDestroy() {
//        unbindService(conn);
//        unregisterReceiver(mr);
//        super.onDestroy();
//    }

//    class mReceive extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
////            mLampList.clear();
////            mDataList.clear();
//
//            List<String> mBinderLamp = mBinder.getLampDataList();
//            for(int i=0;i<mBinderLamp.size();i++){
////                mLampList.add(mBinderLamp.get(i));
//
//            }


//            temporaryData.setmLampList(mLampList);

//            List<String> mServiceDatalist = mBinder.getEnvironmentApplicansData();
//            if(mServiceDatalist.size()!=5)
//                return;
//            for(int i=0;i<mServiceDatalist.size();i++){
//               mDataList.add(mServiceDatalist.get(i));

//            }


//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//
//
//                    List<Boolean> isNormal= UnusualNumerical.isNumericalNormal(mDataList,mStandardList);
//                    for(int i=0;i<Config.getApplicancesList().size();i++){
//                        myappliancesesList.get(i).setApplicansValue(mDataList.get(i));
//                        if(!isNormal.get(i))
//                            myappliancesesList.get(i).setNormal(false);
//                        else
//                            myappliancesesList.get(i).setNormal(true);
//                    }

//                    TEMP = mDataList.get(0);
//                    environment_adapter.notifyDataSetChanged();
//                    adapter.notifyDataSetChanged();


//                }
//            });
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if(resultCode==1){
//            int frequency;
//            frequency=data.getIntExtra("MONITORING_FREQUENCY",Config.MONITORING_FREQUENCY);
//            mBinder.setMonitoringFrequency(frequency);
//            PreferenceUtil.save(context,"MONITORING_FREQUENCY",frequency);
//
//        }
//        if (resultCode==2){
//
//            tempStandard = data.getFloatExtra("tempStandard",Config.TEMPERATURE_STANDARD);
//            humiStandard = data.getFloatExtra("humiStandard",Config.HUMIDITY_STANDARD);
//            waterStandard = data.getFloatExtra("waterStandard",Config.WATER_STANDARD);
//            pm2_5Standard = data.getFloatExtra("pm2_5Standard",Config.PM2_5_STANDARD);
//            dangStandard = data.getFloatExtra("dangStandard",Config.DANGEROUS_GAS_STANDARD);
//
//
//            mStandardList.clear();
//
//            mStandardList.add(tempStandard);
//            mStandardList.add(humiStandard);
//            mStandardList.add(waterStandard);
//            mStandardList.add(pm2_5Standard);
//            mStandardList.add(dangStandard);
//
//            PreferenceUtil.save(context,"tempStandard",tempStandard);
//            PreferenceUtil.save(context,"humiStandard",humiStandard);
//            PreferenceUtil.save(context,"waterStandard",waterStandard);
//            PreferenceUtil.save(context,"pm2_5Standard",pm2_5Standard);
//            PreferenceUtil.save(context,"dangStandard",dangStandard);
//
//            mBinder.setStandardConfig(mStandardList);
//
//
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


//}


