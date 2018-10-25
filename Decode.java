/**
 * Decode --- Class to take the content of an compressed file, decode it using 
 * the Huffman algorithm, then write the decoded output to a specified file.
 * @author      Torvald Johnson
 * SDU Login:   tjohn16
 */

import java.io.*;
import java.util.Arrays;
 
public class Decode{
  
  public static BitInputStream bitIn;
  public static int bytesCount;
  
  public static void main(String args[]) throws Exception{
      File file = new File(args[0]);
      FileInputStream inFile = new FileInputStream(args[0]);
      FileOutputStream outFile = new FileOutputStream(args[1]);
      bitIn = new BitInputStream(inFile);
      BitOutputStream out = new BitOutputStream(outFile);
      
      int i;
      
      // Read frequency table from the encoded file
      int[] freqTable = new int[256];
      for (i=0; i<256; i++){
          freqTable[i] = bitIn.readInt();
      }
      
      // Create a PQHeap using the frequency table
      PQHeap freqHeap = new PQHeap(256);
      for(i = 0; i < 256; i++){
        Element elem = new Element(freqTable[i], i);
        freqHeap.insert(elem);
      }
      
      // Create a Huffman Tree using the PQHeap
      HuffmanTree huffmanTree = new HuffmanTree();
      Element root = huffmanTree.buildTree(freqHeap);
      
      // Get the amount of encoded bytes from the encoded file
      int bytes = 0;
      for(i = 0; i < 256; i++){
        bytes = bytes + freqTable[i];
      }
      bytesCount = 0;
      
      // Decode and write every encoded byte to the decoded file
      while (bytesCount < bytes)
         {
          int foundByte = findByte(root);
          outFile.write(foundByte);
          bytesCount ++;
         }
      }

  /**
   * Recursively reads bits one by one from the encoded file and checks the 
   * relevant elements in the Huffman Tree until a leaf is found.
   * Returns the integer held in the leaf found, which represents a byte.
   * @param z is the element in the Huffman Tree to be analyzed.
   */
  private static int findByte(Element z) throws Exception{
    if (z.isLeaf()){
        return z.data;
    }
    int bit = bitIn.readBit();
    if (bit == 1){
      return findByte(z.right);
    }
    else{
      return findByte(z.left);
    }
  }
}