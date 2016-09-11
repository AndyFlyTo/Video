package com.gcy.thread;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.example.changvvb.gstreamer3.Gstreamer_test2;
import com.gcy.beans.IntentKeyString;
import com.gcy.beans.TemporaryData;
import com.gcy.util.DataOperation;
import com.gcy.util.GetLocalIP;
import com.gcy.util.PreferenceUtil;
import com.gcy.view.SweetAlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import confige.Config;

/**
 * Created by Mr.G on 2016/5/22.
 */
public class HttpService extends Service {

    private boolean servicealive = true;                    //服务销毁标志位   结束本服务的所有线程
    private boolean isAliving = true;                       //获取环境参数的标志位  其为false时app停止向服务器接收环境参数
    private boolean isReadyToVideo = true;                  //设置免打扰的标志位   其为true时  树莓派端点击门铃 app不做响应
    private TemporaryData temporaryData;                    //临时全局变量  储存一些临时需要的数据  实现类为单例模式

    private StringBuffer sb;                                //缓存从服务器接受的数据
    private String temp;                                    //  拆分缓存中的温度数据
    private String humi;                                    //  拆分缓存中的湿度数据
    private String water;
    private String pm;
    private String dang;
    private String lamp1;
    private String lamp2;
    private String lamp3;
    private String lamp4;

    private float tempStandard;                             //      五个环
    private float humiStandard;                             //            境参数的
    private float waterStandard;                            //                     标准值
    private float pmStandard;                               //
    private float dangStandard;                             //
    private boolean tempNormal=true;                        //温度判断标志位
    private boolean humiNormal=true;                        //湿度判断标志位
    private boolean waterNormal=true;                       //水温判断标志位
    private boolean pmNormal=true;                          //pm2.5参数判断标志位
    private boolean dangNormal=true;                        //危险气体判断标志位
    private boolean serviceNormal = true;                   //判断服务器发来的数据是否有误
    private int monitoringFrequency;                        //环境参数监测频率



    private Thread EnvironmentThread;                       //从服务器获取环境数据的线程
    private Thread IPThread;                                //向服务器发送ip的线程

//    private List<String> mList;                             //装载环境参数信息
//    private List<String> mLampList;                         //装载灯信息状态
//    private MDataBinder mdb = new MDataBinder();
//    private boolean isAlerting = false;                     //消息框是否已弹出
//    private boolean isVideo = false;                        //视频是否已打开
//    private String MSG = "";                                //消息框弹出的内容





//    @Override
//    public IBinder onBind(Intent intent) {
//        return mdb;
//    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        servicealive = false;
//        if(EnvironmentThread!=null){
//            EnvironmentThread.interrupt();
//            EnvironmentThread = null;
//            }
        if(IPThread!=null){
            IPThread.interrupt();
            IPThread = null;
        }

//       EnvironmentThread = new Thread(mHttpRunnable);
        IPThread = new Thread(mIPRunnable);

        servicealive = true;

//        EnvironmentThread.start();
        IPThread.start();

        servicealive = true;



        /*********
         *
         * 读取用户设置的环境参数标准值
         *
         *
         *
//         * *******/
//        tempStandard = PreferenceUtil.load(getApplicationContext(),"tempStandard",Config.TEMPERATURE_STANDARD);
//        humiStandard = PreferenceUtil.load(getApplicationContext(),"humiStandard",Config.HUMIDITY_STANDARD);
//        waterStandard = PreferenceUtil.load(getApplicationContext(),"waterStandard",Config.WATER_STANDARD);
//        pmStandard = PreferenceUtil.load(getApplicationContext(),"pmStandard",Config.PM2_5_STANDARD);
//        dangStandard = PreferenceUtil.load(getApplicationContext(),"dangStandard",Config.DANGEROUS_GAS_STANDARD);
//        monitoringFrequency = PreferenceUtil.load(getApplicationContext(),"monitoringFrequency",Config.MONITORING_FREQUENCY);
//        temporaryData = TemporaryData.getInstance();
//
//        isAliving = true;
//
//        sb = new StringBuffer();
//        mList = new ArrayList<String>();
//        mLampList = new ArrayList<String>();


