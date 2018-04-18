public class TNode {
    private String identification = "";
    private int recordNumber = -1;
    private TNode left = null;
    private TNode right = null;
    private TNode parent = null;
    private int height = 1;
    
    public TNode() {
	identification = "";
	recordNumber = -1;
	left = null;
	right = null;
	parent = null;
	height = 1;
    }
    
    public TNode(String i, int r, TNode l, TNode right, TNode p){
	identification = i;
	recordNumber = r;
	left = l;
	this.right = right;
	parent = p;
	height = 1;
    }
    public TNode(String i, int r){
	left = null;
	right = null;
	parent = null;
	identification = i;
	recordNumber = r;
	height = 1;
    }
    public TNode getLeft(){
	return left;
    }
    public TNode getRight(){
	return right;
    }
    public TNode getParent(){
	return parent;
    }
    public int getHeight(){
	return height;
    }
    public void setHeight(int h){
	height = h;
    }
    public void setLeft(TNode l){
	left = l;
    }
    public void setRight(TNode r){
	right = r;
    }
    public void setParent(TNode p){
	parent = p;
    }
    public String getId(){
	return identification;
    }
    public void setId(String id){
	identification = id;
    }
    public void setRecordNumber(int i){
	recordNumber = i;
    }
    public int getRecordNumber(){
	return recordNumber;
    }
    public boolean isLeaf(){
	return left == null && right == null;
    }
    public String toString(){
	return "Id: " + identification + " Record number: " + recordNumber;
    }
}
