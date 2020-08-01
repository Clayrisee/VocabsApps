package com.alisu.vocabsapps.Model.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alisu.vocabsapps.Model.DAO.VocabDao;
import com.alisu.vocabsapps.Model.Entity.Vocab;
import com.alisu.vocabsapps.Model.database.VocabsRoomDatabase;

import java.util.List;

public class VocabRepository {
    private VocabDao vocabDao;
    private LiveData<List<Vocab>> mAllVocabs;

    public VocabRepository(Application application){
        VocabsRoomDatabase db = VocabsRoomDatabase.getDatabase(application);
        vocabDao = db.vocabDao();
        mAllVocabs = vocabDao.getAllVocabs();
    }

    public LiveData<List<Vocab>> getAllVocabs(){
        return  mAllVocabs;
    }

    public void insert(Vocab vocab){
        new insertAsyncTask(vocabDao).execute(vocab);
    }

    public void update(Vocab vocab){
        new updateAsyncTask(vocabDao).execute(vocab);
    }

    public void delete(Vocab vocab){
        new deleteAsyncTask(vocabDao).execute(vocab);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(vocabDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Vocab, Void, Void>{

        private VocabDao mAsyncTaskDao;

        insertAsyncTask(VocabDao dao){
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            mAsyncTaskDao.insert(vocabs[0]);
            return  null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Vocab, Void, Void>{

        private VocabDao mDao;

        updateAsyncTask(VocabDao vocabDao) {
            this.mDao = vocabDao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            mDao.update(vocabs[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Vocab, Void, Void>{

        private VocabDao mDao;

        deleteAsyncTask(VocabDao vocabDao) {
            this.mDao = vocabDao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            mDao.delete(vocabs[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private VocabDao mDao;

        public deleteAllAsyncTask(VocabDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            return null;
        }
    }

}
