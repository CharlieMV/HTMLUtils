/**
 *	Utilities for handling HTML
 *
 *	@author	Charles Chang
 *	@since	October 31, 2023
 */
public class HTMLUtilities {

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString (String str) {
		// make the size of the array large to start
		String[] result = new String[10000];
		int tokenNum = 0;
		String tempToken = "";
		boolean isComplete = false;
		// scan all characters while scanning next for spaces
		for (int i = 0; i < str.length(); i++) {
			tempToken += str.charAt(i);
			// is the token a tag
			if (checkTag(tempToken)) isComplete = true;
			// only run the rest if it doesnt start with <
			if (tempToken.charAt(0) != '<') {
				// check if space is next
				if (i < str.length() - 1 && str.charAt(i + 1) == ' ') 
					isComplete = true;
				/* check if hyphen is next, but not if letter before 
				 * and after or if before an int*/
				if (i < str.length() - 1 && str.charAt(i + 1) == '-'
							&& !Character.isLetterOrDigit(str.charAt(i + 1)) 
							&& !Character.isLetter(str.charAt(i - 1)))
					isComplete = true;
				// check if . is between 2 numbers
				if (i < str.length() - 1 && str.charAt(i + 1) == '.' 
							&& (Character.isDigit(str.charAt(i + 1)) 
							|| Character.isDigit(str.charAt(i - 1))))
					isComplete = false;
				// check if next char is non-hyphen punctuation
				if (i < str.length() - 1 && isPunctuation(str.charAt(i + 1)))
					isComplete = true;
				// check if temp is a single punctuation
				if (tempToken.length() == 1 && isPunctuation(tempToken.charAt(0)))
					isComplete = true;
			}
			// check if it is the last char
			if (i == str.length() - 1) isComplete = true;
			// if the token is valid, add it to result and clear temp
			if (isComplete) {
				result[tokenNum] = tempToken;
				tempToken = "";
				tokenNum ++;
				isComplete = false;
			}
		}
		// return the correctly sized array
		return result;
	}
	
	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens (String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (tokens[a] != null) {
				if (a % 5 == 0) System.out.print("\n  ");
				System.out.print("[token " + a + "]: " + tokens[a] + " ");
			}
		}
		System.out.println();
	}
	
	/**
	 * Does the String start and end with < and > respectively
	 * 
	 * @param	token	token to check
	 * @return			boolean true for if token is tag, false if not
	 */
	private boolean checkTag (String token) {
		if (token.charAt(0) == '<' && token.charAt(token.length() - 1) == '>') {
			return true;
		}
		return false;
	}
	/**
	 * Checks to see if char is punctuation
	 * - and . treated differently, so not included here
	 * 
	 * @param 	c	character to check
	 * @return		boolean true if punctuation
	 */
	private boolean isPunctuation (char c) {
		switch (c) {
			case ',': case ';': case ':': case '(': case ')':
			case '?': case '!': case '=': case '&': case '~': case '+':
			return true; 
			default: return false;
		}
	}
}
