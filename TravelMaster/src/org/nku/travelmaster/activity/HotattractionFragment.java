package org.nku.travelmaster.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.nku.travelmaster.activity.R;
import org.nku.travelmaster.po.Attractions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class HotattractionFragment extends Fragment {
	private Spinner selectYears;
	private Spinner selectSeasons;
	private String[] years;
	private String[] seasons;
	private Button submitSelection;
	private List<Map<String, ?>> lstData;
	private ListView lstAttractions2;
	private int attsum;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.hotattraction_layout,
				container, false);
		selectYears=(Spinner)contactsLayout.findViewById(R.id.selectYears);
		selectSeasons=(Spinner)contactsLayout.findViewById(R.id.selectSeasons);
		submitSelection=(Button)contactsLayout.findViewById(R.id.submitSelection);
		lstAttractions2=(ListView) contactsLayout.findViewById(R.id.listattractions2);
		
	
		this.lstData = fetchData();
		years=getResources().getStringArray(R.array.years);
		seasons=getResources().getStringArray(R.array.seasons);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,years);
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,seasons);
		SimpleAdapter adapter3 = new SimpleAdapter(getActivity(), this.lstData,
				R.layout.attraction, new String[] { "txtAttraction","txtComment"}, new int[] {R.id.txtAttraction,R.id.txtComment});
     lstAttractions2.setAdapter(adapter3);
     this.lstAttractions2.setOnItemClickListener(new ItemOcl());
		selectYears.setAdapter(adapter);
		selectSeasons.setAdapter(adapter2);
		submitSelection.setOnClickListener(new Viewocl());
		return contactsLayout;
	}
	private List<Map<String, ?>> fetchData() {
	    	// TODO Auto-generated method stub
			// 步骤4-1：创建一个空集合对象
			List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();
			// 步骤4-2：创建一个列表中选项对象并实例化
			String uri="http://192.168.191.1:8001/TravelMaster/";
			String webServerName="ADShowAttractionServlet";
			uri += webServerName;
			
			System.out.println("URI:> " + uri);
			Log.d("DUG", "YYYYYYYYYYYYYYYYY");
			HttpPost httpRequestByPost =new HttpPost(uri);
			String data="";
			Log.d("DUG", "ccccccc");
			try {
				
				HttpResponse httpResponse =new DefaultHttpClient().execute(httpRequestByPost);
				System.out.println("1");
				Log.d("DUG", "111111111111111");
				if(httpResponse.getStatusLine().getStatusCode() ==200){
					System.out.println("2");
					data =EntityUtils.toString(httpResponse.getEntity());
					System.out.println("Json Data:> " + data);
				}else{
					Log.d("DUG", "Failed");
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Type ListAttractions =new TypeToken<ArrayList<Attractions>>(){}.getType();
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
			List<Attractions> lstAttraction =gson.fromJson(data,ListAttractions);
			
			for (Attractions attractions : lstAttraction) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("mid", attractions.getAid());
				item.put("txtAttraction", attractions.getAname());
				item.put("txtComment", attractions.getComments());
				
				lst.add(item);
				
			}
			attsum=lst.size();
			System.out.println("景点总数：> "+ attsum);
			return lst;
			
	}
	private class Viewocl implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String year=selectYears.getSelectedItem().toString();
			String season=selectSeasons.getSelectedItem().toString();
			Toast.makeText(getActivity(), "年份是："+year+"季度是："+season,Toast.LENGTH_SHORT ).show();
			
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
			intent.setClass(HotattractionFragment.this.getActivity(), ShowAttractionActivity.class);
			System.out.println("Attraction:Mid="+aid);
			
			intent.putExtra("aid", Integer.toString(aid));
			startActivity(intent);
		}
		
	}
}