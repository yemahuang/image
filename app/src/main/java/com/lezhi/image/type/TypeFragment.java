package com.lezhi.image.type;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

    public static final String TYPE = "type";
    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private String mMaxId;
    private TypeAdapter mAdapter;
    private Unbinder mUnbinder;
    private String mType;


    public static TypeFragment newInstance(String type) {
        TypeFragment fragment = new TypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        final RecyclerView.LayoutManager layoutManager;
        if (getString(R.string.all).equals(mType)) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TypeAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = -1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1) {
                        getData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        return view;
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void getData() {
        RetrofitClient.createService(Api.class)
                .getPinsMaxLimit(mType, mMaxId, 10).map(
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
}
