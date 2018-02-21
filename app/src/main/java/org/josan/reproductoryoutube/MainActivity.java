package org.josan.reproductoryoutube;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import org.josan.reproductoryoutube.model.Video;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDLg8VYPmXnSMh4PCPQXxyUbhbYhPktIo4";
    private static final String YOUTUBE_PLAYLIST_ID = "PLR7JWZAjVdyt484P_iMNVfjWuKceX5BFp";
    public ListView mListVideos;
    public ImageView mImageCabecera;
    private String mFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListVideos = findViewById(R.id.listViewVideos);
        mImageCabecera = findViewById(R.id.imageViewCabecera);

        mImageCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseVideos parseVideos = new ParseVideos(mFileContent);
                parseVideos.process();

                final AdapterVideo adapterVideos = new AdapterVideo(
                        MainActivity.this,
                        parseVideos.getVideos()
                );

                mListVideos.setAdapter(adapterVideos);

                mListVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent video = YouTubeStandalonePlayer.createPlaylistIntent(
                                MainActivity.this,
                                YOUTUBE_API_KEY,
                                YOUTUBE_PLAYLIST_ID,
                                position,
                                1000,
                                true,
                                false
                        );
                        startActivity(video);
                    }
                });
            }
        });

        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://www.youtube.com/feeds/videos.xml?playlist_id=PLR7JWZAjVdyt484P_iMNVfjWuKceX5BFp");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";


        @Override
        protected String doInBackground(String... strings) {
            mFileContent = downloadXmlFile(strings[0]);

            if (mFileContent == null) {
                Log.d(TAG, "Problema al descargar el XML");
            }

            return mFileContent;
        }


        public String downloadXmlFile(String urlPath) {

            StringBuilder tempBuffer = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Code status: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charsRead;
                char[] inputBuffer = new char[500];

                while (true) {
                    charsRead = isr.read(inputBuffer);

                    if (charsRead <= 0) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }

                return tempBuffer.toString();

            } catch (IOException e) {
                Log.e(TAG, "No se ha podido descargar el RSS - " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}