package com.alisu.vocabsapps.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.vocabsapps.Model.Entity.Vocab;
import com.alisu.vocabsapps.R;

public class VocabListAdapter extends ListAdapter<Vocab ,VocabListAdapter.VocabViewHolder> {

//    private List<Vocab> vocabList = new ArrayList<>();
    private OnItemClickListener listener;

    public VocabListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Vocab> DIFF_CALLBACK = new DiffUtil.ItemCallback<Vocab>() {
        @Override
        public boolean areItemsTheSame(@NonNull Vocab oldItem, @NonNull Vocab newItem) {
            return oldItem.getId() ==  newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Vocab oldItem, @NonNull Vocab newItem) {
            return oldItem.getmWordEng().equals(newItem.getmWordEng()) && oldItem.getmWordInd().equals(newItem.mWordInd);
        }
    };

    @NonNull
    @Override
    public VocabListAdapter.VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items, parent, false);
        return new VocabViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabListAdapter.VocabViewHolder holder, int position) {

            Vocab current = getItem(position);
            holder.wordEng.setText(current.getmWordEng());
            holder.wordInd.setText(current.getmWordInd());

    }
//
//    public void setVocabList(List<Vocab> vocabs) {
//        vocabList = vocabs;
//        notifyDataSetChanged();
//    }

    public Vocab getVocabAt(int posistion) {
        return getItem(posistion);
    }


    class VocabViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordEng, wordInd;

        VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            wordEng = itemView.findViewById(R.id.tv_eng_word);
            wordInd = itemView.findViewById(R.id.tv_ind_word);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Vocab vocab);
    }

    public void setOnclickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
