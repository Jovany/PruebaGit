package dreamink.android;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Warp extends ListActivity{
    
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private String nTitulo, urlFeed;
    private FeedBean feed;
    private String isError;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);     
        
        feed = new FeedBean();
        isError = "no";
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
		    	switch (position){
		    		case 0: urlFeed = "http://www.warp.com.mx/noticias/feed";
		    				break;
		    		case 1: urlFeed = "http://www.warp.com.mx/destacados/feed";
    						break;
		    		case 2: urlFeed = "http://www.warp.com.mx/gigs/feed";
    						break;
		    		case 3: urlFeed = "http://www.warp.com.mx/discos/feed";
    						break;
		    		case 4: urlFeed = "http://www.warp.com.mx/encore/feed";
    						break;
		    	}
		    	
		    	feed = getFeed();
		    	if(!isError.equals("si")){
		    		Intent intent = new Intent(Warp.this, FeedsView.class);
			    	intent.putExtra("Titulos", feed.getFeedTitulo());
			    	intent.putExtra("Fechas", feed.getFeedFecha());
			    	intent.putExtra("Imagenes", feed.getFeedImagen());
			    	intent.putExtra("Articulos", feed.getFeedArticulo());
	     	    	startActivity(intent);
		    	}
		    	else{
		    		Dialog dialog = new Dialog(Warp.this);
					dialog.setContentView(R.layout.dialogerror);
					dialog.setTitle("Connection Error");
					dialog.setCancelable(true);
					TextView txtError = (TextView) dialog.findViewById(R.id.txtDialog);
					txtError.setText("No se puede conectar al servidor\n Intente mas tarde.");
					Button btError = (Button) dialog.findViewById(R.id.btDialog);
					btError.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {	
							/*Intent intent = new Intent(Warp.this, Warp.class);
			     	    	startActivity(intent);*/
							Intent intent = getIntent();
							finish();
							startActivity(intent);
						}
					});
					dialog.show();
		    	}
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
        }    
    
    public FeedBean getFeed() {
    	FeedBean fdb = new FeedBean();
    	try {
			URL url = new URL(urlFeed);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				  InputStream is = conn.getInputStream();
				  //--->Test
				  /*BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
				  StringBuilder sb = new StringBuilder();
				  String line = null;
				  while((line = br.readLine())!= null){
					  sb.append(line + "\n");
				  }
				  System.out.println("Feed-->"+sb.toString());*/
				  //<-------fin Test
				  //DocumentBuilderFactory, DocumentBuilder are used for
				  //xml parsing
				  DocumentBuilderFactory dbf = DocumentBuilderFactory
				          .newInstance();
				  DocumentBuilder db = dbf.newDocumentBuilder();

				  //using db (Document Builder) parse xml data and assign
				  //it to Element
				  Document document = db.parse(is);
				  Element element = document.getDocumentElement();

				  //take rss nodes to NodeList
				  NodeList nodeList = element.getElementsByTagName("entry");
				  
				  //-------------->
				  
				  
				  ArrayList<String> tmpTi = new ArrayList<String>();
				  ArrayList<String> tmpFe = new ArrayList<String>();
				  ArrayList<String> tmpIm = new ArrayList<String>();
				  ArrayList<String> tmpAr = new ArrayList<String>();
				  
				  String uur = "http://www.warp.com.mx";
				  
				  for (int i = 0; i < nodeList.getLength(); i++) {
					  Element entry = (Element) nodeList.item(i);
					  Element ti = (Element) entry.getElementsByTagName("title").item(0);
					  Element fe = (Element) entry.getElementsByTagName("updated").item(0);
					  Element cont = (Element) entry.getElementsByTagName("content").item(0);
					  
					  //-->Obtiene catego					  
					  /*NodeList nodeListCategory = entry.getElementsByTagName("category");					  
					  NamedNodeMap atributos = nodeListCategory.item(0).getAttributes();
					  Node unAtributo = atributos.getNamedItem( "term" );
					  String valorAtributo = unAtributo.getNodeValue();
					  System.out.println("Categoria: "+valorAtributo);*/
					  //-->Fin obtiene catego
					  
					  String _title = ti.getFirstChild().getNodeValue();
					  String _fecha = fe.getFirstChild().getNodeValue();
					  String _cont = cont.getFirstChild().getNodeValue();
					  
					  int inicio = _cont.indexOf('<');
					  int fin = _cont.indexOf('>');
					  int end = _cont.length();
					  
					  String _imagen = uur + _cont.substring((inicio+10), (fin-2));
					  String _articulo = _cont.substring((fin+1), (end-1));
					  
					  System.out.println("Titulo--> "+_title);
					  
					  tmpTi.add(_title);
					  tmpFe.add(_fecha);
					  tmpIm.add(_imagen);
					  tmpAr.add(_articulo);					  
				  }
				  
				  fdb.setFeedTitulo(tmpTi);
				  fdb.setFeedFecha(tmpFe);
				  fdb.setFeedImagen(tmpIm);
				  fdb.setFeedArticulo(tmpAr);
				}
		} catch (Exception e) {
			System.out.println("Error Warp.java->en getFeed:(error) "+e);
			System.out.println("Error Warp.java->en getFeed:(mensaje) "+e.getMessage());

			isError = "si";			
		}
		return fdb;
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