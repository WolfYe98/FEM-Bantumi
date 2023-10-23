package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultRepository {
    private final GameResultDao gameResultDao;
    private final LiveData<List<GameResult>> bestResults;
    GameResultRepository(Application application){
        GameRoomDatabase db = GameRoomDatabase.getDatabase(application);
        gameResultDao = db.gameResultDao();
        bestResults = this.gameResultDao.getBestTenResults();
    }
    void insert(GameResult gameResult){
        GameRoomDatabase.databaseWriteExecutor.execute(()->{
            this.gameResultDao.insert(gameResult);
        });
    }

    public LiveData<List<GameResult>> getBestTenResults() {
        return this.bestResults;
    }

    public void deleteAllResults() {
        GameRoomDatabase.databaseWriteExecutor.execute(this.gameResultDao::deleteAll);
    }
}
