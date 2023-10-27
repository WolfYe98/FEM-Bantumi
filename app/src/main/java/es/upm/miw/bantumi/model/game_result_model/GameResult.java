package es.upm.miw.bantumi.model.game_result_model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameResult")
public class GameResult {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "winner_name")
    private String winnerName;
    @ColumnInfo(name = "loser_name")
    private String loserName;
    @ColumnInfo(name = "game_datetime")
    private String gameDateTime;
    @ColumnInfo(name = "winner_seed_number")
    private Integer winnerSeedNumber;
    @ColumnInfo(name = "loser_seed_number")
    private Integer loserSeedNumber;
    @ColumnInfo(name = "is_tie")
    private Boolean isTie;
    @ColumnInfo(name = "game_duration")
    private String gameDuration;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getLoserName() {
        return loserName;
    }

    public void setLoserName(String loserName) {
        this.loserName = loserName;
    }

    public String getGameDateTime() {
        return gameDateTime;
    }

    public void setGameDateTime(String gameDateTime) {
        this.gameDateTime = gameDateTime;
    }

    public Boolean getTie() {
        return isTie;
    }

    public void setTie(Boolean tie) {
        isTie = tie;
    }

    public Integer getWinnerSeedNumber() {
        return winnerSeedNumber;
    }

    public void setWinnerSeedNumber(Integer winnerSeedNumber) {
        this.winnerSeedNumber = winnerSeedNumber;
    }

    public Integer getLoserSeedNumber() {
        return loserSeedNumber;
    }

    public void setLoserSeedNumber(Integer loserSeedNumber) {
        this.loserSeedNumber = loserSeedNumber;
    }

    public String getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(String gameDuration) {
        this.gameDuration = gameDuration;
    }

    public static class Builder implements GameResultBuilder {
        private final GameResult result;

        public Builder() {
            this.result = new GameResult();
        }

        @Override
        public GameResultBuilder setWinnerName(String winnerName) {
            this.result.winnerName = winnerName;
            return this;
        }

        @Override
        public GameResultBuilder setLoserName(String loserName) {
            this.result.loserName = loserName;
            return this;
        }

        @Override
        public GameResultBuilder setDateTime(String gameDate) {
            this.result.gameDateTime = gameDate;
            return this;
        }

        @Override
        public GameResultBuilder setWinnerSeedNumber(Integer winnerSeedNumber) {
            this.result.winnerSeedNumber = winnerSeedNumber;
            return this;
        }

        @Override
        public GameResultBuilder setLoserSeedNumber(Integer loserSeedNumber) {
            this.result.loserSeedNumber = loserSeedNumber;
            return this;
        }

        @Override
        public GameResultBuilder setIsTie(Boolean isTie) {
            this.result.isTie = isTie;
            return this;
        }

        @Override
        public GameResultBuilder setGameDuration(String duration) {
            this.result.gameDuration = duration;
            return this;
        }

        @Override
        public GameResult build() {
            return this.result;
        }
    }
}
