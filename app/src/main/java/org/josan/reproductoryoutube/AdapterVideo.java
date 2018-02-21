package org.josan.reproductoryoutube;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.josan.reproductoryoutube.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Josan on 13/02/2018.
 */

public class AdapterVideo extends BaseAdapter {

    private Activity activity;
    private ArrayList<Video> videosList;

    public AdapterVideo(Activity activity, ArrayList<Video> videosList) {
        this.activity = activity;
        this.videosList = videosList;
    }

    @Override
    public int getCount() {
        return videosList.size();
    }

    @Override
    public Object getItem(int position) {
        return videosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if( convertView == null ){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.videos_list_item, null);
        }

        Video video = videosList.get(position);

        TextView titulo = v.findViewById(R.id.textViewTitulo);
        titulo.setText(video.getTitulo());

        TextView autor = v.findViewById(R.id.textViewCreador);
        autor.setText(video.getAutor());

        ImageView videoImagen = v.findViewById(R.id.imageViewImagen);
        Picasso.with(v.getContext()).load(video.getImagen()).into(videoImagen);

        return v;
    }
}