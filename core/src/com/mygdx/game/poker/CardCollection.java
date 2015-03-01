package com.mygdx.game.poker;

import com.badlogic.gdx.utils.Array;

public class CardCollection {
	final Array<Card> cards;
	
	int score;
	
	// Scoring info
	boolean isFlush, isStraight, isFourKind, isThreeKind, isPair, isTwoPair;
	
	// Useful info 
	int[] numDiff;
	Card tmp;
	int threeKindPos, pairPos, twoPairPos;
	
	public CardCollection(Deck deck, int n) {
		cards = new Array<Card>();
		for (int i = 0; i < n; i ++) {
			cards.add(deck.drawCard());
		}
		cards.sort();
	}
	
	public CardCollection(Array<Card> cards) {
		this.cards = cards;
		cards.sort();
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Card c : cards) {
			s += c + " ";
		}
		return s;
	}

	/**
	 * Flush			// 140-153
	 * Straight			// 120-133
	 * Four of a kind	// 100-113
	 * Full house		// 80-93
	 * Three of a kind 	// 60-73
	 * Two pair			// 40-53
	 * Pair 			// 20-33
	 * high card	 	// 0-13
	 */
	public  void calcScore() {
		

		//TODO: isTwoPair fails IRL as doesn't calculate score for both items in pair.
		//TODO: full house fails for two decks. Three of kind fails for two decks
		//TODO: pair fails for matching and high card.
		
		calcNumDiff();		
		isFlush();
		isStraight();
		if (isStraight && isFlush) {
			score = 140 + cards.get(0).number;
			return;
		}
		
		if (isFlush) {
			score = 120 + cards.get(0).number;
			return;
		}
		
		if (isStraight) {
			score = 100 + cards.get(0).number;
			return;
		}
		
		isFourOfAKind();
		
		if (isFourKind) {
			return;
		}
		
		isThreeKind();
		isPair();
		
		if (isThreeKind && isPair) {
			score = 80 + cards.get(threeKindPos).number;
			return;
		}
		
		if (isThreeKind) {
			score = 60 + cards.get(threeKindPos).number;
			return;
		}
		
		if (isPair) {
			isTwoPair();
		}
		
		if (isTwoPair) {
			score = 40 + cards.get(twoPairPos).number;
			return;
		}
		
		if (isPair) {
			score = 20 + cards.get(pairPos).number;
			return;
		}
		
		score = cards.get(cards.size-1).number;
		
	}

	private void isTwoPair() {
		for (int i = pairPos+1; i <4; i++) {
			if (numDiff[i] == 0) {
				isTwoPair = true;
				twoPairPos = i;
				return;
			}
		}
		isTwoPair = false;
	}

	private void isPair() {
		for (int i = 0; i <4; i++) {
			if (numDiff[i] == 0) {
				if (isThreeKind) {
					if (i != threeKindPos && i != threeKindPos+1) {
						isPair= true;
						pairPos = i;
						return;
					}
				} else {
					isPair= true;
					pairPos = i;
					return;
				}
			}
		}
		isPair = false;
	}
	
	
	private void isThreeKind() {
		for (int i = 0; i <3; i++) {
			if ((numDiff[i]+numDiff[i+1]+numDiff[i+2]) == 0) {
				isThreeKind = true;
				threeKindPos = i;
				return;
			}
		}
		isThreeKind = false;
	}

	private void isFourOfAKind() {
		System.out.println(numDiff.length);
		if ((numDiff[0]+numDiff[1]+numDiff[2]) == 0) {
			isFourKind = true;
			score = 100 + cards.get(cards.size-1).number;
			return;
		} else if ((numDiff[1]+numDiff[2]+numDiff[3]) == 0) {
			isFourKind = true;
			score = 100 + cards.get(0).number;
			return;
		} 
		
		System.out.println("not4k");
		isFourKind = false;
		
	}

	private void calcNumDiff() {
		numDiff = new int[cards.size-1];
		for (int i = 0; i < cards.size -1; i ++) {
			numDiff[i] = cards.get(i+1).number - cards.get(i).number;
		}
	}

	private void isFlush() {
		tmp = cards.get(0);
		for (int i = 1; i < cards.size; i++) {
			if (!tmp.isSameSuit(cards.get(i))) {
				isFlush = false;
				return;
			}
		}
		isFlush = true;
	}
	
	public void isStraight() {
		for (int i = 0; i < cards.size-1; i++) {
			if (numDiff[i] != 1) {
				isStraight = false;
				return;
			}
		}
		isStraight = true;
	}
	
}
