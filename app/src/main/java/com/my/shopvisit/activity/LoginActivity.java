package com.my.shopvisit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.my.shopvisit.R;
import com.my.shopvisit.bean.LoginUser;
import com.my.shopvisit.bean.User;
import com.my.shopvisit.utils.MySharedPreferences;
import com.my.shopvisit.utils.ShowToast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mUserName;
    private TextInputEditText mPassword;
    private Button mBtnLogin;
    private RelativeLayout mRl;
    private TextInputLayout mUsernameLayout;
    private TextInputLayout mPasswordLayout;



    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (checkLogin()){
            finish();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            initView();
        }
//            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
}


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUsernameLayout.setErrorEnabled(false);
            mPasswordLayout.setErrorEnabled(false);
            String pwd = mPassword.getText().toString();
            //密码位数超限
            if (!TextUtils.isEmpty(pwd) && pwd.length() > 10) {
                mPasswordLayout.setHint("密码位数不能大于10");
            } else {
                mPasswordLayout.setHint("请输入密码");
            }
        }
    };
    /**
     * 初始化控件
     */
    private void initView() {
        mUserName = (TextInputEditText) findViewById(R.id.et_username);
        mPassword = (TextInputEditText) findViewById(R.id.et_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mUsernameLayout = (TextInputLayout) findViewById(R.id.input_username);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.input_password);

        mRl = (RelativeLayout) findViewById(R.id.activity_login_rl);
        mUserName.addTextChangedListener(mTextWatcher);
        mPassword.addTextChangedListener(mTextWatcher);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * 检查是否登录
     *
     * @return
     */
    private boolean checkLogin() {
//        List<User> list=new ArrayList<>();
        //获取数据库用户
        List<User>  list = DataSupport.findAll(User.class);
        if (null != list && list.size() > 0) {
            return true;
        }
        return false;
    }

    //点击登陆按钮
    private void login(){

        String name=mUserName.getText().toString();
        String password=mPassword.getText().toString();
        boolean result=checkData(name,password);//校验用户名密码
        if (result){
//            Toast.makeText(getApplicationContext(),"用户名，密码合法",Toast.LENGTH_SHORT).show();
            //请求服务器
            mRl.setVisibility(View.VISIBLE);
            requestLogin();
        }
    }

    private void requestLogin() {
        //创建一个OkHttp实例
        OkHttpClient httpClient = new OkHttpClient();

        //创建一个body，包含params请求
        RequestBody requestBody = new FormBody.Builder()
                .add("userid", "num01")
                .add("password", "123456")
                .build();

        //创建一个request对象
        String loginUrl = "http://10.0.2.2:8080/visitshop/login";
        Request request = new Request.Builder()
                .url(loginUrl)
                .post(requestBody)
                .build();

        //发起请求
        httpClient.newCall(request).enqueue(new Callback() {



            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRl.setVisibility(View.INVISIBLE);
                        ShowToast.showToastLong(getApplicationContext(),"登录失败");
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//               String result=response.body().string();
//                Gson gson=new Gson();
//                LoginUser User = gson.fromJson(result,LoginUser.class);
//                Message  message=Message.obtain();
//                message.obj=User;
//                mHandler.sendMessage(message);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()){
                            String result= null;
                            mRl.setVisibility(View.INVISIBLE);
                            ShowToast.showToastLong(getApplicationContext(),"登录成功！");
                            try {
                                result = response.body().string();
                                Gson gson=new Gson();
                                LoginUser  mUser = gson.fromJson(result,LoginUser.class);
                                DataSupport.deleteAll(User.class);
                                /**
                                 * userid : num01
                                 * job :  经理
                                 * nickname : 张华
                                 * phonenum : 18913145210
                                 * sex : 0
                                 * img : visitshop/img/user/head.png
                                 * registdate : 2016-10-20
                                 * area :  华中地区
                                 */
                                LoginUser.BodyBean body=mUser.body;
                                User user=new User();
                                user.userid=body.userid;
                                user.job=body.job;
                                user.nickname=body.nickname;
                                user.sex=body.sex;
                                user.img=body.img;
                                user.registdate=body.registdate;
                                user.phonenum=body.phonenum;
                                user.area=body.area;
                                boolean saveFlag = user.save();
                                if (saveFlag) {
                                    MySharedPreferences.putString(LoginActivity.this, "userId", mUser.body.userid);
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });
    }

    /**
     * 检测用户名和密码是否符合要求
     * @return
     */
    private boolean checkData(String name,String pwd){
            if (TextUtils.isEmpty(name)) {
                mUsernameLayout.setError("用户名不能为空");
                return false;
            }
            if (TextUtils.isEmpty(pwd)) {
                mPasswordLayout.setError("密码不能为空");
                return false;
            }
            if (pwd.length() < 6 || pwd.length() > 10) {
                mPasswordLayout.setError("密码位数要在6到10之间");
                return false;
            }
            return true;
        }
}
