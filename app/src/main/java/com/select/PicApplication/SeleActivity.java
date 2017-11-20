package com.select.PicApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.select.untils.Untils;

/**
 * @author
 * @version 1.0
 * @date 2017/10/31
 */

public class SeleActivity extends AppCompatActivity {
    GridView sele_pic;
    int draw[];
    int num[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initview();
    }

    private void initview() {
        sele_pic = (GridView) findViewById(R.id.sele_pic);
        draw = new int[]{R.mipmap.white, R.mipmap.blake, R.mipmap.blue, R.mipmap.green, R.mipmap.mi, R.mipmap.zi};
        sele_pic.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return draw.length;
        }

        @Override
        public Object getItem(int position) {
            return draw[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(SeleActivity.this).inflate(R.layout.select_item, null);
            ImageView sele_item_pic = (ImageView) view.findViewById(R.id.sele_item_pic);
            sele_item_pic.setBackgroundDrawable(Untils.getBitmap(SeleActivity.this, draw[position]));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posi(position);
                }
            });
            return view;
        }
    }

    private void posi(int posi) {
        Intent in = new Intent(SeleActivity.this, MainActivity.class);
        in.putExtra("position", posi);
        setResult(100,in);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            posi(-1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
