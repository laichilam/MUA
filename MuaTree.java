package mua;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class MuaTree{
    MuaTree l;
    MuaTree r;
    String content;
    String type;
    MuaTree(){
//        MuaTree l = null;
//        MuaTree r = null;
        MuaTree l = null;
        MuaTree r = null;
        String content = null;
        int inds = 0;
    }
    void summary(){
        if(this.content!=null)
        System.out.println("content: "+this.content);
        if(this.l!=null&&this.l.content!=null){
            System.out.println("child content: "+this.l.content);
            this.l.summary();
        }

        if(this.r!=null&&this.r.content!=null){
            System.out.println("child content: "+this.r.content);
            this.r.summary();
        }
        return;
    }
    int pro (char c){
        switch(c){
            case '-': return 0;
            case '+': return 0;
            case '/': return 1;
            case '%': return 1;
            case '*': return 1;
            case '(': return -2;
            case ')': return 2;
            default: return -1;
        }
    }
    double domath(String in,HashMap<String,String> tmap){
        if(in.contains(":"))
        {
            int i = 0;
            int k = 0;
            String in2 = "";
            for(i=0;i<in.length();i++){
                if(in.charAt(i)==':'){
                    String ts = ""+in.charAt(i)+in.charAt(i+1);
                    i++;
                    ts = tmap.get(ts.substring(1));
                    for(int j = 0;j<ts.length();j++){
                        in2 += ts.charAt(j);
                        k++;
                    }
                }
                else{
                    in2 += in.charAt(i);
                    k++;
                }
            }
            //System.out.println(in2);
            in = in2;
        }

        int id = 0;
        Stack<Double> numstack = new Stack<>();
        Stack<Character> opstack = new Stack<>();
        int flg = 0;
        int ld = 0;
        for(id = 0; id<in.length(); id++){
//            System.out.println(opstack);
//            System.out.println(numstack);
            char c = in.charAt(id);
            if(c==' ') continue;
            if(flg==1&&c=='-') {flg=0;continue;}
            if(c=='+'||c=='-'||c=='*'||c=='/'||c=='%'||c=='('||c==')'){
                flg = 1;
                //add,sub,mul,div,mod,braces
                if(c=='('){
                    opstack.push(c);
                }

                if(c==')'){
                    while(opstack.peek()!='('){
                        char op = opstack.pop();
                        double b = numstack.pop();
                        double a = numstack.pop();
                        switch(op){
                            case '+':a = a+b;break;
                            case '-':a = a-b;break;
                            case '/':a = a/b;break;
                            case '*':a = a*b;break;
                            case '%':a = a%b;break;
                        }
                        numstack.push(a);
                    }
                    if(opstack.peek()=='(') opstack.pop();
                    //to whr?
//                    opstack.pop();
                }

                if(c=='*'||c=='/'||c=='%'||c=='+'||c=='-') {
                    if(c=='-'&&!(in.charAt(id-1)>='0'&&in.charAt(id-1)<='9')){
                        ;
                    }
                    else{
                    if (pro(c) > pro(opstack.peek())) {
                        opstack.push(c);
                    } else {
                        while (pro(c) <= pro(opstack.peek())) {
                            char op = opstack.pop();
                            double b = numstack.pop();
                            double a = numstack.pop();
                            switch (op) {
                                case '+':
                                    a = a + b;
                                    break;
                                case '-':
                                    a = a - b;
                                    break;
                                case '/':
                                    a = a / b;
                                    break;
                                case '*':
                                    a = a * b;
                                    break;
                                case '%':
                                    a = a % b;
                                    break;
                            }
                            numstack.push(a);
                        }
                        opstack.push(c);
                    }
                }
                }

            }

            if(c>='0'&&c<='9'){
                flg=0;
                String ts="";
                if(in.charAt(id+1)>='0'&&in.charAt(id+1)<='9'){
                    ts = ""+c+in.charAt(id+1);
                    numstack.push(Double.valueOf(ts));
                    id++;
                }else{
                    if(id>=2&&in.charAt(id-1)=='-'&&!(in.charAt(id-2)>='0'&&in.charAt(id-2)<='9')){
                        //if double symbol in a row
                        numstack.push(-1.0*Double.parseDouble(String.valueOf(c)));
                    }else
                        {
                    numstack.push(Double.valueOf(String.valueOf(c)));
                    }
                }
            }
        }
//        if(!opstack.isEmpty())
//        {
//            char op = opstack.pop();
//            double b = numstack.pop();
//            double a = numstack.pop();
//            switch(op){
//                case '+':a = a+b;break;
//                case '-':a = a-b;break;
//                case '/':a = a/b;break;
//                case '*':a = a*b;break;
//                case '%':a = a%b;break;
//            }
//            return a;
//        }
//
//        else
            return numstack.peek();}

    boolean isNumber(String in){
        boolean jump = false;
        if (in==null||in.length()==0) return false;
        char[] Sarr = in.toCharArray();
        if(Sarr[0]=='-'||(Sarr[0]>='0'&&Sarr[0]<='9')){
            for (char i : Sarr) {
                if(!jump){jump = true;continue;}
                if(!((i>='0'&&i<='9')||i=='.')){return false;}
            }
            return true;
        }else return false;
    }
    boolean isString(String in){
        char[] Sarr = in.toCharArray();
            for (char i : Sarr) {
                if(!((i>='0'&&i<='9')||(i>='a'&&i<='z')||(i>='A'&&i<='Z')||i=='_')){return false;}
            }
            return true;
    }
    int readin(String in){
        //this.content = in;
        if(in.equals("read")){
            // no more
            return -1;
        }
        if(in.equals("print")||in.equals("thing")||in.equals("erase")||in.equals("isname")||in.equals("isword")||in.equals("islist")||
                in.equals("isnumber")||in.equals("isbool")||in.equals("not")||in.equals("isempty")||in.equals("run")){
            // one more
            return 1;
        }
        if(in.equals("add")||in.equals("sub")||in.equals("mul")||in.equals("div")||in.equals("mod")||in.equals("make")||
        in.equals("eq")||in.equals("gt")||in.equals("lt")||in.equals("and")||in.equals("or")){
            // two more
            return 2;
        }
        if(in.equals("if")){
            // two more
            return 3;
        }
        if(in.startsWith("\"")||in.startsWith(":")||isNumber(in)||isString(in)||in.startsWith("[")||in.endsWith("]")||in.startsWith("(")){
            // one more
            // need to implement: number
            return 0;
        }
        System.out.println("unknown cmd");
        return -1;//error
    }
    int readCommand(MuaTree t,String[] in, int ind, Scanner min,HashMap<String,String> tmap){

        int nind = ind;
//        System.out.println("nind is "+nind);

        if(readin(in[nind])==0) {
            t.content = in[nind];
            nind++;
            return nind;
            }
        if(readin(in[nind])==1) {
            t.content = in[nind];
            nind++;
            //root.c = in 0
            t.l = new MuaTree();
            nind = readCommand(t.l,in,nind,min,tmap);
            //root.l = in[x]
            return nind;
            }
        if(readin(in[nind])==2) {
            t.content = in[nind];
            nind++;
            t.l = new MuaTree();
            nind = readCommand(t.l,in,nind,min,tmap);
            t.r = new MuaTree();
            nind = readCommand(t.r,in,nind,min,tmap);
            return nind;
            }
        if(readin(in[nind])==3) {
            //Triple op, only for if
            //need to reconstruct.
            t.content = in[nind];

            nind++;
            t.l = new MuaTree();
            nind = readCommand(t.l,in,nind,min,tmap);
            t.l.tocommand(tmap,min);

            //if true/else ==> ifelse/ iftrue
            if(!(t.l.content.equals("true")||t.l.content.equals("false"))) System.out.println("not a boolean");
            t.content = "if"+t.l.content;
            t.l = new MuaTree();
            nind = readCommand(t.l,in,nind,min,tmap);
            t.r = new MuaTree();
            nind = readCommand(t.r,in,nind,min,tmap);

            return nind;
        }
        if(readin(in[nind])==-1){
            String ts = "";
            if(min.hasNext()){
                ts = min.nextLine();
            }
                t.content = ts;
            nind ++;
            return nind;
        }
        System.out.println("readin return error");
        return -1;
        }



    MuaTree readList(MuaTree in, String[] ls, Scanner min,HashMap<String,String> tmap){
        int n = ls.length;
        int i = 0;
        //System.out.println(n);
            //till fill in all the elements
        //i = in.read(in,ls,i,n);
        // whole line into this function
        int cnt = 0;
        if(ls==null) return null;
        int flag = in.readCommand(in,ls,0,min,tmap);
        in.tocommand(tmap,min);
        //System.out.println(flag);
        while(flag!=ls.length){
            //restart from not analyzed point
            in = new MuaTree();
            flag = in.readCommand(in,ls,flag,min,tmap);
            in.tocommand(tmap,min);
            //System.out.println(flag);
        }
//        if(flag!=-1){
//            return in;
//        }
//        else{
//            System.out.println("error");
//            return in;
//        }
        return in;
    }

    void tocommand(HashMap<String,String> tmap,Scanner myin){
        //if
        if(this==null||this.content==null||this.content==" "||this.content==""){
            System.out.println("Empty content");
            return;
        }
        if(this.l!=null||this.r!=null){
            if(this.l!=null)
                this.l.tocommand(tmap,myin);
            if(this.r!=null)
                this.r.tocommand(tmap,myin);
        }
                    // null content
        if(this.content!=null&&this.content.equals("print")){
            //single word
            if(this.l.content.startsWith("\""))
            {
                System.out.println(this.l.content.substring(1));
                this.content = this.l.content;
                this.l = null;
                return;
            }
            if(this.l.content.startsWith(":"))
            {
                System.out.println(tmap.getOrDefault(this.l.content.substring(1), "No variables found!"));
                this.content = tmap.getOrDefault(this.l.content.substring(1),"No variables found!");
                this.l = null;
                return;
            }
            if(this.l.content.equals("false")||this.l.content.equals("true"))
            {
                System.out.println(this.l.content);
                this.content = this.l.content;
                this.l = null;
                return;
            }
            if(isNumber(this.l.content)){
                System.out.println(this.l.content);
                this.content = this.l.content;
                this.l = null;
                return;
            }
            if(this.l!=null)System.out.println("want to print "+this.l.content);
            if(this.l.l!=null)System.out.println("its left child"+this.l.l.content);
            if(this.l.r!=null)System.out.println("its right child"+this.l.r.content);
            System.out.println("Error:print command error");
            return;
        }
                    // print command


        if(this.content!=null&&this.content.equals("erase")){
            //single word
            if(this.l.content.startsWith("\""))
            {
                String ts = "";
                if(tmap.containsKey(this.l.content.substring(1)))
                {
                    ts = tmap.get(this.l.content.substring(1));
                    //tmap.remove(this.l.content.substring(1));
                    //*[cannot pass isempty last point, but others are good]
                    tmap.put(this.l.content.substring(1),null);
                }
                else
                    System.out.println("variable not exist");
                this.content = ts;
                //returned value maybe need to add " or : ?
                this.l = null;
                return;
            }
            if(this.l.content.startsWith(":"))
            {

//                System.out.println(tmap.getOrDefault(this.l.content.substring(1), "No variables found!"));
//                this.content = tmap.getOrDefault(this.l.content.substring(1),"No variables found!");
//                this.l.content = null;
                return;
            }
            if(isNumber(this.l.content)){
                System.out.println(this.l.content+"is a number!");
                return;
            }
            System.out.println("Error:erase command error");
            return;
        }
        // erase command

        if(this.content!=null&&this.content.equals("isname")){
            //single word
            if(this.l.content.startsWith("\""))
            {
                String flag = "true";
                String ts = this.l.content.substring(1);
                char c;
                if(! ( (ts.charAt(0)>='a'&&ts.charAt(0)<='z')||(ts.charAt(0)>='A'&&ts.charAt(0)<='Z') ) )
                    flag = "false";
                if(!isString(ts))
                    flag = "false";
                if(!tmap.containsKey(ts)||tmap.get(ts)==null)
                    flag = "false";
                this.content = flag;
                //returned value maybe need to add " or : ?
                this.l = null;
                return;
            }
            if(this.l.content.startsWith(":"))
            {

//                System.out.println(tmap.getOrDefault(this.l.content.substring(1), "No variables found!"));
//                this.content = tmap.getOrDefault(this.l.content.substring(1),"No variables found!");
//                this.l.content = null;
                return;
            }
            if(isNumber(this.l.content)){
                System.out.println(this.l.content+"is a number!");
                return;
            }
            System.out.println("Error:isname command error");
            return;
        }
        // isname command

        if(this.content!=null&&this.content.equals("isword")){
//            //single word
//            if(this.l.content.startsWith("\""))
//            {
//                String flag = "true";
//                String ts = this.l.content.substring(1);
//                char c;
//                if(! ( (ts.charAt(0)>='a'&&ts.charAt(0)<='z')||(ts.charAt(0)>='A'&&ts.charAt(0)<='Z') ) )
//                    flag = "false";
//                if(!isString(ts))
//                    flag = "false";
//                this.content = flag;
//                //returned value maybe need to add " or : ?
//                this.l = null;
//                return;
//            }
//            if(this.l.content.startsWith(":"))
//            {
//
////                System.out.println(tmap.getOrDefault(this.l.content.substring(1), "No variables found!"));
////                this.content = tmap.getOrDefault(this.l.content.substring(1),"No variables found!");
////                this.l.content = null;
//                return;
//            }
//            if(isNumber(this.l.content)){
//                System.out.println(this.l.content+"is a number!");
//                return;
//            }
            if(this.l.content.startsWith(":"))
            {
                this.content = "false";
                String a = tmap.get(this.l.content.substring(1));
                if((a.charAt(0)<=9&&a.charAt(0)>=0)||(a.charAt(0)<='Z'&&a.charAt(0)>='A')||(a.charAt(0)<='z'&&a.charAt(0)>='a'))
                    this.content = "true";
            }
                else{
            if(this.l.content.startsWith("\""))
                this.content = "true";
            else
                this.content = "false";}
            this.l= null;
            return;
        }
        // iswordcommand


        if(this.content!=null&&this.content.equals("islist")){
            String flag = "false";
            if(this.l.content.startsWith(":"))
            {
                String a = tmap.getOrDefault(this.l.content.substring(1),"error list");
                if(a.startsWith("[")&&a.endsWith("]"))
                    flag = "true";
            }
            else{
                if(this.l.content.startsWith("\"")){
                    flag = "false";
                }else{
                    if(this.l.content.startsWith("[")&&this.l.content.endsWith("]"))
                        flag = "true";
                }
            }
            this.content = flag;
            this.l= null;
            return;
        }
        // islistcommand




        if(this.content!=null&&this.content.equals("isempty")){
            //single word
            String flag = "true";
            if(this.l.content.startsWith("\""))
            {
                String ts = this.l.content.substring(1);

                if(tmap.containsKey(ts))
                    flag = "false";
                //*[ + Check if is empty list.]
                this.content = flag;
                //returned value maybe need to add " or : ?
                this.l = null;
                return;
            }
            if(this.l.content.startsWith(":"))
            {
                String ts = tmap.getOrDefault(this.l.content.substring(1),"not found(isempty)");
                if(ts.startsWith("[")&&ts.endsWith("]"))
                    flag = "false";
//                System.out.println(tmap.getOrDefault(this.l.content.substring(1), "No variables found!"));
//                this.content = tmap.getOrDefault(this.l.content.substring(1),"No variables found!");
//                this.l.content = null;
                this.content = flag;
                this.l = null;
                return;
            }
            if(isNumber(this.l.content)){
                System.out.println(this.l.content+"is a number!");
                this.content = flag;
                this.l = null;
                return;
            }
            System.out.println("Error:isname command error");
            this.content = "error node";
            return;
        }
        // isempty command



        if(this.content!=null&&this.content.equals("isbool")){
            //single word
            assert this.l != null;
            if(this.l.content.equals("false") || this.l.content.equals("true"))
                this.content = "true";
            else
                this.content = "false";
            this.l = null;
            return;
        }
        // isbool command


        if(this.content!=null&&this.content.equals("isnumber")){
            //single word
            if(this.l.content.startsWith("\""))
            {
                String flag = "true";
                String ts = this.l.content.substring(1);
                char c;
                if(!isNumber(ts))
                    flag = "false";
                this.content = flag;
                //returned value maybe need to add " or : ?
                this.l = null;
                return;
            }else
            if(this.l.content.startsWith(":"))
            {
                String flag = "false";
                if(tmap.containsKey(this.l.content.substring(1)))
                {
                    String ts = tmap.get(this.l.content.substring(1));
                    if(isNumber(ts)){
                        flag = "true";
                    }
                }
                this.content = flag;
                this.l = null;
                return;
            }else
                if(isNumber(this.l.content)){
                this.content = "true";
                this.l = null;
                return;
            }
                else
                {
                    this.content = "false";
                    this.l = null;
                    return;
                }
        }
        // isnumber command


        if(this.content!=null&&this.content.equals("thing")){
            // simple form
            if(this.l!=null&&this.l.l==null&&this.l.r==null){
                if(this.l.content.startsWith("\"")){
                    if(tmap.containsKey(this.l.content.substring(1))){
                        // reconstruct
                        this.content = tmap.get(this.l.content.substring(1));
                        this.l = null;
                        return;
                    }else{
                        System.out.println("Error: in thing command, no variables found!");
                        return;
                    }
                }
                if(this.l.content.startsWith(":")){
                    String ts = tmap.getOrDefault(this.l.content.substring(1),"*error");
                    //a
                    if(tmap.containsKey(ts)){
                        // reconstruct
                        this.content = tmap.get(ts);
                        this.l = null;
                        return;
                    }else{
                        System.out.println("Error: in thing command, no variables found!type2");
                        return;
                    }
                }
            }
        }
        // thing command


        if(this.content!=null&&this.content.equals("make")){
            // store variable and value into a map.
            if(this.l==null||this.r==null)
                return;
            if(this.r.content.startsWith("\"")){tmap.put(this.l.content.substring(1),this.r.content.substring(1));return;}
            if(this.r.content.startsWith(":")){tmap.put(this.l.content.substring(1),tmap.get(this.r.content.substring(1)));return;}
            tmap.put(this.l.content.substring(1),this.r.content);
            //System.out.println("variable:"+this.l.content.substring(1)+" value:"+this.r.content+" into map");
            return;
            }
                    // make command

        if(this.content!=null&&this.content.equals("eq")){
            double a=1,b=-1;
            String sa="not ini",sb="not ini2";
            String flag = "false";
            if(this.l.content.startsWith(":")||this.r.content.startsWith(":")) {
                sa = this.l.content.substring(1);
                sb = this.r.content.substring(1);
                if (this.l.content.startsWith(":")) {
                    sa = tmap.get(this.l.content.substring(1));
                }
                if (this.r.content.startsWith(":")) {
                    sb = tmap.get(this.r.content.substring(1));
                }
            }else
            {
                sa = this.l.content.substring(1);
                sb = this.r.content.substring(1);
            }
            //System.out.println(sa + " and " +sb);
            if(sa.equals(sb))
                flag = "true";
            else
                ;//
            // System.out.println(sa+"and"+sb);
            this.l = null;
            this.r = null;
            this.content = flag;
            return;
        }
        // eq command


        if(this.content!=null&&this.content.equals("lt")){
            double a=1,b=-1;
            String sa="not ini",sb="not ini2";
            String flag = "false";
            if(this.l.content.startsWith(":")||this.r.content.startsWith(":")) {
                if (this.l.content.startsWith(":")) {
                    sa = tmap.get(this.l.content.substring(1));
                }
                if (this.r.content.startsWith(":")) {
                    sb = tmap.get(this.r.content.substring(1));
                }
            }else
            {
                sa = this.l.content.substring(1);
                sb = this.r.content.substring(1);
            }
            //System.out.println(sa + " and " +sb);
            a = Double.parseDouble(sa);
            b = Double.parseDouble(sb);
            if(a<b)
                flag = "true";
            else
                ;//System.out.println(sa+"and"+sb);
            this.l = null;
            this.r = null;
            this.content = flag;
            return;
        }
        // lt command

        if(this.content!=null&&this.content.equals("gt")){
            double a=-11,b=-1;
            String sa="1",sb="2";
            String flag = "false";
            if(this.l.content.startsWith(":")||this.r.content.startsWith(":")) {
                if (this.l.content.startsWith(":")) {
                    sa = tmap.get(this.l.content.substring(1));
                }
                if (this.r.content.startsWith(":")) {
                    sb = tmap.get(this.r.content.substring(1));
                }
            }else
            {
                if(this.l.content.startsWith("\"")||this.r.content.startsWith("\""))
                {
                    if(isNumber(this.l.content.substring(1)))
                        sa = this.l.content.substring(1);
                    if(isNumber(this.r.content.substring(1)))
                        sb = this.r.content.substring(1);
                }else
                {
                    //nop
                    sa = this.l.content;
                    sb = this.r.content;
                    //System.out.println(sa + " and " + sb);
                }

            }
            //System.out.println(sa + " and " +sb);
            a = Double.parseDouble(sa);
            b = Double.parseDouble(sb);
            if(a>b)
                flag = "true";
            else
                ;//System.out.println(sa+"and"+sb);
            this.l = null;
            this.r = null;
            this.content = flag;
            return;
        }
        // gt command

        if(this.content!=null&&(this.content.equals("and")||this.content.equals("or"))){
            String flag = "false";

            if(this.content.equals("and")){
                if(this.l.content.equals("true") && this.r.content.equals("true"))
                    flag = "true";
            }else
            {
                //or
                if(this.l.content.equals("true") || this.r.content.equals("true"))
                    flag = "true";
            }

            this.content = flag;
            this.l = null;
            this.r = null;
            return;
        }
        // and / or command


        if(this.content!=null&&this.content.equals("not")){
            String flag = "undefined";
            assert this.l != null;
            if(this.l.content.equals("true")||this.l.content.equals("false")){
                if(this.l.content.equals("true"))
                    flag = "false";
                else
                    flag = "true";
            }else
            {
                flag = "error, not a boolean";
            }
            this.content = flag;
            this.l = null;
            this.r = null;
            return;
        }
        // not command

//        if(this.content!=null&&this.content.equals("read")){
//            // read something
//            Scanner tin = new Scanner(System.in);
//            String ts="";
//            if(tin.hasNext()){
//                ts = tin.next();
//            }
//            // reconstruct
//            this.content = "\""+ts;
//            System.out.println("read in"+ts);
//            return;
//        }
        // read command


        if(this.content!=null&&(this.content.equals("iffalse")||this.content.equals("iftrue"))){
            //single word
            assert this.l != null;
            String ts = "";

            if(this.content.equals("iftrue")){
                //choose left child
                ts = this.l.content.substring(1,this.l.content.length()-1);
                //delete [ from both sides
                this.l=null;
                this.r=null;
                this.readList(this,ts.split(" "),myin,tmap);
            }else{
                ts = this.r.content.substring(1,this.r.content.length()-1);
                //System.out.println("ts= "+ts);
                this.l=null;
                this.r=null;
                this.readList(this,ts.split(" "),myin,tmap);
            }
            this.tocommand(tmap,myin);
            this.l = null;
            this.r = null;
            return;
        }
        // ifelse / iftrue command

        if(this.content!=null&&(this.content.equals("run"))){
            //single word
            assert this.l != null;
            String ts = "";
            int flag2 = 0;
            //System.out.println(this.l.content);
            if(this.l.content.startsWith(":")){
                //retrieve from map first
                ts = tmap.get(this.l.content.substring(1));
                //System.out.println(ts);
                ts = ts.substring(1,ts.length()-1);
                //System.out.println("subts="+ts);
                //delete [ from both sides
                int flag = this.readCommand(this,ts.split(" "),0,myin,tmap);
                if(flag!=ts.split(" ").length)
                    flag2 = 1;
                this.l=null;
                this.readList(this,ts.split(" "),myin,tmap);
                //*[ do something if String still not ends
                if(flag2 == 1);

            }else{
                ts = this.l.content.substring(1,this.l.content.length()-1);
                //System.out.println("ts= "+ts);
                this.l=null;
                this.readList(this,ts.split(" "),myin,tmap);

            }
            this.tocommand(tmap,myin);
//            System.out.println("this.content = "+this.content);
//            if(this.l!=null)
//            {System.out.println("this.l.content = "+this.l.content);
//                if(this.l.l!=null)
//            System.out.println("this.l.l.content = "+this.l.l.content);
//                if(this.l.r!=null)
//            System.out.println("this.l.r.content = "+this.l.r.content);}
//            if(this.r!=null){
//            System.out.println("this.r.content = "+this.r.content);
//                if(this.r.l!=null)
//            System.out.println("this.r.l.content = "+this.r.l.content);
//                if(this.r.r!=null)
//            System.out.println("this.r.r.content = "+this.r.r.content);}

            return;
        }
        // run command


        if(this.content.equals("add")||this.content.equals("mul")||this.content.equals("div")||this.content.equals("sub")||this.content.equals("mod")){
            // store variable and value into a map.
            int fx,fy =0;
            double dx,dy =0;
            if(this.l==null||this.r==null) System.out.println("error in operator: no two operand");
            if(this.r.l==null&&this.r.r==null&&this.l.l==null&&this.r.r==null){
                if(this.l.content!=null&&this.l.content.startsWith(":"))
                {
//                    System.out.println(this.l.content+" source:");
                    this.l.content = tmap.get(this.l.content.substring(1));
//                    System.out.println("redirectl:"+this.l.content);
                }
                if(this.r.content.startsWith(":"))
                {
//                    System.out.println(this.r.content+" source:");
                    this.r.content = tmap.get(this.r.content.substring(1));
//                    System.out.println(" redirectr:"+this.r.content);
                }
                // :redirection
                if(this.l.content!=null&&this.l.content.startsWith("\"")&&isNumber(this.l.content.substring(1)))
                {

                    this.l.content = this.l.content.substring(1);

                }
                if(this.r.content!=null&&this.r.content.startsWith("\"")&&isNumber(this.r.content.substring(1)))
                {

                    this.r.content = this.r.content.substring(1);

                }
                // "redirection
                if(true||this.l.content.contains(".")||this.r.content.contains(".")){
                    // any operand that is double
                    Double a = Double.parseDouble(this.l.content);
                    dx = a;
                    Double b = Double.parseDouble(this.r.content);
                    dy = b;
                    double dz = 0;
                    if(this.content.equals("add")){
                        dz = a+b;
                    }if(this.content.equals("sub")){
                        dz = a-b;
                    }if(this.content.equals("mul")){
                        dz = a*b;
                    }if(this.content.equals("div")){
                        dz = a/b;
                    }if(this.content.equals("mod")){
                        dz = a%b;
                    }
                    this.content = Double.toString(dz);
                    this.l = null;
                    this.r = null;
                    return;
                }else{
                    // any operand that is double
                    Integer a = Integer.parseInt(this.l.content);
                    fx = a;
                    Integer b = Integer.parseInt(this.r.content);
                    fy = b;
                    int fz = 0;
                    if(this.content.equals("add")){
                        fz = a+b;
                    }if(this.content.equals("sub")){
                        fz = a-b;
                    }if(this.content.equals("mul")){
                        fz = a*b;
                    }if(this.content.equals("div")){
                        fz = a/b;
                    }if(this.content.equals("mod")){
                        fz = a%b;
                    }

                    this.content = Integer.toString(fz);
                    this.l = null;
                    this.r = null;
                    return;
                }
                //return;
            }
        }
        // make command
        if(isNumber(this.content)||isString(this.content)||this.content.startsWith("\"")||this.content.startsWith(":")||this.content.startsWith("[")){
            // not a command, only word or number or else
            return;
        }
        if(this.content.startsWith("(")&&this.content.endsWith(")")){
            //How to deconstruct a expression?
            //this.content = (5%3-3*3/(5+4))
            //System.out.println(this.content);
            if(this.content.contains("add")){
                String ts = this.content.substring(1,this.content.length()-1);
                this.readList(this,ts.split(" "),myin,tmap);
                //System.out.println(this.l.content);
                return;
            }
            else
            {
                double ans = domath(this.content,tmap);
                this.content = ""+ans;
                return;
            }
        }
        System.out.println("Error: toCommand "+this.content);
    }
}