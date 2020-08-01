package com.alisu.vocabsapps.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alisu.vocabsapps.Model.Entity.Vocab;
import com.alisu.vocabsapps.R;

public class NewEditVocabActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    public static final String EXTRA_EDIT =
            "com.example.android.roomwordssample.EDIT";
    public static final String EXTRA_ID =
            "com.example.android.roomwordssample.ID";


    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int NEW_VOCAB_ACTIVITY_REQUEST_CODE = 1;
    EditText etEng, etInd;
    Button btnSave;
    Vocab vocab;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vocab);

        etEng = findViewById(R.id.et_english);
        etInd = findViewById(R.id.et_indo);
        btnSave = findViewById(R.id.btn_save);
        final String btnUpdateText = "Update Vocab";
            final String btnAddText = "Add Vocab";
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Vocab");
            vocab = intent.getParcelableExtra(EXTRA_EDIT);
            assert vocab != null;
            etEng.setText(vocab.getmWordEng());
            etInd.setText(vocab.getmWordInd());
            btnSave.setText(btnUpdateText);
        } else {
            setTitle("Add Vocab");
            btnSave.setText(btnAddText);
        }


        //there's bug in here, user cannot press save button. fix it later
        //update : bug fixed, but for update it's still have bug
        //update : done
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSave.getText() == btnAddText){
                    addVocab(NEW_VOCAB_ACTIVITY_REQUEST_CODE);
                }
                else if (btnSave.getText() == btnUpdateText){
                    addVocab(EDIT_NOTE_REQUEST);
                }
            }
        });
    }

    //make change with this later
    //update : done
    private void addVocab(int req){
        Intent replyIntent = new Intent();

        // there's bug for update req
        if (TextUtils.isEmpty(etEng.getText()) && TextUtils.isEmpty(etInd.getText())){
            setResult(RESULT_CANCELED, replyIntent);
        }else {
            String engWord, indWord;
            engWord = etEng.getText().toString();
            indWord = etInd.getText().toString();

            Vocab vocab = new Vocab(engWord, indWord);
            if(req == NEW_VOCAB_ACTIVITY_REQUEST_CODE){
                replyIntent.putExtra(EXTRA_REPLY, vocab);

            }
            else if (req == EDIT_NOTE_REQUEST){
                int id = getIntent().getIntExtra(EXTRA_ID, -1);

                if (id != -1) {
                    replyIntent.putExtra(EXTRA_ID, id);
                    replyIntent.putExtra(EXTRA_EDIT, vocab);
                }
            }
            setResult(RESULT_OK, replyIntent);
        }

        finish();
    }

}
