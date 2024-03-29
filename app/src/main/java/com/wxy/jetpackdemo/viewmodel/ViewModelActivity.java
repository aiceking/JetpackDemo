package com.wxy.jetpackdemo.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wxy.jetpackdemo.R;
import com.wxy.jetpackdemo.viewmodel.fragment.VMFragmentOne;
import com.wxy.jetpackdemo.viewmodel.fragment.VMFragmentTwo;
import com.wxy.jetpackdemo.viewmodel.model.MyViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewModelActivity extends AppCompatActivity {
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_one)
    TextView btnOne;
    @BindView(R.id.btn_two)
    TextView btnTwo;
    private VMFragmentOne VMFragmentOne;
    private VMFragmentTwo VMFragmentTwo;
    private Fragment currentFragment=new Fragment();
    private MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmodel);
        ButterKnife.bind(this);
        //同一个Activity中使用多个MyViewModel的时候，用key去区分
        myViewModel = ViewModelProviders.of(this,new MyViewModel.Factory("myViewModel")).get("myViewModel",MyViewModel.class);
        myViewModel.setName("ViewModelActivity");
        initFragment();
        showFragment(VMFragmentOne);

    }

    private void initFragment() {
        VMFragmentOne = (VMFragmentOne) getSupportFragmentManager().findFragmentByTag(VMFragmentOne.class.getName());
        if (VMFragmentOne == null) {
            VMFragmentOne = new VMFragmentOne();
        }
        VMFragmentTwo = (VMFragmentTwo) getSupportFragmentManager().findFragmentByTag(VMFragmentTwo.class.getName());
        if (VMFragmentTwo == null) {
            VMFragmentTwo = new VMFragmentTwo();
        }
    }
    private void showFragment(Fragment fragment) {
        if (currentFragment!=fragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);//  不是则隐藏
            currentFragment=fragment;
            if (!fragment.isAdded()){
                transaction.add(R.id.linear_content,fragment,fragment.getClass().getName()).show(fragment).commit();
            }else {
                transaction.show(fragment).commit();
            }
        }
    }
    @OnClick({R.id.btn_send, R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                myViewModel.setName("ViewModelActivity: key=myViewModel");
                break;
            case R.id.btn_one:
                showFragment(VMFragmentOne);
                break;
            case R.id.btn_two:
                showFragment(VMFragmentTwo);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ViewModelActivity ","onResume");
    }


}
