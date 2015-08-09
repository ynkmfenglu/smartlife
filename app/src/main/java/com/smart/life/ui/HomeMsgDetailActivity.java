package com.smart.life.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.life.R;
import com.smart.life.domain.HomeMsg;
import com.smart.life.utils.SplitNetImagePath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fenglu on 2015/8/4.
 */
public class HomeMsgDetailActivity extends Activity implements View.OnClickListener {
    private TextView title_homemsg,content_homemsg,pic_count,msgdate;
    private ImageView back_button,image1,image2,image3,pic_imageskip;
    private HomeMsg datas;
    private String[]  strings;
    private String  netImagePath;
    private Bitmap[] bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.homemsg_detail);
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        // TODO Auto-generated method stub
        title_homemsg = (TextView) findViewById(R.id.title_homemsg);
        content_homemsg=(TextView) findViewById(R.id.content_homemsg);
        pic_count=(TextView) findViewById(R.id.pic_count);
        msgdate=(TextView) findViewById(R.id.msgdate);

        back_button = (ImageView) findViewById(R.id.back_button);
        image1=(ImageView)findViewById(R.id.msg_image1);
        image2=(ImageView)findViewById(R.id.msg_image2);
        image3=(ImageView)findViewById(R.id.msg_image3);
        pic_imageskip = (ImageView)findViewById(R.id.pic_imageskip);

        //从上一个页面接收数据
        Intent intent = getIntent();
        datas = new HomeMsg();
        int allPosition = intent.getIntExtra("allPosition", 0);
        datas = HomeActivity.current_msgs.get(allPosition);

        title_homemsg.setText(datas.getTitle());
        content_homemsg.setText(datas.getContent());
        msgdate.setText("发布时间：" + datas.getDate());
        strings = SplitNetImagePath.splitNetImagePath(datas.getPic());
        Thread  thread=new Thread(runnable);
        thread.start();

        pic_count.setText(strings.length + "");
        back_button.setOnClickListener(this);
        pic_imageskip.setOnClickListener(this);
    }

    Runnable runnable = new Runnable() {
        public void run() {
            // 把网络地址转换为BitMap
            URL picUrl;
            bitmaps=new Bitmap[strings.length];
            try {
                for(int i=0;i<strings.length;i++){
                    netImagePath=strings[i];
                    picUrl = new URL(netImagePath);
                    Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
                    bitmaps[i]=pngBM;
                }
                Message msg = new Message();
                msg.obj =bitmaps;
                handler.sendMessage(msg);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Bitmap[] pngBM = (Bitmap[]) msg.obj;
            image1.setImageBitmap(pngBM[0]);
            //image2.setImageBitmap(pngBM[1]);
            //image3.setImageBitmap(pngBM[2]);
        }
    };

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.pic_imageskip:
                //startActivity(new Intent(this,.class));
                break;
            default:
                break;
        }
    }
}
