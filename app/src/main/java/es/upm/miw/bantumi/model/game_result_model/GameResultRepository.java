package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

public class GameResultRepository {
    private GameResultDao gameResultDao;
    GameResultRepository(Application application){
        GameRoomDatabase db = GameRoomDatabase.getDatabase(application);
        gameResultDao = db.gameResultDao();
    }
    void insert(GameResult gameResult){
        GameRoomDatabase.databaseWriteExecutor.execute(()->{
            this.gameResultDao.insert(gameResult);
        });
    }
}
