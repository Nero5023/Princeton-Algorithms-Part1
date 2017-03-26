class PathCompMaxUF():
    """docstring for PathCompUF"""
    def __init__(self, n):
        self.parent = [i for i in range(0,n)]

    def validate(self, p):
        n = len(self.parent)
        if p < 0 or p > n:
            raise IndexError("Index out of bound", p)

    def find(self, p):
        self.validate(p)
        while p != self.parent[p]:
            self.parent[p] = self.parent[self.parent[p]]
            p = self.parent[p]
        return p
        
    def connected(self, p, q):
        return self.find(p) == self.find(q)

    def union(self, p, q):
        rootP = self.find(p);
        rootQ = self.find(q);
        if rootP > rootQ:
            self.parent[rootQ] = rootP
        else:
            self.parent[rootP] = rootQ


if __name__ == '__main__':
    p = PathCompMaxUF(10)
    p.union(1, 2)
    p.union(9, 2)
    p.union(1, 5)
    p.union(7, 5)
    print(p.find(1))
    print(p.find(2))
    print(p.find(5))
    print(p.find(7))
    print(p.find(9))
