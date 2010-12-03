package dreamink.android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterFeeds extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private String[] titulo;
    private String[] fecha;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public AdapterFeeds(Activity a, String[] d, String[] t, String[] f) {
        activity = a;
        data=d;
        titulo=t;
        fecha=f;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        public TextView text;
        public TextView txtFecha;
        public ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.item, null);
            holder=new ViewHolder();
            holder.text=(TextView)vi.findViewById(R.id.text);
            holder.txtFecha=(TextView)vi.findViewById(R.id.txtFecha);
            holder.image=(ImageView)vi.findViewById(R.id.image);
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();
        
        holder.txtFecha.setText(fecha[position]);
        holder.text.setText(titulo[position]);
        holder.image.setTag(data[position]);
        imageLoader.DisplayImage(data[position], activity, holder.image);
        return vi;
    }
}
