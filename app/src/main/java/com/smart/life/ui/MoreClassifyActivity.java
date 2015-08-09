package com.smart.life.ui;

import com.smart.life.R;
import com.smart.life.adapter.MoreClassifyAdapter;
import com.smart.life.myview.MyGridView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MoreClassifyActivity extends Activity {
	private ImageView back;
	private MyGridView myGridView_food, myGridView_wineshop,
			myGridView_entertainment, myGridView_lifeservice,
			myGridView_beautifulwomen, myGridView_travel, myGridView_shop,
			myGridView_draw;
	private String[] strs_food = new String[] { "ȫ��", "���", "������", "����",
			"�պ�����", "�������", "�տ�����", "�����", "�����", "����", "����/������", "����/³��",
			"�ƹ��", "��ʳ", "�����", "�����ǲ�", "̨���", "����", "С�Կ��", "��ɫ��", "��/��/����",
			"���Ⱦư�", "�ɲ�", "�½���", "������ʳ" };
	private String[] strs_wineshop = new String[] { "ȫ��", "���þƵ�", "�����Ƶ�" };
	private String[] strs_entertainment = new String[] { "ȫ��", "��Ӱ", "KTV",
			"��Ȫ", "ϴԡ/����", "���ư�Ħ", "�˶�����", "��ѩ", "����/����", "��������", "���Ⱦư�",
			"�ݳ�����", "DIY�ֹ�", "����CS", "4D/5D��Ӱ", "��������" };
	private String[] strs_lifeservice = new String[] { "ĸӤ����", "��Ӱд��", "��챣��",
			"��������", "��Ƭ��ӡ", "��ѵ�γ�", "�ʻ�����", "���η���", "�侵", "�̳����￨", "��������" };
	private String[] strs_beautifulwomen = new String[] { "����", "����", "����SPA",
			"�٤/�赸" };
	private String[] strs_travel = new String[] { "ȫ��", "����/�ܱ���", "������Ʊ",
			"����/������" };
	private String[] strs_shop = new String[] { "ȫ��", "��װЬñ", "�Ҿ�����", "ʳƷ����",
			"���", "ĸӤ��Ʒ", "������ױ", "����ҵ�", "�ӱ�����", "������Ʒ", "ͼ������", "���ع���",
			"��������" };
	private String[] strs_draw = new String[] { "ȫ��", "���γ���", "������" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moreclassify_activity);
		init();

	}

	private void init() {
		myGridView_food = (MyGridView) findViewById(R.id.mygridview_food);
		myGridView_wineshop = (MyGridView) findViewById(R.id.mygridview_wineshop);
		myGridView_entertainment = (MyGridView) findViewById(R.id.mygridview_entertainment);
		myGridView_lifeservice = (MyGridView) findViewById(R.id.mygridview_lifeservice);
		myGridView_beautifulwomen = (MyGridView) findViewById(R.id.mygridview_beautifulwomen);
		myGridView_travel = (MyGridView) findViewById(R.id.mygridview_travel);
		myGridView_shop = (MyGridView) findViewById(R.id.mygridview_shop);
		myGridView_draw = (MyGridView) findViewById(R.id.mygridview_draw);
		back = (ImageView) findViewById(R.id.imageview_moreclassifyback);

		myGridView_food.setAdapter(new MoreClassifyAdapter(this, strs_food));
		myGridView_wineshop.setAdapter(new MoreClassifyAdapter(this,
				strs_wineshop));

		myGridView_entertainment.setAdapter(new MoreClassifyAdapter(this,
				strs_entertainment));
		myGridView_lifeservice.setAdapter(new MoreClassifyAdapter(this,
				strs_lifeservice));
		myGridView_beautifulwomen.setAdapter(new MoreClassifyAdapter(this,
				strs_beautifulwomen));
		myGridView_travel
				.setAdapter(new MoreClassifyAdapter(this, strs_travel));
		myGridView_shop.setAdapter(new MoreClassifyAdapter(this, strs_shop));
		myGridView_draw.setAdapter(new MoreClassifyAdapter(this, strs_draw));

		back.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

	}

}
