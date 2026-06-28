package controller;

public class ScoreManager {
    private int multiplier = 1;

    public int calculateScore(int linesCleared) {
        int baseScore = 0;
        switch (linesCleared) {
            case 1:
                baseScore = 100;
                break;
            case 2:
                baseScore = 300;
                break;
            case 3:
                baseScore = 500;
                break;
            case 4:
                baseScore = 800;
                break;
            default:
                baseScore = 0;
                break;
        }
        return baseScore * multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
