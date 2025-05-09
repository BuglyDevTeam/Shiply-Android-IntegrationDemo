package com.example.shiplyIntegrationDemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.shiplyIntegrationDemo.R;

public class MainActivityNew extends Activity {

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Class<?> targetActivity = null;
            switch (v.getId()) {
                case R.id.btn_config_switch:
                    targetActivity = TestConfigActivity.class;
                    break;
                case R.id.btn_resource_test:
                    targetActivity = TestResActivity.class;
                    break;
                case R.id.btn_app_upgrade:
                    targetActivity = TestUpgradeActivity.class;
                    break;
                case R.id.btn_hotfix_test:
                    targetActivity = TestRFixActivity.class;
                    break;
            }
            if (targetActivity != null) {
                startActivity(new Intent(MainActivityNew.this, targetActivity));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btn_config_switch).setOnClickListener(listener);
        findViewById(R.id.btn_resource_test).setOnClickListener(listener);
        findViewById(R.id.btn_app_upgrade).setOnClickListener(listener);
        findViewById(R.id.btn_hotfix_test).setOnClickListener(listener);
    }
}