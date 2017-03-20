class WeightQuickUnionUF():
    """docstring for WeightQuickUnionUF"""
    def __init__(self, n):
        self.count  = n
        self.parent = [i for i in range(0, n)]
        self.size   = [1 for _ in range(0, n)]
        self.maxCom = [i for i in range(0, n)] 

    def validate(self, p):
        n = len(self.parent)
        if p < 0 or p > n:
            raise IndexError("Index out of bound", p)

    def find(self, p):
        self.validate()
        while p != self.parent[p]:
            p = parent[p]
        return p

    def findMax(self, p):
        self.validate()
        while p != self.maxCom[p]:
            p = self.maxCom[p]
        return p

    def connected(self, p, q):
        return self.find(p) == self.find(q)

    def union(self, p, q):
        rootP = find(p);
        rootQ = find(q);
        if rootQ == rootP:
            return

        if self.size(rootP) < self.size(rootQ):
            self.parent[rootP] = rootQ
            self.size[rootQ] += self.size[rootP]
        else:
            self.parent[rootQ] = rootP
            self.size[rootP] += self.size[rootQ]

        maxP = findMax(p);
        maxQ = findMax(q);
        if maxP > maxQ:
            self.maxCom[maxQ] = maxP
        else:
            self.maxCom[maxP] = maxQ

        self.count -= 1


if __name__ == '__main__':
