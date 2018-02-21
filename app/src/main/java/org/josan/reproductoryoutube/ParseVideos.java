package org.josan.reproductoryoutube;

import android.util.Log;

import org.josan.reproductoryoutube.model.Video;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Josan on 13/02/2018.
 */

public class ParseVideos {
    private static final String TAG = "ParseVideos";

    private String xmlData;
    private ArrayList<Video> videos;

    public ParseVideos(String xmlData) {
        this.xmlData = xmlData;
        videos = new ArrayList<>();
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public boolean process() {
        boolean status = true;
        Video currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){

                String tagName = xpp.getName();

                switch ( eventType ) {
                    case XmlPullParser.START_TAG:
                        if(tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord = new Video();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry) {
                            if(tagName.equalsIgnoreCase("entry")){
                                videos.add(currentRecord);
                                inEntry = false;
                            } else if ( tagName.equalsIgnoreCase("videoId")){
                                currentRecord.setId(textValue);
                            } else if ( tagName.equalsIgnoreCase("thumbnail")){
                                String url = xpp.getAttributeValue(null, "url");
                                currentRecord.setImagen(url);
                            } else if ( tagName.equalsIgnoreCase("title")){
                                currentRecord.setTitulo(textValue);
                            } else if ( tagName.equalsIgnoreCase("name")){
                                currentRecord.setAutor(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }catch (Exception e) {
            Log.d(TAG, "Problema parseando el RSS" + e.getMessage());
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public Video getVideos(int i){
        return videos.get(i);
    }
}

