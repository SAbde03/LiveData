package com.example.roomwordsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomwordsample.R;
import com.example.roomwordsample.model.Word;
import com.example.roomwordsample.ViewModel.WordViewModel;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mWords;
    private WordViewModel mWordViewModel;
    private Context mContext;

    public WordListAdapter(Context context, WordViewModel wordViewModel) {
        mContext = context;
        mWordViewModel = wordViewModel;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());

            // Set long click listener
            holder.itemView.setOnLongClickListener(v -> {
                showDeleteConfirmationDialog(current);
                return true;
            });
        } else {
            holder.wordItemView.setText("No Word");
        }
    }

    private void showDeleteConfirmationDialog(Word word) {
        new AlertDialog.Builder(mContext)
                .setTitle("Delete Word")
                .setMessage("Are you sure you want to delete this word?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    mWordViewModel.delete(word);
                    Toast.makeText(mContext, "Word deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords != null ? mWords.size() : 0;
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}