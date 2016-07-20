package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

/**
 * Created by zhengjuntong on 7/18/16.
 */
public class MenuTestActivity extends BaseActivity {

    int index = 0;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
    }

    /**
     * 在 Android 2.3.x 及更低版本中，每当用户打开选项菜单时（按“菜单”按钮），系统均会调用 onPrepareOptionsMenu()。
     * 在 Android 3.0 及更高版本中，当菜单项显示在操作栏中时，选项菜单被视为始终处于打开状态。发生事件时，如果您要执行菜单更新，则必须调用 invalidateOptionsMenu() 来请求系统调用 onPrepareOptionsMenu()。
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        index++;
        menu.add("menu" + index);
        menu.addSubMenu("submenu" + index);
        return super.onPrepareOptionsMenu(menu);
    }

    //创建option选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;//return true for display
    }

    //处理选项菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_1:
                toast("item_1");
                return true;
            case R.id.item_2:
                toast("item_2");
                return true;
            case R.id.main_item_1:
                toast("main_item_1");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickMenuItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_item_2:
                toast("main_item_2");
                break;
            default:
                break;
        }
    }

    public void onClick(View view) {
        onPrepareOptionsMenu(menu);
    }
}
