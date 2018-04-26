package com.bsty.customerview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Uri> uriList = new ArrayList<>();
        uriList.add(Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3415789.jpg"));
        uriList.add(Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3551735.jpg"));

        LinearLayout content = findViewById(R.id.ll_content);

        HeaderImageListView headView = new HeaderImageListView(this);
        content.addView(headView);

        headView.setData(uriList);

        LinearLayout bottom = findViewById(R.id.ll_bottom);
        BottomitemView bottomView = new BottomitemView(this);

        bottom.addView(bottomView);
        bottomView.setData(uriList);
    }
}
