package com.example.administrator.kaoyan.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kaoyan.R;
import com.example.administrator.kaoyan.activity.AnswerActivity;
import com.example.administrator.kaoyan.adapter.TestListAdapter;
import com.example.administrator.kaoyan.bean.MyEventBus;
import com.example.administrator.kaoyan.view.MyListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/26.
 */
public class TestFragment extends Fragment implements AdapterView.OnItemClickListener {
    private AnswerActivity answerActivity;
    private View view;
    private TextView tv_tigan, tv_answer, tv_analysis;
    private MyListView lv;
    private List<String> list;
    private TestListAdapter adapter;
    private LinearLayout lin_answer;
    private String A, B, C, D, E, answer, analysis, tigan;
    private boolean flag = true;
//    private int correntNums=0;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerActivity = (AnswerActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.from(answerActivity).inflate(R.layout.fragment_test, null);
        getBundle();
        initView();
        initData();
        initListener();
        return view;
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        A = bundle.getString("A");
        B = bundle.getString("B");
        C = bundle.getString("C");
        D = bundle.getString("D");
        E = bundle.getString("E");
        answer = bundle.getString("answer");
        analysis = bundle.getString("analysis");
        tigan = bundle.getString("tigan");
    }

    private void initListener() {
        lv.setOnItemClickListener(this);
    }

    public void show() {
        if (lin_answer != null) {
            lin_answer.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(A);
        list.add(B);
        list.add(C);
        list.add(D);
        if (!E.equals("E. 暂无") && !E.equals("E. ")) {
            list.add(E);
        }
        adapter = new TestListAdapter(answerActivity, list);
        lv.setAdapter(adapter);
        tv_answer.setText(answer);
        tv_analysis.setText(analysis);
        tv_tigan.setText(tigan);

    }

    private void initView() {
        tv_tigan = (TextView) view.findViewById(R.id.tv_tigan);
        tv_answer = (TextView) view.findViewById(R.id.tv_answer);
        tv_analysis = (TextView) view.findViewById(R.id.tv_analysis);
        lv = (MyListView) view.findViewById(R.id.lv);
        lin_answer = (LinearLayout) view.findViewById(R.id.lin_answer);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (flag) {
            adapter.changeTextColor(position, getPosition(answer));
            lin_answer.setVisibility(View.VISIBLE);
            if (list.get(position).substring(0, 1).equals(answer)) {

                if(((AnswerActivity)getActivity()).currectNums++ > 2){
                    Toast.makeText(answerActivity, "恭喜连对"+((AnswerActivity)getActivity()).currectNums+"题", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(answerActivity, "恭喜回答正确咯", Toast.LENGTH_SHORT).show();
                }
                Log.e("TestFragment","---------------"+((AnswerActivity)getActivity()).currectNums);

            } else {
                Toast.makeText(answerActivity, "回答错误", Toast.LENGTH_SHORT).show();
                ((AnswerActivity)getActivity()).currectNums=0;
                Log.e("TestFragment","---------------"+((AnswerActivity)getActivity()).currectNums);
            }
            flag = false;
        }
    }

    private int getPosition(String answer) {
        if (answer.equals("A")) {
            return 0;
        } else if (answer.equals("B")) {
            return 1;
        } else if (answer.equals("C")) {
            return 2;
        } else if (answer.equals("D")) {
            return 3;
        } else if (answer.equals("E")) {
            return 4;
        }
        return 0;
    }
    @Override
    public void onResume() {
        super.onResume();
        flag = true;
        MobclickAgent.onPageStart("AnswerActivity"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AnswerActivity");
    }
}
