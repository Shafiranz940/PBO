public class Campaign
{
    public int level = 1;

    public void nextLevel()
    {
        level++;
    }

    public int getBallSpeed()
    {
        return 4 + level; 
    }

    public int getScoreTarget()
    {
        return 5 + (level * 2);
    }
}
