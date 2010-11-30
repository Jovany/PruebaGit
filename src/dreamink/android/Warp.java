package dreamink.android;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Warp extends ListActivity{
    
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private String nTitulo;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        m_orders = new ArrayList<Order>();
        
        this.m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
        setListAdapter(this.m_adapter);
        
        getOrders();
        algo();
        
        ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	/*Toast.makeText(getApplicationContext(), "Hola",
				          Toast.LENGTH_SHORT).show();*/
		    	Intent intent = new Intent(Warp.this, FeedsView.class);
     	    	startActivity(intent);
		    }
		  });
    }
    
    public void algo() {
        if(m_orders != null && m_orders.size() > 0){
            for(int i=0;i<m_orders.size();i++)
            m_adapter.add(m_orders.get(i));
        }
    }

    private void getOrders(){
        	  	m_orders = new ArrayList<Order>();
        	  	
        	  	Order o1 = new Order();
        	  	Order o2 = new Order();
        	  	Order o3 = new Order();
        	  	Order o4 = new Order();
        	  	Order o5 = new Order();
        	  	
  				o1.setOrderName("Noticias");
  				o2.setOrderName("Destacados");
  				o3.setOrderName("Gigs");
  				o4.setOrderName("Discos");
  				o5.setOrderName("Encore");
  				
  				m_orders.add(o1);
  				m_orders.add(o2);
  				m_orders.add(o3);
  				m_orders.add(o4);
  				m_orders.add(o5);
        	  
      			/*for(int i=0;i<20;i++){
      				Order o = new Order();
      				o.setOrderName("Cine"+i);
      				o.setOrderStatus("Pendiente"+i);
      				m_orders.add(o);
      			}*/
        }
    
    private class OrderAdapter extends ArrayAdapter<Order> {

        private ArrayList<Order> items;

        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
                super(context, textViewResourceId, items);
                this.items = items;           
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }
                Order o = items.get(position);
                if (o != null) {
                        TextView tt = (TextView) v.findViewById(R.id.toptext);
                        if (tt != null) {
                        	  nTitulo = o.getOrderName();
                        	  tt.setText(nTitulo);
                        	  if(nTitulo.equals("Noticias")){
                        		  tt.setBackgroundResource(R.color.Noticias);
                        	  }
                        	  else if(nTitulo.equals("Destacados")){
                        		  tt.setBackgroundResource(R.color.Destacados);
                        	  }
                        	  else if(nTitulo.equals("Gigs")){
                        		  tt.setBackgroundResource(R.color.Gigs);
                        	  }
                        	  else if(nTitulo.equals("Discos")){
                        		  tt.setBackgroundResource(R.color.Discos);
                        	  }
                        	  else if(nTitulo.equals("Encore")){
                        		  tt.setBackgroundResource(R.color.Encore);
                        	  }
                         }
                }
                return v;
        }
}
}