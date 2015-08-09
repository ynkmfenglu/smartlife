package com.smart.life.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.life.R;

public class TextAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private List<String> mListData;
	private String[] mArrayData;
	private int selectedPos = -1;
	private String selectedText = "";
	private int normalDrawbleId;
	private float textSize;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;
	private int[] photos;

	public TextAdapter(Context context, List<String> listData, int nId,int[] photos) {
		super(context, R.string.no_data, listData);
		mContext = context;
		mListData = listData;
		normalDrawbleId = nId;
        this.photos=photos;
		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	public TextAdapter(Context context, String[] arrayData, int nId) {
		super(context, R.string.no_data, arrayData);
		mContext = context;
		mArrayData = arrayData;
		normalDrawbleId = nId;
		init();
	}

	/**
	 * ����ѡ�е�position,��֪ͨ�б�ˢ��
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos);
			notifyDataSetChanged();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos];
			notifyDataSetChanged();
		}
	}

	/**
	 * ����ѡ�е�position,����֪ͨˢ��
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos);
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos];
		}
	}

	/**
	 * ��ȡѡ�е�position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * �����б������С
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView  textView;
		ImageView photo,skip;
		
		if (convertView == null) {
			convertView= (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.choose_item, parent, false);
		  }
			textView=(TextView) convertView.findViewById(R.id.tv_choose_title);
			photo=(ImageView) convertView.findViewById(R.id.iv_choose_photo);
			skip=(ImageView) convertView.findViewById(R.id.iv_choose_skip);
			
		convertView.setTag(position);
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position);
				
			}
		} else if (mArrayData != null) {
			if (position < mArrayData.length) {
				mString = mArrayData[position];
			}
		}
		if (mString.contains("����"))
			textView.setText("����");
		else
			textView.setText(mString);
		    textView.setTextSize(textSize);
		    if(photos!=null){
            photo.setImageResource(this.photos[position]);
            skip.setImageResource(R.drawable.ic_arrow);
		    }
		if (selectedText != null && selectedText.equals(mString)) {
			
		} else {
			textView.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));//����δѡ��״̬����ͼƬ
		}
	    convertView.setOnClickListener(onClickListener);
		
		return convertView;
	}
	


	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * ���¶���˵�ѡ����ӿ�
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

}
