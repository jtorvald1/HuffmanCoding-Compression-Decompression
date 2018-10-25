/**
 * PQHeap --- Program to store and manipulate Element data in an array, and maintain the structure as a min-heap.
 * @author      Torvald Johnson
 * SDU Login:   tjohn16
 */

public class PQHeap implements PQ{
  private int maxElements;
  private int heapSize = 0;
  private Element[] elements;
  
  
  /**
   * Constructor. Returns a new, empty PQHeap with an element array of the size specified in maxElements.
   * 1 is added to the size specified, because we will not use the index at 0.
   * @param maxElms is the maximum number of elements to be held in the min-heap.
   */
  public PQHeap(int maxElms) {
    this.maxElements = maxElms+1;
    this.elements = new Element[maxElements];
  }
  
  /** Returns and removes smallest Element from the min-heap, while preserving the min-heap structure   */
  @Override public Element extractMin() {
    Element min = elements[1];
    elements[1] = elements[heapSize];
    heapSize = heapSize-1;
    
    //Don't need to call minHeapify and preserve structure if the min-heap is empty
    if (heapSize != 0) {
    minHeapify(1);
    }
    return min;
  }
  
  /**
   * Inserts Element e into the min-heap, while preserving the min-heap structure
   * @param e is the element to be inserted into the heap.
   */
  @Override public void insert(Element e) {
    heapSize = heapSize+1;
    elements[heapSize] = e;
    int i = heapSize;
    //if i is 1, it is the root element and does not have a parent
    while(i > 1 && elements[parent(i)].key > elements[i].key) {
      swapElems(i, parent(i));
      i = parent(i);
    }
  }

   /** Returns the index of the parent element of given index i   */
  public int parent(int i) {
    return i/2;
  }
  
  /** Returns the index of the left-child element of given index i  */
  public int left(int i) {
    return 2 * i;
  }
  
  /** Returns the index of the right-child element of given index i   */
  public int right(int i) {
    return 2 * i + 1;
  }
  
  /**
   * Checks if a given index in the heap has any children with smaller keys. 
   * If so, they are swapped and the new parent is subjected to minHeapify.
   * @param i is the index of the array at which minHeapify should begin.
   */
  public void minHeapify(int i) {
  int smallest;
  int l = left(i);
  int r = right(i);
  
  if (l <= heapSize && elements[l].key < elements[i].key) {
    smallest = l;
    }
  else {
    smallest = i;
    }
    
  if (r <= heapSize && elements[r].key < elements[smallest].key) {
    smallest = r;
    }
  
  if (smallest != i) {
    swapElems(i, smallest);
    minHeapify(smallest);
    }
  }
  
  /** Swaps the two elements at indexes i and j within the elements array   */
  public void swapElems(int i, int j) {
    Element temp = elements[i];
    elements[i] = elements[j];
    elements[j] = temp;
  }
}