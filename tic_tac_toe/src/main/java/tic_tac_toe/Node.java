package tic_tac_toe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

	private int[] element = null;
	private List<Node> children = new ArrayList<Node>();
	private Node parent = null;
	private int value;
	private int turn;

	public Node(int[] element, int turn) {
		setElement(element);
		setTurn(turn);
	}

	public Node(int value) {
		setValue(value);
	}

	public static Node min(Node n1, Node n2) {
		return (n1.getValue() <= n2.getValue()) ? n1 : n2;
	}

	public static Node max(Node n1, Node n2) {
		return (n1.getValue() >= n2.getValue()) ? n1 : n2;
	}

	public int compareTo(Node node) {
		return getValue() - node.getValue();
	}

	public Node addChild(Node child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public String toString() {
		return elements(element);
	}

	private String elements(int[] e) {
		String str = "";
		for (int i : e) {
			str += " " + i;
		}
		return str;
	}

	public List<Node> getChildren() {
		return children;
	}

	public int[] getElement() {
		return element;
	}

	public void setElement(int[] element) {
		this.element = element;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getChild() {
		for (Node each : getChildren()) {
			if (Arrays.equals(element, each.getElement())) {
				return each;
			}
		}
		return null;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

}
