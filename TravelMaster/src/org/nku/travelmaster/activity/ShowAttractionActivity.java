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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;







import org.nku.travelmaster.po.upost;
import org.nku.travelmaster.po.Attractions;
import org.nku.travelmaster.activity.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowAttractionActivity extends Activity{
	
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
		
		TextView tvname=(TextView)this.findViewById( R.id.attName);
		tvname.setText((String)lstData_Replys.get(0).get("attName"));
		
		TextView tvcon=(TextView)this.findViewById( R.id.attContext);
		tvcon.setText((String)lstData_Replys.get(0).get("attContext"));
		
		SimpleAdapter adapter = new SimpleAdapter(this, this.lstData_Replys,
				R.layout.list_detail_attraction, new String[] { "attName",
						"attProvince", "attCity", "attComments", "attContext","attSum"
						 }, new int[] { R.id.attName,
						R.id.attProvince, R.id.attCity, R.id.attComments,
						R.id.attContext,R.id.attSum });
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
		Gson gson2 = new GsonBuilder().create();
	    Attractions lstAttraction =gson2.fromJson(data,ListAttractions);
		
		
		Map<String, Object> item = new HashMap<String, Object>();
			item.put("attProvince", lstAttraction.getAprovince());
			item.put("attCity", lstAttraction.getAcity());
			item.put("attComments",lstAttraction.getComments());
			item.put("attName",lstAttraction.getAname());
			item.put("attSum",lstAttraction.getSum());
			item.put("attContext",lstAttraction.getContext());
			
			lst.add(item);
			
		return lst;

	}

}

