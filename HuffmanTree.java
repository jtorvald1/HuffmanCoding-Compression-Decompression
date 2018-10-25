/**
 * HuffmanTree --- Class to take the elements of a PQHeap, create a Huffman tree
 * using the Huffman algorithm, and find the Huffman Code for every leaf of the tree.
 * @author      Torvald Johnson
 * SDU Login:   tjohn16
 */

public class HuffmanTree {
  
  public String[] huffmanCodeStrings;
  public String huffcodes;
  public Element root;
  
  // Constructor 
  public HuffmanTree(){
    this.huffmanCodeStrings = new String[256];
    this.huffcodes = "";
    this.root = null;
  }
 
  /**
   * sorts all elements of a given PQHeap into a Huffman Tree. Returns the root element of the tree.
   * @param heap is the priority queue to be sorted using the Huffman algorithm.
   */
  public Element buildTree(PQHeap heap){
    PQHeap freqHeap = heap;
    for(int i = 0; i < 255; i++){
      // Create a new node with data of 300, which is outside of the integer range used for bytes
      Element z = new Element(0, 300);
      // Set left and right children to the two elements from the heap with the smallest keys
      z.left = freqHeap.extractMin();
      z.left.parent = z;
      z.right = freqHeap.extractMin();
      z.right.parent = z;
      // Set the key of the node to the sum of its left and right child's keys
      z.key = z.left.key + z.right.key;
      // Insert the new node into the PQHeap
      freqHeap.insert(z);
    }
  // Return the remaining element from the Heap, which is the root node of the Huffman Tree
  this.root = freqHeap.extractMin();
  return this.root;
  }
 
 
  /**
   * Recursively finds the Huffman code for each leaf in the Huffman Tree.
   * @param heap is the priority queue to be sorted using the Huffman algorithm.
   */
  public void findHuffmanCodes(Element z){
    if(z != null){
      if(z.isLeaf()){
      // This is a leaf, so we need to find its code
      Element x = z;
      int k = 0;
      huffcodes = "";
      // Until we reach root, add to the code based on if the element is a left or right child
      while(x != this.root){
        if(x == x.parent.left){
                huffcodes += "0";
        }
        else{
                huffcodes += "1";
        }
        x = x.parent;
        k++;
      }
      k = 0;
      
      // Found the code, but it's backwards so reverse it
      String reverse = new StringBuilder(huffcodes).reverse().toString();
      
      // Insert the code to the string array, at the index of the integer pertaining to the byte
      this.huffmanCodeStrings[z.data] = reverse;
      }
      // Check every element recursively
      findHuffmanCodes(z.left);
      findHuffmanCodes(z.right);
    }
  }
}