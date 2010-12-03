package dreamink.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedsView extends Activity {
    
    ListView list;
    AdapterFeeds adapter;
    private ArrayList<String> ti;
    private ArrayList<String> fe;
    private ArrayList<String> im;
    private ArrayList<String> ar;
    
    private String [] Titulos;
    private String [] Fechas;
    private String [] Imagenes;
    private String [] Articulos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedsview);        
        
        Bundle b = getIntent().getExtras();
		ti = b.getStringArrayList("Titulos");
		fe = b.getStringArrayList("Fechas");
		im = b.getStringArrayList("Imagenes");
		ar = b.getStringArrayList("Articulos");		
		
		Titulos = new String[ti.size()];
		Titulos = ti.toArray(Titulos);
		
	    Fechas = new String[fe.size()];
	    Fechas = fe.toArray(Fechas);
	    
	    Imagenes = new String[im.size()];
	    Imagenes = im.toArray(Imagenes);
	    
	    Articulos = new String[ar.size()];
		Articulos = ar.toArray(Articulos);
		
        list=(ListView)findViewById(R.id.list);
        //adapter=new AdapterFeeds(this, mStrings, mTitulos);
        adapter=new AdapterFeeds(this, Imagenes, Titulos, Fechas);
        list.setAdapter(adapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	Intent intent = new Intent(FeedsView.this, Articulo.class);
		    	intent.putExtra("Titulo", Titulos[position]);
		    	intent.putExtra("Fecha", Fechas[position]);
		    	intent.putExtra("Imagen", Imagenes[position]);
		    	intent.putExtra("Articulo", Articulos[position]);
     	    	startActivity(intent);
		    }
		  });
        
    }
    
    @Override
    public void onDestroy()
    {
        adapter.imageLoader.stopThread();
        list.setAdapter(null);
        super.onDestroy();
    }
    
    public OnClickListener listener=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
            adapter.imageLoader.clearCache();
            adapter.notifyDataSetChanged();
        }
    };    
}