package com.alisu.vocabsapps.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alisu.vocabsapps.R;
import com.alisu.vocabsapps.View.Adapter.VocabListAdapter;
import com.alisu.vocabsapps.Model.Entity.Vocab;
import com.alisu.vocabsapps.ViewModel.VocabViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_VOCAB_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;

    RecyclerView recyclerView;
    VocabListAdapter vocabListAdapter;
    private VocabViewModel vocabViewModel;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_vocabs);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        vocabListAdapter = new VocabListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(vocabListAdapter);





//        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);

        vocabViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(VocabViewModel.class);

        vocabViewModel.getmAllVocabs().observe(this, new Observer<List<Vocab>>() {
            @Override
            public void onChanged(List<Vocab> vocabs) {
                vocabListAdapter.submitList(vocabs);

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEditVocabActivity.class);
                startActivityForResult(intent, NEW_VOCAB_ACTIVITY_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                vocabViewModel.delete(vocabListAdapter.getVocabAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Vocab delete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        vocabListAdapter.setOnclickListener(new VocabListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vocab vocab) {
                Intent intent = new Intent(MainActivity.this, NewEditVocabActivity.class);
                intent.putExtra(NewEditVocabActivity.EXTRA_ID, vocab.getId());
                intent.putExtra(NewEditVocabActivity.EXTRA_EDIT, vocab);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_VOCAB_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Vocab vocab = data.getParcelableExtra(NewEditVocabActivity.EXTRA_REPLY);
            vocabViewModel.insert(vocab);
        }
        else if(requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK){
            int id = data.getIntExtra(NewEditVocabActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Vocab can't be updated", Toast.LENGTH_SHORT).show();
            }
            Vocab vocab = data.getParcelableExtra(NewEditVocabActivity.EXTRA_EDIT);
            assert vocab != null;
            vocab.setId(id);
            vocabViewModel.update(vocab);
            Toast.makeText(this, "Vocab updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes :
                vocabViewModel.deleteAll();
                Toast.makeText(this, "All vocabs deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
