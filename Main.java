package mua;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import mua.MuaTree;
public class Main {
    static Scanner myin;
//    MuaTree readOne(MuaTree in, String[] ls, int ind){
//        if (in.readin(ls[ind])==0){
//            in.content = ls[ind];
//            //how to know when ind reaches final one
//        }
//        return in;
//    }
//    MuaTree readTwo(MuaTree in, String[] ls, int ind){
//        in.content = ls[ind];
//        return in;
//    }
//    MuaTree readThr(MuaTree in, String[] ls, int ind){
//        in.content = ls[ind];
//        return in;
//    }


    public static void main(String[] args) {
        myin = new Scanner(System.in);
        MuaTree tMTree = new MuaTree();
        MuaTree root = tMTree;
        HashMap<String,String> tMap = new HashMap<>();
        // variable : value
        while(myin.hasNext()){
            int tCount = 0 ;
            String tString = myin.nextLine();
            if(tString.equals("")||tString.equals(" ")) continue;

            //if(tString.contains("add")&&tString.contains("(")) continue;
            //if(tString.contains(":")&&tString.contains("(")) continue;
            //skip check point

            if(tString.contains("[")||tString.contains("("))
                {
                    if(tString.contains("[")){
                        // List op
                        String[] tStringList=tString.split(" ");
                        String[] tStringList2 = new String[tStringList.length];
                        int flag = 0;
                        int j = -1;
                        //0 -> not seeing "[" or done matching
                        //1 -> seeking for "]"
                        for(int i = 0; i<tStringList.length; i++){
                            if(flag==0){
                                j++;
                                //put in a new slot
                                tStringList2[j] = tStringList[i];
                                //not seeing "[" or done matching
                                if(tStringList[i].startsWith("[")){
                                    //seeing a new [
                                    flag = 1;
                                    if(tStringList[i].endsWith("]"))
                                        flag = 0;
                                }
                            }else
                                if(flag==1){
                                    //seeking for "]"
                                    if(tStringList[i].endsWith("]")){
                                        //found a ]
                                        tStringList2[j] += " "+tStringList[i];
                                        //concat string
                                        flag = 0;
                                    }else
                                        if(tStringList[i].startsWith("[")){
                                            System.out.println("duplicate [ symbol");
                                        }else
                                            {
                                                //concat string
                                                tStringList2[j] += " "+tStringList[i];
                                            }
                            }
                            else
                                System.out.println("unknown flag value");
                        }
                        //continue;
                        tStringList2 = Arrays.copyOf(tStringList2,j+1);
                        //*[ a new readlist function for lists contain [ ?
                        //tMTree.readListBrace(tMTree, tStringList2, myin);
                        //System.out.println(Arrays.toString(tStringList2));
                        tMTree.readList(tMTree, tStringList2, myin,tMap);

                    }else{
                        // math op
                        // *[ what if a line input contains both [ and ( ?
                        String[] tStringList = tString.split(" ");
                        int flag = 0;
                        int left = 0;
                        int j = -1;
                        String[] tL = new String[tStringList.length];
                        //System.out.println(Arrays.toString(tStringList));
                        for(int i = 0;i<tStringList.length;i++){
                            if(tStringList[i].startsWith("(")&&!(tStringList[i].endsWith(")"))){
                                //lonely (
                                flag++;
                                left++;
                                tL[++j] = tStringList[i];
                            }else
                            if(!tStringList[i].startsWith("(")&&(tStringList[i].endsWith(")"))){
                                //lonely (

                                left--;
                                tL[j] += " "+tStringList[i];
                            }else
                            {
                                if(left!=0){
                                    // left not zero, combine
                                    tL[j] += " "+tStringList[i];
                                }else
                                {
                                    tL[++j] = tStringList[i];
                                    flag++;
                                }
                            }
                        }

                        String[] tL2 = Arrays.copyOf(tL,flag);
                        //System.out.println(Arrays.toString(tL2));
                        tMTree.readList(tMTree, tL2, myin,tMap);
                    }
                }
            else {
                // Basic op
                String[] tStringList = tString.split(" ");
                tMTree.readList(tMTree, tStringList, myin,tMap);
            }
//            tMTree.summary();
//            tMTree.tocommand(tMap,myin);
//            tMTree = new MuaTree();
        }
        //System.out.println(tMap);
    }

}