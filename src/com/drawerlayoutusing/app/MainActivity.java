package com.drawerlayoutusing.app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity implements OnItemClickListener {
	/*
	 * 需要用到的变量的声明
	 */
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mtitle; //用于保存程序最开始的那个标题
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //控件初始化
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        //字符串列表初始化
        menuLists = new ArrayList<String>();
        for(int i=1; i<=5; i++){
        	menuLists.add("抽屉布局列表项0" + i);
        }
        
        //初始化一个适配器
        adapter = new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1 , menuLists);
        
        //对抽屉布局左侧的列表设置一个设配器，适配器中已经设置好了布局内容
        mDrawerList.setAdapter(adapter);
        
        //给左侧抽屉布局的菜单项设置监听器，监听点击事件
        mDrawerList.setOnItemClickListener(this);
        
        //mDrawerToggle用于表示抽屉是被打开还是关闭
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this , mDrawerLayout , R.drawable.ic_drawer , R.string.drawer_open , R.string.drawer_close){
        	
        	@Override
        	public void onDrawerOpened(View drawerView) {
        		// 当抽屉打开的时候执行这个方法
        		super.onDrawerOpened(drawerView);
        		//更改标题
        		mtitle = getActionBar().getTitle().toString();
        		getActionBar().setTitle("请选择");
        		//我们需要重新对ActionBar上的Menu进行操作，因此需要下面这一句重绘ActionBar上面的菜单项
        		invalidateOptionsMenu();
        	}
        	
        	@Override
        	public void onDrawerClosed(View drawerView) {
        		// 当抽屉关闭的时候执行这个方法
        		super.onDrawerClosed(drawerView);
        		//还原原来的那个标题
        		getActionBar().setTitle(mtitle);
        		//我们需要重新对ActionBar上的Menu进行操作，因此需要下面这一句重绘ActionBar上面的菜单项
        		invalidateOptionsMenu();
        	}
        };
        
        //设置一个监听器，对mDrawerToggle进行监听
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        //开启ActionBar上App Icon的功能，之后才能单击图片就能把抽屉打开
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }
    
    
    /*
     * 当调用invalidateOptionsMenu()的时候，系统会自动调一个方法这个方法叫做
     * onPrepareOptionsMenu()
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	//根据抽屉是否打开或隐藏，来实现ActionBar右边的图标是否打开或隐藏
    	//因此首先要做的就是判断当前抽屉到底是打开的还是隐藏的
    	boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
    	//如果抽屉是打开的，就让那个menu隐藏；如果抽屉是隐藏的，就让那个menu显示
    	menu.findItem(R.id.action_websearch).setVisible( !isDrawerOpen );
    	return super.onPrepareOptionsMenu(menu);
    }
    
    /*
     * 初始化标题栏最右边那个图标
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main , menu);
    	return true;
    }
    
    
    /*
     * 当标题栏最右边那个图标被点击的时候
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//将ActionBar上的图标与Drawer结合起来
    	//这个if语句执行完之后就能实现单击最左边的那个按钮打开或关闭抽屉
    	if(mDrawerToggle.onOptionsItemSelected(item)){
    		return true;
    	}
    	
    	switch(item.getItemId()){
    	case R.id.action_websearch: //如果点击的是标题栏最右边那个图标
    		//打开浏览器，并打开百度主页
    		Intent intent = new Intent();
    		intent.setAction("android.intent.action.VIEW");
    		Uri uri = Uri.parse("http://www.baidu.com");
    		intent.setData(uri);
    		startActivity(intent);
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    
    /*
     * 需要将ActionDrawerToggle与DrawerLayout的状态同步
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	//将ActionDrawerToggle与DrawerLayout的状态同步
    	//也需要将ActionDrawerToggle中的drawer图标设置成新的
    	mDrawerToggle.syncState();
    }
    
    
    /*
     * 当屏幕旋转的时候，对Configuration重新进行配置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
    	/*
    	 * 每当单击列表项的时候，就在主内容视图中插入一个Fragment视图
    	 */
    	Fragment contentFragment = new ContentFragment();
    	
    	//为了区别每个Fragment都不同，这里需要传进去一个参数以作区分
    	Bundle args = new Bundle();
    	args.putString("text" , menuLists.get(position)); //键值对,键是“text”，值是列表项的序列号
    	
    	//然后再把这个携带着区分信息的args，传进给不同的Fragment
    	contentFragment.setArguments(args);
    	
    	/*
    	 * 到此为止，一个独特的Fragment就创建好了，下面对Fragment们进行管理
    	 */
    	
    	FragmentManager fm = getFragmentManager();
    	//为当前的Fragment开启一个事务，因为要让当前新的Fragment替换掉原来的，所以要用到replace()函数
    	//第一个参数是，把当前的Fragment填充到哪个视图当中，即第一个参数是被填充的那个视图的id
    	//第二个参数是，把哪个fragment进行插入，当然是刚写好的contentFragment了
    	//最后一定要commit()提交才能让事务生效
    	fm.beginTransaction().replace(R.id.content_frame , contentFragment).commit();
    	
    	/*
    	 * 当点击完菜单之后，当然要让左侧的布局自动隐藏了
    	 */
    	mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    

    


}


