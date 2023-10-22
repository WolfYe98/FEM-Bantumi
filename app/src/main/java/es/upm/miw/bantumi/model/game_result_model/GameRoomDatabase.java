package es.upm.miw.bantumi.model.game_result_model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {GameResult.class}, version = 1, exportSchema = false)
public abstract class GameRoomDatabase extends RoomDatabase{
    public static final String DATABASE_NAME = "game_database";
    public abstract GameResultDao gameResultDao();
    private static volatile GameRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GameRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (GameRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GameRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
