package com.example.rxjavatest;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavatest.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private int i;
    private Observable observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.createButton.setOnClickListener(this);
        binding.mapButton.setOnClickListener(this);
        binding.zipButton.setOnClickListener(this);
        binding.concatButton.setOnClickListener(this);
        binding.flatMapButton.setOnClickListener(this);
        binding.concatMapButton.setOnClickListener(this);
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onComplete();
            }
        });
    }

    private Observable<Integer> getIntegarOvservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        binding.textView.setText("");
        switch (v.getId()) {
            case R.id.createButton:
                createRxJava();
                break;
            case R.id.mapButton:
                mapRxJava();
                break;
            case R.id.zipButton:
                zipRxJava();
                break;
            case R.id.concatButton:
                concatRxJava();
                break;
            case R.id.flatMapButton:
                flatMapRxJava();
                break;
            case R.id.concatMapButton:
                concatMapRxJava();
                break;
        }
    }

    private void concatMapRxJava() {
        observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        binding.textView.append(s + "");
                    }
                });
    }

    //为传入的observable的数据添加多个子数据，但不保证顺序，如果需要保证顺序使用concatMap
    private void flatMapRxJava() {
        observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value" + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        binding.textView.append("flatMap : accept : " + s + "\n");
                    }
                });
    }

    private void concatRxJava() {
        //两个数据源连成一个进行输出
        observable.concat(Observable.just(1, 2, 3), Observable.just("h", "h", "g"))
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        binding.textView.append(String.valueOf(serializable));
                    }
                });
    }

    private void zipRxJava() {
        //合并事件，两两配对
//        Observable.zip(getStringObservable(), getIntegarOvservable(), new BiFunction<String, Integer, String>() {
//            @Override
//            public String apply(String s, Integer integer) throws Exception {
//                return s + integer;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                binding.textView.append(s + "\n");
//            }
//        });

        observable.zip(getStringObservable(), getIntegarOvservable(), getStringObservable(), new Function3<String, Integer, String, String>() {
            @Override
            public String apply(String s, Integer integer, String s2) throws Exception {
                return s + integer + s2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                binding.textView.append(s + "\n");
            }
        });
    }

    //将输入的类型进行变换再输出
    private void mapRxJava() {
        observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                binding.textView.append(s + "");
            }
        });
    }

    private void createRxJava() {
        observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                binding.textView.append(integer + "\n");
                if (i == 2) {
                    mDisposable.dispose();
                    binding.textView.append(integer + "\n");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
