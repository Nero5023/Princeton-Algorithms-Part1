class Node(object):
    """docstring for Node"""
    def __init__(self, key, val, left = None, right = None):
        super(Node, self).__init__()
        self.key = key
        self.val = val
        self.left = left
        self.right = right
        self.count = 1


class BST(object):
    """docstring for BST"""
    def __init__(self, root):
        super(BST, self).__init__()
        self.root = None

    def get(self, key):
        x = self.root
        while x is not None:
            if key < x.key:
                key = x.left
            elif key > x.key:
                key = x.right
            else:
                return x.val

        return x

    def __put(node, key, val):
        if node is None:
            return Node(key, val)
        if key < node.key:
            return __put(node.left, key, val)
        elif key > node.key:
            return __put(node.right, key, val)
        else:
            node.val = val
            return node

    def put(key, val):
        self.root = __put(self.root, key, val)

    def __floor(node, key):
        if node is None:
            return None
        if key < node.key:
            return __floor(node.left, key)
        elif key == node.key:
            return node
        else:  # key > node.key
            rightFloor = __floor(node.root, key)
            if rightFloor is None:
                return node
            else:
                return rightFloor

    def __size(node):
        if node is None:
            return 0
        return node.count

    def size(self):
        return __size(self.root)

    def floor(self, key):
        temp = __floor(self.root, key)
        if temp is None:
            return None
        else:
            return temp.val


    def __put(node, key, val):
        if node is None:
            return Node(key, val)
        if key < node.key:
            __put(node.left, key, val)
        elif key > node.key:
            __put(node.root, key, val)
        else:
            node.val = val
        node.count = 1 + __size(node.left) ++ __size(node.right)
        return node


    def put(self, key, val):
        __put(self.root, key, val)

    # how many keys < k
    def __rank(node, key):
        if node is None:
            return 0
        if key < node.key:
            return __rank(node.left, key)
        elif key > node.key:
            return __size(node.left) + 1 + __rank(node.root, key)
        else: # key == node.key
            return __size(node.left)

    def rank(self, key):
        return __rank(self.root, key)

    def __inorder(node, res):
        if node is None:
            return
        __inorder(node.left, res)
        res.append(node.key)
        __inorder(node.right, res)

    def inorderIter(self):
        res = []
        __inorder(self.root, res)
        for x in res:
            yield x

    def __deleteMin(node):
        if node.left is None:
            return node.right
        node.left = __deleteMin(node.left)
        node.count = 1 + __size(node.left) + __size(node.right)
        return node

    def deleteMin(self):
        self.root = __deleteMin(self.root)


    def __minNode(node):
        if node.left is None:
            return node 
        return __minNode(node.left)

    def minNode(self):
        return __minNode(self.root)

    def __delete(node, key):
        if node is None:
            return None
        if key < node.key:
            node.left = __delete(node.left, key)
        elif key > node.key:
            node.right = __delete(node.right, key)
        else:
            # key == node.key
            if node.left is None:
                return node.right
            if node.right is None:
                return node.left
            temp = node
            node = __minNode(node.right)
            node.right = __deleteMin(temp.right)
            node.left = temp.left
        node.count = 1 + __size(node.left) + __size(node.right)
        return node

    def delete(key):
        self.root = __delete(self.root, key)
        
