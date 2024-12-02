package com.example.evaluacion1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttActivity extends AppCompatActivity{
    private static String mqttHost = "tcp://servermqtt.cloud.shiftr.io:1883"; //IP SERVIDOR
    private static String IdUsuario = "AppAndroid";
    private static String Topico = "Mensaje";
    private static String User = "servermqtt";
    private static String Pass = "WMPjIhwJ2CHsVNBs";

    private TextView textView;
    private EditText editTextMessage;
    private Button botonEnvio;

    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);

        textView = findViewById(R.id.txtRecibido);
        editTextMessage = findViewById(R.id.txtMensaje);
        botonEnvio = findViewById(R.id.botonEnvioMensaje);

        try {
            mqttClient = new MqttClient(mqttHost, IdUsuario,null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(User);
            options.setPassword(Pass.toCharArray());

            mqttClient.connect(options);
            Toast.makeText(this,"Aplicación conectada",Toast.LENGTH_SHORT).show();

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Conexión perdida");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    runOnUiThread(() -> textView.setText(payload));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("MQTT", "Entrega completa");
                }
            });

        }catch (MqttException e){
            e.printStackTrace();
        }

        botonEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = editTextMessage.getText().toString();
                try {
                    if (mqttClient != null && mqttClient.isConnected()){
                        mqttClient.publish(Topico, mensaje.getBytes(), 0,false);
                        textView.append("\n - "+ mensaje);
                        Toast.makeText(MqttActivity.this,"Mensaje enviado", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MqttActivity.this,"Error, no hay conexión", Toast.LENGTH_SHORT).show();
                    }
                }catch (MqttException e){
                    e.printStackTrace();
                }
            }
        });

    }
}
