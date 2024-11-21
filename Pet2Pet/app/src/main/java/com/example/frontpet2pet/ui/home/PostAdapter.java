package com.example.frontpet2pet.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frontpet2pet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private Context context;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        // Cargar imagen de perfil con manejo de null
        if (post.getPetProfileImage() != null && holder.profileImage != null) {
            Glide.with(context)
                    .load(post.getPetProfileImage())
                    .error(android.R.drawable.ic_menu_gallery)  // imagen por defecto de Android
                    .into(holder.profileImage);
        }

        // Cargar imagen del post con manejo de null
        if (post.getImageUrl() != null && holder.postImage != null) {
            Glide.with(context)
                    .load(post.getImageUrl())
                    .error(android.R.drawable.ic_menu_gallery)  // imagen por defecto de Android
                    .into(holder.postImage);
        }

        // Establecer textos con verificación null
        if (holder.petName != null) {
            holder.petName.setText(post.getPetName() != null ? post.getPetName() : "");
        }

        if (holder.postDescription != null) {
            holder.postDescription.setText(post.getDescription() != null ? post.getDescription() : "");
        }

        // Configurar listeners
        if (holder.likeButton != null) {
            holder.likeButton.setOnClickListener(v -> {
                // Implementar lógica de likes
            });
        }

        if (holder.commentButton != null) {
            holder.commentButton.setOnClickListener(v -> {
                // Implementar lógica de comentarios
            });
        }
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        ImageView postImage;
        TextView petName, postDescription;
        ImageButton likeButton, commentButton;

        @SuppressLint("WrongViewCast")
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            postImage = itemView.findViewById(R.id.post_image);
            petName = itemView.findViewById(R.id.post_user_name);
            postDescription = itemView.findViewById(R.id.post_description);
            likeButton = itemView.findViewById(R.id.button_like);
            commentButton = itemView.findViewById(R.id.button_comment);
        }
    }
}