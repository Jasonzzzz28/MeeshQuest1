package cmsc420.meeshquest.part1;
import java.util.*;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cmsc420.meeshquest.part1.BST.BNode;
import cmsc420.meeshquest.part1.SGT.ExternalNode;
import cmsc420.meeshquest.part1.SGT.InternalNode;
import cmsc420.meeshquest.part1.SGT.Node;
	


public class Commands {
	protected Document results; 
	protected Element res; 
	protected HashMap <Coord, String> namesandco  = new HashMap<>(); 
	protected HashMap <String, city> Cities  = new HashMap<>(); 
	protected BST treebyname = new BST();
	protected SGT treebycoord = new SGT();
	
	public void setResults(Document Results) {
		this.results = Results;
		res = results.createElement("results");
		results.appendChild(res);
	}
	
	private String helpAttributes(Element cmd, String Aname, Element param) {
		String v = cmd.getAttribute(Aname);
		Element attr = results.createElement(Aname);
		attr.setAttribute("value", v);
		param.appendChild(attr);
		return v;
	}
	
	public void pcreatecity(Element elem){
		//getting command node
		Element cmd = results.createElement("command");
		cmd.setAttribute("name", elem.getNodeName());
		Element param = results.createElement("parameters");
		
		String name = helpAttributes(elem,"name",param);
		
		String x = helpAttributes(elem,"x",param);
	    String y = helpAttributes(elem,"y",param);
        String radius = helpAttributes(elem,"radius",param);
        String color = helpAttributes(elem,"color",param);
        Coord coordinate = new Coord(Integer.parseInt(x), Integer.parseInt(y));
 
        if (namesandco.containsKey(coordinate)) {
        	Element err = results.createElement("error");
    		err.setAttribute("type", "duplicateCityCoordinates");
    		err.appendChild(cmd);
    		err.appendChild(param);
    		res.appendChild(err);
        }
        else if (namesandco.containsValue(name)) {
        	Element err = results.createElement("error");
    		err.setAttribute("type", "duplicateCityName");
    		err.appendChild(cmd);
    		err.appendChild(param);
    		res.appendChild(err);
        }
        else {
        	city acity = new city(name, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(radius), color);
        	namesandco.put(coordinate, name);
        	Cities.put(name, acity);
        	treebyname.insert(name, acity);
        	treebycoord.insert(coordinate, acity);
        	
        	Element output = results.createElement("output");
		
        	Element s = results.createElement("success");
        	s.appendChild(cmd);
        	s.appendChild(param);
        	s.appendChild(output);
        	res.appendChild(s);
        }
	}
	
	private void putCity(Element where, city input) {
		Element city = results.createElement("city");
		city.setAttribute("name", input.getName());
		city.setAttribute("x", Integer.toString(input.getX()));
		city.setAttribute("y", Integer.toString(input.getY()));
		city.setAttribute("radius", Integer.toString(input.getRadius()));
		city.setAttribute("color", input.getColor());
		where.appendChild(city);
	}
	
	private void setCity(Element where, city input) {
		where.setAttribute("color", input.getColor());
		where.setAttribute("name", input.getName());
		where.setAttribute("radius", Integer.toString(input.getRadius()));
		where.setAttribute("x", Integer.toString(input.getX()));
		where.setAttribute("y", Integer.toString(input.getY()));
	}
	
	private Element putNode(Element where, city input) {
		Element node = results.createElement("node");
		node.setAttribute("name", input.getName());
		node.setAttribute("x", Integer.toString(input.getX()));
		node.setAttribute("y", Integer.toString(input.getY()));
		where.appendChild(node);
		return node;
	}
	
	public void plistcities(Element elem) {
		Element cmd = results.createElement("command");
		cmd.setAttribute("name", elem.getNodeName());
		Element param = results.createElement("parameters");
		
		String key = helpAttributes(elem,"sortBy",param);
		
		Element output = results.createElement("output");
		Element CL = results.createElement("cityList");
		if(! namesandco.isEmpty()) {
			
			if (key.equals("name")) {
				bstinorderRec(treebyname.root, CL);
			}
			else if (key.equals("coordinate")) {
				sgtinRec(treebycoord.root, CL);
			}
			
			output.appendChild(CL);
			
		    Element success = results.createElement("success");
			success.appendChild(cmd);
			success.appendChild(param);
			success.appendChild(output);
			res.appendChild(success);
		}
		else {
			Element err = results.createElement("error");
    		err.setAttribute("type", "noCitiesToList");
    		err.appendChild(cmd);
    		err.appendChild(param);
    		res.appendChild(err);
		}
	}
	
