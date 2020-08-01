package com.alisu.vocabsapps.Model.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alisu.vocabsapps.Model.DAO.VocabDao;
import com.alisu.vocabsapps.Model.Entity.Vocab;

@Database(entities = {Vocab.class}, version = 1, exportSchema = false)
public abstract class VocabsRoomDatabase extends RoomDatabase {

    public abstract VocabDao vocabDao();
    private static VocabsRoomDatabase INSTANCE;

    public static VocabsRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (VocabsRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VocabsRoomDatabase.class, "vocabs_database")
                            .fallbackToDestructiveMigration().addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final VocabDao dao;
        String[] engWords = {"Book", "Table", "Chair"};
        String[] indWords = {"Buku", "Meja", "Kursi"};

        private PopulateDbAsync(VocabsRoomDatabase database) {
            dao = database.vocabDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0 ; i <= engWords.length -1 ; i++){
                Vocab vocab = new Vocab(engWords[i], indWords[i]);
                dao.insert(vocab);
            }
            return null;
        }
    }
}
