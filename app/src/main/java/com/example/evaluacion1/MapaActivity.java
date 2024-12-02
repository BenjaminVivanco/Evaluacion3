package com.example.evaluacion1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle; // Importa la clase Bundle para guardar el estado de la actividad.
import android.preference.PreferenceManager; // Importa PreferenceManager para gestionar las preferencias de la aplicación.
import android.view.View; // Importa la clase View para manejar vistas en la interfaz.
import android.widget.AdapterView; // Importa AdapterView para manejar eventos en vistas adaptables.
import android.widget.ArrayAdapter; // Importa ArrayAdapter para adaptar arrays a vistas como Spinners.
import android.widget.Spinner; // Importa Spinner, que es una lista desplegable en Android.
import org.osmdroid.config.Configuration; // Importa la clase Configuration para configurar el mapa.
import org.osmdroid.tileprovider.tilesource.TileSourceFactory; // Importa TileSourceFactory para definir los tipos de mapas disponibles.
import org.osmdroid.tileprovider.tilesource.XYTileSource; // Importa XYTileSource para crear un proveedor de azulejos específico para mapas personalizados.
import org.osmdroid.util.GeoPoint; // Importa GeoPoint, que representa coordenadas geográficas (latitud y longitud).
import org.osmdroid.views.MapView; // Importa MapView, que es el componente visual del mapa.
import org.osmdroid.views.overlay.Marker; // Importa Marker para agregar marcadores en el mapa.
import org.osmdroid.views.overlay.Polyline; // Importa Polyline para dibujar líneas en el mapa.


public class MapaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        //Configuración de mapa

        Configuration.getInstance().load(getApplicationContext(),PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        //Coordenadas de sedes

        double sede1Latitud = -33.448279;
        double sede1Longitud = -70.6706621;

        double sede2Latitud = -33.4227325;
        double sede2Longitud = -70.5817971;

        //Puntos de las sedes

        GeoPoint sede1Point = new GeoPoint(sede1Latitud,sede1Longitud);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(sede1Point);

        GeoPoint sede2Point = new GeoPoint(sede2Latitud,sede2Longitud);

        //Marcador de sedes en el mapa

        Marker marcadorSede1 = new Marker(mapView);
        marcadorSede1.setPosition(sede1Point);
        marcadorSede1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorSede1.setTitle("Crustaceo cascarudo");
        marcadorSede1.setSnippet("Sede 1");

        mapView.getOverlays().add(marcadorSede1);

        Marker marcadorSede2 = new Marker(mapView);
        marcadorSede2.setPosition(sede2Point);
        marcadorSede2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorSede2.setTitle("Crustaceo cascarudo");
        marcadorSede2.setSnippet("Sede 2");

        mapView.getOverlays().add(marcadorSede2);


        //Linea entre sedes
        Polyline linea = new Polyline();
        linea.addPoint(sede1Point);
        linea.addPoint(sede2Point);
        linea.setColor(0xFF0000FF);
        linea.setWidth(5);
        mapView.getOverlayManager().add(linea);


        //Spinner para cambiar el tipo de mapa

        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = {"Mapa normal", "Mapa de transporte", "Mapa topografico"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter);

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
             switch (position){
                 case 0:
                     mapView.setTileSource(TileSourceFactory.MAPNIK);
                     break;
                 case 1:
                     mapView.setTileSource(new XYTileSource(
                             "PublicTransport",
                             0,18,256,".png", new String[]{
                                     "https://tile.memomaps.de/tilegen/"}));
                     break;
                 case 2:
                     mapView.setTileSource(new XYTileSource(
                             "USGS_Satellite",
                             0,18,256,".png",new String[]{
                                     "https://a.tile.opentopomap.org/",
                                     "https://b.tile.opentopomap.org/",
                                     "https://c.tile.opentopomap.org/"}));
                     break;
             }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}
