package com.juanito.friendlystalk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.MyViewHolder> {

    List<String> userList;


    public MyUserAdapter(List<String> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public MyUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.user_item,parent,false);
        int height = parent.getMeasuredHeight() / 4;
        itemView.setMinimumHeight(height);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyUserAdapter.MyViewHolder holder, int position) {
        if(userList != null ){
            String pseudo = userList.get(position);
            holder.pseudo.setText(pseudo);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView pseudo;

        public MyViewHolder(final View itemView) {
            super(itemView);
            pseudo = (TextView) itemView.findViewById(R.id.textViewItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), pseudo.getText(), Toast.LENGTH_SHORT).show();

                }
            });
        }


    }
}
