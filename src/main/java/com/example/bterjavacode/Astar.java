package com.example.bterjavacode;
import com.example.bterjavacode.Pair;

import java.util.*;
import java.io.*;

public class Astar{
    static final Map<Character,Pair[]> Graph_nodes = new HashMap<Character,Pair[]>();
    private static String finalPath;
    static{
        Graph_nodes.put('A',new Pair[] {new Pair('B',3), new Pair('H', 1)});
        Graph_nodes.put('B',new Pair[] {new Pair('A',3),new Pair('C',2),new Pair('E',1),new Pair('F',1),new Pair('G',1), new Pair('H', 4)});
        Graph_nodes.put('C',new Pair[] {new Pair('B',2),new Pair('D',3),new Pair('E',1),new Pair('F',1),new Pair('G',1)});
        Graph_nodes.put('D',new Pair[] {new Pair('C',3)});
        Graph_nodes.put('E',new Pair[] {new Pair('B',1),new Pair('C',1),new Pair('F',1),new Pair('G',1)});
        Graph_nodes.put('F',new Pair[] {new Pair('B',1),new Pair('C',1),new Pair('E',1),new Pair('G',1)});
        Graph_nodes.put('G',new Pair[] {new Pair('B',1),new Pair('C',1),new Pair('E',1),new Pair('F',1)});
        Graph_nodes.put('H', new Pair[]{new Pair('A',1), new Pair('B', 4)});

    }


    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter mode of calculation: [1] for shortest path [2] for cheapest path:");
        int mode = scanner.nextInt();
        scanner.nextLine();
        String source,target;

