public class Player {
    private int startingPoint;
    private int diceRolled;
    private int positionOnDiceRolled;
    private int finishingPoint;
    private int startRowNo;
    private int startColNo;
    private int finishRowNo;
    private int finishColNo;

    public Player(){
        this.finishingPoint = 0;
        this.diceRolled = 0;
        this.positionOnDiceRolled = 0;
        this.finishingPoint = 0;
        this.startRowNo = -1;
        this.startColNo = -1;
        this.finishRowNo = 0;
        this.finishColNo = 0;
    }

    public int getPositionOnDiceRolled() {
        return positionOnDiceRolled;
    }

    public void setPositionOnDiceRolled(int positionOnDiceRolled) {
        this.positionOnDiceRolled = positionOnDiceRolled;
    }

    public Player(int startingPoint, int diceRolled, int positionOnDiceRolled, int finishingPoint, int startRowNo, int startColNo, int finishRowNo, int finishColNo){
        this.finishingPoint = startingPoint;
        this.diceRolled = diceRolled;
        this.positionOnDiceRolled = positionOnDiceRolled;
        this.finishingPoint = finishingPoint;
        this.startRowNo = startRowNo;
        this.startColNo = startColNo;
        this.finishRowNo = finishRowNo;
        this.finishColNo = finishColNo;
    }
    public int getStartingPoint(){
        return startingPoint;
    }
    public int getDiceRolled(){
        return  diceRolled;
    }
    public int getFinishingPoint(){
        return finishingPoint;
    }
    public void setStartingPoint(int value){
        this.startingPoint = value;
    }
    public void setDiceRolled(int value){
        this.diceRolled = value;
    }
    public void setFinishingPoint(int value){
        this.finishingPoint = value;
    }
    public int getStartRowNo() {
        return startRowNo;
    }

    public void setStartRowNo(int startRowNo) {
        this.startRowNo = startRowNo;
    }

    public int getStartColNo() {
        return startColNo;
    }

    public void setStartColNo(int startColNo) {
        this.startColNo = startColNo;
    }

    public int getFinishRowNo() {
        return finishRowNo;
    }

    public void setFinishRowNo(int finishRowNo) {
        this.finishRowNo = finishRowNo;
    }

    public int getFinishColNo() {
        return finishColNo;
    }

    public void setFinishColNo(int finishColNo) {
        this.finishColNo = finishColNo;
    }
}