package dreamink.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Articulo extends Activity {
	private TextView titulo, fecha, articulo;
	private ImageView imagen;
	private String Titulo, Fecha, Imagen, Articulo;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulo);

        Bundle b = getIntent().getExtras();
		Titulo = b.getString("Titulo");
		Fecha = b.getString("Fecha");
		Imagen = b.getString("Imagen");
		Articulo = b.getString("Articulo");
		
		ImageLoader imageLoader=new ImageLoader(this.getApplicationContext());
        
        titulo = (TextView) findViewById(R.id.txtTituloArticulo);
        fecha = (TextView) findViewById(R.id.txtFechaArticulo);
        articulo = (TextView) findViewById(R.id.txtContenidoArticulo);

        imagen = (ImageView) findViewById(R.id.imgArticulo);
        
        titulo.setText(Titulo);
        fecha.setText(Fecha);
        articulo.setText(Articulo);
        
        imagen.setTag(Imagen);
        imageLoader.DisplayImage(Imagen, this, imagen);

    }
}