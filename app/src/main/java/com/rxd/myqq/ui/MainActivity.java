package com.rxd.myqq.ui;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rxd.myqq.R;
import com.rxd.myqq.base.BaseActivity;
import com.rxd.myqq.ui.fragment.ContactFragment;
import com.rxd.myqq.ui.fragment.DynamicFragment;
import com.rxd.myqq.ui.fragment.MessageFragment;
import com.rxd.myqq.widget.CircleImageView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_dl_root)
    public DrawerLayout drawerLayout;
    @BindView(R.id.main_nv_navigation)
    NavigationView navigationView;
    @BindView(R.id.main_right_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.main_tv_tab_message)
    TextView tvMessage;
    @BindView(R.id.main_tv_tab_contact)
    TextView tvContact;
    @BindView(R.id.main_tv_tab_dynamic)
    TextView tvDynamic;

    @BindView(R.id.main_tv_header)
    TextView tvMainHeader;
    @BindView(R.id.main_tv_add)
    TextView tvMainAdd;
    @BindView(R.id.main_iv_add)
    ImageView ivMainAdd;
    @BindView(R.id.main_iv_avatar)
    CircleImageView cvAvatar;

    //当前界面，默认时消息页
    private int currentId = R.id.main_tv_tab_message;

    private DynamicFragment dynamicFragment;
    private ContactFragment contactFragment;
    private MessageFragment messageFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //设置启动时的默认fragment
        tvMessage.setSelected(true);
        messageFragment = new MessageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, messageFragment).commit();

        tvMessage.setOnClickListener(tabClickListener);
        tvContact.setOnClickListener(tabClickListener);
        tvDynamic.setOnClickListener(tabClickListener);

        drawerLayout.setDrawerListener(drawerListener);

        cvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //toolbar右侧按钮的监听事件
        tvMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentId){
                    case R.id.main_tv_tab_contact:
                        //TODO
                        break;
                    case R.id.main_tv_tab_dynamic:
                        //TODO
                        break;
                    default:
                        break;
                }
            }
        });

        //toolbar右侧按钮的监听事件
        ivMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

    }

    /**
     * 侧滑菜单的监听器
     */
    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            //获取屏幕的宽高
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
            relativeLayout.layout(navigationView.getRight(), 0, navigationView.getRight() + display.getWidth(), display.getHeight());
            //设置透明度
            relativeLayout.setAlpha(relativeLayout.getAlpha()+slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerClosed(View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    /**
     * 底部菜单栏的监听器
     */
    private View.OnClickListener tabClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Log.d("sc","onclick");
            if (v.getId() != currentId){
                //该表底部图标颜色
                changeSelect(v.getId());
                //切换fragment
                changeFragment(v.getId());
                //设置当前页为点击也
                currentId = v.getId();
            }
        }
    };

    /**
     * textview图标和文本的切换
     * @param resId
     */
    private void changeSelect(int resId){
        tvMessage.setSelected(false);
        tvContact.setSelected(false);
        tvDynamic.setSelected(false);
        Log.d("sc","切换文本");
        switch (resId){
            case R.id.main_tv_tab_message:
                tvMessage.setSelected(true);
                tvMainHeader.setText("消息");
                tvMainAdd.setVisibility(View.GONE);
                ivMainAdd.setVisibility(View.VISIBLE);
                break;
            case R.id.main_tv_tab_contact:
                tvContact.setSelected(true);
                tvMainHeader.setText("联系人");
                tvMainAdd.setText("添加");
                tvMainAdd.setVisibility(View.VISIBLE);
                ivMainAdd.setVisibility(View.GONE);
                break;
            case R.id.main_tv_tab_dynamic:
                tvDynamic.setSelected(true);
                tvMainHeader.setText("动态");
                tvMainAdd.setText("更多");
                tvMainAdd.setVisibility(View.VISIBLE);
                ivMainAdd.setVisibility(View.GONE);
                break;
        }
    }

    private void changeFragment(int resId){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        Log.d("sc","切换fragment");
        switch (resId){
            case R.id.main_tv_tab_message:
                if (messageFragment == null){
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.main_container,messageFragment);
                }else{
                    transaction.show(messageFragment);
                }
                break;
            case R.id.main_tv_tab_contact:
                if (contactFragment == null){
                    contactFragment = new ContactFragment();
                    transaction.add(R.id.main_container,contactFragment);
                }else{
                    transaction.show(contactFragment);
                }
                break;
            case R.id.main_tv_tab_dynamic:
                if (dynamicFragment == null){
                    dynamicFragment = new DynamicFragment();
                    transaction.add(R.id.main_container,dynamicFragment);
                }else{
                    transaction.show(dynamicFragment);
                }
                break;
        }
        //提交事务
        transaction.commit();
    }

    /**
     * 隐藏所有fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction){
        if (messageFragment != null)
            transaction.hide(messageFragment);

        if (contactFragment != null)
            transaction.hide(contactFragment);

        if (dynamicFragment != null)
            transaction.hide(dynamicFragment);
    }

}
