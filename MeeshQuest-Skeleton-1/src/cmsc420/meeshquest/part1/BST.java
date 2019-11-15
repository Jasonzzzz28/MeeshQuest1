package cmsc420.meeshquest.part1;

/* code adapted from https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/ */

public class BST { 
  
    /* Class containing left and right child of current BNode and key value*/
    class BNode { 
        String key; 
        city City; 
        BNode left, right; 
  
        public BNode(String item, city c) { 
            key = item; 
            City = c;
            left = right = null; 
        } 
    } 
  
    // Root of BST 
    BNode root; 
  
    // Constructor 
    BST() {  
        root = null;  
    } 
  
    boolean isEmpty() {
    	if (root == null) return true;
    	return false;
    }
    // This method mainly calls insertRec() 
    void insert(String key, city c) { 
       root = insertRec(root, key, c); 
    } 
      
    /* A recursive function to insert a new key in BST */
    BNode insertRec(BNode root, String key, city c) { 
  
        /* If the tree is empty, return a new BNode */
        if (root == null) { 
            root = new BNode(key, c); 
            return root; 
        } 
  
        /* Otherwise, recur down the tree */
        if (key.compareTo(root.key) < 0) 
            root.left = insertRec(root.left, key, c); 
        else if (key.compareTo(root.key) > 0) 
            root.right = insertRec(root.right, key, c); 
  
        /* return the (unchanged) BNode pointer */
        return root; 
    } 
  
    // This method mainly calls InorderRec() 
    void inorder()  { 
       inorderRec(root); 
    } 
  
    // A utility function to do inorder traversal of BST 
    void inorderRec(BNode root) { 
        if (root != null) { 
            inorderRec(root.left); 
            System.out.println(root.key); 
            inorderRec(root.right); 
        } 
    } 
  
    void deleteKey(String key) 
    { 
        root = deleteRec(root, key); 
    } 
  
    /* A recursive function to insert a new key in BST */
    BNode deleteRec(BNode root, String key) 
    { 
        if (root == null)  return root; 

        if (key.compareTo(root.key) < 0) 
            root.left = deleteRec(root.left, key); 
        else if (key.compareTo(root.key) > 0) 
            root.right = deleteRec(root.right, key); 
  
        else
        { 
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
            
            BNode replace = minValue(root.right);
            root.key = replace.key; 
            root.City = replace.City;
            root.right = deleteRec(root.right, root.key); 
        } 
  
        return root; 
    } 
    
    BNode minValue(BNode root) 
    { 
        BNode res = root; 
        while (res.left != null) 
        { 
            res = res.left; 
        } 
        return res; 
    } 
} 