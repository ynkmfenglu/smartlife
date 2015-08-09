package com.smart.life.Parser;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.smart.life.domain.HomeMsg;

public class HomeMsgParser {
	public ArrayList<HomeMsg> getHomemsgs(Object res){
		ArrayList<HomeMsg> homemsg = null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("items");
			if (array != null && !array.equals("[]")) {
				homemsg = new ArrayList<HomeMsg>();
				for(int i=0;i<array.length();i++){
					HomeMsg msg = new HomeMsg();
					JSONObject object2 = array.optJSONObject(i);

					msg.setTitle(object2.optString("title"));
					msg.setDate(object2.optString("date"));
					msg.setContent(object2.optString("content"));
					msg.setMsgtype(object2.optInt("type"));
					msg.setPic(object2.optString("msgpic"));
					msg.setPiccacheidx(-1);
					homemsg.add(msg);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return homemsg;
	}
}
