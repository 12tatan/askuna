
public class ToLatin {
    
    private String _latinChar;  // ref
    
    // angka
    private boolean matchNumber(char currChar, String latinChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1bb0':  latinChar = "0";    break;
            case '\u1bb1':  latinChar = "1";    break;
            case '\u1bb2':  latinChar = "2";    break;
            case '\u1bb3':  latinChar = "3";    break;
            case '\u1bb4':  latinChar = "4";    break;
            case '\u1bb5':  latinChar = "5";    break;
            case '\u1bb6':  latinChar = "6";    break;
            case '\u1bb7':  latinChar = "7";    break;
            case '\u1bb8':  latinChar = "8";    break;
            case '\u1bb9':  latinChar = "9";    break;
            default:
                    ret = false;
                    break;
        }

        _latinChar = latinChar;

        return ret;
    }

    // konsonan sisip
    private boolean matchSubCons(char currChar, String latinChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1ba1':  latinChar = "y";    break;
            case '\u1ba2':  latinChar = "r";    break;
            case '\u1ba3':  latinChar = "l";    break;
            default:
                ret = false;
                break;
        }

        _latinChar = latinChar;

        return ret;
    }

    // panungtung
    private boolean matchEnding(char currChar, String latinChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1b80':  latinChar = "ng";   break;
            case '\u1b81':  latinChar = "r";    break;
            case '\u1b82':  latinChar = "h";    break;
            default:
                    ret = false;
                    break;
        }

        _latinChar = latinChar;

        return ret;
    }

    // sora vokal
    private boolean matchVocalSign(char currChar, String latinChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1ba4':  latinChar = "i";    break;
            case '\u1ba5':  latinChar = "u";    break;
            case '\u1ba6':  latinChar = "é";    break;
            case '\u1ba7':  latinChar = "o";    break;
            case '\u1ba8':  latinChar = "e";    break;
            case '\u1ba9':  latinChar = "eu";   break;
            default:
                    ret = false;
                    break;
        }

        _latinChar = latinChar;

        return ret;
    }

    // vokal mandiri
    private boolean matchVocal(char currChar, String latinChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1b83':  latinChar = "a";    break;
            case '\u1b84':  latinChar = "i";    break;
            case '\u1b85':  latinChar = "u";    break;
            case '\u1b86':  latinChar = "é";    break;
            case '\u1b87':  latinChar = "o";    break;
            case '\u1b88':  latinChar = "e";    break;
            case '\u1b89':  latinChar = "eu";   break;
            default:
                    latinChar = ""; ret = false;
                    break;
        }

        _latinChar = latinChar;

        return ret;
    }

    // konsonan ngalagena
    private boolean matchConsonant(char currChar, String latinChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1b8a':  latinChar = "k";    break;
            case '\u1b8b':  latinChar = "q";    break;
            case '\u1b8c':  latinChar = "g";    break;
            case '\u1b8d':  latinChar = "ng";   break;
            case '\u1b8e':  latinChar = "c";    break;
            case '\u1b8f':  latinChar = "j";    break;
            case '\u1b90':  latinChar = "z";    break;
            case '\u1b91':  latinChar = "ny";   break;
            case '\u1b92':  latinChar = "t";    break;
            case '\u1b93':  latinChar = "d";    break;
            case '\u1b94':  latinChar = "n";    break;
            case '\u1b95':  latinChar = "p";    break;
            case '\u1b96':  latinChar = "f";    break;
            case '\u1b97':  latinChar = "v";    break;
            case '\u1b98':  latinChar = "b";    break;
            case '\u1b99':  latinChar = "m";    break;
            case '\u1b9a':  latinChar = "y";    break;
            case '\u1b9b':  latinChar = "r";    break;
            case '\u1b9c':  latinChar = "l";    break;
            case '\u1b9d':  latinChar = "w";    break;
            case '\u1b9e':  latinChar = "s";    break;
            case '\u1b9f':  latinChar = "x";    break;
            case '\u1ba0':  latinChar = "h";    break;
            case '\u1bae':  latinChar = "kh";    break; // <== tambah kha
            case '\u1baf':  latinChar = "sy";    break; // <== tambah sya
            default:
                    latinChar = ""; ret = false;
                    break;
        }

        _latinChar = latinChar;

        return ret;
    }

    public String convert(String iInputStr){
        int    inputLen;
        String latinStr = "";
        char ch1, ch2, ch3, ch4;
        
        iInputStr = iInputStr.toLowerCase();
        inputLen  = iInputStr.length();

        while ((inputLen > 0)) {
            int matches = 1, numbers;
            boolean on;
            String latinCh1 = "";
            String latinCh2 = "";
            String latinCh3 = "";
            String latinCh4 = "";

            ch1 = iInputStr.charAt(0);

            // syllable begins with consonant
            if ((matchConsonant(ch1, latinCh1))) {  // V
                latinCh1 = _latinChar;

                //ch2 = iInputStr.charAt(1);
                ch2 = (iInputStr.length()>1) ? iInputStr.charAt(1) : ' ';

                if (ch2 == '\u1baa') { // pameh
                    latinCh2 = "";
                    matches  = 2;

                } else if ((matchSubCons(ch2, latinCh2))) {
                    latinCh2 = _latinChar;

                    //ch3 = iInputStr.charAt(2);
                    ch3 = (iInputStr.length()>2) ? iInputStr.charAt(2) : ' ';

                    if ((matchVocalSign(ch3, latinCh3))) {
                        latinCh3 = _latinChar;

                        //ch4 = iInputStr.charAt(3);
                        ch4 = (iInputStr.length()>3) ? iInputStr.charAt(3) : ' ';

                        if ( (matchEnding(ch4, latinCh4)) ) {
                            latinCh4 = _latinChar;
                            matches  = 4;
                        } else
                            matches  = 3;

                    } else {
                        matches  = 2;
                        latinCh3 = "a";
                    }

                } else if ((matchVocalSign(ch2, latinCh2))) {
                    latinCh2 = _latinChar;

                    //ch3 = iInputStr.charAt(2);
                    ch3 = (iInputStr.length()>2) ? iInputStr.charAt(2) : ' ';

                    if ((matchEnding(ch3, latinCh3))) {
                        latinCh3 = _latinChar;
                        matches  = 3;
                    } else
                        matches = 2;

                } else {
                    latinCh2 = "a";

                    if ((matchEnding(ch2, latinCh3))) {
                        latinCh3 = _latinChar;
                        matches  = 2;
                    }

                }

            // syllable begins with vowels
            } else if ((matchVocal(ch1, latinCh1))) {
                latinCh1 = _latinChar;

                //ch2 = iInputStr.charAt(1);
                ch2 = (iInputStr.length()>1) ? iInputStr.charAt(1) : ' ';
                
                if ((matchEnding(ch2, latinCh2))) {
                    latinCh2 = _latinChar;
                    matches = 2;
                }

            // number begins with pipe
            } else if (ch1 == '|') {
                latinCh1 = String.valueOf(ch1);

                on = true;
                numbers = 0;

                // try matching numbers
                do {
                    //ch2 = iInputStr.charAt(1+numbers);
                    ch2 = (iInputStr.length()>1+numbers) ? iInputStr.charAt(1+numbers) : ' ';

                    if ((matchNumber(ch2, latinCh2))) {
                        latinCh2  = _latinChar;
                        numbers  += 1;
                        latinCh1 += latinCh2;
                    } else
                        on = false;

                    latinCh2 = "";

                } while (on);

                matches += numbers;

                if ((numbers > 0)) {
                    latinCh1 = latinCh1.substring(1);

                    ch2 = iInputStr.charAt(1+numbers);

                    if (ch2 == '|') matches += 1;
                }

            // bad formatted number, but ok we accept.
            } else if ((matchNumber(ch1, latinCh1))) {

                latinCh1 = _latinChar;

                on = true;
                numbers = 0;

                // try matching other numbers
                do {
                    //ch2 = iInputStr.charAt(1+numbers);
                    ch2 = (iInputStr.length()>1+numbers) ? iInputStr.charAt(1+numbers) : ' ';

                    if ((matchNumber(ch2, latinCh2))) {
                        latinCh2 = _latinChar;
                        numbers  += 1;
                        latinCh1 += latinCh2;
                    } else
                        on = false;

                    latinCh2 = "";

                } while (on);

                matches += numbers;

            // others
            } else {
                latinCh1 = String.valueOf(ch1);
            }

            inputLen -= matches;
            latinStr += latinCh1 + latinCh2 + latinCh3 + latinCh4;

            if ((inputLen > 0))
                iInputStr = iInputStr.substring(iInputStr.length()- inputLen);


        }   // while

        return latinStr;
    }
}
