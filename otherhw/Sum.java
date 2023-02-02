public class Sum {
    public static void main(String[] args){
	    int sum = 0;
		if (args.length != 0) {
			for(int i = 0; i < args.length; i++){
				String currStr = args[i];
				int l = 0, r = 0;
				for(int j = 0; j < currStr.length(); j++) {
					if (!Character.isWhitespace(currStr.charAt(j))) {
						if (j == 0 || Character.isWhitespace(currStr.charAt(j - 1))) {
							l = j;
						}
						if (j == currStr.length() - 1 || Character.isWhitespace(currStr.charAt(j + 1))) {
							r = j + 1;
						}
					}
					if (r != 0) {
						sum += Integer.parseInt(currStr.substring(l, r));
						l = 0;
						r = 0;
					}
					
				}
			}
		}
		System.out.println(sum);
	}
}