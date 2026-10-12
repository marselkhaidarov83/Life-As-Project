package com.MH83.LifeAsProject.data.Custom;

import android.nfc.Tag;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CWebData
{
    private static final String urlLoadInflation = "https://уровень-инфляции.рф/таблицы-инфляции";
    private static final String urlLoadExchangeRate = "https://cbr.ru/currency_base/daily?UniDbQuery.Posted=True&UniDbQuery.To";

    private static StringBuilder LoadFromWeb(String urlString)
    {
        try
        {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // установка метода получения данных -GET
            connection.setReadTimeout(10000); // установка таймаута перед выполнением - 10 000 миллисекунд
            connection.connect(); // подключаемся к ресурсу

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);

            return stringBuilder;
        }
        catch (Exception e)
        {
            Log.d(Tag.class.toString(), e.getMessage());
            return null;
        }
    }

    public static void LoadInflation()
    {
        Runnable task = () ->
        {
            try
            {
                Document doc = Jsoup.connect(urlLoadInflation).get();
                int i = 0;
            }
            catch (Exception e)
            {
                Log.d(Tag.class.toString(), e.getMessage());
            }
//            StringBuilder stringBuilder = LoadFromWeb(urlLoadInflation);
//            if (stringBuilder != null)
//            {
//
//            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public static void LoadExchangeRate()
    {
//        Runnable task = () ->
//        {
//            StringBuilder stringBuilder = LoadFromWeb(urlLoadExchangeRate);
//            if (stringBuilder != null)
//            {
//                try {
////                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
////                    DocumentBuilder builder = factory.newDocumentBuilder();
////                    Document document = builder.parse(urlLoadExchangeRate);
//
//                }
//                catch (Exception e)
//                {
//                    Log.d(Tag.class.toString(), e.getMessage());
//                }
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.start();

    }
}
