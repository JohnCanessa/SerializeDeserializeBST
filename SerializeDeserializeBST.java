import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Design an algorithm to serialize and deserialize a binary search tree.
 * 
 * Runtime: 14 ms, faster than 20.04% of Java online submissions
 * Memory Usage: 39.6 MB, less than 82.93% of Java online submissions.
 * 
 * Runtime: 10 ms, faster than 36.77% of Java online submissions.
 * Memory Usage: 39.6 MB, less than 89.01% of Java online submissions.
 */
class Codec {


    /**
     * Encodes a BST to a single string.
     */
    public String serialize(TreeNode root) {

        // **** initialization ****
        StringBuilder sb = new StringBuilder();

        // **** recursive call (implements DFS) ****
        serialize(root, sb);

        // **** return trimmed string ****
        return sb.toString().trim();
    }


    /**
     * Auxiliary recursive call implements a preorder DFT.
     */
    private void serialize(TreeNode root, StringBuilder sb) {

        // **** end / base condition ****
        if (root == null)
            return;

        // **** append node to string builder ****
        sb.append(root.val + " ");

        // **** traverse left subtree ****
        serialize(root.left, sb);

        // **** traverse right subtree ****
        serialize(root.right, sb);
    }


    /**
     * Decodes your encoded data to tree.
     * 
     * Entry point for recursion.
     */
    public TreeNode deserialize1(String tree) {
        
        // **** sanity checks ****
        if (tree.isEmpty())
            return null;

        // **** split the tree string ****
        String[] strs = tree.split(" ");

        // **** deserialise BST ****
        return deserialize1(strs, 0, strs.length - 1);
    }


    /**
     * Recursive call.
     */
    private TreeNode deserialize1(String[] strs, int start, int end) {

        // ???? ????
        System.out.println("deserialize1 <<< start: " + start + " end: " + end);

        // **** end condition ****
        if (start > end) {
            return null;
        }

        // **** root node ****
        TreeNode root = new TreeNode(Integer.parseInt(strs[start]));

        // ???? ????
        System.out.println("deserialize1 <<<  root: " + root.val);

        // **** index of value > start ****
        int ndx;

        // **** look for next node based on the index ****
        for (ndx = start; ndx <= end; ndx++) {
            if (Integer.parseInt(strs[ndx]) > Integer.parseInt(strs[start])) {
                break;
            }
        }

        // ???? ????
        System.out.println("deserialize1 <<<   ndx: " + ndx);
 
        // **** process left subtree ****
        root.left = deserialize1(strs, start + 1, ndx - 1);

        // **** process right sub tree ****
        root.right = deserialize1(strs, ndx, end);

        // **** return BST ****
        return root;
    }


    /**
     * Decodes your encoded data to tree.
     * 
     * Entry for recursive call.
     */
    public TreeNode deserialize(String tree) {

        // **** sanity check ****
        if (tree.isEmpty())
            return null;

        // **** populate the queue with the serialized data ****
        Queue<String> q = new LinkedList<>(Arrays.asList(tree.split(" ")));

        // **** deserialize the BST ****
        return deserialize(q, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


    /**
     * Recursive call.
     */
    private TreeNode deserialize(Queue<String> q, int start, int end) {

        // **** end condition ****
        if (q.isEmpty())
            return null;

        // **** get next string (node value) from the queue ****
        String s = q.peek();

        // ???? ????
        System.out.println("deserialize <<< s ==>" + s + "<==");

        // **** convert to integer ****
        int val = Integer.parseInt(s);

        // ???? ????
        System.out.println("deserialize <<< val: " + val + " start: " + start + " end: " + end);

        // **** skip this node (if needed) ****
        if (val < start || val > end)
            return null;

        // **** remove node from the queue ****
        q.poll();

        // **** generate node for this value ****
        TreeNode root = new TreeNode(val);

        // **** process left subtree ****
        root.left = deserialize(q, start, val);

        // **** process right subtree ****
        root.right = deserialize(q, val, end);

        // **** return root of BST ****
        return root;
    }
}


/**
 * 
 */
public class SerializeDeserializeBST {


    /**
     * Test scaffold
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        
        // **** create String[] with values for the BST ****
        String[] strArr = br.readLine().trim().split(",");
 
        // **** close the buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< strArr.length: " + strArr.length);
        System.out.println("main <<<        strArr: " + Arrays.toString(strArr));

        // **** generate an Integer[] to build the BST ****
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("null") || strArr[i].isEmpty())
                arr[i] = null;
            else
                arr[i] = Integer.parseInt(strArr[i]);
        }
 
        // ???? ????
        System.out.println("main <<< arr: " + Arrays.toString(arr));
 
        // **** create and populate the BST ****
        BST bst = new BST();
        bst.root = bst.populate(arr);

        // ???? ????
        System.out.println("main <<< DFT: " + bst.inOrder(bst.root));

        // ???? ????
        System.out.println("main <<< BFT: " + bst.levelOrder());

        // ???? ????
        System.out.print("main <<< preorder: ");
        bst.preOrder(bst.root);
        System.out.println();

        // **** instantiate Codec to serialize ****
        Codec ser = new Codec();

        // **** instantiate Codec to deserialize ****
        Codec deser = new Codec();

        // **** serialize BST ****
        String tree = ser.serialize(bst.root);

        // ???? ????
        System.out.println("main <<< tree ==>" + tree + "<==");

        // **** deserialize BST ****
        TreeNode ans = deser.deserialize(tree);

        // **** for ease of use ****
        bst = new BST(ans);
       
        // ???? ????
        System.out.println("main <<< DFT: " + bst.inOrder(bst.root)); 

        // ???? ????
        System.out.println("main <<< BFT: " + bst.levelOrder());
    }

}