package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


public class GameResultViewModel extends AndroidViewModel {
    private GameResultRepository gameResultRepository;
    public GameResultViewModel(Application application){
        super(application);
        this.gameResultRepository = new GameResultRepository(application);
    }
    public void insert(GameResult result){
        this.gameResultRepository.insert(result);
    }
}