        if(mode==1){
            System.out.println("Enter source node: ");
            source = scanner.nextLine();
            // source = source.toUpperCase();
            System.out.println("Enter target node: ");
            target = scanner.nextLine();
            // target = target.toUpperCase();


        }
        scanner.close();
    }

    public static String aStarAlgo(char start_node, char stop_node){
        Set<Character> open_set = new HashSet<Character>();
        open_set.add(start_node);
        Set<Character> close_set = new HashSet<Character>();
        Map<Character,Double> g = new HashMap<Character,Double>();
        g.put(start_node,0.0);
        Map<Character,Character> parent = new HashMap<>();
        parent.put(start_node,start_node);

        while(open_set.size() > 0){
            char n = ' ';

            for(char v:open_set){
                if(n==' ' || g.get(v)+heuristic(v, stop_node)< g.get(n)+heuristic(n, stop_node)){
                    n = v;
                }
            }
            if(n==stop_node || Graph_nodes.get(n) == null){
                ;
            }
            else{
                for(Pair x : getNeighbor(n)){
                    if(!open_set.contains(x.key()) && !close_set.contains(x.key())){
                        open_set.add(x.key());
                        parent.put(x.key(), n);
                        g.put(x.key(),(g.get(n)+x.value()));

                    }
                    else{
                        if(g.get(x.key()) > g.get(n)+ x.value()){
                            g.put(x.key(),g.get(n)+x.value());
                            parent.put(x.key(),n);
                            if(close_set.contains(x.key())){
                                close_set.remove(x.key());
                                open_set.add(x.key());
                            }
                        }
                    }
                }
            }
            if(n==' '){
                System.out.println("Path does not Exist!");
                return null;
            }
            if(n == stop_node){
                List<Character> path = new ArrayList<Character>();
                while(parent.get(n) != n){
                    path.add(n);
                    n = parent.get(n);
                }
                path.add(start_node);
                Collections.reverse(path);

                int counter = 0;
                System.out.println(path.size());
                finalPath = "Path found: [";
                for(char node:path){
                    counter++;
                    if(counter == path.size()){
                        finalPath += (convertChar(node));
                    }
                    else{
                        finalPath += (convertChar(node)+", ");
                    }
                }
                finalPath += "]";
                return finalPath;
            }
            open_set.remove(n);
            close_set.add(n);
        }
        System.out.println("Path does not exist!");
        return null;


    }

    public static String convertChar(char x){
        String place = "";
        if(x=='A'){
            place = "SOUTH STATION";
        }
        else if(x=='B'){
            place = "VILLAGE SQUARE";
        }
        else if(x=='C'){
            place = "PUREGOLD";
        }
        else if(x=='D'){
            place = "PERPETUAL HELP HOSPITAL";
        }
        else if(x=='E'){
            place = "MAMA LOU'S";
        }
        else if(x=='F'){
            place = "EL GRANDE";
        }
        else if(x=='G'){
            place = "BF SOUTHLAND CLASSIC";
        }
        else if(x == 'H'){
            place = "FESTIVAL MALL";
        }
        return place;

    }

    public static Pair[] getNeighbor(char v){
        return Graph_nodes.get(v);
    }
    public static double manhattan(double[] node1,double[] node2){
        double val1 = Math.abs(node1[0]-node2[0]);
        double val2 = Math.abs(node1[1]-node2[1]);
        double ans = val1+val2;
        return ans;
    }
    public static double heuristic(char n, char stop_node){
        double[] ANode = {14.421636643674436, 121.04315613656041};
        double[] BNode = {14.429156204261789, 121.02041268141576};
        double[] CNode = {14.438055604532122, 121.00458172498732};
        double[] DNode = {14.448575259930099, 120.98579938040515};
        double[] ENode = {14.448632090532035, 121.01004273738914};
        double[] FNode = {14.455960461169026, 121.00753827328728};
        double[] GNode = {14.44624773037558, 121.00777026893012};
        double[] HNode = {14.41755, 121.04052};

        Map<Character,Double>A_heuristic = new HashMap<Character,Double>();
        A_heuristic.put('A', manhattan(ANode, ANode));
        A_heuristic.put('B', manhattan(BNode, ANode));
        A_heuristic.put('C', manhattan(CNode, ANode));
        A_heuristic.put('D', manhattan(DNode, ANode));
        A_heuristic.put('E', manhattan(ENode, ANode));
        A_heuristic.put('F', manhattan(FNode, ANode));
        A_heuristic.put('G', manhattan(GNode, ANode));
        A_heuristic.put('H', manhattan(HNode, ANode));

        Map<Character,Double>B_heuristic = new HashMap<Character,Double>();
        B_heuristic.put('A', manhattan(ANode, BNode));
        B_heuristic.put('B', manhattan(BNode, BNode));
        B_heuristic.put('C', manhattan(CNode, BNode));
        B_heuristic.put('D', manhattan(DNode, BNode));
        B_heuristic.put('E', manhattan(ENode, BNode));
        B_heuristic.put('F', manhattan(FNode, BNode));
        B_heuristic.put('G', manhattan(GNode, BNode));
        B_heuristic.put('H', manhattan(HNode, BNode));

        Map<Character,Double> C_heuristic = new HashMap<Character,Double>();
        C_heuristic.put('A', manhattan(ANode, CNode));
        C_heuristic.put('B', manhattan(BNode, CNode));
        C_heuristic.put('C', manhattan(CNode, CNode));
        C_heuristic.put('D', manhattan(DNode, CNode));
        C_heuristic.put('E', manhattan(ENode, CNode));
        C_heuristic.put('F', manhattan(FNode, CNode));
        C_heuristic.put('G', manhattan(GNode, CNode));
        C_heuristic.put('H', manhattan(HNode, CNode));

        Map<Character,Double>D_heuristic = new HashMap<Character,Double>();
        D_heuristic.put('A', manhattan(ANode, DNode));
        D_heuristic.put('B', manhattan(BNode, DNode));
        D_heuristic.put('C', manhattan(CNode, DNode));
        D_heuristic.put('D', manhattan(DNode, DNode));
        D_heuristic.put('E', manhattan(ENode, DNode));
        D_heuristic.put('F', manhattan(FNode, DNode));
        D_heuristic.put('G', manhattan(GNode, DNode));
        D_heuristic.put('H', manhattan(HNode, DNode));

        Map<Character,Double>E_heuristic = new HashMap<Character,Double>();
        E_heuristic.put('A', manhattan(ANode, ENode));
        E_heuristic.put('B', manhattan(BNode, ENode));
        E_heuristic.put('C', manhattan(CNode, ENode));
        E_heuristic.put('D', manhattan(DNode, ENode));
        E_heuristic.put('E', manhattan(ENode, ENode));
        E_heuristic.put('F', manhattan(FNode, ENode));
        E_heuristic.put('G', manhattan(GNode, ENode));
        E_heuristic.put('H', manhattan(HNode, ENode));

        Map<Character,Double>F_heuristic = new HashMap<Character,Double>();
        F_heuristic.put('A', manhattan(ANode, FNode));
        F_heuristic.put('B', manhattan(BNode, FNode));
        F_heuristic.put('C', manhattan(CNode, FNode));
        F_heuristic.put('D', manhattan(DNode, FNode));
        F_heuristic.put('E', manhattan(ENode, FNode));
        F_heuristic.put('F', manhattan(FNode, FNode));
        F_heuristic.put('G', manhattan(GNode, FNode));
        F_heuristic.put('H', manhattan(HNode, FNode));

        Map<Character,Double>G_heuristic = new HashMap<Character,Double>();
        G_heuristic.put('A', manhattan(ANode, GNode));
        G_heuristic.put('B', manhattan(BNode, GNode));
        G_heuristic.put('C', manhattan(CNode, GNode));
        G_heuristic.put('D', manhattan(DNode, GNode));
        G_heuristic.put('E', manhattan(ENode, GNode));
        G_heuristic.put('F', manhattan(FNode, GNode));
        G_heuristic.put('G', manhattan(GNode, GNode));
        G_heuristic.put('H', manhattan(HNode, GNode));

        Map<Character, Double>H_heuristic = new HashMap<Character, Double>();
        H_heuristic.put('A', manhattan(ANode, HNode));
        H_heuristic.put('B', manhattan(BNode, HNode));
        H_heuristic.put('C', manhattan(CNode, HNode));
        H_heuristic.put('D', manhattan(DNode, HNode));
        H_heuristic.put('E', manhattan(ENode, HNode));
        H_heuristic.put('F', manhattan(FNode, HNode));
        H_heuristic.put('G', manhattan(GNode, HNode));
        H_heuristic.put('H', manhattan(HNode, HNode));

        if (stop_node == 'A')
            return A_heuristic.get(n);
        else if (stop_node == 'B')
            return B_heuristic.get(n);
        else if (stop_node == 'C')
            return C_heuristic.get(n);
        else if (stop_node == 'D')
            return D_heuristic.get(n);
        else if (stop_node == 'E')
            return E_heuristic.get(n);
        else if (stop_node == 'F')
            return F_heuristic.get(n);
        else if (stop_node == 'G')
            return G_heuristic.get(n);
        else if (stop_node == 'H')
            return H_heuristic.get(n);
        else{return -1;}
    }
}