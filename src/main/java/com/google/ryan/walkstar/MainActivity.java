package com.google.ryan.walkstar;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnDataPointListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    public int walkstars;
    private String wsTokens;
    private TextView textView;
    private static final int REQUEST_OAUTH = 1;
    private final String SESSION_NAME = "Start Challenge";
    private Session mSession;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;
    private ProgressBar progressBar;
    private Button button;
    private Handler handler = new Handler();
    private ImageButton quiz;
    private NotificationManager notificationManager;
    private int notID = 1;
    private Button notifyBtn;
    private TextView wsAmountTxt;
   // private Button restartBtn;
    public int goal = 10000;
    public boolean pass;
    public boolean pSteps;
    public boolean ch1,ch2,ch3,status;
    public CountSteps countSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent serv = new Intent(this,BackgroundService.class);
        startService(serv);



        mSession = new Session.Builder()
                .setName(SESSION_NAME)
                .setIdentifier(getString(R.string.app_name) + " " + System.currentTimeMillis())
                .setDescription("Challenge Description")
                .setStartTime(Calendar.getInstance().getTimeInMillis(), TimeUnit.MILLISECONDS)
                .setActivity(FitnessActivities.WALKING)
                .build();

        notifyBtn = (Button)findViewById(R.id.notify);

        Intent mIntent = getIntent();
        walkstars = mIntent.getIntExtra("walkstars", 0);
        pass = getIntent().getExtras().getBoolean("passScore");
        pSteps = getIntent().getExtras().getBoolean("passSteps");

        ch1 = getIntent().getExtras().getBoolean("challenge1");
        ch2 = getIntent().getExtras().getBoolean("challenge2");
        ch3 = getIntent().getExtras().getBoolean("challenge3");


        textView = (TextView) findViewById(R.id.stepCounter);
        wsAmountTxt = (TextView)findViewById(R.id.wsAmount);
        wsTokens = String.valueOf(walkstars);

        if(pass){
            wsAmountTxt.setText(wsTokens);
        }

        quiz = (ImageButton) findViewById(R.id.quizBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button = (Button) findViewById(R.id.button);
        //restartBtn = (Button)findViewById(R.id.restart);

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(quiz);
            }
        });

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent options = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(options);
            }
        });

        GoogleFit();
    }

    public void GoogleFit(){
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            CountSteps.LocalBinder binder = (CountSteps.LocalBinder)service;
            countSteps = binder.getService();
            status = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();

    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {


        for( final Field field : dataPoint.getDataType().getFields() ) {
            final Value value = dataPoint.getValue( field );
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent i = new Intent(MainActivity.this, CountSteps.class);
                    bindService(i, sc, Context.BIND_AUTO_CREATE);
                    status=true;
                    Toast.makeText(getBaseContext(),"Sevice Binded Successfully", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        public void run() {
                            while(value.asInt() < goal){
                                handler.post(new Runnable(){
                                    public void run(){}
                                });
                                try{
                                    Thread.sleep(1);
                                }
                                catch(InterruptedException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }

            });

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(ch1){
                        String step1 = getIntent().getExtras().getString("challengeOne");
                        int stepInt1=Integer.parseInt(step1);
                        progressBar.setMax(stepInt1);
                        textView.setText("Step Counter : " + value + "/" + step1);
                        progressBar.setProgress(value.asInt());

                    }
                    else if(ch2){
                        String step2 = getIntent().getExtras().getString("challengeTwo");
                        int stepInt2=Integer.parseInt(step2);
                        progressBar.setMax(stepInt2);
                        textView.setText("Step Counter : " + value + "/" + step2);
                        progressBar.setProgress(value.asInt());

                    }
                    else if(ch3){
                        String step3 = getIntent().getExtras().getString("challengeThree");
                        int stepInt3=Integer.parseInt(step3);
                        progressBar.setMax(stepInt3);
                        textView.setText("Step Counter : " + value + "/" + step3);
                        progressBar.setProgress(value.asInt());

                    }
                    else {
                        textView.setText("Step Counter : " + value + "/" + goal);
                        progressBar.setProgress(value.asInt());
                    }
                }
            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes( DataType.TYPE_STEP_COUNT_CUMULATIVE )
                .setDataSourceTypes( DataSource.TYPE_RAW )
                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                    if( DataType.TYPE_STEP_COUNT_CUMULATIVE.equals( dataSource.getDataType() ) ) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {

        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType( dataType )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add( mApiClient, request, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e( "GoogleFit", "SensorApi successfully added" );
                        }
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult( MainActivity.this, REQUEST_OAUTH );
            } catch(IntentSender.SendIntentException e ) {

            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                if( !mApiClient.isConnecting() && !mApiClient.isConnected() ) {
                    mApiClient.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }

    /*
    @Override
    protected void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove( mApiClient, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
    }
    */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    public void Notification(View view) {
        NotificationCompat.Builder notifyBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(this)
                .setContentTitle("WalkStars")
                .setContentText("WalkStars Running in Background")
                .setTicker("WalkStars")
                .setSmallIcon(R.mipmap.walkstar);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notID, notifyBuilder.build());
    }


}

