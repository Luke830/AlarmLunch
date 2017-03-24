package alarmlunch.groupware.com.kggroup.alarmlunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShowActivity extends Activity implements View.OnClickListener {

    ViewPager pager;
    CustomPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String strs[] = {

                "김밥	",
                "돈까스",
                "김치찌개",
                "된장찌개",
                "뼈해장국",
                "감자탕",
                "라멘	",
                "돈부리",
                "우동	",
                "라면	",
                "볶음밥",
                "짬뽕	",
                "짜장면",
                "육개장",
                "파스타",
                "피자	",
                "떡볶이",
                "백반	",
                "불백	",
                "닭볶음탕",
                "햄버거",
                "초밥	",
                "칼국수",
                "만두	",
                "쌀국수",
                "샐러드",
                "갈비탕",
                "곰탕	",
                "쭈꾸미",
                "오징어볶음",
                "죽",
                "콩나물국밥",
                "소고기국밥",
                "돼지국밥",
                "카레	",
                "팟타이",
                "생선구이",
                "부대찌개",
                "해장국",
                "제육덮밥",
                "김치찜"

        };

        List<String> list = random(strs);

        for (String string : list) {
            Log.e("DEBUG", "" + string);
        }

        pager = (ViewPager) findViewById(R.id.pager);

        //ViewPager에 설정할 Adapter 객체 생성
        //ListView에서 사용하는 Adapter와 같은 역할.
        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름
        //PagerAdapter를 상속받은 CustomAdapter 객체 생성
        //CustomAdapter에게 LayoutInflater 객체 전달
        adapter = new CustomPagerAdapter<>(this, getLayoutInflater(), list, this);

        //ViewPager에 Adapter 설정
        pager.setAdapter(adapter);


    }

    public List<String> random(String[] strs) {

//        String strs[] = {"a", "b", "c", "d", "e", "f", "g"};
        List<String> list = Arrays.asList(strs);
        Collections.shuffle(list);

        return list;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textView:

                String msg = (String) v.getTag();
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("searchName", msg);
                startActivity(intent);

                break;
        }

    }
}
