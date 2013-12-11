arr = [3, 4, 5, 6, 7, 8, 9, 10, 1, 2]


def find(arr, elem):
    lo = 0
    hi = len(arr) - 1
    while lo < hi:
        mid = (hi + lo) // 2
        first = arr[lo]
        middle = arr[mid]
        last = arr[hi]
        
        if first <= middle and elem >= first and elem <= middle:
            return binary_search(arr, elem, lo, mid)
        elif last >= middle and elem >= middle and elem <= last:
            return binary_search(arr, elem, mid+1, hi)
        elif elem >= first and elem <= middle:
            hi = mid
        else:
            lo = mid + 1

    if arr[lo] == elem:
        return lo
    return None


def binary_search(arr, elem, lo, hi):
    while lo < hi:
        mid = (hi + lo) // 2
        if arr[mid] < elem:
            lo = mid + 1
        else:
            hi = mid
    if arr[lo] == elem:
        return lo
    return None


print find(arr, 0)  # 8
