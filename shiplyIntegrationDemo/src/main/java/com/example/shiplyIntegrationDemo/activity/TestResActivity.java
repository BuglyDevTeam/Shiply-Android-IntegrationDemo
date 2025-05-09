package com.example.shiplyIntegrationDemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shiplyIntegrationDemo.R;
import com.tencent.rdelivery.reshub.api.IRes;
import com.tencent.rdelivery.reshub.api.IResCallback;
import com.tencent.rdelivery.reshub.api.IResHub;
import com.tencent.rdelivery.reshub.api.IResLoadError;
import com.tencent.shiply.integration.ShiplyIntegrationHelper;

public class TestResActivity extends Activity {
    private IResHub reshub;
    private TextView tvRemoteResult;
    private TextView tvLocalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_res);

        reshub = ShiplyIntegrationHelper.INSTANCE.getReshubInstance();

        Button loadButton = (Button) findViewById(R.id.load);
        Button getButton = (Button) findViewById(R.id.get);
        Button loadLatestButton = (Button) findViewById(R.id.loadLatest);
        Button getLatestButton = (Button) findViewById(R.id.getLatest);

        EditText etRemoteResName = (EditText) findViewById(R.id.et_remote_res_name);
        EditText etGetResName = (EditText) findViewById(R.id.et_get_res_name);
        tvRemoteResult = (TextView) findViewById(R.id.tv_remote_result);
        tvLocalResult = (TextView) findViewById(R.id.tv_local_result);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resId = etRemoteResName.getText().toString();
                loadResource(resId);
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resId = etGetResName.getText().toString();
                getResource(resId);
            }
        });

        loadLatestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resId = etRemoteResName.getText().toString();
                loadLatestResource(resId);
            }
        });

        getLatestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resId = etGetResName.getText().toString();
                getLatestResource(resId);
            }
        });
    }

    private void loadLatestResource(String resId) {
        reshub.loadLatest(resId, new IResCallback() {
            @Override
            public void onProgress(float progress) {
                Log.d("ResHubLoad", "资源加载进度: " + progress);
            }

            @Override
            public void onComplete(boolean isSuccess, IRes result, IResLoadError error) {
                if (isSuccess) {
                    Log.d("ResHubLoad", "资源拉取成功");
                    showCustomToast("异步最新资源拉取成功");
                    if (result != null) {
                        tvRemoteResult.setText("异步最新资源拉取成功，文件路径：" + result.getLocalPath());
                    }
                } else {
                    Log.e("ResHubLoad", "异步最新资源拉取失败");
                    showCustomToast("异步最新资源拉取失败");
                    tvRemoteResult.setText("异步最新资源拉取失败");
                }
            }
        });
    }

    private void loadResource(String resId) {
        reshub.load(resId, new IResCallback() {
            @Override
            public void onProgress(float progress) {
                Log.d("ResHubLoad", "资源加载进度: " + progress);
            }

            @Override
            public void onComplete(boolean isSuccess, IRes result, IResLoadError error) {
                if (isSuccess) {
                    Log.d("ResHubLoad", "异步资源拉取成功");
                    showCustomToast("异步资源拉取成功");
                    if (result != null) {
                        tvRemoteResult.setText("异步资源拉取成功，文件路径：" + result.getLocalPath());
                    }
                } else {
                    Log.e("ResHubLoad", "异步资源拉取失败");
                    showCustomToast("异步资源拉取失败");
                    tvRemoteResult.setText("异步资源拉取失败");
                }
            }
        });
    }

    private void getResource(String resId) {
        IRes res = reshub.get(resId, false);
        if (res != null) {
            Log.d("ResHubGet", "同步资源获取成功: " + res.getLocalPath());
            showCustomToast("同步资源获取成功");
            updateResourceContent(res);
        } else {
            Log.e("ResHubGet", "同步资源获取失败");
            showCustomToast("同步资源获取失败");
            tvLocalResult.setText("同步资源获取失败");
        }
    }

    private void getLatestResource(String resId) {
        IRes res = reshub.getLatest(resId, false);
        if (res != null) {
            Log.d("ResHubGet", "成功获取同步最新资源: " + res.getLocalPath());
            showCustomToast("同步最新资源获取成功");
            updateResourceContent(res);
        } else {
            Log.e("ResHubGet", "获取同步最新资源失败");
            showCustomToast("同步最新资源获取失败");
            tvLocalResult.setText("同步最新资源获取失败");
        }
    }

    private void updateResourceContent(IRes res) {
        String content = "ResId: " + res.getResId() + "\n"
                + "LocalPath: " + res.getLocalPath() + "\n"
                + "Version: " + res.getVersion() + "\n"
                + "Size: " + res.getSize() + "\n"
                + "MD5: " + res.getMd5() + "\n"
                + "DownloadUrl: " + res.getDownloadUrl() + "\n"
                + "FileExtra: " + res.getFileExtra() + "\n"
                + "ResType: " + res.getResType() + "\n"
                + "Description: " + res.getDescription() + "\n"
                + "TaskId: " + res.getTaskId();
        tvLocalResult.setText(content);
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
        ImageView toastIcon = layout.findViewById(R.id.toast_icon);
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastIcon.setImageResource(R.drawable.ic_launcher);
        toastText.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}