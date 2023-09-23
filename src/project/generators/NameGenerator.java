package src.project.generators;

import java.util.HashSet;
import java.util.Set;

public class NameGenerator {
    private final static java.util.Random rand = new java.util.Random();
    private static Set<String> identifiers = new HashSet<String>();
    public  final static String generateName(){
        final String lexicon="AABGDĐŽ ZIJKLMNOPR STĆUFHCČŠ";
        return generate(lexicon);
    }
    public  final static String generateReg(){
        final String lexicon="ABQXYZ-12345674-890";
        return generate(lexicon);
    }
    public final  static String generate(String lexicon) {
        String res="";
        StringBuilder builder = new StringBuilder();
        while(res.length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            res=builder.toString();
            if(identifiers.contains(res)) {
                builder = new StringBuilder();res="";
            }
        }
        identifiers.add(res);
        return builder.toString();
    }
}
