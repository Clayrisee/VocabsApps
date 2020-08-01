package com.alisu.vocabsapps.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alisu.vocabsapps.Model.Entity.Vocab;

import java.util.List;

@Dao
public interface VocabDao {


    @Query("SELECT * from vocabs_table ORDER BY engWord ASC")
    LiveData<List<Vocab>> getAllVocabs();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Vocab vocab);

    @Query("DELETE FROM vocabs_table ")
    void deleteAll();

    @Delete
    void delete(Vocab vocab);

    @Update
    void update(Vocab vocab);
}
