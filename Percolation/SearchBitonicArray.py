def searchBitonicArray(biArray, target):
    def iter(lo, hi):
        if lo > hi:
            return False
        mid = int((lo+hi)/2)
        if lo == hi:
            return biArray[mid] == target
        if biArray[mid] == target:
            return True
        # if biArray[mid] <= biArray[lo]:
        #     if biArray[mid] > target:
        #         return iter(mid+1, hi)
        #     else:
        #         return iter(lo, mid-1)
        # else:  #biArray[mid] > biArray[lo]
        if biArray[mid] > biArray[mid+1] and biArray[mid] > biArray[mid-1]:
            return iter(lo, mid-1) or iter(mid+1, hi)
        if biArray[mid] > biArray[mid+1]:
            if biArray[mid] < target:
                return iter(lo, mid-1)
            else:
                return iter(mid+1, hi)
        else:
            if biArray[mid] < target:
                return iter(mid+1, hi)
            else:
                return iter(lo, mid-1)
    return iter(0, len(biArray)-1)

if __name__ == '__main__':
    array = [1, 2, 3, 4, 5, 15, 10, 9, 8, 7, 6]
    print(searchBitonicArray(array, 1))