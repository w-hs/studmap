package de.whs.studmap.web;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import de.whs.studmap.data.Constants;
import de.whs.studmap.data.Floor;
import de.whs.studmap.data.Node;
import de.whs.studmap.data.PoI;

public class Service implements Constants {
	
	public static boolean login(String name, String password) throws WebServiceException, ConnectException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(REQUEST_PARAM_USERNAME, name));
		params.add(new BasicNameValuePair(REQUEST_PARAM_PASSWORD, password));
		
		httpPost(URL_USER,METHOD_LOGIN, params);

		return true;
	}
	
	public static boolean logout(String name) throws WebServiceException, ConnectException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(REQUEST_PARAM_USERNAME, name));
		
		httpPost(URL_USER, METHOD_LOGOUT, params);
		
		return true;
	}
	
	public static boolean register(String name, String password) throws WebServiceException, ConnectException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(REQUEST_PARAM_USERNAME, name));
		params.add(new BasicNameValuePair(REQUEST_PARAM_PASSWORD, password));
		
		httpPost(URL_USER, METHOD_REGISTER, params);
		
		return true;
	}
	
	public static List<PoI> getPoIsForMap(int mapId) throws WebServiceException, ConnectException{
		List<PoI> poiList = new ArrayList<PoI>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(REQUEST_PARAM_MAPID, String.valueOf(mapId)));
				
		try {
			JSONObject pois = httpGet(URL_MAPS, METHOD_GETPOIS, params);
			JSONArray poiArray = pois.getJSONArray(RESPONSE_PARAM_LIST);
			
			for (int i = 0; i < poiArray.length(); i++){
				JSONObject o = poiArray.getJSONObject(i);
				PoI poi = parseJsonToPoI(o);
				poiList.add(poi);
			}
			
		} catch (JSONException e) {
			Log.e(LOG_TAG_WEBSERVICE, "getPoIsForMap - PoIs konnten nicht geparst werden!");
			throw new ConnectException();
		}	
		
		return poiList;
	}
	
	public static List<Node> getRoomsForMap(int mapId) throws WebServiceException, ConnectException{
		List<Node> nodes = new ArrayList<Node>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(REQUEST_PARAM_MAPID, String.valueOf(mapId)));
		
		try {
			JSONObject rooms = httpGet(URL_MAPS,METHOD_GETROOMS,params);
			JSONArray roomArray = rooms.getJSONArray(RESPONSE_PARAM_LIST);
			
			for (int i = 0; i < roomArray.length(); i++){
				JSONObject o = roomArray.getJSONObject(i);
				Node node = parseJsonToNode(o);
				nodes.add(node);
			}
			
		} catch (JSONException ignore) {
			Log.e(LOG_TAG_WEBSERVICE, "getRoomsForMap - Rooms konnten nicht geparst werden!");
		}		
		
		return nodes;		
	}
	
	public static List<Floor> getFloorsForMap(int mapId) throws WebServiceException, ConnectException{
		List<Floor> floorList = new ArrayList<Floor>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(REQUEST_PARAM_MAPID, String.valueOf(mapId)));
		
		try {
			JSONObject floors = httpGet(URL_MAPS, METHOD_GETFLOORS, params);
			JSONArray roomArray = floors.getJSONArray(RESPONSE_PARAM_LIST);
			
			for (int i = 0; i < roomArray.length(); i++){
				JSONObject o = roomArray.getJSONObject(i);
				Floor floor = parseJsonToFloor(o);
				floorList.add(floor);
			}
			
		} catch (JSONException ignore) {
			Log.e(LOG_TAG_WEBSERVICE, "getFloorsForMap - Floors konnten nicht geparst werden!");
		}		
		
		return floorList;		
	}
	
	public static String getActiveUsers() throws WebServiceException, ConnectException{
		//Wird eigentlich noch garnicht ben�tigt, eignet sich aber gut zu Testzwecken
		String result = httpGet(URL_USER, "GetActiveUsers").toString();
		//TODO: parse activeUsers
		return result;
	}
	
	public static Node getNodeInformationForNode(int nodeId) {
		Node node = null;
		//TODO: parse NodeInformation
		return node;
	}
  
	private static JSONObject httpPost(String url, String methodName, List<NameValuePair> params) 
			throws WebServiceException, ConnectException {
		
		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		 
			try {
				// Add data
				String entityString = URLEncodedUtils.format(params, "utf-8");
				HttpPost httpPost = new HttpPost(url + methodName + "?" + entityString);
 
				// Execute HTTP Post Request
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				
				if (entity != null){
					String responseString = EntityUtils.toString(entity); 			  
					JSONObject jObject = new JSONObject(responseString);
					
					int status = jObject.getInt(RESPONSE_STATUS);
					if (status == ResponseStatus.Ok)
						return jObject;
					else
						throw new WebServiceException(jObject);
				}				
 
			} catch (ClientProtocolException e) {
				Log.d(LOG_TAG_WEBSERVICE, "httpPost - httpClient.execute - ClientProtocolException");
			} catch (IOException e) {
				Log.d(LOG_TAG_WEBSERVICE, "httpPost - IOException");
			} catch (JSONException e) {
				Log.d(LOG_TAG_WEBSERVICE,"httpPost - JSONException");
			}
			
			throw new ConnectException();
	}
	
	private static JSONObject httpGet(String url, String methodName) throws WebServiceException, ConnectException{
		return httpGet(url, methodName, null);
	}
	
	private static JSONObject httpGet(String url, String methodName, List<NameValuePair> params) 
			throws WebServiceException, ConnectException {
				
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url + methodName);
		
		try {
			
			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				httpGet = new HttpGet(url + methodName + "?" + paramString);
			}
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if (entity != null){
				String responseString = EntityUtils.toString(entity); 			  
				JSONObject jObject = new JSONObject(responseString);
				
				int status = jObject.getInt(RESPONSE_STATUS);
				if (status == ResponseStatus.Ok)
					return jObject;
				else
					throw new WebServiceException(jObject);
			}
			
		} catch (ClientProtocolException e) {
			Log.d(LOG_TAG_WEBSERVICE, "httpGet - httpClient.execute hat eine ClientProtocolException ausgel�st");
		} catch (IOException e) {
			Log.d(LOG_TAG_WEBSERVICE, "httpGet - IOException");
		} catch (JSONException e) {
			Log.d(LOG_TAG_WEBSERVICE,"httpGet - JSONException");
		}
		
		throw new ConnectException();
	}
	
	private static Node parseJsonToNode(JSONObject o) throws JSONException{
		Node node = null;		

		int id = o.getInt(RESPONSE_PARAM_NODE_ID);
		String roomName = o.getString(RESPONSE_PARAM_NODE_ROOMNAME);
		String displayName = o.getString(RESPONSE_PARAM_NODE_DISPLAYNAME);
		node = new Node(id,roomName,displayName);

		return node;
	}
	
	private static Floor parseJsonToFloor(JSONObject o) throws JSONException{
		Floor floor = null;
	
		int id = o.getInt(RESPONSE_PARAM_FLOOR_ID);
		int mapId = o.getInt(RESPONSE_PARAM_FLOOT_MAPID);
		String imageUrl = o.getString(RESPONSE_PARAM_FLOOR_IMAGE_URL);
		String name = o.getString(RESPONSE_PARAM_FLOOR_NAME);
		floor = new Floor(id, mapId, imageUrl, name);

		return floor;
	}
	
	private static PoI parseJsonToPoI(JSONObject o) throws JSONException{
		//Room 
		JSONObject room = o.getJSONObject(RESPONSE_PARAM_POI_ROOM);
		Node node = parseJsonToNode(room);
		
		//PoI
		JSONObject poi = o.getJSONObject(RESPONSE_PARAM_POI_POI);
		JSONObject type = poi.getJSONObject(RESPONSE_PARAM_POI_TYPE);
		int typeId = type.getInt(RESPONSE_PARAM_POI_TYPEID);
		String name = type.getString(RESPONSE_PARAM_POI_NAME);
		
		//Description
		String description = poi.getString(RESPONSE_PARAM_POI_DESCRIPTION);
				
		PoI mPoI = new PoI(name, description, typeId,node);

		return mPoI;
	}
	 
}