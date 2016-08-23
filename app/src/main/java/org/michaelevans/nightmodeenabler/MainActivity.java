package org.michaelevans.nightmodeenabler;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button enable;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent tunerIntent = getNightmodeIntent();

        enable = (Button) findViewById(R.id.enable);
        message = (TextView) findViewById(R.id.enable_system_ui);

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(tunerIntent);
                Toast.makeText(MainActivity.this, R.string.yay, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @NonNull
    private Intent getNightmodeIntent() {
        final Intent tunerIntent = new Intent();
        tunerIntent.setClassName("com.android.systemui", "com.android.systemui.tuner.TunerActivity");
        tunerIntent.putExtra("show_night_mode", true);
        return tunerIntent;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isSystemUiTunerEnabled(getNightmodeIntent())) {
            enable.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);
        } else {
            enable.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
        }
    }

    boolean isSystemUiTunerEnabled(Intent intent) {
        PackageManager manager = getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        return !infos.isEmpty();
    }
}