        return super.onStartCommand(intent, flags, startId);
    }

    //发送IP线程  并监测是否有人敲门  和获取树莓派的IP
    Runnable mIPRunnable = new Runnable() {
        @Override
        public void run() {

            while (servicealive){
                if(!isReadyToVideo){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                while (isReadyToVideo) {

                    try {
                        Thread.sleep(5000);
                        URL httpUrl = new URL(Config.URL);
                        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setReadTimeout(5000);
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setUseCaches(false);
                        conn.connect();


                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                        String ipinfo = "ip " + GetLocalIP.getLocalIpAddress();
                        Log.d("chen",ipinfo);
                        bw.write(ipinfo);
                        bw.flush();
                        bw.close();
                        if (conn.getResponseCode() == 200) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String str1;
                            StringBuffer message = new StringBuffer();
                            while ((str1 = reader.readLine()) != null) {
                                message.append(str1);
                            }

                            reader.close();
                            conn.disconnect();

//                            String []str = message.toString().split(" ");
//                            if(str.length==2)
//                                if (!isVideo)
//                                    if(!temporaryData.getFlagIs()){
//                                        if(str[0].equals("YOU_CAN_SEE_ME_NOW")) {
//                                            temporaryData.IP_CLIENT = str[1];
//                                            Message msg = new Message();
//                                            msg.what = 1;
//                                            mHandler.sendMessage(msg);
//                                            MSG = "是否打开视频？";
//                                            isVideo = true;
//                                            temporaryData.setFlagIsVideo(true);
//
//
//                                    }
//                            } else {
//
//
//                                Thread.sleep(5000);
//
//
//                            }
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
        }
    };


//    //环境监测线程
//    Runnable mHttpRunnable = new Runnable() {
//        @Override
//        public void run() {
//
//            while (servicealive){
//                        if(!isAliving){
//                            try {
//                                Thread.sleep(3000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//
//                while (isAliving) {
//                    try {
//
//
//
//                        URL httpUrl = new URL(Config.URL);
//                        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//
//
//                        conn.setRequestMethod("POST");
//                        conn.setReadTimeout(5000);
//                        conn.setDoOutput(true);
//                        conn.setDoInput(true);
//                        conn.setUseCaches(false);
//                        conn.connect();
//                        OutputStream out = conn.getOutputStream();
//                        out.write("ch".getBytes());
//                        out.close();
//                        if (conn.getResponseCode() == 200) {
//
//                            if(!(sb.length()<=0))
//                                sb.delete(0,sb.length()-1);
//
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                            String str1;
//                            //储存临时数据
//
//                            while ((str1 = reader.readLine()) != null) {
//                                sb.append(str1 + "_");
//                            }
//
//
//
//
//
//                            reader.close();
//                            conn.disconnect();
//                            mList.clear();
//                            mLampList.clear();
//                            dang = pm = water = humi = temp =lamp1=lamp2=lamp3=lamp4= "null";
//
//                            String[] applicansData = DataOperation.getApplicanData(sb.toString());
//
//
//
//
//
//                                for (int i = 0; i < applicansData.length; i++) {
//
//                                    String[] str = DataOperation.getDetailApplicanData(applicansData[i]);
//                                    if (str.length == 3)
//                                        switch (str[0]) {
//                                            case "temp":
//                                                if (str[1].equals("0"))
//                                                    temp = str[2];
//
//                                                break;
//                                            case "humi":
//                                                if (str[1].equals("0"))
//                                                    humi = str[2];
//                                                break;
//                                            case "water":
//                                                if (str[1].equals("0"))
//                                                    pm = str[2];
//
//                                                break;
//                                            case "pm":
//                                                if (str[1].equals("0"))
//                                                    pm = str[2];
//                                                break;
//                                            case "dang":
//                                                if (str[1].equals("0"))
//                                                    dang = str[2];
//                                                break;
//                                            case "lamp1":
//                                                if (str[1].equals("0"))
//                                                    lamp1 = str[2];
//                                                break;
//                                            case "lamp2":
//                                                if (str[1].equals("0"))
//                                                    lamp2 = str[2];
//                                                break;
//
//                                            case "lamp3":
//                                                if (str[1].equals("0"))
//                                                    lamp3 = str[2];
//                                                break;
//
//                                            case "lamp4":
//                                                if (str[1].equals("0"))
//                                                    lamp4 = str[2];
//                                                break;
//
//                                        }
//
//
//                                }
//
//
//
//
//
//
//                            mList.add(temp);
//                            mList.add(humi);
//                            mList.add(water);
//                            mList.add(pm);
//                            mList.add(dang);
//                            mLampList.add(lamp1);
//                            mLampList.add(lamp2);
//                            mLampList.add(lamp3);
//                            mLampList.add(lamp4);
//
//
//
//                                if (!isAlerting)
//                                    if (check()) {
//                                        isAlerting = true;
//                                        Message msg = new Message();
//                                        msg.what = 0;
//                                        mHandler.sendMessage(msg);
//                                    }
//                            if(serviceNormal) {
//                                Intent intent = new Intent("com.smartHome.demo.GET_DATA");
//                                sendBroadcast(intent);
//                            }
//
//
//                            Thread.sleep(monitoringFrequency);
//
//                        } else {
//                            Thread.sleep(monitoringFrequency * 2);
//
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//        }
//        }
//    };
//            //检查从服务器接受的参数是否异常  以及是否有错误数据
//    private boolean check() {
//        boolean isUnusual = false;
//        boolean maches = true;    //   辅助检查接受参数是否为数字或设备离线   null为离线
//        MSG="";
//
//        if(mList.size()!=5)
//            return false;
//
//
//        if(!temp.matches("\\d+\\.*\\d*")&&!temp.equals("null"))
//            maches = false;
//        if(!humi.matches("\\d+\\.*\\d*")&&!humi.equals("null"))
//            maches = false;
//        if(!water.matches("\\d+\\.*\\d*")&&!water.equals("null"))
//            maches = false;
//        if(!pm.matches("\\d+\\.*\\d*")&&!pm.equals("null"))
//            maches = false;
//        if(!dang.matches("\\d+\\.*\\d*")&&!dang.equals("null"))
//            maches = false;
//        if(!maches){
//            if(serviceNormal){
//                MSG="接受参数非数字，请检查服务器！";
//                serviceNormal=false;
//                return true;
//            }
//        }else{
//            serviceNormal = true;
//        }
//


//        for (int i = 0; i <mList.size() ; i++) {    //不知道为什么会报数组越界的错误
//
//            if(!mList.get(i).matches("\\d+\\.*\\d*")&&!mList.get(i).equals("null")){
//                if(!mList.get(i).matches("\\d+"))
//                    if(serviceNormal){
//
//                        MSG="接受参数非数字，请检查服务器！";
//                        serviceNormal=false;
//                        return true;
//
//                    }
//            }else{
//                serviceNormal = true;
//            }
//        }


//        if(!temp.equals("null"))
//            if(Float.parseFloat(temp)>tempStandard){
//                if(tempNormal){
//                    isUnusual = true;
//                    MSG = MSG+"温度异常！\n";
//                    tempNormal=false;
//            }
//        }else{
//                tempNormal = true;
//
//            }
//
//
//        if(!humi.equals("null"))
//            if(Float.parseFloat(humi)>humiStandard){
//                if(humiNormal){
//                    isUnusual = true;
//                    MSG = MSG+"湿度异常！\n";
//                    humiNormal=false;
//            }
//        }else{
//                humiNormal = true;
//            }
//
//        if (!water.equals("null"))
//            if (Float.parseFloat(water) > waterStandard) {
//                if(waterNormal) {
//                    isUnusual = true;
//                    MSG = MSG + "水温异常！\n";
//                    waterNormal = false;
//                }
//        }else{
//                waterNormal = true;
//            }
//
//        if(!pm.equals("null"))
//            if(Float.parseFloat(pm)>pmStandard){
//                if(pmNormal){
//                    isUnusual = true;
//                    pmNormal = false;
//                    MSG = MSG+"PM2.5异常！\n";}
//
//        }else{
//                pmNormal = true;
//            }
//
//        if(!dang.equals("null"))
//            if(Float.parseFloat(dang)>dangStandard){
//                if(dangNormal){
//                    isUnusual = true;
//                    MSG = MSG+"危险气体异常！\n";
//                    dangNormal = false;
//                }
//            }else{
//                dangNormal = true;
//            }

//        if(isUnusual){
//            return true;
//        }
//
//        return false;
//    }



//    Handler mHandler = new Handler()                //服务中开启dialog  提示进行视频 或 弹出环境异常警告
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            // TODO Auto-generated method stub
//            if(msg.what==0)
//            {
//                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//
//                vibrator.vibrate(1000);
//
//                final SweetAlertDialog waring = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE);
//                waring.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                waring.setTitleText("环境参数异常!")
//                        .setContentText(MSG)
//                        .setConfirmText("确定")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                isAlerting = false;
//                                waring.dismiss();
//                            }
//                        })
//                        .show();
//
//            }
//            if(msg.what==1)
//            {


    // TODO: 16-9-10 振动
//                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//                long [] pattern = {400,500,400,500};
//                vibrator.vibrate(pattern,-1);
//                final SweetAlertDialog waring = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.SUCCESS_TYPE);
//                waring.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                waring.setTitleText("有人敲门!")
//                        .setContentText(MSG)
//                        .setConfirmText("确定")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//
//                                isVideo = false;








//                                Intent intent = new Intent(getApplicationContext(),Gstreamer_test2.class);
//                                intent.putExtra(IntentKeyString.REMOTE_VIDEO_IP,temporaryData.IP_CLIENT);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                waring.dismiss();
//                            }
//                        })
//                        .showCancelButton(true)
//                        .setCancelText("取消")
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                isVideo = false;
//
//                                waring.dismiss();
//                            }
//                        })
//                        .show();
//            }

//            super.handleMessage(msg);
//        }
//    };
            //与该服务绑定的activity将获取该类的实例  以便进行activity与service通讯
//    public class MDataBinder extends Binder{
//
//            //返回环境参数数据至活动界面
//        public List<String> getEnvironmentApplicansData(){
//            return mList;
//
//        }
//
//        public List<String> getLampDataList(){
//            return mLampList;
//        }
//
//
//        public void setStandardConfig(List<Float> mList){
//            if(mList.size()!=5)
//                return;
//            tempStandard=mList.get(0);
//            humiStandard=mList.get(1);
//            waterStandard=mList.get(2);
//            pmStandard=mList.get(3);
//            dangStandard=mList.get(4);
//            /********
//             *
//             * 获取用户输入的各个参数值标准并保存
//             *
//             *
//             * *******/
//            PreferenceUtil.save(getApplicationContext(),"tempStandard",tempStandard);
//            PreferenceUtil.save(getApplicationContext(),"humiStandard",humiStandard);
//            PreferenceUtil.save(getApplicationContext(),"waterStandard",waterStandard);
//            PreferenceUtil.save(getApplicationContext(),"pmStandard",pmStandard);
//            PreferenceUtil.save(getApplicationContext(),"dangStandard",dangStandard);
//
//
//        }
//
//
//                /******设置监测频率******/
//        public void setMonitoringFrequency(int value){
//            monitoringFrequency = value;
//            PreferenceUtil.save(getApplicationContext(),"dangStandard",monitoringFrequency);
//
//        }
//                /******是否开始监测环境******/
//        public void startMonitoring(boolean command){
//            isAliving = command;
//
//        }
//                /*******视频通话是否正在进行中*******/
//        public void setReadyToVideo(boolean flag){
//            isReadyToVideo = flag;
//        }
//
//
//    }

    @Override
    public void onDestroy() {
        servicealive = false;
        isAliving = false;
        isReadyToVideo = false;
        super.onDestroy();
    }
}
