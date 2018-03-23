import java.io.*;
public class Tree {
    private TNode root = null;
    
    public Tree(){
	root = null;
    }
    public Tree(TNode r){
	root = r;
    }
    public TNode getRoot(){
	return root;
    }
    public void setRoot(TNode r){
	root = r;
    }
    public void insertNode(TNode t){
	if(root == null){
	    root = t;
	} else if(t.getId().compareTo(root.getId()) < 0){
	    if(root.getLeft() == null){
		root.setLeft(t);
		t.setParent(root);
	    } else {
		Tree tr = new Tree(root.getLeft());
		tr.insertNode(t);
	    }
	} else if(t.getId().compareTo(root.getId()) > 0){
	    if(root.getRight() == null){
		root.setRight(t);
		t.setParent(root);
	    } else {
		Tree tr = new Tree(root.getRight());
		tr.insertNode(t);
	    }
	} else {
	    System.out.println("Attempting to insert an identification that already exists in tree");
	}
    }
    public void printTree() {
	if(root != null){
	    Tree t = new Tree(root.getLeft());
	    t.printTree();
	    System.out.println(root);
	    t = new Tree(root.getRight());
	    t.printTree();
	}
    }
    public String toString(){
	if(root == null)
	    return "Empty tree";
	else
	    return "Root Id: " + root.getId();
    }
    public TNode findNode(String id){
	if(root == null){
	    return null;
	} else if(id.equals(root.getId())){
	    return root;
	} else if(id.compareTo(root.getId()) < 0){
	    Tree t = new Tree(root.getLeft());
	    return t.findNode(id);
	} else if(id.compareTo(root.getId()) > 0){
	    Tree t = new Tree(root.getRight());
	    return t.findNode(id);
	} else {
	    System.out.println("Error");
	    return null;
	}
    }
    public TNode findNode(String partialKey, int where){
	if(partialKey.length() == Globals.IDENTIFICATION_LEN){
	    return findNode(partialKey);
	} else if(root == null){
	    return null;
	}
	int n = partialKey.length();
	if(partialKey.compareTo(root.getId().substring(0, n)) < 0){
	    Tree t = new Tree(root.getLeft());
	    return t.findNode(partialKey, where);
	} else if(partialKey.compareTo(root.getId().substring(0, n)) > 0){
	    Tree t = new Tree(root.getRight());
	    return t.findNode(partialKey, where);
	} else {
	    TNode p = root;
	    if(where == 0){
		TNode q = p.getLeft();
		while(q != null){
		    if(q.getId().substring(0, n).equals(partialKey)){
			p = q;
			q = q.getLeft();
		    } else {
			q = q.getRight();
		    }
		}
	    } else {
		TNode q = p.getRight();
		while(q != null){
		    if(q.getId().substring(0, n).equals(partialKey)){
			p = q;
			q = q.getRight();
		    } else {
			q = q.getLeft();
		    }
		}
	    }
	    return p;
	}
    }
    public void printTree(int level){
	if(root != null){
	    Tree t = new Tree(root.getLeft());
	    t.printTree(level+1);
	    System.out.println(root + " in level " + level);
	    t = new Tree(root.getRight());
	    t.printTree(level+1);
	}
    }
    public void breadthFirstRetrieve(String fileName){
	try {
	    RandomAccessFile f = new RandomAccessFile(fileName, "rw");
	    int nodes = (int) (f.length() / (Globals.IDENTIFICATION_LEN + Globals.INT_LEN));
	    TNode p = null;
	    byte[] identification = new byte[Globals.IDENTIFICATION_LEN];
	    String identificationString = Globals.STR_NULL;
	    for(int i = 0; i < nodes; i++){
		identificationString = Globals.STR_NULL;
		f.read(identification);
		for(int j = 0; j < identification.length; j++){
		    identificationString = identificationString + (char) identification[j];
		}
		p = new TNode(identificationString, f.readInt(), null, null, null);
		this.insertNode(p);
	    }
	    f.close();
	} catch (IOException e){
	    System.out.println("***Error: Unable to retrieve tree file " + fileName);
	}
    }
    public void breadthFirstSave(String fileName){
	try {
	    RandomAccessFile f = new RandomAccessFile(fileName, "rw");
	    f.setLength(0);
	    for(int i = 0; i < height(); i++)
		writeLevel(i, f);
	    f.close();
	} catch (IOException e){
	    System.out.println(e);
	}
    }
    public void writeLevel(int level, RandomAccessFile f){
	if(level == 0){
	    try {
		if(root != null){
		    f.write(root.getId().getBytes());
		    f.writeInt(root.getRecordNumber());
		}
	    } catch (IOException e){
		System.out.println(e);
	    }
	} else if(root != null){
	    Tree tree = new Tree(root.getLeft());
	    tree.writeLevel(level-1, f);
	    tree = new Tree(root.getRight());
	    tree.writeLevel(level-1, f);
	}
    }
    public int height(){
	if(root == null)return 0;
	Tree tree = new Tree(root.getLeft());
	Tree tree2 = new Tree(root.getRight());
	return Math.max(tree.height()+1, tree2.height()+1);
    }
    public void buildFromMessagesFile(int whatId){
	Record record = new Record();
	for(int recordNumber = 0; recordNumber < Globals.totalRecordsInMessageFile; recordNumber++){
	    record.readFromMessagesFile(recordNumber);
	    if(record.getData().charAt(Globals.FIRST_RECORD_MARKER_POS) ==
		Globals.FIRST_RECORD_MARKER){
		Message message = new Message();
		message.readFromMessagesFile(recordNumber);
		
		String key = Globals.STR_NULL;
		if(whatId == Globals.SENDER_ID)
		    key = message.getIdSenderFirst();
		else if(whatId == Globals.RECEIVER_ID)
		    key = message.getIdReceiverFirst();
		else
		    System.out.println("***invalid whatKey parameter in buildFromMessagesFile method");
		insertNode(new TNode(key, recordNumber));
	    }
	}
    }
    public static void main(String args[]){
	Tree t = new Tree();
	//t.buildFromMessagesFile(Globals.SENDER_ID);
	//t.printTree();
	for(int i = 0; i < 50000; i++){
	    int key = (int)(Math.random()*1000000000);
	    t.insertNode(new TNode(Utils.leftPad(""+key, 5, '0'), i));
	}
	t.insertNode(new TNode(Utils.leftPad("12300", 5, '0'), 100));
	System.out.println(t.findNode("123", 0));
	System.out.println(t.height());
	Tree x = new Tree();
	x.insertNode(new TNode("27", 1));
	x.insertNode(new TNode("19", 1));
	x.insertNode(new TNode("30", 1));
	x.insertNode(new TNode("32", 1));
	System.out.println(x.height());
	/*TNode n;
	Tree test6 = new Tree();
	for(int i = 0; i < 500; i++){
	    boolean found = false;
	    while(!found){
		String x = ""+((char)(Math.random()*26+'a'))+((char)(Math.random()*26+'a'));
		if(test6.findNode(x) == null){
		    n = new TNode();
		    n.setRecordNumber(i);
		    n.setId(x);
		    test6.insertNode(n);
		    found = true;
		}
	    }
	}
	test6.printTree();*/
    }
}
