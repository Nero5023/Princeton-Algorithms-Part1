def threeSum(targetSum, inputArrays):
    inputArrays.sort()
    results = []
    print inputArrays
    for index in range(0, len(inputArrays)-2):
        leftSum = targetSum - inputArrays[index]
        i = index + 1;
        j = len(inputArrays) - 1;
        while i < j:
            diff = inputArrays[i] + inputArrays[j] - leftSum
            if diff == 0:
                results.append((inputArrays[index], inputArrays[i], inputArrays[j]))
                break  # assume that there is no equal elements
            if diff < 0:
                i += 1
            else:
                j -= 1
    return results

if __name__ == '__main__':
    print sorted([4,5,1,6,7,9,32,-1,0,2, 3])
    print threeSum(7, [4,5,1,6,7,9,32,-1,0,2, 3])
    # assert threeSum(7, [4,5,1,6,7,9,32,-1,0,2])