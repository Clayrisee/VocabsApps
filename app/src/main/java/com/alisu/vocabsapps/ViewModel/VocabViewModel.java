package com.alisu.vocabsapps.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alisu.vocabsapps.Model.Entity.Vocab;
import com.alisu.vocabsapps.Model.Repository.VocabRepository;

import java.util.List;

public class VocabViewModel extends AndroidViewModel {

    private VocabRepository vocabRepository;
    private LiveData<List<Vocab>> mAllVocabs;

    public VocabViewModel(@NonNull Application application) {
        super(application);
        vocabRepository = new VocabRepository(application);
        mAllVocabs = vocabRepository.getAllVocabs();
    }

    public LiveData<List<Vocab>> getmAllVocabs(){
        return mAllVocabs;
    }

    public void insert(Vocab vocab){
        vocabRepository.insert(vocab);
    }
    public void update(Vocab vocab){vocabRepository.update(vocab);}
    public void delete(Vocab vocab){
        vocabRepository.delete(vocab);
    }
    public void deleteAll(){
        vocabRepository.deleteAll();
    }
}
