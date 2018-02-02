public class SNode {
    private String name = "";
    private double mark = 0.0;
    private SNode next = null;
    public SNode(String n, double m, SNode nxt){
	name = n;
	mark = m;
	next = nxt;
    }
    public String getName(){
	return name;
    }
    public double getMark(){
	return mark;
    }
    public SNode getNext(){
	return next;
    }
    public void setName(String n){
	name = n;
    }
    public void setMark(double m){
	mark = m;
    }
    public void setNext(SNode nxt){
	next = nxt;
    }
    public String toString(){
	return this != null ? name+" "+mark : null;
    }
}
