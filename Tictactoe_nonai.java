import java.util.*;

public class Tictactoe_nonai {
    
    static char[][] board={
        {' ',' ',' '},
        {' ',' ',' '},
        {' ',' ',' '}
    };

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

    public static boolean isvalid(int row,int col){
        if(row<0 || row>2 || col<0 || col>2){
            return false;
        }
        if(board[row][col]==' '){
            return true;
        }
        return false;
    }

    public static boolean  checkwin(char c){
        for(int i=0;i<3;i++){
            if(board[i][0]==c && board[i][1]==c && board[i][2]==c){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(board[0][i]==c && board[1][i]==c && board[2][i]==c){
                return true;
            }
        }
        if(board[0][0]==c && board[1][1]==c && board[2][2]==c){
            return true;
        }
        if(board[0][2]==c && board[1][1]==c && board[2][0]==c){
            return true;
        }
        return false;
    }

    public static void computermove(){
        if(board[1][1]==' '){
            board[1][1]='O';
            return;
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]==' '){
                    board[i][j]='O';
                    return; 
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        while (true) { 
            printboard(); 

            System.out.println("player: "+'X');
            System.out.println("enter row and col: ");
            int row=sc.nextInt();
            int col=sc.nextInt();

            if(!isvalid(row,col)){
                System.out.println("enter another position ");
                continue;
            }
            else{
                board[row][col]='X';
            }

            if(checkwin('X')){ 
                System.out.println("player "+'X'+" wins");
                break;
            }

            if(isboardfull()){
                System.out.println("Draw");
                break; 
            }

            computermove();

            if(checkwin('O')){
                printboard(); 
                System.out.println("computer wins");
                break;
            }

            if(isboardfull()){
                System.out.println("Draw");
                break; 
            }
        }
    }
}
