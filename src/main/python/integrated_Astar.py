
def aStarAlgo(start_node, stop_node):

        open_set = set(start_node)
        closed_set = set()
        g = {} #store distance from starting node
        parents = {}# parents contains an adjacency map of all nodes

        #distance of starting node from itself is zero
        g[start_node] = 0
        #start_node is root node i.e it has no parent nodes
        #so start_node is set to its own parent node
        parents[start_node] = start_node

        while len(open_set) > 0:
            n = None

            #node with lowest f() is found
            for v in open_set:
                if n == None or g[v] + heuristic(v, stop_node) < g[n] + heuristic(n, stop_node):
                    n = v


            if n == stop_node or Graph_nodes[n] == None:
                pass
            else:
                for (m, weight) in get_neighbors(n):
                    #nodes 'm' not in first and last set are added to first
                    #n is set its parent
                    if m not in open_set and m not in closed_set:
                        open_set.add(m)
                        parents[m] = n
                        g[m] = g[n] + weight



                    #for each node m,compare its distance from start i.e g(m) to the
                    #from start through n node
                    else:
                        if g[m] > g[n] + weight:
                            #update g(m)
                            g[m] = g[n] + weight
                            #change parent of m to n
                            parents[m] = n
                            #if m in closed set,remove and add to open
                            if m in closed_set:
                                closed_set.remove(m)
                                open_set.add(m)


            if n == None:
                print('Path does not exist!')
                return None

            # if the current node is the stop_node
            # then we begin reconstructin the path from it to the start_node
            if n == stop_node:
                path = []

                while parents[n] != n:
                    path.append(n)
                    n = parents[n]

                path.append(start_node)

                path.reverse()

                print('Path found: {}'.format(path))
                #print('Path Cost: ', g[v])
                #print('Path Cost: ', g[n]+g[v])
                distance_total=g[n]+g[v]
                print('Total distance: ', distance_total)
                ##This is for fare cost code
                fare = 13
                if distance_total <= 5:
                    print(fare)
                else:
                    for i in range(6,distance_total+1):
                        if i % 5 == 3:
                            fare += 2
                        else:
                            fare += 2.25
                    print("Fare to be paid = ",fare)

                return path


            # remove n from the open_list, and add it to closed_list
            # because all of his neighbors were inspected
            open_set.remove(n)
            closed_set.add(n)

        print('Path does not exist!')
        return None

#define fuction to return neighbor and its distance
#from the passed node
def get_neighbors(v):
    if v in Graph_nodes:
        return Graph_nodes[v]
    else:
        return None

def manhattan(node1, node2):
    return sum(abs(val1-val2) for val1, val2 in zip(node1,node2))

