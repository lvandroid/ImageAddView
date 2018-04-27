package com.bsty.customerview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Uri> uriList = new ArrayList<>();

        Uri u1 = Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3415789.jpg");
        Uri u2 = Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3551735.jpg");
        uriList.add(u1);
        uriList.add(u2);

        List<BottomMultiPicItemVo> voList = new ArrayList<>();
        voList.add(new BottomMultiPicItemVo(0, u1));
        voList.add(new BottomMultiPicItemVo(2, u2));

        LinearLayout content = findViewById(R.id.ll_content);

        HeadMultiPicView headView = new HeadMultiPicView(this);
        content.addView(headView);

        headView.setData(uriList);

        LinearLayout bottom = findViewById(R.id.ll_bottom);
        BottomMultiPicView bottomView = new BottomMultiPicView(this);

        bottom.addView(bottomView);
        bottomView.setData(voList);
    }
}
