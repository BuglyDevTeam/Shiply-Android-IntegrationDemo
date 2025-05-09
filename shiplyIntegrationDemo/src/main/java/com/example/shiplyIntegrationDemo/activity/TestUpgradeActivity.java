package com.example.shiplyIntegrationDemo.activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import com.example.shiplyIntegrationDemo.R;
import com.tencent.shiply.integration.ShiplyIntegrationHelper;
import com.tencent.upgrade.core.UpgradeReqCallbackForUserManualCheck;

public class TestUpgradeActivity extends Activity {

    private OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_detect_upgrade:
                manualDetectUpgrade();
                break;
            default:
                break;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upgrade);
        findViewById(R.id.btn_detect_upgrade).setOnClickListener(onClickListener);
    }

    private void manualDetectUpgrade() {
        ShiplyIntegrationHelper.INSTANCE.getUpgradeInstance()
                .checkUpgrade(true, null, new UpgradeReqCallbackForUserManualCheck());
    }
}