def heuristic(n, stop_node):
    ANode = (14.410699895415755, 121.0380546798303) #FEU ALABANG DROP-OFF POINT
    BNode= (14.41755, 121.04052) # FESTIVAL MALL DROP-OFF POINT
    CNode = (14.424237250117503, 121.031766999137)
    DNode = (14.53414433, 120.9981471)
    ENode = (14.52223473, 120.9967539)
    FNode = (14.55438577, 120.9851946)
    GNode = (14.41264687, 120.9985847)
    HNode = (14.48550503, 120.9850211)
    INode = (14.57926916, 120.9985623)

    #FEUA Drop-off Point
    A_heuristic = {
            'A': manhattan(ANode, ANode),
            'B': manhattan(BNode, ANode),
            'C': manhattan(CNode, ANode),
            'D': manhattan(DNode, ANode),
            'E': manhattan(ENode, ANode),
            'F': manhattan(FNode, ANode),
            'G': manhattan(GNode, ANode),
            'H': manhattan(HNode, ANode),
            'I': manhattan(INode, ANode)
        }
    B_heuristic = {
            'A': manhattan(ANode, BNode),
            'B': manhattan(BNode, BNode),
            'C': manhattan(CNode, BNode),
            'D': manhattan(DNode, BNode),
            'E': manhattan(ENode, BNode),
            'F': manhattan(FNode, BNode),
            'G': manhattan(GNode, BNode),
            'H': manhattan(HNode, BNode),
            'I': manhattan(INode, BNode)
            }
    C_Heuristic = {
            'A': manhattan(ANode, CNode),
            'B': manhattan(BNode, CNode),
            'C': manhattan(CNode, CNode),
            'D': manhattan(DNode, CNode),
            'E': manhattan(ENode, CNode),
            'F': manhattan(FNode, CNode),
            'G': manhattan(GNode, CNode),
            'H': manhattan(HNode, CNode),
            'I': manhattan(INode, CNode)
            }
    D_Heuristic = {
            'A': manhattan(ANode, DNode),
            'B': manhattan(BNode, DNode),
            'C': manhattan(CNode, DNode),
            'D': manhattan(DNode, DNode),
            'E': manhattan(ENode, DNode),
            'F': manhattan(FNode, DNode),
            'G': manhattan(GNode, DNode),
            'H': manhattan(HNode, DNode),
            'I': manhattan(INode, DNode)
            }
    E_Heuristic = {
            'A': manhattan(ANode, ENode),
            'B': manhattan(BNode, ENode),
            'C': manhattan(CNode, ENode),
            'D': manhattan(DNode, ENode),
            'E': manhattan(ENode, ENode),
            'F': manhattan(FNode, ENode),
            'G': manhattan(GNode, ENode),
            'H': manhattan(HNode, ENode),
            'I': manhattan(INode, ENode)
            }
    F_Heuristic = {
            'A': manhattan(ANode, FNode),
            'B': manhattan(BNode, FNode),
            'C': manhattan(CNode, FNode),
            'D': manhattan(DNode, FNode),
            'E': manhattan(ENode, FNode),
            'F': manhattan(FNode, FNode),
            'G': manhattan(GNode, FNode),
            'H': manhattan(HNode, FNode),
            'I': manhattan(INode, FNode)
            }
    G_Heuristic = {
            'A': manhattan(ANode, GNode),
            'B': manhattan(BNode, GNode),
            'C': manhattan(CNode, GNode),
            'D': manhattan(DNode, GNode),
            'E': manhattan(ENode, GNode),
            'F': manhattan(FNode, GNode),
            'G': manhattan(GNode, GNode),
            'H': manhattan(HNode, GNode),
            'I': manhattan(INode, GNode)
            }
    H_Heuristic = {
            'A': manhattan(ANode, HNode),
            'B': manhattan(BNode, HNode),
            'C': manhattan(CNode, HNode),
            'D': manhattan(DNode, HNode),
            'E': manhattan(ENode, HNode),
            'F': manhattan(FNode, HNode),
            'G': manhattan(GNode, HNode),
            'H': manhattan(HNode, HNode),
            'I': manhattan(INode, HNode)
            }
    I_Heuristic = {
            'A': manhattan(ANode, INode),
            'B': manhattan(BNode, INode),
            'C': manhattan(CNode, INode),
            'D': manhattan(DNode, INode),
            'E': manhattan(ENode, INode),
            'F': manhattan(FNode, INode),
            'G': manhattan(GNode, INode),
            'H': manhattan(HNode, INode),
            'I': manhattan(INode, INode)
            }

    if stop_node == 'A':
        return A_heuristic[n]
    elif stop_node == 'B':
        return B_heuristic[n]
    elif stop_node == 'C':
        return C_Heuristic[n]
    elif stop_node == 'D':
        return D_Heuristic[n]
    elif stop_node == 'E':
        return E_Heuristic[n]
    elif stop_node == 'F':
        return F_Heuristic[n]
    elif stop_node == 'G':
        return G_Heuristic[n]
    elif stop_node == 'H':
        return H_Heuristic[n]
    elif stop_node == 'I':
        return I_Heuristic[n]

#Describe your graph here
Graph_nodes = {
    'A': [('B', 5), ('C', 8), ('E', 8)],
    'B': [('A', 5), ('C', 4)],
    'C': [('A', 8), ('B', 4)],
    'D': [('E', 1), ('H', 9)],
    'E': [('A', 8), ('D', 1), ('I', 7)],
    'F': [('G', 3), ('I', 8)],
    'G': [('F', 3), ('H', 4)],
    'H': [('G', 4), ('D', 9)],
    'I': [('E', 7), ('F', 8)]
}
def main():
    source = input("Enter source node: ")
    target = input("Enter target node: ")
    aStarAlgo(source, target)
