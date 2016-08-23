package activity.gcy.com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import confige.Config;

/**
 * Created by Mr.G on 2016/6/12.
 *                                  这里是一个测试页
 */
public class ApplicansChatActivity extends Activity {
    private TextView otherMessage;
    private EditText myMessage;
    private Button send;
    private Thread getMessageThread;
    private boolean chat = true;
    private String mSendMessage;
    private String mGetMessage;
    private StringBuffer sb;
    private String who = "";

    private Button whoBut;
    private EditText whoEt;
    private LinearLayout beforeIn;
    private LinearLayout afterIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout);

        otherMessage = (TextView) findViewById(R.id.chat_other_message);
        myMessage = (EditText) findViewById(R.id.chat_message);
        send = (Button)findViewById(R.id.chat_send);
        whoBut = (Button)findViewById(R.id.before_in_button);
        whoEt = (EditText) findViewById(R.id.before_in_message);
        beforeIn = (LinearLayout) findViewById(R.id.before_in);
        afterIn = (LinearLayout) findViewById(R.id.after_in);
        otherMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
        sb = new StringBuffer();
        getMessageThread = new Thread(mGetMessageRunnable);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSendMessage = myMessage.getText().toString();
                new Thread(mSendMessageRunnable).start();
                otherMessage.append("\n\n"+who+":mSendMessage");
                myMessage.setText("");
            }
        });

        whoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(whoEt.getText().toString()){
                    case "14104104":
                        who="郭晨阳";
                        break;
                    case "14104401":
                        who="常伟伟";
                        break;
                    case "15104419":
                        who = "张璞";
                        break;
                    case "15103118":
                        who = "向启怀";
                        break;
                    case "我宝塔镇河妖":
                        Toast.makeText(ApplicansChatActivity.this, "哦,仅供内部人员使用", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(ApplicansChatActivity.this, "正确答案：\"我宝塔镇河妖\"", Toast.LENGTH_SHORT).show();
                        finish();
                        break;}
                beforeIn.setVisibility(View.GONE);
                afterIn.setVisibility(View.VISIBLE);
                getMessageThread.start();

            }
        });


    }




    Runnable mGetMessageRunnable = new Runnable() {
        @Override
        public void run() {
            while(chat){
                try{

                    if(sb.length()>1)
                        sb.delete(0,sb.length()-1);
                    URL httpUrl = new URL(Config.URL);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.connect();


                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

                    bw.write("GET_MESSAGE");
                    bw.flush();
                    bw.close();
                    if (conn.getResponseCode() == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String str1;

                        while ((str1 = reader.readLine()) != null) {
                            sb.append(str1+"\n\n");
                        }
                        mGetMessage = sb.toString();
                        Message msg = new Message();
                        msg.what=1;
                        mHandler.sendMessage(msg);

                        reader.close();
                    }

                    conn.disconnect();




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



            }

        }
    };

    Runnable mSendMessageRunnable = new Runnable() {
        @Override
        public void run() {

                try{
                    URL httpUrl = new URL(Config.URL);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);

                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.connect();


                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

                    bw.write("SEND_MESSAGE "+who+" :"+mSendMessage);
                    bw.flush();
                    bw.close();
                    if (conn.getResponseCode() == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String str1;

                        while ((str1 = reader.readLine()) != null) {

                        }


                        reader.close();
                    }

                    conn.disconnect();




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }





        }
    };


    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                otherMessage.setText(mGetMessage);
            }


            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        chat = false;
        super.onDestroy();
    }
}
