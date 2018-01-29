package com.githubusersearch.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ankit on 30/01/18.
 */

public class RxSearchObservable {
    public static Flowable<String> fromView(EditText searchView) {

        final PublishRelay<String> relay = PublishRelay.create();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", s +  "");
                if (s != null) {
                    relay.accept(s.toString());
                    //subject.onNext(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //no-op
            }
        });

        return relay.toFlowable(BackpressureStrategy.BUFFER);
    }
}
