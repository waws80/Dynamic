package top.waws.dynamicapp;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidthanatos.dynamic.Dynamic;

import pw.androidthanatos.annotation.Module;
import pw.androidthanatos.annotation.Modules;
import pw.androidthanatos.annotation.Path;
import pw.androidthanatos.router.Response;
import pw.androidthanatos.router.ResultCallBack;
import pw.androidthanatos.router.Router;


@Path("main")
@Module("app")
@Modules("app")
public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void go(View view){
        Router.debug(true);
        Dynamic dynamic = new Dynamic.Builder()
                .router(Router.getInstance())
                .build();
        RouterService service = dynamic.create(RouterService.class,this);
        Bundle option = ActivityOptions.makeSceneTransitionAnimation(this,tv,"share").toBundle();
        service.toSecond(option, new ResultCallBack() {
            @Override
            public void next(int i, Intent intent) {
                if (i == 1000){
                    Toast.makeText(MainActivity.this,intent.getStringExtra("tag"),Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }
}
