package com.example.nishtha.popular_movie_stage2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.nishtha.popular_movie_stage2.R;
import com.example.nishtha.popular_movie_stage2.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nishtha on 15/4/16.
 */
public class Trailer_adapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final Trailer mLock = new Trailer();

    private List<Trailer> mObjects;

    public Trailer_adapter(Context context, List<Trailer> objects) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObjects = objects;
    }

    public Context getContext() {
        return mContext;
    }

    public void add(Trailer object) {
        synchronized (mLock) {
            mObjects.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Trailer getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_trailer, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final Trailer trailer = getItem(position);
        viewHolder = (ViewHolder) view.getTag();
        String yt_thumbnail_url = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        Picasso.with(getContext()).load(yt_thumbnail_url).into(viewHolder.imageView);
        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_image);
        }
    }
}
