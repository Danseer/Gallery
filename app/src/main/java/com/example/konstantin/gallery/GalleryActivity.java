package com.example.konstantin.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private RecyclerView photoRecyclerView;
    private List<gallery_item> mItems = new ArrayList<>();
    Intent intent;
    private String method, text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        photoRecyclerView = (RecyclerView) findViewById(R.id.photo_gallery_recycler);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        setUpAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_latest_public_photos:
                method = "flickr.photos.getRecent";
                text = "";
                new FetchItemTask().execute();
                return true;

            case R.id.action_popular_photos:
                method = "flickr.interestingness.getList";
                text = "";
                new FetchItemTask().execute();
                return true;

            case R.id.action_find_photos_photos:

                LayoutInflater li = LayoutInflater.from(this);
                View find_dialogView = li.inflate(R.layout.find_dialog, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);

                mDialogBuilder.setView(find_dialogView);

                final EditText inputText = (EditText) find_dialogView.findViewById(R.id.editText);

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                       text = inputText.getText().toString();
                                        method = "flickr.photos.search";
                                        new FetchItemTask().execute();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog dialog = mDialogBuilder.create();
                dialog.show();


                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private class photoHolder extends RecyclerView.ViewHolder {
        private ImageView imageItemView;

        public photoHolder(View itemView) {
            super(itemView);
            imageItemView = (ImageView) itemView.findViewById(R.id.photo_gallery_image_view);
        }
    }

    private class photoAdapter extends RecyclerView.Adapter<photoHolder> {

        private List<gallery_item> mGalleryItems;

        public photoAdapter(List<gallery_item> items) {
            mGalleryItems = items;
        }

        @Override
        public photoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GalleryActivity.this);
            View v = inflater.inflate(R.layout.galery_item, parent, false);
            return new photoHolder(v);
        }

        @Override
        public void onBindViewHolder(photoHolder holder, final int position) {
            gallery_item GalleryItem = mGalleryItems.get(position);
            Picasso.with(GalleryActivity.this).load(GalleryItem.getUrls()).into(holder.imageItemView);

            holder.imageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String urls=mGalleryItems.get(position).getUrls();
                    String urlm=mGalleryItems.get(position).getUrlm();
                    String title=mGalleryItems.get(position).getCaption();
                    String ownername=mGalleryItems.get(position).getOwner_name();
                    //Toast.makeText(GalleryActivity.this,url, Toast.LENGTH_SHORT).show();
                    intent = new Intent(GalleryActivity.this, ItemInfoActivity.class);
                   intent.putExtra("urls",urls);
                    intent.putExtra("urlm",urlm);
                    intent.putExtra("title",title);
                    intent.putExtra("ownername",ownername);

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<gallery_item>> {


        @Override
        protected List<gallery_item> doInBackground(Void... voids) {
            return new FlickFetcher().fetchItems(method, text);
        }

        @Override
        protected void onPostExecute(List<gallery_item> gallery_items) {
            mItems = gallery_items;
            setUpAdapter();
        }
    }

    private void setUpAdapter() {
        photoRecyclerView.setAdapter(new photoAdapter(mItems));
    }


}
