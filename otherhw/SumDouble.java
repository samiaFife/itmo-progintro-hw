public class SumDouble {
    public static void main(String[] args){
	    double sum = 0;
		if (args.length != 0) {
			for(int i = 0; i < args.length; i++){
				String currentString = args[i];
				int left = 0, right = 0;
				for(int j = 0; j < currentString.length(); j++) {
					if (!Character.isWhitespace(currentString.charAt(j))) {
						if (j == 0 || Character.isWhitespace(currentString.charAt(j - 1))) {
							left = j;
						} 
						if (j == currentString.length() - 1 || Character.isWhitespace(currentString.charAt(j + 1))) {
							right = j + 1;
							sum += Double.parseDouble(currentString.substring(left, right));
							left = 0;
							right = 0;
						}
					} 
				}
			}
		}
		System.out.println(sum);
	}
}