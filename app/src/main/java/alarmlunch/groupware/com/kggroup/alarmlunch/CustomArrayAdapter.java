package alarmlunch.groupware.com.kggroup.alarmlunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by itsm02 on 2016. 10. 14..
 */

public class CustomArrayAdapter<T> extends ArrayAdapter<T> {

    Context context;
    int resourceId;


    public CustomArrayAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(resourceId, parent, false);
        }

        ModePoI poi = (ModePoI) getItem(position);

        ((TextView) convertView.findViewById(R.id.text_title)).setText(poi.title);
        ((TextView) convertView.findViewById(R.id.text_addr)).setText(poi.addr);
        ((TextView) convertView.findViewById(R.id.text_description)).setText(poi.description);

        return convertView;


    }
}
