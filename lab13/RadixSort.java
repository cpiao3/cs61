/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {

        String[] sorted = new String[asciis.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < asciis.length ; i += 1){
            sorted[i] = asciis[i];
            if (asciis[i].length() > max){
                max = asciis[i].length();
            }
        }


        for (int b = 0; b < asciis.length ; b += 1){
            if (sorted[b].length() < max){
                for (int c = 0; c< max - sorted[b].length(); c += 1) {
                    sorted[b] += " ";
                }
            }
        }
        for (int j = 1; j<=max ; j += 1){
            sortHelperLSD(sorted,j);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int [] count = new int[256];
        String[] record = new String[asciis.length];
        for (int i = 0 ; i < asciis.length ; i += 1){
            int a = asciis[i].charAt(asciis[i].length()-index);
            count[a] += 1;
            record[i] = asciis[i];
        }

        int lastcount = 0;
        int lastdigit = 0;
        for (int c = 0; c < 256 ; c += 1){
            lastdigit = count[c];
            count[c] = lastcount;
            lastcount += lastdigit;
        }

        for (int j = 0 ; j < asciis.length ; j += 1){
            String word = record[j];
            int pos = word.charAt(word.length()-index);
            asciis[count[pos]] = word;
            count[pos] += 1;
        }

        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String a = "csd";
        String c = "abe";
        String e = "ab";
        String d = "hde";

        String[] arr = new String[4];
        arr[0] = a;
        arr[1] = c;
        arr[2] = d;
        arr[3] = e;
        String[] result = sort(arr);

        for (int p = 0 ; p < result.length ; p+=1){
            System.out.println(result[p]);
        }
    }
}
