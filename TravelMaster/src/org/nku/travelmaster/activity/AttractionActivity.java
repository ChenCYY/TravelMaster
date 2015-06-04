package org.nku.travelmaster.activity;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Inflater;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;









import org.nku.travelmaster.internet.WebAccessUtils;
import org.nku.travelmaster.po.Attractions;

import com.example.fragmentdemo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AttractionActivity extends Activity{
	
	 ViewPager pager = null;
	 ArrayList<View> viewContainter = new ArrayList<View>();
		
		private Bitmap[] bitmaps;
	   
	    ArrayList<String> titleContainer = new ArrayList<String>();
	private List<Map<String, ?>> lstData_Replys;
	private ListView listattractions;
	int aid;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attraction_layout);
		
		Intent intent = getIntent();
		System.out.println("intentString=" + intent.getStringExtra("aid"));
		this.aid = Integer.parseInt(intent.getStringExtra("aid"));

		this.listattractions = (ListView) this.findViewById(R.id.listattractions);
		this.lstData_Replys = fetchData(aid);
		pager=(ViewPager) this.findViewById(R.id.viewpager2);
		
		TextView tvname=(TextView)this.findViewById( R.id.attName);
		tvname.setText((String)lstData_Replys.get(0).get("attName"));
		
		TextView tvcon=(TextView)this.findViewById( R.id.attContext);
		ImageView imageview=(ImageView)this.findViewById(R.id.image01);
		imageview.setImageBitmap(bitmaps[0]);
		imageview=(ImageView)this.findViewById(R.id.image02);
		imageview.setImageBitmap(bitmaps[1]);
	    imageview=(ImageView)this.findViewById(R.id.image03);
		imageview.setImageBitmap(bitmaps[2]);
	    imageview=(ImageView)this.findViewById(R.id.image04);
		imageview.setImageBitmap(bitmaps[3]);
		LayoutInflater inflater;
		View view1=inflater.inflate(R.layout.image1, null);
		 View view2=inflater.inflate(R.layout.image2, null);
		 View view3=inflater.inflate(R.layout.image3, null);
		 View view4=inflater.inflate(R.layout.image4, null);
		 viewContainter.add(view1);
	     viewContainter.add(view2);
	     viewContainter.add(view3);
	     viewContainter.add(view4);
		tvcon.setText((String)lstData_Replys.get(0).get("attContext"));
		
		SimpleAdapter adapter = new SimpleAdapter(this, this.lstData_Replys,
				R.layout.list_detail_attraction, new String[] { "attName",
						"attProvince", "attCity", "attComments", "attContext","attSum"
						 }, new int[] { R.id.attName,
						R.id.attProvince, R.id.attCity, R.id.attComments,
						R.id.attContext,R.id.attSum });
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
		this.listattractions.setAdapter(adapter);
		
	}
	
	private List<Map<String, ?>> fetchData(int aid) {
		// TODO Auto-generated method stub
		List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();

		// System.out.println(pid);
		Gson gson = new GsonBuilder().create();
		String aid_data = gson.toJson(aid);

		System.out.println("Activity:Aid=" + aid_data);
		System.out.println("Activity:aid_data" + aid_data);

		String uri = "http://192.168.191.1:8001/TravelMaster/";
		String webServiceName = "ADAttractionServlet";
		String parm="?aid_data=";
		uri += webServiceName;
		uri += parm+aid_data;
		System.out.println(uri);

		List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
		lstNameValuePairs.add(new BasicNameValuePair("aid_data", aid_data));
		String data = "";
		HttpPost httpRequestByPost = new HttpPost(uri);
		System.out.println("hhhhhhhhhh");
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					lstNameValuePairs, HTTP.UTF_8);
			httpRequestByPost.setEntity(entity);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequestByPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				data = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("Json Data:> " + data);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aaaaaaaaaaaa");
		Type ListAttractions =new TypeToken<Attractions>(){}.getType();
		
		Log.d("DUG", "444444444444");
		Gson gson2 = new GsonBuilder().create();
		Log.d("DUG", "22222222222");
	    Attractions lstAttraction =gson2.fromJson(data,ListAttractions);
		Log.d("DUG", "33333333333");
		
		
		
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("attProvince", lstAttraction.getAprovince());
			item.put("attCity", lstAttraction.getAcity());
			item.put("attComments",lstAttraction.getComments());
			item.put("attName",lstAttraction.getAname());
			item.put("attSum",lstAttraction.getSum());
			item.put("attContext",lstAttraction.getContext());
			String temp[]=lstAttraction.getResourceids().split(",");
			Bitmap bitmap=null;
			String imagesource="images/attractions/";
			WebAccessUtils webAccessUtils=new WebAccessUtils();
			for(int i=0;i<=3;i++){
				imagesource=imagesource+temp[i];
				bitmap=webAccessUtils.DownloadImage(imagesource);
				bitmaps[i]=bitmap;
			}
			
		return lst;

	}

}
