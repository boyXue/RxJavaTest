package com.example.rxjavatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavatest.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    //图片的网络地址
    private String PATH = "http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg";
    //加载时的弹出框
    private ProgressDialog progressDialog;
    private List<String> list = new ArrayList<>();
    private boolean img = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list.add("hello");
        list.add("skjlf");
        list.add("dsfafasf");
        list.add("dfafebvg");

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Observable.create(new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        subscriber.onNext("hello");
//                        subscriber.onNext("hello1");
//                        subscriber.onNext("hello2");
//                        subscriber.onNext("hello3");
//                        subscriber.onNext("hello4");
//                        subscriber.onCompleted();
//                    }
//                }).subscribe(new Action1<String>() {//Action是没有返回值的
//                    @Override
//                    public void call(String s) {
//                        binding.textView.append("\n"+s);
//                    }
//                });

//                Observable.from(list)
//                        .subscribe(new Action1<String>() {
//                            @Override
//                            public void call(String s) {
//                                binding.textView.append("\n"+s);
//                            }
//                        });
//                if (!img) {
//                    progressDialog = new ProgressDialog(MainActivity.this);
//                    progressDialog.setTitle("download run");
//                    progressDialog.show();
//                    Observable.just(PATH)
//                            .map(new Func1<String, Bitmap>() {
//                                @Override
//                                public Bitmap call(String s) {
//                                    try {
//                                        URL url = new URL(s);
//                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                                        httpURLConnection.setConnectTimeout(5000);
//                                        int responseCode = httpURLConnection.getResponseCode();
//                                        if (responseCode == HttpURLConnection.HTTP_OK) {
//                                            InputStream inputStream = httpURLConnection.getInputStream();
//                                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                                            return bitmap;
//                                        }
//
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    return null;
//                                }
//                            })
//                            .map(new Func1<Bitmap, Bitmap>() {//Fun1是带有返回值的
//                                @Override
//                                public Bitmap call(Bitmap bitmap) {
//                                    Paint paint = new Paint();
//                                    paint.setTextSize(88);
//                                    paint.setColor(Color.BLUE);
//                                    return drawTextToBitmap.drawTextToBitmap(bitmap, "这是水印", paint, 80, 80);
//                                }
//                            })
//                            .subscribeOn(Schedulers.io())//给上面的代码分配到异步线程
//                            .observeOn(AndroidSchedulers.mainThread())//切换到主线程
//                            .subscribe(new Observer<Bitmap>() {
//                                @Override
//                                public void onCompleted() {
//
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onNext(Bitmap bitmap) {
//                                    if (progressDialog != null) {
//                                        progressDialog.dismiss();
//                                    }
//                                    binding.imageView.setImageBitmap(bitmap);
//                                    img=true;
//                                }
//                            });
//                } else {
//                    Toast.makeText(MainActivity.this, "图片已加载，请不要重复操作", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }


}
