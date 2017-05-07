package com.qm.homework.testdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjun.view.drag_list_view.DragListView;
import com.zjun.view.drag_list_view.DragListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private DragListView dvl_drag_list;
    private List<Image> imagList;
    private ImageView iv1,iv2,iv3,iv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        imagList = new ArrayList<>();
        Image image1 = new Image(R.mipmap.d1);
        image1.setTag(1);
        Image image2 = new Image(R.mipmap.d2);
        image2.setTag(2);
        Image image3 = new Image(R.mipmap.d3);
        image3.setTag(3);
        Image image4 = new Image(R.mipmap.d4);
        image4.setTag(4);
        imagList.add(image1);
        imagList.add(image2);
        imagList.add(image3);
        imagList.add(image4);
    }

    private void initView() {
        dvl_drag_list = $(R.id.dvl_drag_list);
        iv1 = $(R.id.iv1);
        iv2 = $(R.id.iv2);
        iv3 = $(R.id.iv3);
        iv4 = $(R.id.iv4);
        // 1、添加Header测试
        TextView header = new TextView(this);
        header.setBackgroundColor(Color.GREEN);
        AbsListView.LayoutParams p = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
        header.setLayoutParams(p);
        String headerText = "Header of DragListView";
        header.setText(headerText);
        dvl_drag_list.addHeaderView(header);

        // 2、设置Adapter
        BaseAdapter listAdapter = new MyAdapter(this, imagList);
        dvl_drag_list.setAdapter(listAdapter);

        // 3、设置条目长按监听事件
        dvl_drag_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 有长按监听事件，为了防止与删除产生冲突，此句必须加上。
                dvl_drag_list.setLongClickFlag();
                int index = position - dvl_drag_list.getHeaderViewsCount();
                if (index < 0 || index >= imagList.size()) {
                    return false;
                }
                toast(imagList.get(index) + "，被长按");
                return true;
            }
        });

        //4. 位置信息
        dvl_drag_list.setmItemChangeListening(new DragListView.OnItemChangeListening() {
            @Override
            public void onItemChange(int[] index) {
                int[] imageList = new int[]{R.mipmap.ic_launcher,R.mipmap.d1,R.mipmap.d2,R.mipmap.d3,R.mipmap.d4};
                int[] imageIndex = new int[imageList.length];
                for (int i = 0; i < imageIndex.length; i++) {
                    imageIndex[i] = index[i];
                }
                if (imageIndex!=null){
                    iv1.setImageResource(imageList[imageIndex[1]]);
                    iv2.setImageResource(imageList[imageIndex[2]]);
                    iv3.setImageResource(imageList[imageIndex[3]]);
                    iv4.setImageResource(imageList[imageIndex[4]]);
                }
            }
        });
    }

    public class MyAdapter extends DragListViewAdapter<Image> {

        public MyAdapter(Context context, List<Image> imagList) {
            super(context, imagList);
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_drag_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (ImageView) convertView.findViewById(R.id.tv_name_drag_list);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setImageResource(imagList.get(position).getId());

            return convertView;
        }

        class ViewHolder{
            ImageView name;
        }
    }


    @SuppressWarnings("unchecked")
    private <V  extends View> V $(int id) {
        return (V) findViewById(id);
    }

    private Toast mToast;
    private void toast(String info) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(info);
        mToast.show();
    }

}
