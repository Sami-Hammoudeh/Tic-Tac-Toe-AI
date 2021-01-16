package tic_tac_toe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	final static int X = 1;
	final static int O = 2;
	final static int N = 3;
	static int engineTurn;
	static int playerTurn;
	static Node root = new Node(new int[N * N], X);

	public static void main(String[] args) {
		int test = startGame();
		if (test != X) {
			buildTree(root);
		}
		while (!isDraw(root)) {
			play();
			if (isWin(root, engineTurn)) {
				System.out.println("You lose!");
				break;
			}
			if (isDraw(root)) {
				System.out.println("Draw!");
				break;
			}
			playerTurn();
			if (isWin(root, playerTurn)) {
				System.out.println("You Win!");
				break;
			}
			if (isDraw(root)) {
				System.out.println("Draw!");
				break;
			}
		}
	}

	private static void play() {
		Node alpha = new Node(-1000);
		Node beta = new Node(1000);
		Node position = miniMax(root, getDepthOf(root), true, alpha, beta);
		while (position.getParent().getParent() != null) {
			position = position.getParent();
		}
		setRoot(position);
		print(root);
	}

	private static int getValueOf(Node node) {
		if (isWin(node, engineTurn))
			return 10;
		if (isWin(node, playerTurn))
			return -10;
		return 0;
	}

	private static Node miniMax(Node node, int depth, boolean isMaximizer, Node alpha, Node beta) {
		if (isLeaf(node)) {
			node.setValue(getValueOf(node));
			return node;
		}
		if (isMaximizer) {
			Node bestVal = new Node(-1000);
			for (Node child : node.getChildren()) {
				Node value = miniMax(child, depth + 1, false, alpha, beta);
				bestVal = Node.max(bestVal, value);
				alpha = Node.max(alpha, bestVal);
				if (beta.compareTo(alpha) <= 0)
					break;
			}
			return bestVal;
		} else {
			Node bestVal = new Node(1000);
			for (Node child : node.getChildren()) {
				Node value = miniMax(child, depth + 1, true, alpha, beta);
				bestVal = Node.min(bestVal, value);
				beta = Node.min(beta, bestVal);
				if (beta.compareTo(alpha) <= 0)
					break;
			}
			return bestVal;
		}
	}

	private static boolean isLeaf(Node node) {
		return node.getChildren().isEmpty();
	}

	private static int startGame() {
		char turn;
		do {
			System.out.println("Do you want to play as X or O ?");
			turn = in.next().charAt(0);
			if (turn == 'x' || turn == 'x') {
				engineTurn = O;
				playerTurn = X;
				print(root);
				buildTree(root);
				playerTurn();
				return X;
			}
			if (turn == 'o' || turn == 'O') {
				engineTurn = X;
				playerTurn = O;
			}
		} while (!(turn == 'o' || turn == 'O' || turn == 'X' || turn == 'x'));
		return 0;
	}

	private static void print(Node node) {
		int state[] = node.getElement();
		for (int i = 1; i <= N * N; i++) {
			System.out.print((state[i - 1] == 0 ? "-" : state[i - 1] == 1 ? "X" : "O") + " ");
			if (i % N == 0)
				System.out.println();
		}
		System.out.println("---------------");
	}

	private static void playerTurn() {
		System.out.println("Inter a number to replace with " + ((playerTurn == X) ? "X" : "O"));
		int i = in.nextInt();
		if (root.getElement()[i - 1] == 1 || root.getElement()[i - 1] == 2) {
			playerTurn();
			return;
		}
		root.getElement()[i - 1] = playerTurn;
		setRoot(root.getChild());
		print(root);
	}

	private static int getDepthOf(Node node) {
		if (node == null) {
			return -1;
		}
		return 1 + getDepthOf(node.getParent());
	}

	private static void buildTree(Node root) {
		if (isWin(root, X) || isWin(root, O) || isDraw(root))
			return;
		for (byte index = 0; index < N * N; index++) {
			if (canAddChild(root, index)) {
				Node child = createChild(root, index);
				root.addChild(child);
				buildTree(child);
			}
		}
	}

	private static int flipTurn(Node node) {
		return (node.getTurn() == X) ? O : X;
	}

	private static Node createChild(Node root, int i) {
		int[] element = Arrays.copyOf(root.getElement(), N * N);
		element[i] = root.getTurn();
		return new Node(element, flipTurn(root));
	}

	private static boolean canAddChild(Node node, int index) {
		if (isDraw(node)) {
			return false;
		}
		if (isWin(node, X)) {
			return false;
		}
		if (isWin(node, O)) {
			return false;
		}
		if (node.getElement()[index] == 0)
			return true;
		return false;
	}

	private static boolean isWin(Node node, int type) {
		int[] element = node.getElement();
		return isEquals(element, 0, 1, 2, type) || isEquals(element, 3, 4, 5, type) || isEquals(element, 6, 7, 8, type)
				|| isEquals(element, 0, 3, 6, type) || isEquals(element, 1, 4, 7, type)
				|| isEquals(element, 2, 5, 8, type) || isEquals(element, 0, 4, 8, type)
				|| isEquals(element, 2, 4, 6, type);
	}

	private static boolean isEquals(int[] element, int i, int j, int k, int type) {
		return element[i] == element[j] && element[j] == element[k] && element[k] == type;
	}

	private static boolean isDraw(Node node) { // If draw state
		if (isWin(node, X) || isWin(node, O))
			return false;
		int element[] = node.getElement();
		for (int i = 0; i < N * N; i++) {
			if (element[i] != X && element[i] != O)
				return false;
		}
		return true;
	}

	private static void setRoot(Node root) {
		Main.root = root;
		root.setParent(null);
	}

}