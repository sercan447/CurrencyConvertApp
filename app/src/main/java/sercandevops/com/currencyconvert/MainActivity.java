package sercandevops.com.currencyconvert;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


        TextView tvcad;
    TextView tvtry;
    TextView chfText;
    TextView jpyText;
    TextView usdTetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         tvtry = findViewById(R.id.tryText);
        tvcad = findViewById(R.id.cadText);
        chfText = findViewById(R.id.chfText);
        jpyText = findViewById(R.id.jpyText);
        usdTetxt = findViewById(R.id.usdTetxt);
    }

    public void GETREATES(View view) {

        DownloadTask obje = new DownloadTask();

        try
        {
            String url = "http://data.fixer.io/api/latest?access_key=fecc3ac2203c2a16cff8a5249a84d5fc&format=1";
            obje.execute(url);



        }catch (Exception e)
        {

        }
    }


    public class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while(data > 0)
                {
                    char character = (char)data;
                    result += character;

                    data = inputStreamReader.read();
                }



                return result;
            }catch (Exception e)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //System.out.println("alınan daata :"+s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");

                System.out.println("BASE : "+base);

                String rates = jsonObject.getString("rates");
                System.out.println("RATES : "+rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishLira = jsonObject1.getString("TRY");

                System.out.println("TRY : "+turkishLira);


                String usd = jsonObject1.getString("USD");
                usdTetxt.setText("USD : "+usd);

                String cad = jsonObject1.getString("CAD");
                tvcad.setText("CAD : "+cad);

                String tr = jsonObject1.getString("TRY");
                tvtry.setText("TRY : "+tr);

                String chf = jsonObject1.getString("CHF");
                chfText.setText("CHF : "+chf);

                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("JPY : "+jpy);

            }catch (Exception e)
            {

            }

        }
    }
}
