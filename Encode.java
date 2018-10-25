/**
 * Encode --- Class to take the contents of a specified file, compress it using the 
 * Huffman algorithm, and write the compressed output to another specified file.
 * @author      Torvald Johnson
 * SDU Login:   tjohn16
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
public class Encode {
 
 
  public static void main(String[] args) throws IOException {
    FileInputStream in = new FileInputStream(args[0]);
    FileOutputStream out = new FileOutputStream(args[1]);
    BitOutputStream bitOut = new BitOutputStream(out);
   
    int[] freqTable = new int[256];
   
    /**
     * Generate frequency table. The index of an element represents the byte, 
     * and the integer held at that index represents the frequency of that byte.
     */
    int i = in.read();
    while(i != -1){
            freqTable[i] = freqTable[i] + 1;
            i = in.read();
    }
    
    /**
     * Create a PQHeap using the frequency table. All bytes are held in Element 
     * objects, where the element key is the frequency and the element data is an 
     * integer representing the byte.
     */
    PQHeap freqHeap = new PQHeap(256);
    for(i = 0; i < 256; i++){
      Element elem = new Element(freqTable[i], i);
      freqHeap.insert(elem);
    }
    
    // Create a Huffman Tree using the PQHeap, then find all Huffman codes 
    HuffmanTree huffmanTree = new HuffmanTree();
    Element root = huffmanTree.buildTree(freqHeap);
    huffmanTree.findHuffmanCodes(root);
   
   
    // Write 256 integers, representing the frequency table of the original file
    for(i = 0; i < 256; i++){
      bitOut.writeInt(freqTable[i]);
    }
    
    FileInputStream newIn = new FileInputStream(args[0]);
    
    // Loop through every byte in the input file again
    int j = newIn.read();
    while(j != -1){
      int k = 0;
      // For each byte, write the Huffman code of that byte, bit by bit, to the output file
      while(k < huffmanTree.huffmanCodeStrings[j].length()){
        int bitToWrite = Character.getNumericValue(huffmanTree.huffmanCodeStrings[j].charAt(k));
        bitOut.writeBit(bitToWrite);
        k++;
      }
      j = newIn.read();
    }
    in.close();
    bitOut.close();
    out.close();
    newIn.close();
  }
}