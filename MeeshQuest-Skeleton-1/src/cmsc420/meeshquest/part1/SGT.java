package cmsc420.meeshquest.part1;

import java.math.*;
import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SGT {
	
	abstract class Node {
		abstract Node insert(Coord o, city c);
		abstract int getsize();
		abstract int geth();
		abstract Node left();
		abstract Node right();
		abstract boolean isEx();
	}
	class InternalNode extends Node {
		Coord splitter;
		public Node left, right;
		int height, size;
		String name;
		
		public InternalNode(Coord spl, Node l, Node r, int h, int s, String na) {
			left = l; right = r; height = h; size = s; splitter = spl; name = na;
		}
		public int getsize() {
			return size;
		}
		public int geth() {
			return height;
		}
		public Node left() {
			return left;
		}
		public Node right() {
			return right;
		}
		public boolean isEx() {
			return false;
		}
		Node insert(Coord o, city c) {
			if (o.compareTo(splitter) <= 0) {
				left = left.insert(o, c);
				size = left.getsize() + right.getsize();
				height = 1 + Math.max(left.geth(), right.geth());
			}
			else {
				right = right.insert(o, c);
				size = left.getsize() + right.getsize();
				height = 1 + Math.max(left.geth(), right.geth());
			}
			return this;
		} 
		
	}
	class ExternalNode extends Node {
		city City;
		public ExternalNode(city c) {
			City = c;
		}
		public int getsize() {
			return 1;
		}
		public int geth() {
			return 0;
		}
		public Node left() {
			return null;
		}
		public Node right() {
			return null;
		}
		public boolean isEx() {
			return true;
		}
		
		Node insert(Coord o, city c) {
			Coord inco = new Coord(c.getX(), c.getY());
			Coord co = new Coord(City.getX(), City.getY());
			if (inco.compareTo(co) <= 0) {
				InternalNode inN = new InternalNode(inco, new ExternalNode(c), this, 1, 2, c.getName());
				return inN;
			}
			else {
				InternalNode inN = new InternalNode(co, this, new ExternalNode(c), 1, 2, City.getName());
				return inN;
			}
		} 
	}
	
	Node root;
	int n, m;
	
	SGT(){
		root = null;
		n = 0;
		m = 0;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public boolean isempty() {
		if (root == null) {
			return true;
		}
		return false;
	}
	
	public void insert(Coord o, city c) {
		if (root == null) {
			root = new ExternalNode(c);
		}
		else {
			root = root.insert(o, c);
		}
		n++; m++; 
		if ((double) root.geth() > (Math.log(m) / Math.log(1.5))) {
			Node parent = null;
			Node cur = root;
			while (!cur.isEx()) {
				Node child = o.compareTo(((InternalNode) cur).splitter) <= 0? cur.left():cur.right();
				if (2*cur.getsize() < 3*child.getsize()) {
					break;
				}
				else {
					parent = cur;
					cur = child;
				}
			}
			if (parent == null) {
				root = rebuild(root);
			}
			else if(parent.left() == cur) {
				((InternalNode) parent).left = rebuild(cur);
				
			}
			else {
				((InternalNode) parent).right = rebuild(cur);
			}
			
			if (parent != null) recupdate(root, (InternalNode) parent);
		}
	}
	
	Node recupdate(Node r, InternalNode par) {
		if( r.equals(par)) return par;
		else{
			Node x = par.splitter.compareTo(((InternalNode) r).splitter) <= 0? 
					recupdate(r.left(), par) : recupdate(r.right(), par);
			((InternalNode) x).height = 1+ Math.max(x.left().geth(), x.right().geth());
			return r;
		}
	}
	
	public void delete(Coord o) {
		root = deleteRec(null, root, o);
		n --;
		if (2*n < m) {
			rebuild(root);
			m = n;
		}
	}
	
	Node deleteRec(Node par, Node r, Coord o) {
		if (r.isEx()) return null;
		if (r.left().isEx() && ((ExternalNode) r.left()).City.getX() == o.x && 
				((ExternalNode) r.left()).City.getY() == o.y) {
			
			return ((InternalNode) r).right;
		}
		if (r.right().isEx() && ((ExternalNode) r.right()).City.getX() == o.x && 
				((ExternalNode) r.right()).City.getY() == o.y) {
			
			return ((InternalNode) r).left;
		}
		
		else if (o.compareTo(((InternalNode)r).splitter) <= 0) {
			 ((InternalNode)r).left = deleteRec(r, ((InternalNode)r).left, o); 
		}
		else {
			((InternalNode)r).right = deleteRec(r, ((InternalNode)r).right, o);
		}
		((InternalNode)r).size = ((InternalNode)r).left.getsize() + ((InternalNode)r).right.getsize();
		((InternalNode)r).height = 1 + Math.max(((InternalNode)r).left.geth(), ((InternalNode)r).right.geth());
		return r;
	}
	
	Node rebuild (Node cur) {
		ExternalNode [] arr = new ExternalNode [cur.getsize()];
		packIntoArray(cur, arr, 0);
		return RECrebu(arr, 0, arr.length);
		
	}
   
	// function adapted from https://opendatastructures.org/ods-java/8_Scapegoat_Trees.html
	int packIntoArray(Node u, Node[] a, int i) {
        if (u == null) {
            return i;
        }
        i = packIntoArray(u.left(), a, i);
        if (u.isEx()) a[i++] = u;
        return packIntoArray(u.right(), a, i);
    }
	
	Node RECrebu(ExternalNode[] arr, int i, int k) {
		if (k == 2) {
			Coord spl = new Coord(arr[i].City.getX(), arr[i].City.getY());
			InternalNode p = new InternalNode(spl, arr[i], arr[i+1], 1, 2, arr[i].City.getName());
			return p;
		}
		else if(k == 1) {
			return arr[i];
		}
		else {
			
			int m = k - k/2;
			Coord spl = new Coord(arr[i+m-1].City.getX(), arr[i+m-1].City.getY());
			InternalNode p = new InternalNode(spl, null, null, 0, 0, arr[i+m-1].City.getName());
			p.left = RECrebu(arr, i, m);
			p.right = RECrebu(arr, i+m, k-m);
			p.size = p.left.getsize() + p.right.getsize();
			p.height = 1 + Math.max(p.left.geth(), p.right.geth());
			return p;
		}
	}
}
