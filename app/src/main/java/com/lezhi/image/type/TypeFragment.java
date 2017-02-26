package com.lezhi.image.type;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lezhi.image.R;
import com.lezhi.image.api.Api;
import com.lezhi.image.api.RetrofitClient;
import com.lezhi.image.api.model.Pin;
import com.lezhi.image.api.model.Pins;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lezhi on 2017/2/26.
 */

public class TypeFragment extends Fragment {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private String mMaxId;
    private TypeAdapter mAdapter;
    private Unbinder mUnbinder;

    private void getData() {
        RetrofitClient.createService(Api.class)
                .getPinsMaxLimit("beauty", mMaxId, 10).map(
                new Func1<Pins, List<Pin>>() {

                    @Override
                    public List<Pin> call(Pins pins) {
                        return pins.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Pin>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Pin> pins) {
                        mMaxId = getMaxId(pins);
                        mAdapter.addListNotify(pins);
                    }
                });
    }


    private String getMaxId(List<Pin> pins) {
        return pins.get(pins.size() - 1).getPin_id();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new TypeAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        getData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
