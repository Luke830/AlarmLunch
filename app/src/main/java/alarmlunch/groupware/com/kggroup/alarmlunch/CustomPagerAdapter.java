package alarmlunch.groupware.com.kggroup.alarmlunch;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by itsm02 on 2016. 10. 14..
 */

public class CustomPagerAdapter<T> extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    List<T> list;
    View.OnClickListener onClickListener;

    public CustomPagerAdapter(Context context, LayoutInflater inflater, List<T> list, View.OnClickListener onClickListener) {

        this.context = context;
        this.inflater = inflater;
        this.list = list;
        this.onClickListener = onClickListener;

    }

    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;


        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
        view = inflater.inflate(R.layout.viewpager_childview, null);

        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setOnClickListener(onClickListener);

        textView.setText((String) list.get(position));
        textView.setTag((String) list.get(position));

        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub

        //ViewPager에서 보이지 않는 View는 제거
        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
        container.removeView((View) object);

    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v == obj;
    }
}




