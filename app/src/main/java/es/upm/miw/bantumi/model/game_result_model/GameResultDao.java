package es.upm.miw.bantumi.model.game_result_model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GameResult result);
    @Query("select * from GameResult order by winner_seed_number desc limit 10")
    LiveData<List<GameResult>> getBestTenResults();
    @Query("delete from GameResult")
    void deleteAll();
}
