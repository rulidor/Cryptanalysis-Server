package server;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Decoder {

    private Map<String, Integer> frequencyCounter = new HashMap<>();
    private String[] lettersFrequency = {"E", "T", "A", "O", "I", "N", "S", "R", "H", "D", "L", "U", "C",
            "M", "F", "Y", "W", "G", "P", "B", "V", "K", "X", "Q", "J", "Z"}; //frequent letters will appear in decreased order

    private Map<String, String> predictedMap = new HashMap<>();


    public Map<String, String> makePrediction(String inputTxt){

        String[] txtTokens = inputTxt.split("\\s+");

        int counter;
        for(int i = 0; i< txtTokens.length; i++){
            if(!frequencyCounter.keySet().contains(txtTokens[i])){
                frequencyCounter.put(txtTokens[i], 0);
            }

            counter = frequencyCounter.get(txtTokens[i]);
            frequencyCounter.put(txtTokens[i], counter +1 );

        }

        if(frequencyCounter.keySet().contains(""))
            frequencyCounter.remove("");

        System.out.println("debug: number of keys is: " + frequencyCounter.keySet().size());
        System.out.println("keyset is: "+ frequencyCounter.keySet());

        System.out.println("Predicted:");

        Map<String, Integer> sorted = frequencyCounter.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        System.out.println("sorted is: "+ sorted);

        int index = 0;
        for(String k : sorted.keySet()){
            predictedMap.put(k, lettersFrequency[index]);
            index ++ ;
        }

        return predictedMap; // TODO: change this


    }

    public String[] getLettersFrequency() {
        return lettersFrequency;
    }
}
