package activity.gcy.com.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.changvvb.gstreamer3.Gstreamer3;
import com.gcy.beans.TemporaryData;
import com.gcy.thread.HttpGetLoginThread;


/**
 * Created by Mr.G on 2016/5/10.
 */
public class LoginActivity extends Activity {

    private Button login_button;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_layout);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initViewGroup();

        //username.setText(Config.URL);



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Config.URL = username.getText().toString();
//                String name = username.getText().toString();
//                String passwordStr = password.getText().toString();
//                TemporaryData temporaryData = TemporaryData.getInstance();
//                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//
//                new Thread(new HttpGetLoginThread(progressDialog,name+" "+passwordStr,LoginActivity.this)).start();
//
//                progressDialog.setTitle("登陆中...");
//                progressDialog.setMessage("请稍后...");
//                progressDialog.setCancelable(true);
//                progressDialog.show();
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                Intent intent = new Intent(LoginActivity.this, Gstreamer3.class);
                startActivity(intent);
            }
        });
    }

    private void initViewGroup() {
        login_button = (Button)findViewById(R.id.login_but_login);
        username = (EditText)findViewById(R.id.login_et_username);
        password = (EditText)findViewById(R.id.login_et_password);
    }
}
