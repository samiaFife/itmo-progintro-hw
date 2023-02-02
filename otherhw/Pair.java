package otherhw;

import java.util.Arrays;

public class Pair {
    public String s;
    public int n;

    Pair(String s, int n) {
        this.s = s;
        this.n = n;
    }

    public static int bigger(String s1, String s2) {
        if (s1.equals(s2)) {
            return 3;
        }
        int len1 = s1.length(), len2 = s2.length();
        for (int i = 0; i < Math.min(len1, len2); i++) {
            if (s1.charAt(i) > s2.charAt(i)) {
                return 1;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                return 2;
            } else {
                if (i == len1 - 1) {
                    return 2;
                } else if (i == len2 - 1) {
                    return 1;
                }
            }
        }
        return 3;
    }

    public static void merge(Pair[] a, int l, int m, int r) {
        Pair[] res = new Pair[r - l];
        Arrays.fill(res, new Pair("", 0));
        int it1 = 0, it2 = 0;
        while (l + it1 < m && m + it2 < r) {
            if (bigger(a[l + it1].s, a[m + it2].s) == 2) {
                res[it1 + it2] = a[l + it1];
                it1++;
            } else {
                res[it1 + it2] = a[m + it2];
                it2++;
            }
        }
        while (l + it1 < m) {
            res[it1 + it2] = a[l + it1];
            it1++;
        }
        while (m + it2 < r) {
            res[it1 + it2] = a[m + it2];
            it2++;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[l + i] = res[i];
        }
    }

    public static void mergeSort(Pair[] a, int l, int r) {
        if (l + 1 >= r) {
            return;
        }
        int m = (l + r) / 2;
        mergeSort(a, l, m);
        mergeSort(a, m, r);
        merge(a, l, m, r);
    }
}
