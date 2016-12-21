package com.example.hl_th.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import entities.Atm;
import server.AsyncAtm;
import server.AsyncListener;
import server.DataManager;
import utils.Utils;


public class SplashActivity extends AppCompatActivity {

    private static final int INTERNET_CONNECTION = 1;
    private static final int GPS_CONNECTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // get atm list from server
        //check Internet connection
        Utils.activity=this;
        if (Utils.isNetworkAvailable(this)) {
            //check GPS connection
            checkGps();
        } else {
            //Show message about Internet problem and allow user config connection
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please connect Internet!");
            dlgAlert.setTitle("Connection is fail!");
            dlgAlert.setPositiveButton("Configuration", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), INTERNET_CONNECTION);
                }
            });
            dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTERNET_CONNECTION) {
            if (!Utils.isNetworkAvailable(this)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // waiting for connection in 5s
                        boolean hasConnection;
                        int count = 0;
                        do {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            hasConnection = Utils.isNetworkAvailable(SplashActivity.this);
                        } while (!hasConnection && count++ < 5);
                        // app is being continued ...
                        final boolean finalResult = hasConnection;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalResult) {
                                    checkGps();
                                } else {
                                    showExitDialog("Connection Fail!", "Please connect Internet!");
                                }
                            }
                        });
                    }
                }).start();
            } else {
                checkGps();
            }
        } else if (requestCode == GPS_CONNECTION) {
            if (!Utils.isGPSEnabled(this)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // waiting for connection in 5s
                        boolean hasConnection;
                        int count = 0;
                        do {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            hasConnection = Utils.isGPSEnabled(SplashActivity.this);
                        } while (!hasConnection && count++ < 5);
                        // app is being continued ...
                        final boolean finalResult = hasConnection;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalResult) {
                                    startApp();
                                } else {
                                    showExitDialog("Connection Fail!","Please connect GPS!");
                                }
                            }
                        });
                    }
                }).start();
            } else {
                startApp();
            }
        }
    }

    private void showExitDialog(String title, String message) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("Config", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dlgAlert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dlgAlert.create().show();
    }

    private void checkGps() {
        if (Utils.checkJps()) {
            //Get All ATM List

            startApp();

        } else {
            //Show message about GPS problem and exit this app
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please connect GPS!");
            dlgAlert.setTitle("Connection GPS fail");
            dlgAlert.setPositiveButton("Configuration", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_CONNECTION);
                }
            });
            dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.create().show();
        }
    }

    private String getDistrict(String district)
    {
        if(district.equals("Thủ Đức"))
        {
            district="ThuDuc";
        }
        else if(district.equals("Dĩ An"))
        {
            district="ThuDuc";
        }
        else if(district.equals("Gò Vấp"))
        {
            district="GoVap";
        }
        else if(district.equals("Bình Thạnh"))
        {
            district="BinhThanh";
        }
        else if(district.equals("Tân Bình"))
        {
            district="TanBinh";
        }
        else if(district.equals("Tân Phú"))
        {
            district="TanPhu";
        }
        else if(district.equals("Phú Nhuận"))
        {
            district="PhuNhuan";
        } else if(district.equals("Bình Tân"))
        {
            district="BinhTan";
        }
        else if(district.equals("Củ Chi"))
        {
            district="CuChi";
        }
        else if(district.equals("Hóc Mộn"))
        {
            district="HocMon";
        }
        else if(district.equals("Bình Chánh"))
        {
            district="BinhChanh";
        }
        else if(district.equals("Nhà Bè"))
        {
            district="NhaBe";
        }
        else if(district.equals("Cần Giờ"))
        {
            district="CanGio";
        }else
            district=district.replace("Quận ","");

      return district;
    }

    private void startApp() {
        String district= new Utils().getCurrentDistrict();
        final String defaultDistrict=district;
        district=getDistrict(district);
        String url = "http://jobmaps.tk/android_connect/get_by_district.php?";
        url+="District="+"'"+district+"'";
        AsyncAtm asyncAtm = new AsyncAtm(new AsyncListener() {
            @Override
            public void onAsyncComplete() {
                List<Atm> atms = DataManager.getInstance().getAtmDetail();
                if (atms != null && atms.size() > 0) {
                    //Go to Map activity
                    Toast.makeText(SplashActivity.this,"Download data successfully",Toast.LENGTH_LONG).show();

                } else {
                    String message="I am so sorry. No data for %s. We will update after";
                    Toast.makeText(SplashActivity.this,String.format(message,defaultDistrict),Toast.LENGTH_LONG).show();

                }
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        asyncAtm.execute(url);
    }
}
