package org.nku.travelmaster.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import org.nku.travelmaster.activity.R;
import org.nku.travelmaster.internet.WebAccessUtils;
import org.nku.travelmaster.po.Attractions;
import org.nku.travelmaster.po.Users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HomepageFragment extends Fragment {
	    ViewPager pager = null;
	    PagerTitleStrip titleStrip = null;
	    ArrayList<View> viewContainter = new ArrayList<View>();
	    ArrayList<String> titleContainer = new ArrayList<String>();
	    private List<Map<String, ?>> lstData;
		private ListView lstAttractions;
		private int attsum=4;
	    private int currentIndex=0;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.homepage_layout,
				container, false);
		pager=(ViewPager) messageLayout.findViewById(R.id.viewpager);
		//titleStrip=(PagerTitleStrip) messageLayout.findViewById(R.id.titlestrip);
		lstAttractions=(ListView) messageLayout.findViewById(R.id.listattractions);
		this.lstData = fetchData();
		ViewGroup.LayoutParams params=lstAttractions.getLayoutParams();
		params.height=126*attsum;
		lstAttractions.setLayoutParams(params);
		 View view1=inflater.inflate(R.layout.image1, null);
		 View view2=inflater.inflate(R.layout.image2, null);
		 View view3=inflater.inflate(R.layout.image3, null);
		 View view4=inflater.inflate(R.layout.image4, null);
		 view1.setOnClickListener(new Viewocl());
		 view2.setOnClickListener(new Viewocl());
		 view3.setOnClickListener(new Viewocl());
		 view4.setOnClickListener(new Viewocl());
		 viewContainter.add(view1);
	     viewContainter.add(view2);
	     viewContainter.add(view3);
	     viewContainter.add(view4);
	    
	     SimpleAdapter adapter = new SimpleAdapter(getActivity(), this.lstData,
					R.layout.attraction, new String[] { "txtAttraction","txtComment"}, new int[] {R.id.txtAttraction,R.id.txtComment});
	     lstAttractions.setAdapter(adapter);
	     this.lstAttractions.setOnItemClickListener(new ItemOcl());
	     
	     pager.setAdapter(new PagerAdapter() {
	    	 
	            //viewpager中的组件数量
	            @Override
	            public int getCount() {
	                return viewContainter.size();
	            }
	          //滑动切换的时候销毁当前的组件
	            @Override
	            public void destroyItem(ViewGroup container, int position,
	                    Object object) {
	                ((ViewPager) container).removeView(viewContainter.get(position));
	            }
	          //每次滑动的时候生成的组件
	            @Override
	            public Object instantiateItem(ViewGroup container, int position) {
	                ((ViewPager) container).addView(viewContainter.get(position));
	                currentIndex=position;
	                return viewContainter.get(position);
	            }
	 
	            @Override
	            public boolean isViewFromObject(View arg0, Object arg1) {
	                return arg0 == arg1;
	            }
	 
	            @Override
	            public int getItemPosition(Object object) {
	                return super.getItemPosition(object);
	            }
	 
	            @Override
	            public CharSequence getPageTitle(int position) {
	                return titleContainer.get(position);
	            }
	        });
	 
		return messageLayout;
	}

    private List<Map<String, ?>> fetchData() {
    	// TODO Auto-generated method stub
		// 步骤4-1：创建一个空集合对象
		List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();
		// 步骤4-2：创建一个列表中选项对象并实例化
		
		//String uri="http://192.168.191.1:8001/TravelMaster/";
		//String webServerName="ADShowAttractionServlet";
		//uri += webServerName;
		String response = WebAccessUtils.httpRequest("ADShowAttractionServlet");
		//System.out.println("URI:> " + uri);
		
		//HttpPost httpRequestByPost =new HttpPost(uri);
		System.out.println(response);
		
		Type ListAttractions =new TypeToken<ArrayList<Attractions>>(){}.getType();
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		List<Attractions> lstAttraction =gson.fromJson(response,ListAttractions);
		
		for (Attractions attractions : lstAttraction) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("mid", attractions.getAid());
			item.put("txtAttraction", attractions.getAname());
			item.put("txtComment", attractions.getComments());
			
			lst.add(item);
			
		}
		attsum=lst.size();
		System.out.println("热门总数：> "+ attsum);
		return lst;
}

	    
	


// 滑动页面更改事件监听器
private class Viewocl implements View.OnClickListener{
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(currentIndex){
		case 0:
			Toast.makeText(getActivity(), "I am no.0", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(getActivity(), "I am no.1", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(getActivity(), "I am no.2", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(getActivity(), "I am no.3", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
			}
		}
}
private class ItemOcl implements AdapterView.OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long arg3) {
		// TODO Auto-generated method stub
		// 步骤6-1：使用该方法的position参数获取选中的选项对象并赋值到Map集合中
		Map<String, ?> selectedItem = lstData.get(position);
		// 测试
		Toast.makeText(getActivity(), "您选中的是编号为:"+selectedItem.get("mid"), Toast.LENGTH_LONG).show();
		int aid=(Integer) selectedItem.get("mid");
		
		
		Intent intent = new Intent();
		intent.setClass(HomepageFragment.this.getActivity(), ShowAttractionActivity.class);
		System.out.println("Attraction:Mid="+aid);
		
		intent.putExtra("aid", Integer.toString(aid));
		startActivity(intent);
	}
	
}
}

