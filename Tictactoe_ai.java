import java.util.Scanner;

public class Tictactoe_ai {
    static char[][] board={
        {' ',' ',' '},
        {' ',' ',' '},
        {' ',' ',' '}
    };

    // ✅ added printboard
    public static void printboard(){
        System.out.println("-------------");
        for(int i=0;i<3;i++){
            System.out.print("| ");
            for(int j=0;j<3;j++){
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public static int checkwin(){
        for(int i=0;i<3;i++){
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0]!=' '){
                if(board[i][0]=='X'){
                    return -10;
                }
                else{
                    return +10;
                }
            }
        }
        for(int i=0;i<3;i++){
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i]!=' '){
                if(board[0][i]=='X'){
                    return -10;
                }
                else{
                    return +10;
                }
            }
        }
        if(board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[0][0]!=' '){
            if(board[0][0]=='X'){
                return -10;
            }
            else{
                return 10;
            }
        }
        if(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[1][1]!=' '){
            if(board[1][1]=='X'){
                return -10;
            }
            else{
                return 10;
            }
        }
        return 0;
    }

    public static boolean isboardfull(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]==' '){
                    return false;
                }
            }
        }
        return true;
    }

    public static int minimax(boolean ismaximizaing){
        int score=checkwin();

        if(score==10 || score==-10){
            return score;
        }

        if(isboardfull()){
            return 0;
        }

        if(ismaximizaing){
            int best=-1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]==' '){
                        board[i][j]='O';
                        best=Math.max(best,minimax(false));
                        board[i][j]=' ';
                    }
                }
            }
            return best;
        }
        else{
            int best=1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]==' '){
                        board[i][j]='X';
                        best=Math.min(best,minimax(true));
                        board[i][j]=' ';
                    }
                }
            }
            return best;
        }
    }

    public static int[] findbestmove(){
        int bestval=-1000;
        int[] bestmove=new int[2];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]==' '){
                    board[i][j]='O';
                    int moveval=minimax(false);
                    board[i][j]=' ';

                    if(moveval>bestval){
                        bestmove[0]=i;
                        bestmove[1]=j;
                        bestval=moveval;
                    }
                }
            }
        }
        return bestmove;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        while(true){
        System.out.println("Enter position:");
        int row=sc.nextInt();
        int col=sc.nextInt();

        if(board[row][col]!=' '){
            System.out.println("invalid move");
            return;
        }

        board[row][col]='X';
        printboard(); // ✅ after player move

        if(checkwin()==-10){
            System.out.println("you win🥳");
            return;
        }

        if(isboardfull()){
            System.out.println("Draw🙂");
            return;
        }

        int [] bestmove=findbestmove();
        board[bestmove[0]][bestmove[1]]='O';

        printboard(); // ✅ after computer move

        if(checkwin()==10){
            System.out.println("computer wins😎");
            return;
        }

        if(isboardfull()){
            System.out.println("Draw🙂");
            return;
        }
    }
    }
}
