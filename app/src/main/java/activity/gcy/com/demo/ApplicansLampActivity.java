package activity.gcy.com.demo;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcy.beans.TemporaryData;
import com.gcy.thread.HttpThread;
import com.gcy.view.TitleBar;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.G on 2016/5/29.
 */
public class ApplicansLampActivity extends Activity {


    private TextView lamp1Status;
    private TextView lamp2Status;
    private TextView lamp3Status;
    private TextView lamp4Status;


    private boolean lamp1;
    private boolean lamp2;
    private boolean lamp3;
    private boolean lamp4;

    private ImageView lamp1image;
    private ImageView lamp2image;
    private ImageView lamp3image;
    private ImageView lamp4image;

    private RelativeLayout lampPress1;
    private RelativeLayout lampPress2;
    private RelativeLayout lampPress3;
    private RelativeLayout lampPress4;

    private LinearLayout refreshLayout;
    private ImageView refresh;

    private Button save;
    private TitleBar titleBar;

    private boolean isDeviceOnLine;

    private Handler handler = new Handler();
    private List<String> mLampList;
    TemporaryData temporaryData;
    private TextView tips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myapplicans_lamp);

        mLampList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        temporaryData = TemporaryData.getInstance();

        if (bundle.getString("lamp1", "1").equals("0")) {
            lamp1 = true;
        } else
            lamp1 = false;
        if (bundle.getString("lamp2", "1").equals("0")) {
            lamp2 = true;
        } else
            lamp2 = false;
        if (bundle.getString("lamp3", "1").equals("0")) {
            lamp3 = true;
        } else
            lamp3 = false;
        if (bundle.getString("lamp4", "1").equals("0")) {
            lamp4 = true;
        } else
            lamp4 = false;
        isDeviceOnLine = bundle.getBoolean("online", false);


        initView();
        initStatus();

        //handler.post(mRunnable);

        if (!isDeviceOnLine) {
            lampPress1.setEnabled(false);
            lampPress2.setEnabled(false);
            lampPress3.setEnabled(false);
            lampPress4.setEnabled(false);
            tips.setText("服务器未连接请检查网络后再试！");
        }

        lampPress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lamp1) {
                    lamp1 = false;
                } else
                    lamp1 = true;
                changeStatus(1);
                sendToService(1, lamp1);

            }
        });
        lampPress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lamp2) {
                    lamp2 = false;
                } else
                    lamp2 = true;
                changeStatus(2);
                sendToService(2, lamp2);

            }
        });
        lampPress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lamp3) {
                    lamp3 = false;
                } else
                    lamp3 = true;
                changeStatus(3);
                sendToService(3, lamp3);

            }
        });
        lampPress4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lamp4) {
                    lamp4 = false;
                } else
                    lamp4 = true;
                changeStatus(4);
                sendToService(4, lamp4);
            }
        });
        titleBar.initTitleBarInfo("灯", -1, -1, "", "");
        titleBar.setLeftContainerClickAble(true);
        titleBar.setIsBack(true);
        titleBar.setActionView();
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void sendToService(int whichLamp, boolean status) {
        String statusStr = "";
        String lampStr = "";
        if (status)
            statusStr = " 0";
        else
            statusStr = " 1";

        switch (whichLamp) {
            case 1:
                lampStr = "lamp1";
                break;
            case 2:
                lampStr = "lamp2";
                break;
            case 3:
                lampStr = "lamp3";
                break;
            case 4:
                lampStr = "lamp4";
                break;

        }
        new Thread(new HttpThread(lampStr + statusStr)).start();

    }

    private void changeStatus(int which) {
        switch (which) {
            case 1:
                if (lamp1) {
                    lamp1image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
                    lamp1Status.setText("已打开");
                } else {
                    lamp1image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
                    lamp1Status.setText("已关闭");
                }
                break;
            case 2:
                if (lamp2) {
                    lamp2image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
                    lamp2Status.setText("已打开");
                } else {
                    lamp2image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
                    lamp2Status.setText("已关闭");
                }
                break;
            case 3:
                if (lamp3) {
                    lamp3image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
                    lamp3Status.setText("已打开");
                } else {
                    lamp3image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
                    lamp3Status.setText("已关闭");
                }
                break;
            case 4:
                if (lamp4) {
                    lamp4image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
                    lamp4Status.setText("已打开");
                } else {
                    lamp4image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
                    lamp4Status.setText("已关闭");
                }
                break;
            default:
                break;


        }


    }

    private void initStatus() {
        if (lamp1) {
            lamp1image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
            lamp1Status.setText("已打开");
        } else {
            lamp1image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
            lamp1Status.setText("已关闭");
        }
        if (lamp2) {
            lamp2image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
            lamp2Status.setText("已打开");
        } else {
            lamp2image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
            lamp2Status.setText("已关闭");
        }
        if (lamp3) {
            lamp3image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
            lamp3Status.setText("已打开");
        } else {
            lamp3image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
            lamp3Status.setText("已关闭");
        }
        if (lamp4) {
            lamp4image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampopen));
            lamp4Status.setText("已打开");
        } else {
            lamp4image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lampclose));
            lamp4Status.setText("已关闭");
        }


    }

    private void initView() {
        lamp1Status = (TextView) findViewById(R.id.lamp1_status);
        lamp2Status = (TextView) findViewById(R.id.lamp2_status);
        lamp3Status = (TextView) findViewById(R.id.lamp3_status);
        lamp4Status = (TextView) findViewById(R.id.lamp4_status);

        lamp1image = (ImageView) findViewById(R.id.lamp1);
        lamp2image = (ImageView) findViewById(R.id.lamp2);
        lamp3image = (ImageView) findViewById(R.id.lamp3);
        lamp4image = (ImageView) findViewById(R.id.lamp4);

        lampPress1 = (RelativeLayout) findViewById(R.id.lamp1_press);
        lampPress2 = (RelativeLayout) findViewById(R.id.lamp2_press);
        lampPress3 = (RelativeLayout) findViewById(R.id.lamp3_press);
        lampPress4 = (RelativeLayout) findViewById(R.id.lamp4_press);

        refresh = (ImageView) findViewById(R.id.refresh);
        refreshLayout = (LinearLayout) findViewById(R.id.layout_refresh);

        save = (Button) findViewById(R.id.applican_save_button);
        tips = (TextView) findViewById(R.id.applicans_lamp_tips);
        titleBar = (TitleBar) findViewById(R.id.title_bar);

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }



}
