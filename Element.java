public class Element {
 
        Element left;
        Element right;
        Element parent;
       
    public int key;
    public int data;
    //
 
    public Element(int i, int b){
    this.parent = null;
    this.left = null;
    this.right = null;
        this.key = i;
        this.data = b;
    }
   
    public boolean isLeaf(){
        if(this.left == null && this.right == null){
                return true;
        }
        else{
                return false;
        }
    }
}