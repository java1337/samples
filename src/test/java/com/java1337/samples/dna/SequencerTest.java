package com.java1337.samples.dna;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequencerTest {

    protected Map<BigInteger, List<String>> poolCache;
    protected Map<String, Integer> FOUR_CHAR_LOOKUP_MAP;

    public static final String SAMPLE = "ACTGTGAAGCTA";

    public static final String[] POOL = {
        "GAGATTCAGACT",
        "ACTGTGAAGCTA",
        "TTTTTTTTTTTT",
        "ACTGTGCAGCTA",
        "ACTGATAACTGA"
    };


    public List<String> checkSample(String sample) {
        if (poolCache == null || poolCache.isEmpty()) {
            populateCache(POOL);
        }
        BigInteger sampleSequenceHash = calculateSequenceHash(sample);
        List<String> returnMe = poolCache.get(sampleSequenceHash);
        if (returnMe == null) {
           returnMe = Collections.emptyList();
        }
        return returnMe;
    }

    public void populateCache(String[] pool) {
        if (poolCache == null) {
            poolCache = new HashMap<BigInteger, List<String>>();
        }
        for (String sequence : pool) {
            BigInteger sequenceHash = calculateSequenceHash(sequence);
            List<String> matchingSequences = poolCache.get(sequenceHash);
            if (matchingSequences == null) {
                matchingSequences = new ArrayList<String>();
                poolCache.put(sequenceHash, matchingSequences);
            }
            System.out.println("Adding matching sequence " + sequenceHash + " for sequence " + sequence);
            matchingSequences.add(sequence);
        }
    }

    public BigInteger calculateSequenceHash(final String sequence) {
        if (FOUR_CHAR_LOOKUP_MAP == null) {
            initLookupMap();
        }
        BigInteger returnMe = BigInteger.valueOf(0);
        String toBeAdded = sequence;
        toBeAdded = toBeAdded.replace('A', 'C');
        toBeAdded = toBeAdded.replace('G', 'T');
        
        while (toBeAdded.length() > 0) {
            returnMe = returnMe.multiply(BigInteger.valueOf(256));
            returnMe = returnMe.add(BigInteger.valueOf(FOUR_CHAR_LOOKUP_MAP.get(toBeAdded.substring(0, 4))));
            toBeAdded = toBeAdded.substring(4);
        }
        return returnMe;
    }

    private void initLookupMap() {
        FOUR_CHAR_LOOKUP_MAP = new HashMap<String, Integer>();
        for (int i = 0; i < 256; i++) {
            int remainder = i;

            int char1val = remainder % 4;
            remainder = remainder / 4;

            int char2val = remainder % 4;
            remainder = remainder / 4;

            int char3val = remainder % 4;
            remainder = remainder / 4;

            int char4val = remainder % 4;
            remainder = remainder / 4;

            char char1 = getCharForInt(char1val);
            char char2 = getCharForInt(char2val);
            char char3 = getCharForInt(char3val);
            char char4 = getCharForInt(char4val);

            String sampleSequence = new String(new char[] { char1, char2, char3, char4 });
//            System.out.println("Sequence # " + i + " is " + sampleSequence);
            FOUR_CHAR_LOOKUP_MAP.put(sampleSequence, i);
        }
    }

    private char getCharForInt(int charVal) {
       switch (charVal) {
           case 0 : return 'A';
           case 1 : return 'C';
           case 2 : return 'G';
           case 3 : return 'T';
       }
       throw new RuntimeException("CharVal should be between 0 and 3");
    }

    @SuppressWarnings("unused")
    private int getIntForChar(char charVal) {
        switch (charVal) {
            case 'A' : return 0;
            case 'C' : return 1;
            case 'G' : return 2;
            case 'T' : return 3;
        }
        throw new RuntimeException("charVal should be 'A' 'C' 'G' or 'T'");
    }

    public static void main(String[] args) {
        SequencerTest test = new SequencerTest();
        List<String> matches = test.checkSample(SAMPLE);
        if (matches.isEmpty()) {
            System.out.println("No matches found");
        } else {
            System.out.println("Found the following matches");
            int counter = 0;
            for (String match : matches) {
                System.out.println("Match " + ++counter + " is " + match);
            }
        }
    }
}