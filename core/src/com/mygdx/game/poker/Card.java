package com.mygdx.game.poker;


/**
 * This is a card. Keep it simple. Offer suit and number comparison only.
 * @author george
 *
 */

public class Card implements Comparable {
	public static final String[] suits = {"H","D","S","C"}; // Hearts, diamonds, spades, clubs
	public static final int[] numbers = {2,3,4,5,6,7,8,9,10,11,12,13,14}; //11(J),12(Q),13(K),14(A)
	
	public final String suit;
	public final int number; 
	public final String stringVal;
	
	public Card (String suit, int number) {
		this.suit = suit;
		this.number = number;
		stringVal = suit + number;
	}
	
	public boolean isSameSuit(Card card) {
		return card.suit.equals(suit);
	}
	
	@Override
	public String toString() {
		return stringVal;
	}

	@Override
	public int compareTo(Object o) {
		Card c = (Card) o; 
		
		if (number > c.number) {
			return 1;
		} else if (number == c.number) {
			return 0;
		} else {
			return -1;
		}
	}
}
