package com.smart.life.ui;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.smart.life.R;
import com.smart.life.adapter.GroupBuyLikeAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.domain.Good;
import com.smart.life.myview.MyListView;

public class TodayNewOrderActivity extends Activity implements OnClickListener,OnItemClickListener{

    private MyListView listviewlistView_todayNewOrder;
    private AppContext appContext;
    //�����µ�����Դ
	private ArrayList<Good> newGoods;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_new_order);
        
        appContext = (AppContext) getApplicationContext();
        this.initView();
        
        List<Good> goods=appContext.getGoods();
        //�����µ�����Դ
        newGoods = new ArrayList<Good>();
        for(int i=0;i<goods.size();i++){
        	int flag=goods.get(i).getGoods_isLatest();
        	if(flag==1){
        		newGoods.add(goods.get(i));
        	}
        }
        GroupBuyLikeAdapter adapter=new GroupBuyLikeAdapter(this, newGoods);
        listviewlistView_todayNewOrder.setAdapter(adapter);
    }

	private void initView() {
		listviewlistView_todayNewOrder =(MyListView) findViewById(R.id.listView_todayNewOrder);
		listviewlistView_todayNewOrder.setOnItemClickListener(this);
		//����
		RelativeLayout relativeLayout_groupbuydetails_left = (RelativeLayout) findViewById(R.id.relativeLayout_groupbuydetails_left);
		relativeLayout_groupbuydetails_left.setOnClickListener(this);
		//��ͼ
		ImageButton imagebuttton_map=(ImageButton) findViewById(R.id.imagebutton_map_order);
		imagebuttton_map.setOnClickListener(this);
		//����
		ImageButton imagebutton_search=(ImageButton) findViewById(R.id.imagebutton_search_order);
		imagebutton_search.setOnClickListener(this);
		//��ʾ��λ��
		LinearLayout linearlayout_todayNewOrder=(LinearLayout) findViewById(R.id.linearlayout_todayNewOrder);
		linearlayout_todayNewOrder.setVisibility(View.GONE);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.relativeLayout_groupbuydetails_left://����
			finish();
			break;
		case R.id.imagebutton_map_order://��ͼ
			startActivity(new Intent(this,MyMapActivity.class));
			break;
		case R.id.imagebutton_search_order://����
			startActivity(new Intent(this,SeekActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(this,GroupBuyDetailsActivity.class);
		intent.setAction("TodayNewOrderActivity");
		int goods_id= newGoods.get(position).getGoods_id();
		intent.putExtra("goods_id", goods_id);
		startActivity(intent);		
	}

}
