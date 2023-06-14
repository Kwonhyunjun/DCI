package dciproject.backend.subjectStatistics;

public enum CompetitionRate {


    LEVEL0(0),
    LEVEL1(1),
    LEVEL2(2),
    LEVEL3(3),
    LEVEL4(4);

    final private int level;
    private CompetitionRate(int lv){
        level=lv;
    }

    public int getLevel(){
        return this.level;
    }
}
