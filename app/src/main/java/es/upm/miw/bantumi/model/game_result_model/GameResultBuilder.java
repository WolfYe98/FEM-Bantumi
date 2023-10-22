package es.upm.miw.bantumi.model.game_result_model;

import androidx.room.ColumnInfo;

import java.time.LocalDateTime;

public interface GameResultBuilder {
    GameResultBuilder setWinnerName(String winnerName);
    GameResultBuilder setLoserName(String loserName);
    GameResultBuilder setDateTime(String gameDate);
    GameResultBuilder setWinnerSeedNumber(Integer winnerSeedNumber);
    GameResultBuilder setLoserSeedNumber(Integer loserSeedNumber);
    GameResultBuilder setIsTie(Boolean isTie);
    GameResult build();
}
