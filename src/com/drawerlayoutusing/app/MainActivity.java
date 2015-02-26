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
	 * ��Ҫ�õ��ı���������
	 */
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mtitle; //���ڱ�������ʼ���Ǹ�����
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //�ؼ���ʼ��
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        //�ַ����б��ʼ��
        menuLists = new ArrayList<String>();
        for(int i=1; i<=5; i++){
        	menuLists.add("���벼���б���0" + i);
        }
        
        //��ʼ��һ��������
        adapter = new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1 , menuLists);
        
        //�Գ��벼�������б�����һ�������������������Ѿ����ú��˲�������
        mDrawerList.setAdapter(adapter);
        
        //�������벼�ֵĲ˵������ü���������������¼�
        mDrawerList.setOnItemClickListener(this);
        
        //mDrawerToggle���ڱ�ʾ�����Ǳ��򿪻��ǹر�
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this , mDrawerLayout , R.drawable.ic_drawer , R.string.drawer_open , R.string.drawer_close){
        	
        	@Override
        	public void onDrawerOpened(View drawerView) {
        		// ������򿪵�ʱ��ִ���������
        		super.onDrawerOpened(drawerView);
        		//���ı���
        		mtitle = getActionBar().getTitle().toString();
        		getActionBar().setTitle("��ѡ��");
        		//������Ҫ���¶�ActionBar�ϵ�Menu���в����������Ҫ������һ���ػ�ActionBar����Ĳ˵���
        		invalidateOptionsMenu();
        	}
        	
        	@Override
        	public void onDrawerClosed(View drawerView) {
        		// ������رյ�ʱ��ִ���������
        		super.onDrawerClosed(drawerView);
        		//��ԭԭ�����Ǹ�����
        		getActionBar().setTitle(mtitle);
        		//������Ҫ���¶�ActionBar�ϵ�Menu���в����������Ҫ������һ���ػ�ActionBar����Ĳ˵���
        		invalidateOptionsMenu();
        	}
        };
        
        //����һ������������mDrawerToggle���м���
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        //����ActionBar��App Icon�Ĺ��ܣ�֮����ܵ���ͼƬ���ܰѳ����
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }
    
    
    /*
     * ������invalidateOptionsMenu()��ʱ��ϵͳ���Զ���һ�����������������
     * onPrepareOptionsMenu()
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	//���ݳ����Ƿ�򿪻����أ���ʵ��ActionBar�ұߵ�ͼ���Ƿ�򿪻�����
    	//�������Ҫ���ľ����жϵ�ǰ���뵽���Ǵ򿪵Ļ������ص�
    	boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
    	//��������Ǵ򿪵ģ������Ǹ�menu���أ�������������صģ������Ǹ�menu��ʾ
    	menu.findItem(R.id.action_websearch).setVisible( !isDrawerOpen );
    	return super.onPrepareOptionsMenu(menu);
    }
    
    /*
     * ��ʼ�����������ұ��Ǹ�ͼ��
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main , menu);
    	return true;
    }
    
    
    /*
     * �����������ұ��Ǹ�ͼ�걻�����ʱ��
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//��ActionBar�ϵ�ͼ����Drawer�������
    	//���if���ִ����֮�����ʵ�ֵ�������ߵ��Ǹ���ť�򿪻�رճ���
    	if(mDrawerToggle.onOptionsItemSelected(item)){
    		return true;
    	}
    	
    	switch(item.getItemId()){
    	case R.id.action_websearch: //���������Ǳ��������ұ��Ǹ�ͼ��
    		//������������򿪰ٶ���ҳ
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
     * ��Ҫ��ActionDrawerToggle��DrawerLayout��״̬ͬ��
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	//��ActionDrawerToggle��DrawerLayout��״̬ͬ��
    	//Ҳ��Ҫ��ActionDrawerToggle�е�drawerͼ�����ó��µ�
    	mDrawerToggle.syncState();
    }
    
    
    /*
     * ����Ļ��ת��ʱ�򣬶�Configuration���½�������
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
    	/*
    	 * ÿ�������б����ʱ�򣬾�����������ͼ�в���һ��Fragment��ͼ
    	 */
    	Fragment contentFragment = new ContentFragment();
    	
    	//Ϊ������ÿ��Fragment����ͬ��������Ҫ����ȥһ��������������
    	Bundle args = new Bundle();
    	args.putString("text" , menuLists.get(position)); //��ֵ��,���ǡ�text����ֵ���б�������к�
    	
    	//Ȼ���ٰ����Я����������Ϣ��args����������ͬ��Fragment
    	contentFragment.setArguments(args);
    	
    	/*
    	 * ����Ϊֹ��һ�����ص�Fragment�ʹ������ˣ������Fragment�ǽ��й���
    	 */
    	
    	FragmentManager fm = getFragmentManager();
    	//Ϊ��ǰ��Fragment����һ��������ΪҪ�õ�ǰ�µ�Fragment�滻��ԭ���ģ�����Ҫ�õ�replace()����
    	//��һ�������ǣ��ѵ�ǰ��Fragment��䵽�ĸ���ͼ���У�����һ�������Ǳ������Ǹ���ͼ��id
    	//�ڶ��������ǣ����ĸ�fragment���в��룬��Ȼ�Ǹ�д�õ�contentFragment��
    	//���һ��Ҫcommit()�ύ������������Ч
    	fm.beginTransaction().replace(R.id.content_frame , contentFragment).commit();
    	
    	/*
    	 * �������˵�֮�󣬵�ȻҪ�����Ĳ����Զ�������
    	 */
    	mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    

    


}


