import PathCompMaxUF

class SuccessorWithDelete():
    """docstring for SuccessorWithDelete"""
    def __init__(self, n):
        self.count = n;
        self.maxUF = PathCompMaxUF.PathCompMaxUF(n+1);


    def validate(self, x):
        if x >= self.count:
            raise IndexError("Index out of bound", x)

    def remove(self, x):
        self.validate(x)
        self.maxUF.union(x, x+1)

    def checkIsRemoved(self, x):
        root = self.maxUF.find(x)
        if root == x:
            return False
        else:
            return True

    # if there is no sussor return -1
    def successor(self, x):
        self.validate(x)
        if self.checkIsRemoved(x):
            raise IndexError("%s have been removed" % (x,))
        nextEle = self.maxUF.find(x+1)
        if nextEle >= self.count:
            return -1
        return nextEle

if __name__ == '__main__':
    obj = SuccessorWithDelete(10)
    obj.remove(2)
    assert obj.successor(1) == 3, "After removed 2, 1's successor should be 3"
    obj.remove(4)
    obj.remove(3)
    assert obj.successor(1) == 5, "After removed 2,3,4, 1's successor should be 5"