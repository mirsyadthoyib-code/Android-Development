package com.example.faceteknik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.faceteknik.API.Comment;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Comment> mCommentList;

    public PostAdapter(Context mContext, ArrayList<Comment> mCommentList) {
        super(mContext, 0, mCommentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Comment comment = (Comment) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.activity_comment, parent, false);
        }

        TextView tvUsername = (TextView)convertView.findViewById(R.id.username_activity_comment);
        TextView tvComment = (TextView)convertView.findViewById(R.id.status_activity_comment);

        tvUsername.setText(comment.getUsername());
        tvComment.setText(comment.getComment());

        convertView.setTag(comment.getId());

        return convertView;
    }
}
