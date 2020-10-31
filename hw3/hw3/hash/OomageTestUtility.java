package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.*;


import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        LinkedList<Oomage>[] a = new LinkedList[M];
        for (int i = 0; i < oomages.size(); i += 1) {
            int bucketNum = (oomages.get(i).hashCode() & 0x7FFFFFFF) % M;
            if (a[bucketNum]==null){
                a[bucketNum] = new LinkedList<Oomage>();
            }
            a[bucketNum].addFirst((Oomage) oomages.get(i));
        }
        for (int i = 0;i<a.length;i++){
            if (a[i]!=null) {
                if (oomages.size() / 50 > a[i].size() || oomages.size() / 2.5 < a[i].size()) {
                    return false;
                }
            }
        }
        return true;
    }

}
