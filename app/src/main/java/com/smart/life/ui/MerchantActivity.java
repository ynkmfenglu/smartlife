package com.smart.life.ui;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.smart.life.R;
import com.smart.life.Parser.MerchantParser;
import com.smart.life.adapter.MerchantAllAdapter;
import com.smart.life.adapter.MerchantSaleAdapter;
import com.smart.life.app.AppContext;
import com.smart.life.common.RequestConstant;
import com.smart.life.common.UrlConstant;
import com.smart.life.domain.Seller;
import com.smart.life.interfaces.Netcallback;
import com.smart.life.myview.ExpandTabView;
import com.smart.life.myview.ViewLeft;
import com.smart.life.myview.ViewMiddle;
import com.smart.life.myview.ViewRight;
import com.smart.life.utils.HttpRequests;
import com.smart.life.utils.Logger;
import com.smart.life.utils.ScrollLayout;
import com.smart.life.utils.ScrollLayout.OnViewChangeListener;
import com.smart.life.utils.XListView;
import com.smart.life.utils.XListView.IXListViewListener;

@SuppressLint("HandlerLeak")
public class MerchantActivity extends BaseActivity implements
		OnViewChangeListener, OnClickListener {
	private RadioButton button_allmerchant, button_salemerchant;
	private ImageButton imagebutton_map;
	private ScrollLayout scrollLayout;
	private int viewCount;
	private int mCurSel;
	private RadioButton[] buttons;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	public static ArrayList<Seller> allList = new ArrayList<Seller>(),
			saleList = new ArrayList<Seller>();
	private MerchantSaleAdapter merchantSaleAdapter;
	private MerchantAllAdapter merchantAllAdapter;
	public static XListView  aListView,sListView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.merchant_activity);
		super.onCreate(savedInstanceState);
	}

	@Override
	void init() {
		initMerchantHeader();
		initMerchantAllListView();
		initMerchantSaleListView();
		initPageScroll();
		initClassifyView();
	}

	/*
	 * ��ʼ��������
	 */
	private void initClassifyView() {
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		viewLeft = new ViewLeft(this);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);

		mViewArray.add(viewMiddle);
		mViewArray.add(viewLeft);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("3ǧ��");
		mTextArray.add("ȫ������");
		mTextArray.add("�������");
		int[] photo = { R.drawable.ic_category_all, R.drawable.ic_addr,
				R.drawable.ic_order };
		expandTabView.setValue(mTextArray, mViewArray, photo);
		expandTabView.setTitle(viewMiddle.getShowText(), 0);
		expandTabView.setTitle(viewLeft.getShowText(), 1);
		expandTabView.setTitle(viewRight.getShowText(), 2);
		expandTabView.setPhoto(photo[0], 0);

		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText);
			}
		});
		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			public void getValue(String showText) {
				onRefresh(viewMiddle, showText);

			}

			public void getPhoto(int showPhoto) {
				onRefreshPhoto(viewMiddle, showPhoto);
			}

		});

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText);
			}
		});
	}

	/*
	 * ˢ�·���������ͼƬ
	 */
	private void onRefreshPhoto(View view, int showPhoto) {
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0
				&& !expandTabView.getPhoto(position).equals(showPhoto)) {
			expandTabView.setPhoto(showPhoto, position);
		}
	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(MerchantActivity.this, showText, Toast.LENGTH_SHORT)
				.show();
	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
	}

	// ��ʼ��textView,button�ؼ�
	private void initMerchantHeader() {
		imagebutton_map = (ImageButton) findViewById(R.id.imagebutton_map);
		button_allmerchant = (RadioButton) findViewById(R.id.button_allmerchant);
		button_salemerchant = (RadioButton) findViewById(R.id.button_salemerchant);
		imagebutton_map.setOnClickListener(this);
	}

	private void initMerchantAllListView() {
		aListView = (XListView) findViewById(R.id.listview_merchantall);
		RequestConstant nrc = new RequestConstant();
		RequestConstant.requestUrl = UrlConstant.SELLERURL;
		RequestConstant.context = this;
		nrc.setType(HttpRequests.HttpRequestType.GET);
		getServer(new Netcallback() {

			public void preccess(Object res, boolean flag) {

				if (res != null) {
					try {
						MerchantParser merchantParer = new MerchantParser();
						ArrayList<Seller> merchant = merchantParer.getMerchant(res);

						if (merchant != null && !merchant.isEmpty()) {
							allList.addAll(merchant);
						}
						AppContext  appContext=(AppContext)getApplicationContext();
						appContext.setSeller(allList);
						merchantAllAdapter = new MerchantAllAdapter(MerchantActivity.this, allList);
						aListView.setAdapter(merchantAllAdapter);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, nrc);
		
		aListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����
				Logger.i("�����ListView");
			}
		});

		aListView.setXListViewListener(new IXListViewListener() {
			public void onRefresh() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				onLoad(aListView);

			}

			public void onLoadMore() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				onLoad(aListView);
			}
		});
	}

	private void onLoad(XListView listView) {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("�ո�");
	}

	private void initMerchantSaleListView() {
		sListView = (XListView) findViewById(R.id.listview_merchantsale);
		sListView.setPullLoadEnable(true);
		RequestConstant nrc = new RequestConstant();
		RequestConstant.requestUrl = UrlConstant.SELLERURL;
		RequestConstant.context = this;
		nrc.setType(HttpRequests.HttpRequestType.GET);
		getServer(new Netcallback() {

			public void preccess(Object res, boolean flag) {

				if (res != null) {
					try {
						MerchantParser merchantParer = new MerchantParser();
						ArrayList<Seller> merchant = merchantParer.getMerchant(res);

						if (merchant != null && !merchant.isEmpty()) {
							for (int i = 0; i < merchant.size(); i++) {
								Seller seller = new Seller();
								seller =merchant.get(i);
								if (seller.getSeller_isSale() == 1) {
									saleList.add(seller);
									}
		                          }
							merchantSaleAdapter = new MerchantSaleAdapter(MerchantActivity.this, saleList);
							sListView.setAdapter(merchantSaleAdapter);
		                        }	
						} catch (Exception e) {
			                  e.printStackTrace();
		                 }
	                  }
                  }
        }, nrc);
		
		/*
		 * itmes ���ü���
		 */
		sListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����
				Logger.i("�����ListView");
			}
		});

		sListView.setXListViewListener(new IXListViewListener() {

			public void onRefresh() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				onLoad(sListView);

			}

			public void onLoadMore() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				onLoad(sListView);

			}
		});

	}

	/**
	 * ��ʼ��ˮƽ������ҳ RadioButton
	 */
	private void initPageScroll() {
		scrollLayout = (ScrollLayout) findViewById(R.id.scrollView);
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.merchant_header);

		viewCount = scrollLayout.getChildCount();
		Logger.i("MerActivity:   viewCount" + viewCount);
		buttons = new RadioButton[viewCount];

		for (int i = 0; i < viewCount; i++) {
			buttons[i] = (RadioButton) relativeLayout.getChildAt(i * 2);
			buttons[i].setTag(i);
			buttons[i].setChecked(false);
			buttons[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int pos = (Integer) (v.getTag());
					switch (pos) {
					case 0:
						// ˢ��ȫ���̼Һ��Ż��̼ҵ�radioButton�¶˵�ͼƬ
						button_allmerchant
								.setCompoundDrawablesWithIntrinsicBounds(0, 0,
										0, R.drawable.bottom_divider);
						button_salemerchant
								.setCompoundDrawablesWithIntrinsicBounds(0, 0,
										0, R.color.green);
						break;
					case 1:
						button_salemerchant
								.setCompoundDrawablesWithIntrinsicBounds(0, 0,
										0, R.drawable.bottom_divider);
						button_allmerchant
								.setCompoundDrawablesWithIntrinsicBounds(0, 0,
										0, R.color.green);
						break;
					}
					scrollLayout.snapToScreen(pos);
				}
			});
		}

		// ���õ�һ��ʾ��
		mCurSel = 0;
		buttons[mCurSel].setChecked(true);
		scrollLayout
				.SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {
					public void OnViewChange(int viewIndex) {
						// �л��б���ͼ-����б�����Ϊ�գ���������
						switch (viewIndex) {
						case 0:
							button_allmerchant
									.setCompoundDrawablesWithIntrinsicBounds(0,
											0, 0, R.drawable.bottom_divider);
							button_salemerchant
									.setCompoundDrawablesWithIntrinsicBounds(0,
											0, 0, R.color.green);
							break;
						case 1:
							button_salemerchant
									.setCompoundDrawablesWithIntrinsicBounds(0,
											0, 0, R.drawable.bottom_divider);
							button_allmerchant
									.setCompoundDrawablesWithIntrinsicBounds(0,
											0, 0, R.color.green);
							break;
						}
					}
				});
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imagebutton_map:
			startActivity(new Intent(this, MyMapActivity.class));

		case R.id.rl_categories:
			// ������ɸѡ

			Logger.i("�����ListView");
			break;
		case R.id.rl_distance:
			// ����ɸѡ
			break;
		case R.id.rl_rank:
			// ����ɸѡ
			break;
		default:
			break;
		}
	}

	

	public void OnViewChange(int view) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
