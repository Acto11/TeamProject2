package com.example.teamproject1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {
    private Context context;

    ArrayList<UserAccount> list = new ArrayList<>();

    public UserAdapter(Context context, ArrayList<UserAccount> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        return new UserVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        UserAccount user = list.get(holder.getAdapterPosition());
    //이름
        holder.nameText.setText(user.getEmailId());

        //카드뷰

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("key", user.getIdToken());
                intent.putExtra("name", user.getName());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("company", user.getCompany());
                intent.putExtra("Dept", user.getDept());
                intent.putExtra("Role", user.getRole());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class UserVH extends RecyclerView.ViewHolder{

        TextView nameText;

        CardView cardView;

        public UserVH(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_text);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

}