	public void pdeletecity(Element elem) {
		Element cmd = results.createElement("command");
		cmd.setAttribute("name", elem.getNodeName());
		Element param = results.createElement("parameters");
		String name = helpAttributes(elem, "name", param);
		
		if (! Cities.containsKey(name)) {
			Element err = results.createElement("error");
    		err.setAttribute("type", "cityDoesNotExist");
    		err.appendChild(cmd);
    		err.appendChild(param);
    		res.appendChild(err);
    		return;
		}
		Element output = results.createElement("output");
		Element del = results.createElement("cityDeleted");
		city tcity = Cities.get(name);
		Coord coo = new Coord(tcity.getX(), tcity.getY());
		setCity(del, tcity);
		output.appendChild(del);
		
		treebyname.deleteKey(name);
		treebycoord.delete(coo);
		namesandco.remove(coo);
		Cities.remove(name);
		
		Element success = results.createElement("success");
	    success.appendChild(cmd);
		success.appendChild(param);
		success.appendChild(output);
		res.appendChild(success);
		
		
	}
   
     // A utility function to do inorder traversal of BST 
     private void bstinorderRec(BNode root, Element CL) { 
         if (root != null) { 
             bstinorderRec(root.left, CL); 
             putCity(CL, root.City);
             bstinorderRec(root.right, CL); 
         } 
     }
     
     private void sgtinRec(Node root, Element CL) {
    	 if (root != null) {
    		 sgtinRec(root.left(), CL);
    		 if (root.isEx()) putCity(CL, ((ExternalNode) root).City);
    		 sgtinRec(root.right(), CL);
    	 }
     }
     
     public void printbst(Element elem) {
 		Element cmd = results.createElement("command");
 		cmd.setAttribute("name", elem.getNodeName());	
 		Element param = results.createElement("parameters");
 		
 		
    	 if (treebyname.isEmpty()) {
 			Element err = results.createElement("error");
    		err.setAttribute("type", "mapIsEmpty");
    		err.appendChild(cmd);
    		err.appendChild(param);
    		res.appendChild(err);
    	 }
    	 else {
    		Element output = results.createElement("output");
    		Element BS = results.createElement("binarysearchtree");
    		PreBNode(BS, treebyname.root);
    		output.appendChild(BS);
    		
    		Element success = results.createElement("success");
 			success.appendChild(cmd);
 			success.appendChild(param);
 			success.appendChild(output);
 			res.appendChild(success);
    	 }
     }
     
     private void PreBNode(Element where, BNode root) {
    	 if (root != null) { 
    		 Element e = putNode(where, root.City);
    		 PreBNode(e, root.left);
    		 PreBNode(e, root.right);
    	 }
     }
     
     public void printsgt(Element elem) {
    	 Element cmd = results.createElement("command");
  		 cmd.setAttribute("name", elem.getNodeName());	
  		 Element param = results.createElement("parameters");
  		 if (treebycoord.isEmpty()) {
  			Element err = results.createElement("error");
     		err.setAttribute("type", "mapIsEmpty");
     		err.appendChild(cmd);
     		err.appendChild(param);
     		res.appendChild(err);
     	 }
     	 else {
     		Element output = results.createElement("output");
     		Element SG = results.createElement("SGTree");
     		PresgNode(SG, treebycoord.root);
     		output.appendChild(SG);
     		
     		Element success = results.createElement("success");
     		
  			success.appendChild(cmd);
  			success.appendChild(param);
  			success.appendChild(output);
  			res.appendChild(success);
     	 }
     }
     
     private void PresgNode(Element where, Node root) {
    	 if (root.isEx()) {
    		 city mycity = ((ExternalNode) root).City;
    		 putSGnode(where, mycity.getName(), mycity.getX(), mycity.getY(), "external");
    	 }
    	 else if (root != null) { 
    		 Coord spl = ((InternalNode) root).splitter;
    		 String nam = ((InternalNode) root).name;
    		 Element e = putSGnode(where, nam, spl.x, spl.y, "internal");
    		 PresgNode(e, ((InternalNode) root).left);
    		 PresgNode(e, ((InternalNode) root).right);
    	 }
     }
     
     Element putSGnode(Element where, String name, int x, int y, String tag) {
 		Element node = results.createElement(tag);
 		node.setAttribute("name", name);
 		node.setAttribute("x", Integer.toString(x));
 		node.setAttribute("y", Integer.toString(y));
 		where.appendChild(node);
 		return node;
     }
     
     public void clearall(Element elem) {
    	Element cmd = results.createElement("command");
  		cmd.setAttribute("name", elem.getNodeName());	
  		Element param = results.createElement("parameters");
  		Element output = results.createElement("output");
  		Element success = results.createElement("success");
  		success.appendChild(cmd);
		success.appendChild(param);
		success.appendChild(output);
		res.appendChild(success);
		namesandco.clear();
		Cities.clear();
		treebyname = new BST();
		treebycoord = new SGT(); 
     }

}
