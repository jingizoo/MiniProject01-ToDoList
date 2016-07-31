package com.jalaj.firstapp.todolist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalaj.firstapp.todolist.Initiator;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.activity.AddUpdateItem;
import com.jalaj.firstapp.todolist.dialogues.CategoryDialog;
import com.jalaj.firstapp.todolist.model.CategoryTypes;

import java.util.ArrayList;

/**
 * Created by jalajmehta on 7/30/16.
 */

public class CategoryListAdapter extends BaseAdapter {

    ArrayList<CategoryTypes> arrayList;
    Context ctx;
    LayoutInflater layoutInflater;
    public static CategoryListAdapter categoryListAdapter;
    public CategoryListAdapter(ArrayList arrayList, Context ctx) {

        this.arrayList = arrayList;
        this.ctx = ctx;
        layoutInflater = LayoutInflater.from(ctx);
        categoryListAdapter = this;
    }

    public static CategoryListAdapter getInstance()
    {
        if (categoryListAdapter!=null)
     return categoryListAdapter;
        else
            return null;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

       // Log.d("position, size",position+","+arrayList.size()+" catid"+arrayList.get(position).getCategory_id());

        convertView = layoutInflater.inflate(R.layout.layout_front_grid_child,parent,false);
       TextView tdTxtGridCategory = (TextView)convertView.findViewById(R.id.tdTest);
        TextView tdTxtTotalPending = (TextView)convertView.findViewById(R.id.tdTxtFrontPending);
        TextView tdTxtTotalElapsed = (TextView)convertView.findViewById(R.id.tdTxtFrontElapsed);
        TextView tdTxtTotalScheduledOrDone = (TextView)convertView.findViewById(R.id.tdTxtFrontScheduled);

        if (arrayList.get(position).getCategory_id()!=-1){
        tdTxtGridCategory.setId(arrayList.get(position).getCategory_id());
            if (arrayList.get(position).getDescription().length() > 10)
        tdTxtGridCategory.setText(arrayList.get(position).getDescription().substring(0,8)+"...");
            else
                tdTxtGridCategory.setText(arrayList.get(position).getDescription());
            tdTxtTotalElapsed.setText(Initiator.getInstance(ctx).getTotalElapsed(arrayList.get(position).getCategory_id())+"");
            tdTxtTotalPending.setText(Initiator.getInstance(ctx).getTotalPending(arrayList.get(position).getCategory_id())+"");
            tdTxtTotalScheduledOrDone.setText(Initiator.getInstance(ctx).getTotalScheduledOrDone(arrayList.get(position).getCategory_id())+"");
        }
        else {
            tdTxtGridCategory.setId(-1);
            tdTxtGridCategory.setText("+");
            tdTxtTotalElapsed.setVisibility(View.INVISIBLE);
            tdTxtTotalPending.setVisibility(View.INVISIBLE);
            tdTxtTotalScheduledOrDone.setVisibility(View.INVISIBLE);
        }


        tdTxtGridCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()!=-1) {
                    Intent intent = new Intent(ctx, AddUpdateItem.class);
                    Log.d("Cat Id", arrayList.get(position).getCategory_id() + "");
                    intent.putExtra("category_id", arrayList.get(position).getCategory_id() + "");
                    ctx.startActivity(intent);
                }
                else
                {
                    CategoryDialog  categoryDialog= new CategoryDialog();
                    categoryDialog.showCategoryDialog(ctx);
                }
            }
        });

        if (position==arrayList.size()-1 && arrayList.get(position).getCategory_id()!=-1){
         //   Log.d("If position, size",position+","+arrayList.size()+" catid"+arrayList.get(position).getCategory_id());
            CategoryTypes categoryTypes = new CategoryTypes(-1,"app_default_desc");
            arrayList.add(categoryTypes);
          //  Log.d (arrayList.get(position+1).getCategory_id()+" ","Cat Id Added");
            this.notifyDataSetChanged();
        }
        return convertView;
    }

    public void refreshArrayList()
    {
        arrayList = Initiator.getInstance(ctx).getAllCategoryTypes();
        CategoryTypes categoryTypes = new CategoryTypes(-1,"app_default_desc");
        arrayList.add(categoryTypes);
        notifyDataSetChanged();
    }

}
