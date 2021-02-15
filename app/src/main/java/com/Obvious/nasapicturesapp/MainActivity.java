package com.Obvious.nasapicturesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Obvious.nasapicturesapp.Adapter.GridAdapter;
import com.Obvious.nasapicturesapp.DataModel.DataModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<DataModel> nasaModelArrayList = new ArrayList<>();
    GridAdapter Adapter;

    private class ImagePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return MainActivity.this.nasaModelArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //return view == ((ImageView) object);
            return view == ((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = MainActivity.this;

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.sheet_activity, container, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.nasaImage);

            TextView date = (TextView) view.findViewById(R.id.date);

            TextView title = (TextView) view.findViewById(R.id.title);

            TextView description = (TextView) view.findViewById(R.id.description);

            //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            String imageURL = MainActivity.this.nasaModelArrayList.get(position).getUrl();
            Glide.with(MainActivity.this).load(imageURL).apply(new RequestOptions()
                    .placeholder(R.drawable.image)).into(imageView);
            //((ViewPager) container).addView(imageView, 0);

            date.setText(MainActivity.this.nasaModelArrayList.get(position).getDate());

            title.setText(MainActivity.this.nasaModelArrayList.get(position).getTitle());

            description.setText(MainActivity.this.nasaModelArrayList.get(position).getExplanation());



            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //((ViewPager) container).removeView((ImageView) object);
            container.removeView((View) object);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.UserList);
        getJsonFileFromLocally();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_details,(ConstraintLayout) this.findViewById(R.id.bottom_sheet_container));
        bottomSheetDialog.setContentView(bottomSheetView);

        Adapter.setOnImageClickListener(new GridAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int pos) {
                bottomSheetDialog.show();
                ViewPager viewPager = bottomSheetView.findViewById(R.id.view_pager);
                ImagePagerAdapter adapter = new ImagePagerAdapter();
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(pos);
            }
        });
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("Data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getJsonFileFromLocally() {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            nasaModelArrayList = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                //Log.d("TAG", jsonArray.getJSONObject(i)+"getJsonFileFromLocally: ");
                DataModel model = gson.fromJson(jsonArray.getString(i), DataModel.class);
                nasaModelArrayList.add(model);
            }

            Adapter = new GridAdapter(MainActivity.this, nasaModelArrayList);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(Adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}