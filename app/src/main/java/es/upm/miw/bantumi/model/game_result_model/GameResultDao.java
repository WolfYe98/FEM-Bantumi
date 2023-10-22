package es.upm.miw.bantumi.model.game_result_model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface GameResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GameResult result);
}
