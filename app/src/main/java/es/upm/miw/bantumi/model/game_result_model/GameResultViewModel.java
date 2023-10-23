package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


public class GameResultViewModel extends AndroidViewModel {
    private GameResultRepository gameResultRepository;
    private LiveData<List<GameResult>> bestResults;
    public GameResultViewModel(Application application){
        super(application);
        this.gameResultRepository = new GameResultRepository(application);
        this.bestResults = gameResultRepository.getBestTenResults();
    }
    public void insert(GameResult result){
        this.gameResultRepository.insert(result);
    }

    public LiveData<List<GameResult>> getBestResults() {
        return bestResults;
    }

    public void deleteAll() {
        this.gameResultRepository.deleteAllResults();
    }
}
